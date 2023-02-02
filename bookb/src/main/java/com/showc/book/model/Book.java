package com.showc.book.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.Collection;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Integer id;
    @Column(unique = true, length = 13)
    @Size(min = 10, message = "This is not an ISBN number")
    @NotEmpty(message = "ISBN field cannot be empty.")
    private String isbn;
    @Column(length = 100)
    @NotEmpty(message = "Title field cannot be empty.")
    private String title;
    @Column(length = 60)
    @NotEmpty(message = "Author field cannot be empty.")
    private String author;
    @Column(length = 200)
    @NotEmpty(message = "About field cannot be empty.")
    private String about;
    @Column
    private String image;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Store> stores;

    public Book() {
    }

    public Book(Integer id, String isbn, String title, String author, String about, String image, Collection<Store> stores) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.about = about;
        this.image = image;
        this.stores = stores;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Collection<Store> getStores() {
        return stores;
    }

    public void setStores(Collection<Store> stores) {
        this.stores = stores;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", about='" + about + '\'' +
                ", image='" + image + '\'' +
                ", stores=" + stores +
                '}';
    }
}
