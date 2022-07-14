package com.sistema.blog.sistemablogspringbootapirest.servicioImplementacion;

import com.sistema.blog.sistemablogspringbootapirest.dto.PublicacionDTO;
import com.sistema.blog.sistemablogspringbootapirest.dto.PublicacionRespuesta;
import com.sistema.blog.sistemablogspringbootapirest.entity.Publicacion;
import com.sistema.blog.sistemablogspringbootapirest.exceptions.ResourceNotFoundException;
import com.sistema.blog.sistemablogspringbootapirest.repositorio.IPublicacionRepositorio;
import com.sistema.blog.sistemablogspringbootapirest.servicio.IPublicacionServicio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicacionServicioImpl implements IPublicacionServicio {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IPublicacionRepositorio publicacionRepositorio;

    @Override
    public PublicacionDTO crear(PublicacionDTO publicacionDTO) {
        Publicacion publicacion = mapearEntidad(publicacionDTO);

        Publicacion nuevaPublicacion = publicacionRepositorio.save(publicacion);

        PublicacionDTO publicacionRespuesta = mapearDTO(nuevaPublicacion);
        return publicacionRespuesta;
    }

    @Override
    public PublicacionRespuesta listarTodo(int numeroDePagina, int cantidadDePagina) {
        Pageable pageable = PageRequest.of(numeroDePagina,cantidadDePagina);
        Page<Publicacion> publicaciones = publicacionRepositorio.findAll(pageable);

        List<Publicacion> publicacionlista = publicaciones.getContent();
        List<PublicacionDTO> contenido = publicacionlista.stream().map( publicacion -> mapearDTO(publicacion)).collect(Collectors.toList());

        PublicacionRespuesta publicacionRespuesta = new PublicacionRespuesta();
        publicacionRespuesta.setContenido(contenido);
        publicacionRespuesta.setNumeroPagina(publicaciones.getNumber());
        publicacionRespuesta.setCantidadPagina(publicaciones.getSize());
        publicacionRespuesta.setTotalElementos(publicaciones.getTotalElements());
        publicacionRespuesta.setTotalPaginas(publicaciones.getTotalPages());
        publicacionRespuesta.setUltima(publicaciones.isLast());

        return publicacionRespuesta;
    }

    @Override
    public PublicacionDTO obtenerPorId(Long id) {
        Publicacion publicacion = publicacionRepositorio.findById(id).
                                    orElseThrow(() -> new ResourceNotFoundException("publicacion","id", id));
        return  mapearDTO(publicacion);
    }

    @Override
    public PublicacionDTO actualizar(PublicacionDTO publicacionDTO, long id) {
        Publicacion publicacion = publicacionRepositorio.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("publicacion","id", id));

        publicacion.setTitulo(publicacionDTO.getTitulo());
        publicacion.setDescripcion(publicacionDTO.getDescripcion());
        publicacion.setContenido(publicacionDTO.getContenido());

        Publicacion publicacionActualizada = publicacionRepositorio.save(publicacion);
        return mapearDTO(publicacionActualizada);
    }

    @Override
    public void eliminar(long id) {
        Publicacion publicacion = publicacionRepositorio.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("publicacion","id", id));

        publicacionRepositorio.delete(publicacion);
    }


    // Convierte entidad a DTO
    private PublicacionDTO mapearDTO(Publicacion publicacion) {
        PublicacionDTO publicacionDTO = modelMapper.map(publicacion, PublicacionDTO.class);
        return publicacionDTO;
    }

    // Convierte de DTO a Entidad
    private Publicacion mapearEntidad(PublicacionDTO publicacionDTO) {
        Publicacion publicacion = modelMapper.map(publicacionDTO, Publicacion.class);
        return publicacion;
    }
}
