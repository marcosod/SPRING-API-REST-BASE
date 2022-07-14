package com.sistema.blog.sistemablogspringbootapirest.servicio;

import com.sistema.blog.sistemablogspringbootapirest.dto.PublicacionDTO;
import com.sistema.blog.sistemablogspringbootapirest.dto.PublicacionRespuesta;
import com.sistema.blog.sistemablogspringbootapirest.entity.Publicacion;

import java.util.List;

public interface IPublicacionServicio {

    public PublicacionDTO crear(PublicacionDTO publicacionDTO);
    public PublicacionRespuesta listarTodo(int numeroDePagina, int cantidadDePagina);
    public PublicacionDTO obtenerPorId(Long id);
    public PublicacionDTO actualizar(PublicacionDTO publicacion, long id);
    public void eliminar(long id);
}
