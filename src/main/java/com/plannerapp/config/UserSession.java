package com.plannerapp.config;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UserSession {
    private Long userId;
    private String username;

    public void login(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public void logout() {
        this.userId = null;
        this.username = null;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public boolean isLoggedIn() {
        return userId != null;
    }
}
