package com.sistema.blog.sistemablogspringbootapirest.dto;

import com.sistema.blog.sistemablogspringbootapirest.entity.Publicacion;
import lombok.Data;

import java.util.List;

@Data
public class PublicacionRespuesta {

    private List<PublicacionDTO> contenido;
    private int numeroPagina;
    private int cantidadPagina;
    private long totalElementos;
    private int totalPaginas;
    private boolean ultima;
}
