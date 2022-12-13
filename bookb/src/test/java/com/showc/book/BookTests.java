package com.showc.book;

import com.showc.book.model.Book;
import com.showc.book.repository.BookRepository;
import com.showc.book.repository.StoreRepository;
import com.showc.book.service.BookServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookTests {
    @Mock private BookRepository bookRepository;
    @Mock private StoreRepository storeRepository;
    private BookServiceImp bookServiceImp;

    @BeforeEach
    void init(){
        bookServiceImp = new BookServiceImp(bookRepository,storeRepository);
    }

    @Test
    public void testAddBook() {
        Book testBook = new Book(
                null,
                "1234567891055",
                "Test Title",
                "Test Author",
                "Test about",
                null,
                new ArrayList<>());
        bookServiceImp.addBook(testBook);
        Mockito.verify(bookRepository).save(any());
    }

    @Test
    public void testGetAllBook(){
        bookServiceImp.findAllBook();
        verify(bookRepository).findAll();
    }

    @Test
    public void testGetSingleBook(){
        Book testBook = new Book(
                1,
                "1234567891055",
                "Test Title",
                "Test Author",
                "Test about",
                null,
                new ArrayList<>());
        Mockito.when(bookRepository.findBookByIsbn("1234567891055"))
                .thenReturn(Optional.of(testBook));
        bookServiceImp.findBook("1234567891055");
        verify(bookRepository).findBookByIsbn("1234567891055");
    }
    @Test
    public void testDeleteBook(){
        Book testBook = new Book(
                1,
                "1234567891055",
                "Test Title",
                "Test Author",
                "Test about",
                null,
                new ArrayList<>());
        Mockito.when(bookRepository.findBookByIsbn("1234567891055"))
                .thenReturn(Optional.of(testBook));
        bookServiceImp.deleteBook("1234567891055");
        verify(bookRepository).deleteByIsbn(testBook.getIsbn());
    }

    @Test
    public void testUpdateBook() {
        Book testBook = new Book(
                1,
                "1234567891055",
                "Test Title",
                "Test Author",
                "Test about",
                null,
                new ArrayList<>());
        Mockito.when(bookRepository.findBookByIsbn("1234567891055"))
                .thenReturn(Optional.of(testBook));

        Book book = bookServiceImp.findBook("1234567891055");

        book.setAuthor("Updated Author");
        bookServiceImp.updateBook(book);
        verify(bookRepository).save(book);
        assertNotEquals("Test Author", bookRepository.findBookByIsbn("1234567891055").get().getAuthor());
    }
}
