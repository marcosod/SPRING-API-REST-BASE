package com.sistema.blog.sistemablogspringbootapirest.dto;

import lombok.Data;

@Data
public class ComentarioDTO {
    private long id;
    private String nombre;
    private String email;
    private String cuerpo;
}
