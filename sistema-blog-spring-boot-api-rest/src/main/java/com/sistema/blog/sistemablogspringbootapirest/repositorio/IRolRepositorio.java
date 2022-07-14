package com.sistema.blog.sistemablogspringbootapirest.repositorio;

import com.sistema.blog.sistemablogspringbootapirest.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRolRepositorio extends JpaRepository<Rol, Long> {

    public Optional<Rol> findByNombre(String nombre);
}
