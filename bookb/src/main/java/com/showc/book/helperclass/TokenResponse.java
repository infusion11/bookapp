package com.showc.book.helperclass;

import java.time.LocalDateTime;
import java.util.Date;

public class TokenResponse {
    private String admintoken;
    private LocalDateTime generatedAt;

    public String getAdmintoken() {
        return admintoken;
    }

    public void setAdmintoken(String admintoken) {
        this.admintoken = admintoken;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }
}
