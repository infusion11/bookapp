package com.showc.book;

import com.showc.book.model.Book;
import com.showc.book.model.Store;
import com.showc.book.repository.StoreRepository;
import com.showc.book.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class BookApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookApplication.class, args);
	}
/*
	@Bean
	CommandLineRunner run(BookService bookService, StoreRepository storeRepository){
		return args -> {
			bookService.addBook(new Book(null, "1234567891011","Error terror",
					"Coder Cole", "This is your worst nightmare.",null, new ArrayList<>()));
			bookService.addBook(new Book(null, "1234567891012","Serious title",
					"Tester Toby", "I should've just paste some Lorem...to be honest.",null, new ArrayList<>()));
			bookService.addBook(new Book(null, "1234567891013","Monday madness",
					"Lonely Larry", "First day of the week. Scary isn't it?",null, new ArrayList<>()));
			bookService.addBook(new Book(null, "1234567891014","Can you pass the salt please?",
					"Hungry Henry", "I am really hungry now.",null, new ArrayList<>()));
			storeRepository.save(new Store(null, "ImaginedBook", "www.imaginedbookstore.com"));
			storeRepository.save(new Store(null, "ThisIsntReal", "www.itsallaliesorry.com"));
		};
	}*/
}
