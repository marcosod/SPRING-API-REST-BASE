package com.sistema.blog.sistemablogspringbootapirest.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Required;

import javax.validation.constraints.NotEmpty;

@Data
public class UsuarioDTO {
    @NotEmpty
    private String email;
    @NotEmpty
    private String nombre;
    @NotEmpty
    private String password;
    @NotEmpty
    private String username;
}
