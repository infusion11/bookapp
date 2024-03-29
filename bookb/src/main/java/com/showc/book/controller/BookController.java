package com.showc.book.controller;

import com.showc.book.exceptions.RequestException;
import com.showc.book.helperclass.AssignStore;
import com.showc.book.helperclass.MessageResponse;
import com.showc.book.helperclass.TokenResponse;
import com.showc.book.model.Book;
import com.showc.book.security.TokenHandler;
import com.showc.book.service.BookServiceImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/bookapp/v1")
public class BookController {
    private final BookServiceImp bookServiceImp;
    private final AssignStore assignStore;
    private final TokenHandler tokenHandler;
    @Value("${imagefolder}")
    private String imagefolder;

    public BookController(BookServiceImp bookServiceImp, AssignStore assignStore, TokenHandler tokenHandler) {
        this.bookServiceImp = bookServiceImp;
        this.assignStore = assignStore;
        this.tokenHandler = tokenHandler;
    }

    @GetMapping("/login")
    public ResponseEntity<TokenResponse> login(Principal principal){
        TokenResponse tokenResponse = new TokenResponse();
        String token = new String(Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes()));
        tokenResponse.setAdmintoken(token);
        tokenResponse.setGeneratedAt(LocalDateTime.now());
        tokenHandler.saveToken(tokenResponse.getAdmintoken(), principal.getName(), tokenResponse.getGeneratedAt());
        return new ResponseEntity<>(tokenResponse,HttpStatus.OK);
    }

    @GetMapping("/getallbook")
    public ResponseEntity<List<Book>> getAllBook(){
        List<Book> books = bookServiceImp.findAllBook();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
    @GetMapping("/getbook/{isbn}")
    public ResponseEntity<Book> getABookByIsbn(@PathVariable("isbn") String isbn){
        Book book = bookServiceImp.findBook(isbn);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
    @PostMapping("/addbook")
    public ResponseEntity<Book> addBook(@RequestHeader("admintoken") String token,
                                        @Valid @RequestBody Book book){
        Book newbook = bookServiceImp.addBook(book);
        return new ResponseEntity<>(newbook, HttpStatus.CREATED);
    }

    @PutMapping("/updatebook")
    public ResponseEntity<Book> updateBook(@RequestHeader("admintoken") String token,
                                           @Valid @RequestBody Book book){
        Book updated = bookServiceImp.updateBook(book);
        return new ResponseEntity<>(updated, HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{isbn}")
    public ResponseEntity<MessageResponse> deleteABookByIsbn(@RequestHeader("admintoken") String token,
                                                             @PathVariable("isbn") String isbn){
        String img = bookServiceImp.findBook(isbn).getImage();
        bookServiceImp.deleteBook(isbn);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage(String.format("A book with isbn: %s is successfully deleted.",isbn));
        File imgToDel = new File(imagefolder+img);
        if(imgToDel.isFile()){
            imgToDel.delete();
        }
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }
    @PostMapping("/assignstore")
    public ResponseEntity<MessageResponse> assignStore(@RequestHeader("admintoken") String token,
                                                       @RequestBody AssignStore form){
        bookServiceImp.assignStore(form.getBookISBN(), form.getStoreName());
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("A store("+form.getStoreName()+") to a book("+form.getBookISBN()+") successfully added.");
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }
    @PostMapping("/uploadimage")
    public ResponseEntity<MessageResponse> uploadImage(@RequestHeader("admintoken") String token,
                                                       @RequestParam("file") MultipartFile multipartFile){
        if(!multipartFile.isEmpty()){
            try{
                byte[] bytes = multipartFile.getBytes();
                Files.write(Path.of(imagefolder + multipartFile.getOriginalFilename()), bytes);
                MessageResponse messageResponse = new MessageResponse();
                messageResponse.setMessage("Image successfully uploaded.");
                return new ResponseEntity<>(messageResponse, HttpStatus.OK);
            } catch (IOException ioException){
                ioException.printStackTrace();
            }
        }
        throw new RequestException("Uploading an image was not successful");
    }
}
