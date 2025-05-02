package com.sistema.blog.servicio;

import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.entidades.Publicacion;
import com.sistema.blog.exception.ResourceNotFoundException;
import com.sistema.blog.mapper.PublicacionMapper;
import com.sistema.blog.repositorio.PublicacionRepositorio;
import com.sistema.blog.service.PublicacionServiceImpl;
import com.sistema.blog.utils.PublicacionTestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublicacionServiceImpTest {

    @Mock
    private PublicacionRepositorio repositorio;
    @Mock
    private PublicacionMapper mapper;
    @InjectMocks
    private PublicacionServiceImpl servicio;

    @Test
    @DisplayName("Debe crear una publicación correctamente")
    void crearPublicacion() {
        var dto = PublicacionTestUtils.crearDto();
        var entidad = PublicacionTestUtils.crearEntidad();

        when(mapper.toEntity(dto)).thenReturn(entidad);
        when(repositorio.save(entidad)).thenReturn(entidad);
        when(mapper.toDto(entidad)).thenReturn(dto);

        var resultado = servicio.crearPublicacion(dto);

        assertNotNull(resultado);
        assertEquals(dto.getTitulo(), resultado.getTitulo());
        verify(repositorio).save(entidad);
    }

    @Test
    @DisplayName("Debe obtener todas las publicaciones paginadas")
    void obtenerTodasLasPublicaciones() {
        List<Publicacion> entidades = List.of(
                PublicacionTestUtils.crearEntidad(),
                PublicacionTestUtils.crearEntidadConDatos(2L, "Otro título", "Otra descripción", "Otro contenido")
        );
        List<PublicacionDTO> dtos = List.of(
                PublicacionTestUtils.crearDto(),
                PublicacionTestUtils.crearDtoConDatos(2L, "Otro título", "Otra descripción", "Otro contenido")
        );

        Page<Publicacion> page = new PageImpl<>(entidades);
        when(repositorio.findAll(any(Pageable.class))).thenReturn(page);
        when(mapper.toDto(entidades)).thenReturn(dtos);

        var resultado = servicio.obtenerTodasLasPublicaciones(0, 10, "titulo", "ASC");

        assertNotNull(resultado);
        assertEquals(2, resultado.getContenido().size());
        verify(repositorio).findAll(any(Pageable.class));
        verify(mapper).toDto(entidades);
    }

    @Test
    @DisplayName("Debe obtener una publicación por ID")
    void obtenerPublicacionPorId_existente() {
        var entidad = PublicacionTestUtils.crearEntidad();
        var dto = PublicacionTestUtils.crearDto();

        when(repositorio.findById(1L)).thenReturn(Optional.of(entidad));
        when(mapper.toDto(entidad)).thenReturn(dto);

        var resultado = servicio.obtenerPublicacionPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    @DisplayName("Debe lanzar excepción si no existe la publicación")
    void obtenerPublicacionPorId_noExiste() {
        when(repositorio.findById(99L)).thenReturn(Optional.empty());

        var ex = assertThrows(ResourceNotFoundException.class, () -> {
            servicio.obtenerPublicacionPorId(99L);
        });

        assertEquals("Publicación no encontrada con id: 99", ex.getMessage());
    }

    @Test
    @DisplayName("Debe actualizar una publicación existente")
    void actualizarPublicacion() {
        var existente = PublicacionTestUtils.crearEntidad();
        var dtoEntrada = PublicacionTestUtils.crearDto();
        var actualizado = PublicacionTestUtils.crearEntidad();

        when(repositorio.findById(1L)).thenReturn(Optional.of(existente));
        doNothing().when(mapper).actualizarDesdeDto(dtoEntrada, existente);
        when(repositorio.save(existente)).thenReturn(actualizado);
        when(mapper.toDto(actualizado)).thenReturn(dtoEntrada);

        var resultado = servicio.actualizarPublicacion(1L, dtoEntrada);

        assertNotNull(resultado);
        assertEquals(dtoEntrada.getTitulo(), resultado.getTitulo());
    }

    @Test
    @DisplayName("Debe lanzar excepción al actualizar si no existe")
    void actualizarPublicacion_noExiste() {
        var dto = PublicacionTestUtils.crearDto();

        when(repositorio.findById(99L)).thenReturn(Optional.empty());

        var ex = assertThrows(ResourceNotFoundException.class, () ->
                servicio.actualizarPublicacion(99L, dto));

        assertEquals("Publicación no encontrada con id: 99", ex.getMessage());
    }

    @Test
    @DisplayName("Debe eliminar una publicación existente")
    void eliminarPublicacion() {
        var entidad = PublicacionTestUtils.crearEntidad();
        when(repositorio.findById(1L)).thenReturn(Optional.of(entidad));
        doNothing().when(repositorio).delete(entidad);

        servicio.eliminarPublicacion(1L);

        verify(repositorio).delete(entidad);
    }

    @Test
    @DisplayName("Debe lanzar excepción al eliminar si no existe")
    void eliminarPublicacion_noExiste() {
        when(repositorio.findById(99L)).thenReturn(Optional.empty());

        var ex = assertThrows(ResourceNotFoundException.class, () ->
                servicio.eliminarPublicacion(99L));

        assertEquals("Publicación no encontrada con id: 99", ex.getMessage());
    }
}