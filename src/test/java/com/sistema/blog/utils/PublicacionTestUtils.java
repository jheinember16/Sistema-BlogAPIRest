package com.sistema.blog.utils;

import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.entidades.Publicacion;
import java.util.ArrayList;
import java.util.HashSet;

public class PublicacionTestUtils {

    public static PublicacionDTO crearDto() {
        PublicacionDTO dto = new PublicacionDTO();
        dto.setId(1L);
        dto.setTitulo("Título de prueba");
        dto.setDescripcion("Descripción de prueba con más de 10 caracteres");
        dto.setContenido("Contenido de prueba con más de 15 caracteres");
        dto.setComentarios(new HashSet<>()); // Por si se prueba el campo comentarios
        return dto;
    }
    public static Publicacion crearEntidad() {
        Publicacion entidad = new Publicacion();
        entidad.setId(1L);
        entidad.setTitulo("Título de prueba");
        entidad.setDescripcion("Descripción de prueba con más de 10 caracteres");
        entidad.setContenido("Contenido de prueba con más de 15 caracteres");
        entidad.setComentarios(new ArrayList<>()); // Inicializa la lista vacía
        return entidad;
    }
    public static PublicacionDTO crearDtoConDatos(Long id, String titulo, String descripcion, String contenido) {
        PublicacionDTO dto = new PublicacionDTO();
        dto.setId(id);
        dto.setTitulo(titulo);
        dto.setDescripcion(descripcion);
        dto.setContenido(contenido);
        dto.setComentarios(new HashSet<>());
        return dto;
    }
    public static Publicacion crearEntidadConDatos(Long id, String titulo, String descripcion, String contenido) {
        Publicacion entidad = new Publicacion();
        entidad.setId(id);
        entidad.setTitulo(titulo);
        entidad.setDescripcion(descripcion);
        entidad.setContenido(contenido);
        entidad.setComentarios(new ArrayList<>());
        return entidad;
    }
}
