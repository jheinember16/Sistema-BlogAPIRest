package com.sistema.blog.service;

import com.sistema.blog.dto.PublicacionRespuesta;
import com.sistema.blog.exception.ResourceNotFoundException;
import com.sistema.blog.mapper.PublicacionMapper;
import com.sistema.blog.repositorio.PublicacionRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.entidades.Publicacion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicacionServiceImpl implements PublicacionService {

    @Autowired
    private final PublicacionRepositorio publicacionRepositorio;
    @Autowired
    private final PublicacionMapper publicacionMapper;

    @Override
    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO) {
        Publicacion publicacion = publicacionMapper.toEntity(publicacionDTO);
        Publicacion guardada = publicacionRepositorio.save(publicacion);
        return publicacionMapper.toDto(guardada);
    }

    @Override
    public PublicacionRespuesta obtenerTodasLasPublicaciones(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(ordenarPor).ascending()
                : Sort.by(ordenarPor).descending();
        Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina, sort);
        Page<Publicacion> paginaPublicaciones = publicacionRepositorio.findAll(pageable);
        List<PublicacionDTO> contenido = publicacionMapper.toDto(paginaPublicaciones.getContent());
        return PublicacionRespuesta.desdePagina(paginaPublicaciones, contenido);
    }
    @Override
    public PublicacionDTO obtenerPublicacionPorId(Long id) {
        Publicacion publicacion = buscarPorId(id);
        return publicacionMapper.toDto(publicacion);
    }
    @Override
    public PublicacionDTO actualizarPublicacion(Long id, PublicacionDTO publicacionDTO) {
        Publicacion existente = buscarPorId(id);
        publicacionMapper.actualizarDesdeDto(publicacionDTO, existente);
        Publicacion actualizada = publicacionRepositorio.save(existente);
        return publicacionMapper.toDto(actualizada);
    }
    @Override
    public void eliminarPublicacion(Long id) {
        Publicacion publicacion = buscarPorId(id);
        publicacionRepositorio.delete(publicacion);
    }
    private Publicacion buscarPorId(Long id) {
        return publicacionRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publicación", "id", id));
    }
}