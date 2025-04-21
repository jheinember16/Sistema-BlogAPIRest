package com.sistema.blog.repositorio;

import com.sistema.blog.entidades.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ComentarioRepositorio extends JpaRepository<Comentario, Long> {
    List<Comentario> findByPublicacionId(Long publicacionId);
    Optional<Comentario> findByIdAndPublicacionId(Long comentarioId, Long publicacionId);
}