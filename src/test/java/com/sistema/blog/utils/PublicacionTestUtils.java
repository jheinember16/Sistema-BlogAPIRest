package com.sistema.blog.utils;

import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.entidades.Publicacion;

public class PublicacionTestUtils {

    public static PublicacionDTO crearDto() {
        PublicacionDTO dto = new PublicacionDTO();
        dto.setId(1L);
        dto.setTitulo("Título de prueba");
        dto.setDescripcion("Descripción de prueba");
        dto.setContenido("Contenido de prueba");
        return dto;
    }

    public static Publicacion crearEntidad() {
        Publicacion entidad = new Publicacion();
        entidad.setId(1L);
        entidad.setTitulo("Título de prueba");
        entidad.setDescripcion("Descripción de prueba");
        entidad.setContenido("Contenido de prueba");
        return entidad;
    }
}