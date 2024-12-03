package com.spas.dto;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String role;
    private String username;
    
    public AuthResponse(String token, String role, String username) {
        this.token = token;
        this.role = role;
        this.username = username;
    }
}
