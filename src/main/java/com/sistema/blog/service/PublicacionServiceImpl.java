package com.sistema.blog.service;

import com.sistema.blog.mapper.PublicacionMapper;
import com.sistema.blog.repositorio.PublicacionRepositorio;
import org.springframework.stereotype.Service;
import com.sistema.blog.dto.PublicacionDTO;

import java.util.List;

@Service
public class PublicacionServiceImpl implements PublicacionService {

    private final PublicacionRepositorio publicacionRepositorio;

    public PublicacionServiceImpl(PublicacionRepositorio publicacionRepositorio) {
        this.publicacionRepositorio = publicacionRepositorio;
    }

    @Override
    public List<PublicacionDTO> obtenerTodasLasPublicaciones() {
        return publicacionRepositorio.findAll().stream()
                .map(PublicacionMapper.mapper::toDto)
                .toList();
    }

    @Override
    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO) {
        var publicacion = PublicacionMapper.mapper.toEntity(publicacionDTO);
        var publicacionGuardada = publicacionRepositorio.save(publicacion);
        return PublicacionMapper.mapper.toDto(publicacionGuardada);
    }

    @Override
    public PublicacionDTO obtenerPublicacionPorId(Long id) {
        var publicacion = publicacionRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada con ID: " + id));
        return PublicacionMapper.mapper.toDto(publicacion);
    }

    @Override
    public PublicacionDTO actualizarPublicacion(Long id, PublicacionDTO dto) {
        var publicacion = publicacionRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada con ID: " + id));

        PublicacionMapper.mapper.actualizarDesdeDto(dto, publicacion);

        var actualizada = publicacionRepositorio.save(publicacion);
        return PublicacionMapper.mapper.toDto(actualizada);
    }


    @Override
    public void eliminarPublicacion(Long id) {
        var publicacion = publicacionRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada con ID: " + id));
        publicacionRepositorio.delete(publicacion);
    }
}