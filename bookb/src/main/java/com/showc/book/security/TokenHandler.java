package com.showc.book.security;

import com.showc.book.exceptions.RequestException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
public class TokenHandler {
    private final HashMap<String, Date> logs = new HashMap<>();

    public void saveToken(String token, Date date) {
        logs.put(token, date);
    }

    //TODO: expiration date.
    public void isTokenExists(String token) {
        if(logs.get(token) == null){
            throw new RequestException("You need a valid token to access this endpoint.");
        }
        System.out.println("Admin logged in. Token was created at: " + logs.get(token) + ".");
    }
}
