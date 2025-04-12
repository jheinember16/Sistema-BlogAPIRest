package com.sistema.blog.service;
import com.sistema.blog.dto.PublicacionDTO;
import java.util.List;

public interface PublicacionService {
    List<PublicacionDTO> obtenerTodasLasPublicaciones();
    PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO);
    PublicacionDTO obtenerPublicacionPorId(Long id);
    PublicacionDTO actualizarPublicacion(Long id, PublicacionDTO publicacionDTO);
    void eliminarPublicacion(Long id);
}