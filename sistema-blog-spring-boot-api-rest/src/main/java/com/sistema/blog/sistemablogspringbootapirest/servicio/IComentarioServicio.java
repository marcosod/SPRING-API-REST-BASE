package com.sistema.blog.sistemablogspringbootapirest.servicio;

import com.sistema.blog.sistemablogspringbootapirest.dto.ComentarioDTO;

import java.util.List;

public interface IComentarioServicio {

    public ComentarioDTO crear(long publicacionId, ComentarioDTO comentarioDTO);
    public List<ComentarioDTO> obtenerComentariosPorPublicacion(long publicacionId);
    public ComentarioDTO obtenerComentarioPorId(long publicacionId,long comentarioId);

    public ComentarioDTO actualizarComentario(Long publicacionId,Long comentarioId,ComentarioDTO solicitudDeComentario);

    public void eliminarComentario(Long publicacionId,Long comentarioId);
}
