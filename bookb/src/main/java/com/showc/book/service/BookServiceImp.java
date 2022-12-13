package com.showc.book.service;

import com.showc.book.exceptions.RequestException;
import com.showc.book.model.Book;
import com.showc.book.model.Store;
import com.showc.book.repository.BookRepository;
import com.showc.book.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookServiceImp implements BookService{
    private final BookRepository bookRepository;
    private final StoreRepository storeRepository;

    public BookServiceImp(BookRepository bookRepository, StoreRepository storeRepository) {
        this.bookRepository = bookRepository;
        this.storeRepository = storeRepository;
    }

    @Override
    public Book addBook(Book book) {
        Optional<Book> checkbook =bookRepository.findBookByIsbn(book.getIsbn());
        if(checkbook.isPresent()){
            throw new RequestException("Book with this ISBN already exists in the database.");
        }
        if(!book.getIsbn().matches("^[0-9]*$")){

            throw new RequestException("Bad ISBN format.");
        }
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAllBook() {
        return bookRepository.findAll();
    }

    @Override
    public Book findBook(String isbn) {
        Optional<Book> isBookExists = bookRepository.findBookByIsbn(isbn);
        if(!isBookExists.isPresent()){
            throw new RequestException("This book does not exists in our database.");
        }
        return isBookExists.get();
    }

    @Override
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(String isbn) {
        Optional<Book> isBookExists = bookRepository.findBookByIsbn(isbn);
        if(!isBookExists.isPresent()){
            throw new RequestException("This book does not exists in our database.");
        }
        bookRepository.deleteByIsbn(isbn);
    }

    @Override
    public void assignStore(String isbn, String storeName) {
        Optional<Book> book = bookRepository.findBookByIsbn(isbn);
        Store store = storeRepository.findByName(storeName);
        if(!book.isPresent()){
            throw new RequestException("Cant assign a store, book doesn't exists.");
        }
        if(book.get().getStores().contains(store)){
            throw new RequestException("Already assigned.");
        }
        book.get().getStores().add(store);
    }
}
