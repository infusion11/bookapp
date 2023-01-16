package com.showc.book.security;

import com.showc.book.exceptions.RequestException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

@Component
public class TokenHandler {
    private final HashMap<String, LocalDateTime> logs = new HashMap<>();

    public void saveToken(String token, LocalDateTime date) {
        logs.put(token, date);
    }

    //TODO: expiration date.
    public void isTokenExists(String token) {
        if(logs.get(token) == null){
            throw new RequestException("You need a valid token to access this endpoint.");
        }
        isTokenExpired(token);
        System.out.println("Admin logged in. Token was created at: " + logs.get(token) + ".");
    }

    public void isTokenExpired(String token) {
        LocalDateTime generatedAt = logs.get(token);
        LocalDateTime now = LocalDateTime.now();
        if(generatedAt.plusDays(7).isBefore(now)) {
            logs.remove(token);
            throw new RequestException("Your token is expired. Please login again.");
        }
    }
}
