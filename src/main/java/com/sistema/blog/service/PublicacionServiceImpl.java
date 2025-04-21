package com.sistema.blog.service;

import com.sistema.blog.dto.PublicacionRespuesta;
import com.sistema.blog.mapper.PublicacionMapper;
import com.sistema.blog.repositorio.PublicacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.entidades.Publicacion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class PublicacionServiceImpl implements PublicacionService {

    @Autowired
    private PublicacionRepositorio publicacionRepositorio;

    @Autowired
    private PublicacionMapper publicacionMapper;

    @Override
    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO) {
        Publicacion publicacion = publicacionMapper.toEntity(publicacionDTO);
        Publicacion guardada = publicacionRepositorio.save(publicacion);
        return publicacionMapper.toDto(guardada);
    }

    @Override
    public PublicacionRespuesta obtenerTodasLasPublicaciones(int numeroDePagina, int medidaDePagina,
                                                             String ordenarPor, String sortDir) {
        // Determinar si el orden es ascendente o descendente
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(ordenarPor).ascending()
                : Sort.by(ordenarPor).descending();

        // Crear el objeto pageable con la paginación y orden
        Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina, sort);

        // Obtener los datos paginados desde el repositorio
        Page<Publicacion> publicaciones = publicacionRepositorio.findAll(pageable);

        // Convertir entidades a DTO con el mapper
        List<PublicacionDTO> contenido = publicacionMapper.toDto(publicaciones.getContent());

        // Construir y retornar la respuesta
        return PublicacionRespuesta.desdePagina(publicaciones, contenido);
    }


    @Override
    public PublicacionDTO obtenerPublicacionPorId(Long id) {
        Publicacion publicacion = publicacionRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada con ID: " + id));
        return publicacionMapper.toDto(publicacion);
    }

    @Override
    public PublicacionDTO actualizarPublicacion(Long id, PublicacionDTO dto) {
        Publicacion publicacion = publicacionRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada con ID: " + id));

        // Validación del DTO antes de actualizar
        if (dto.getTitulo() == null || dto.getDescripcion() == null || dto.getContenido() == null) {
            throw new RuntimeException("Error al actualizar la publicación con ID: " + id);
        }

        publicacionMapper.actualizarDesdeDto(dto, publicacion);
        Publicacion actualizada = publicacionRepositorio.save(publicacion);
        return publicacionMapper.toDto(actualizada);
    }

    @Override
    public void eliminarPublicacion(Long id) {
        Publicacion publicacion = publicacionRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada con ID: " + id));
        publicacionRepositorio.delete(publicacion);
    }
}