package com.showc.book.security;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
public class TokenHandler {
    private final HashMap<String, Date> logs = new HashMap<>();

    public void saveToken(String token, Date date) {
        logs.put(token, date);
    }
    public boolean isTokenExists(String token) {
        if(logs.get(token) != null){
            return true;
        }
        return false;
    }
}
