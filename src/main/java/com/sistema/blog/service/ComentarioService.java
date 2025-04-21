package com.sistema.blog.service;

import com.sistema.blog.dto.ComentarioDTO;

import java.util.List;

public interface ComentarioService {
    ComentarioDTO crearComentario(Long publicacionId, ComentarioDTO comentarioDTO);
    List<ComentarioDTO> listarComentariosPorPublicacionId(Long publicacionId);
    ComentarioDTO obtenerComentarioPorId(Long publicacionId, Long comentarioId);
    ComentarioDTO actualizarComentario(Long publicacionId, Long comentarioId, ComentarioDTO comentarioDTO);
    void eliminarComentario(Long publicacionId, Long comentarioId);
}