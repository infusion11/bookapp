package com.showc.book.security;

import com.showc.book.exceptions.RequestException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class TokenHandler {
    private final HashMap<String, HashMap<String, LocalDateTime>> logs = new HashMap<>();

    public void saveToken(String token, String adminName, LocalDateTime date) {
        HashMap<String, LocalDateTime> nameAndDate = new HashMap<>();
        nameAndDate.put(adminName, date);
        logs.put(token, nameAndDate);
        System.out.println(logs);
    }

    public String isTokenExists(String token) {
        if(logs.get(token) == null){
            throw new RequestException("You need a valid token to access this endpoint.");
        }
        isTokenExpired(token);
        System.out.println("Admin db action with token: " + token + ". Token was created at: " + logs.get(token) + ".");
        List<String> str= logs.get(token).keySet().stream().toList();
        return str.get(0);
    }

    public void isTokenExpired(String token) {
        HashMap<String, LocalDateTime> map = logs.get(token);
        List<LocalDateTime> unformattedGenAt = map.values().stream().toList();
        LocalDateTime generatedAt = unformattedGenAt.get(0);
        LocalDateTime now = LocalDateTime.now();
        if(generatedAt.plusDays(7).isBefore(now)) {
            logs.remove(token);
            throw new RequestException("Your token is expired. Please login again.");
        }
    }
}
