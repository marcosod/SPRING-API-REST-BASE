package com.sistema.blog.sistemablogspringbootapirest.dto;

import com.sistema.blog.sistemablogspringbootapirest.entity.Comentarios;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Data

public class PublicacionDTO {

    private Long id;

    @NotEmpty
    @Size(min = 2, message = "El titulo de la publicación deberia tener al menos 2 caracteres")
    private String titulo;

    @NotEmpty
    @Size(min = 5, message = "La descripcion de la publicación deberia tener al menos 5 caracteres")
    private String descripcion;
    private String contenido;
    private List<Comentarios> comentarios;
}
