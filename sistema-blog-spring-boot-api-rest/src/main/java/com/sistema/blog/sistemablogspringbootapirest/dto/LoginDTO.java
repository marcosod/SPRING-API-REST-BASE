package com.sistema.blog.sistemablogspringbootapirest.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private String usernameOrEmail;
    private String password;
}
