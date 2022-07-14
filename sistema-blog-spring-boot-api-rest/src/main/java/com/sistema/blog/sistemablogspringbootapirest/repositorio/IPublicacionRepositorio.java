package com.sistema.blog.sistemablogspringbootapirest.repositorio;

import com.sistema.blog.sistemablogspringbootapirest.entity.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPublicacionRepositorio extends JpaRepository<Publicacion, Long> {
}
