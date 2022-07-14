package com.sistema.blog.sistemablogspringbootapirest.controller;

import com.sistema.blog.sistemablogspringbootapirest.dto.PublicacionDTO;
import com.sistema.blog.sistemablogspringbootapirest.dto.PublicacionRespuesta;
import com.sistema.blog.sistemablogspringbootapirest.servicio.IPublicacionServicio;
import com.sistema.blog.sistemablogspringbootapirest.util.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {
    //https://www.youtube.com/watch?v=dJaY43Butm8 41:19
    @Autowired
    private IPublicacionServicio publicacionServicio;

    @PostMapping
    public ResponseEntity<PublicacionDTO> crear(@Valid @RequestBody PublicacionDTO publicacionDTO){
        return new ResponseEntity<>(publicacionServicio.crear(publicacionDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public PublicacionRespuesta listarTodo(
            @RequestParam(value = "pageNro", defaultValue = Constantes.NUMERO_DE_PAGINA_POR_DEFECTO,required = false ) int numeroDePagina,
            @RequestParam(value = "pageSize", defaultValue = Constantes.CANTIDAD_DE_PAGINA_POR_DEFECTO , required = false) int cantidadPagina){
        return publicacionServicio.listarTodo(numeroDePagina,cantidadPagina);
    }

    @GetMapping("/{id}")
    public PublicacionDTO obtenerPorId(@PathVariable long id){

        return publicacionServicio.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicacionDTO> actualizar(@PathVariable Long id,@Valid @RequestBody PublicacionDTO publicacionDTO){
        return new ResponseEntity<>(publicacionServicio.actualizar(publicacionDTO,id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable long id){
        publicacionServicio.eliminar(id);
        return new ResponseEntity<>("Publicación Eliminada con éxito", HttpStatus.OK);

    }
}
