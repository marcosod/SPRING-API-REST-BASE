package com.sistema.blog.sistemablogspringbootapirest.repositorio;

import com.sistema.blog.sistemablogspringbootapirest.entity.Comentarios;
import com.sistema.blog.sistemablogspringbootapirest.entity.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IComentarioRepositorio extends JpaRepository<Comentarios, Long> {

    public List<Comentarios> findByPublicacionId(long publicacionId);

}
