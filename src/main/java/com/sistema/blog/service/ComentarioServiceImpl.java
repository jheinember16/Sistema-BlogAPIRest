package com.sistema.blog.service;

import com.sistema.blog.dto.ComentarioDTO;
import com.sistema.blog.entidades.Comentario;
import com.sistema.blog.entidades.Publicacion;
import com.sistema.blog.exception.ResourceNotFoundException;
import com.sistema.blog.mapper.ComentarioMapper;
import com.sistema.blog.repositorio.ComentarioRepositorio;
import com.sistema.blog.repositorio.PublicacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    @Autowired
    private ComentarioMapper comentarioMapper;
    @Autowired
    private ComentarioRepositorio comentarioRepositorio;
    @Autowired
    private PublicacionRepositorio publicacionRepositorio;

    @Override
    public ComentarioDTO crearComentario(Long publicacionId, ComentarioDTO comentarioDTO) {

        Publicacion publicacion = publicacionRepositorio.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicación", "id", publicacionId));
        Comentario comentario = comentarioMapper.mapearAEntidadConPublicacion(comentarioDTO, publicacion);
        Comentario comentarioGuardado = comentarioRepositorio.save(comentario);
        return comentarioMapper.toDto(comentarioGuardado);
    }

    @Override
    public List<ComentarioDTO> listarComentariosPorPublicacionId(Long publicacionId) {
        publicacionRepositorio.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicación", "id", publicacionId));

        return comentarioMapper.toDtoList(comentarioRepositorio.findByPublicacionId(publicacionId));
    }

    @Override
    public ComentarioDTO obtenerComentarioPorId(Long publicacionId, Long comentarioId) {
        Comentario comentario = comentarioRepositorio.findById(comentarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));

        if(!comentario.getPublicacion().getId().equals(publicacionId)) {
            throw new ResourceNotFoundException("Comentario", "id",
                    "No existe o no pertenece a la publicación " + publicacionId);
        }

        return comentarioMapper.toDto(comentario);
    }

    @Override
    public ComentarioDTO actualizarComentario(Long publicacionId, Long comentarioId, ComentarioDTO comentarioDTO) {
        Comentario comentario = comentarioRepositorio.findByIdAndPublicacionId(comentarioId, publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Comentario", "id",
                        "No encontrado o no pertenece a la publicación " + publicacionId));

        comentarioMapper.actualizarDesdeDto(comentarioDTO, comentario);
        return comentarioMapper.toDto(comentarioRepositorio.save(comentario));
    }

    @Override
    public void eliminarComentario(Long publicacionId, Long comentarioId) {
        Comentario comentario = comentarioRepositorio.findByIdAndPublicacionId(comentarioId, publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Comentario", "id",
                        "No encontrado o no pertenece a la publicación " + publicacionId));

        comentarioRepositorio.delete(comentario);
    }
}
