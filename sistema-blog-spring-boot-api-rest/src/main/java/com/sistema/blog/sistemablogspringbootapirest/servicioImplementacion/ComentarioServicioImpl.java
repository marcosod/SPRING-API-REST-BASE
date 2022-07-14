package com.sistema.blog.sistemablogspringbootapirest.servicioImplementacion;

import com.sistema.blog.sistemablogspringbootapirest.dto.ComentarioDTO;
import com.sistema.blog.sistemablogspringbootapirest.entity.Comentarios;
import com.sistema.blog.sistemablogspringbootapirest.entity.Publicacion;
import com.sistema.blog.sistemablogspringbootapirest.exceptions.BlogAPPExceptions;
import com.sistema.blog.sistemablogspringbootapirest.exceptions.ResourceNotFoundException;
import com.sistema.blog.sistemablogspringbootapirest.repositorio.IComentarioRepositorio;
import com.sistema.blog.sistemablogspringbootapirest.repositorio.IPublicacionRepositorio;
import com.sistema.blog.sistemablogspringbootapirest.servicio.IComentarioServicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComentarioServicioImpl implements IComentarioServicio {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IComentarioRepositorio comentarioRepositorio;
    @Autowired
    private IPublicacionRepositorio publicacionRepositorio;

    @Override
    public ComentarioDTO crear(long publicacionId, ComentarioDTO comentarioDTO) {
        Comentarios comentario = mapearEntidad(comentarioDTO);
        Publicacion publicacion = publicacionRepositorio.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));

        comentario.setPublicacion(publicacion);
        Comentarios nuevoComentario = comentarioRepositorio.save(comentario);
        return mapearDTO(nuevoComentario);
    }

    @Override
    public List<ComentarioDTO> obtenerComentariosPorPublicacion(long publicacionId) {
       List<Comentarios> comentarios = comentarioRepositorio.findByPublicacionId(publicacionId);
        return  comentarios.stream().map(comentario -> mapearDTO(comentario)).collect(Collectors.toList());
    }

    @Override
    public ComentarioDTO obtenerComentarioPorId(long publicacionId, long comentarioId) {
        Publicacion publicacion = publicacionRepositorio.findById(publicacionId).
                orElseThrow(() -> new ResourceNotFoundException("publicacion","id", publicacionId));

        Comentarios comentarios = comentarioRepositorio.findById(comentarioId).
                orElseThrow(() -> new ResourceNotFoundException("comentario","id", comentarioId));

        if(!comentarios.getPublicacion().getId().equals(publicacionId)){
            throw new BlogAPPExceptions(HttpStatus.BAD_REQUEST, "Comentario no pertenece a la publicación");
        }

        return mapearDTO(comentarios);
    }

    @Override
    public ComentarioDTO actualizarComentario(Long publicacionId, Long comentarioId, ComentarioDTO solicitudDeComentario) {
        Publicacion publicacion = publicacionRepositorio.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));

        Comentarios comentario = comentarioRepositorio.findById(comentarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));

        if(!comentario.getPublicacion().getId().equals(publicacion.getId())) {
            throw new BlogAPPExceptions(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicación");
        }

        comentario.setNombre(solicitudDeComentario.getNombre());
        comentario.setEmail(solicitudDeComentario.getEmail());
        comentario.setCuerpo(solicitudDeComentario.getCuerpo());

        Comentarios comentarioActualizado = comentarioRepositorio.save(comentario);
        return mapearDTO(comentarioActualizado);
    }

    @Override
    public void eliminarComentario(Long publicacionId, Long comentarioId) {
        Publicacion publicacion = publicacionRepositorio.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));

        Comentarios comentario = comentarioRepositorio.findById(comentarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));

        if(!comentario.getPublicacion().getId().equals(publicacion.getId())) {
            throw new BlogAPPExceptions(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicación");
        }

        comentarioRepositorio.delete(comentario);
    }

    private ComentarioDTO mapearDTO(Comentarios comentario) {
        ComentarioDTO comentarioDTO = modelMapper.map(comentario, ComentarioDTO.class);
        return comentarioDTO;
    }

    private Comentarios mapearEntidad(ComentarioDTO comentarioDTO) {
        Comentarios comentario = modelMapper.map(comentarioDTO, Comentarios.class);
        return comentario;
    }


}
