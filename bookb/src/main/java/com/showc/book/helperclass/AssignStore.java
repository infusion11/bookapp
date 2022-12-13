package com.showc.book.helperclass;

import org.springframework.stereotype.Component;

@Component
public class AssignStore {
    private String bookISBN;
    private String storeName;

    public String getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
