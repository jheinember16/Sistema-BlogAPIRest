package com.sistema.blog.utils;

import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.entidades.Publicacion;

import static org.junit.jupiter.api.Assertions.*;

public class PublicacionTestUtils {

    public static final String TITULO = "Mi publicación";
    public static final String DESCRIPCION = "Una descripción";
    public static final String CONTENIDO = "Contenido largo...";

    public static PublicacionDTO crearDto(Long id) {
        var dto = new PublicacionDTO();
        dto.setId(id);
        dto.setTitulo(TITULO);
        dto.setDescripcion(DESCRIPCION);
        dto.setContenido(CONTENIDO);
        return dto;
    }

    public static PublicacionDTO crearDtoInvalido(Long id) {
        var dto = new PublicacionDTO();
        dto.setId(id);
        dto.setTitulo(null);
        dto.setDescripcion(null);
        dto.setContenido(null);
        return dto;
    }

    public static Publicacion crearEntidad(Long id) {
        var entidad = new Publicacion();
        entidad.setId(id);
        entidad.setTitulo(TITULO);
        entidad.setDescripcion(DESCRIPCION);
        entidad.setContenido(CONTENIDO);
        return entidad;
    }

    public static Publicacion actualizarDesdeDto(PublicacionDTO dto, Publicacion entidad) {
        if (dto.getTitulo() != null) entidad.setTitulo(dto.getTitulo());
        if (dto.getDescripcion() != null) entidad.setDescripcion(dto.getDescripcion());
        if (dto.getContenido() != null) entidad.setContenido(dto.getContenido());
        return entidad;
    }

    public static void assertDtoIgual(PublicacionDTO dto, Long idEsperado) {
        assertAll("Validando DTO",
                () -> assertNotNull(dto),
                () -> assertEquals(idEsperado, dto.getId()),
                () -> assertEquals(TITULO, dto.getTitulo()),
                () -> assertEquals(DESCRIPCION, dto.getDescripcion()),
                () -> assertEquals(CONTENIDO, dto.getContenido())
        );
    }
}
