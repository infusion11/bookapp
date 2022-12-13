package com.showc.book.helperclass;

import java.util.Date;

public class TokenResponse {
    private String admintoken;
    private Date generatedAt;

    public String getAdmintoken() {
        return admintoken;
    }

    public void setAdmintoken(String admintoken) {
        this.admintoken = admintoken;
    }

    public Date getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(Date generatedAt) {
        this.generatedAt = generatedAt;
    }
}
