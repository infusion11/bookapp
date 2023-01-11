package com.showc.book.service;


import com.showc.book.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BookService {
    Book addBook(Book book);
    List<Book> findAllBook();
    Book findBook(String isbn);
    Book updateBook(Book book);
    void deleteBook(String isbn);
    void assignStore(String isbn, String storeName);
}
