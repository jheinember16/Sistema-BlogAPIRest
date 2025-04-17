package com.sistema.blog.servicio;

import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.entidades.Publicacion;
import com.sistema.blog.mapper.PublicacionMapper;
import com.sistema.blog.repositorio.PublicacionRepositorio;
import com.sistema.blog.service.PublicacionServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static com.sistema.blog.utils.PublicacionTestUtils.*;
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
    @DisplayName("Crea una publicación con un DTO válido y devuelve el DTO guardado")
    void crearPublicacion_conDtoValido() {
        var dtoEntrada = crearDto(null);
        var entidad = crearEntidad(null);
        var entidadGuardada = crearEntidad(1L);
        var dtoEsperado = crearDto(1L);

        when(mapper.toEntity(dtoEntrada)).thenReturn(entidad);
        when(repositorio.save(entidad)).thenReturn(entidadGuardada);
        when(mapper.toDto(entidadGuardada)).thenReturn(dtoEsperado);

        var resultado = servicio.crearPublicacion(dtoEntrada);

        assertDtoIgual(resultado, 1L);
        verify(repositorio).save(entidad);
    }

    @Test
    @DisplayName("Obtiene todas las publicaciones paginadas cuando existen datos")
    void obtenerTodasPublicaciones_conDatos() {
        // Datos de entrada simulados
        int numeroDePagina = 0;
        int medidaDePagina = 10;
        String ordenarPor = "id"; // o "titulo", según lo que uses en tu entidad
        String sortDir = "ASC";

        var entidades = List.of(crearEntidad(1L), crearEntidad(2L));
        var dtosEsperados = List.of(crearDto(1L), crearDto(2L));

        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), ordenarPor);
        PageRequest pageRequest = PageRequest.of(numeroDePagina, medidaDePagina, sort);

        // Simula una Page con las entidades
        Page<Publicacion> page = new PageImpl<>(entidades);
        when(repositorio.findAll(pageRequest)).thenReturn(page);
        when(mapper.toDto(entidades)).thenReturn(dtosEsperados);

        // Ejecutar
        var resultado = servicio.obtenerTodasLasPublicaciones(numeroDePagina, medidaDePagina, ordenarPor, sortDir);

        // Verificaciones
        assertAll("Lista publicaciones",
                () -> assertNotNull(resultado),
                () -> assertEquals(2, resultado.getContenido().size()),
                () -> assertEquals(1L, resultado.getContenido().get(0).getId()),
                () -> assertEquals(2L, resultado.getContenido().get(1).getId())
        );

        verify(repositorio).findAll(pageRequest);
        verify(mapper).toDto(entidades);
    }



    @Test
    @DisplayName("Obtiene una publicación por ID si existe")
    void obtenerPublicacionPorId_existente() {
        Long id = 1L;
        var entidad = crearEntidad(id);
        var dtoEsperado = crearDto(id);

        when(repositorio.findById(id)).thenReturn(Optional.of(entidad));
        when(mapper.toDto(entidad)).thenReturn(dtoEsperado);

        var resultado = servicio.obtenerPublicacionPorId(id);

        assertDtoIgual(resultado, id);
        verify(repositorio).findById(id);
        verify(mapper).toDto(entidad);
    }

    @Test
    @DisplayName("Lanza excepción si la publicación no existe al buscar por ID")
    void obtenerPublicacionPorId_noExiste_lanzaExcepcion() {
        Long id = 99L;
        when(repositorio.findById(id)).thenReturn(Optional.empty());

        var ex = assertThrows(RuntimeException.class, () ->
                servicio.obtenerPublicacionPorId(id)
        );

        assertEquals("Publicación no encontrada con ID: " + id, ex.getMessage());
        verify(repositorio).findById(id);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Lanza excepción si la publicación no existe al actualizar")
    void actualizarPublicacion_noExiste_lanzaExcepcion() {
        Long id = 99L;
        var dtoEntrada = crearDto(id);

        when(repositorio.findById(id)).thenReturn(Optional.empty());

        var ex = assertThrows(RuntimeException.class, () ->
                servicio.actualizarPublicacion(id, dtoEntrada)
        );

        assertEquals("Publicación no encontrada con ID: " + id, ex.getMessage());
        verify(repositorio).findById(id);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Lanza excepción si el DTO de actualización es inválido")
    void actualizarPublicacion_dtoInvalido_lanzaExcepcion() {
        Long id = 1L;
        var dtoEntrada = new PublicacionDTO(); // DTO inválido

        when(repositorio.findById(id)).thenReturn(Optional.of(crearEntidad(id)));

        var ex = assertThrows(RuntimeException.class, () ->
                servicio.actualizarPublicacion(id, dtoEntrada)
        );

        assertEquals("Error al actualizar la publicación con ID: " + id, ex.getMessage());
        verify(repositorio).findById(id);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Elimina una publicación existente por ID")
    void eliminarPublicacion_conIdValido() {
        Long id = 1L;
        var entidad = crearEntidad(id);

        when(repositorio.findById(id)).thenReturn(Optional.of(entidad));

        servicio.eliminarPublicacion(id);

        verify(repositorio).findById(id);
        verify(repositorio).delete(entidad);
    }

    @Test
    @DisplayName("Lanza excepción si se intenta eliminar una publicación que no existe")
    void eliminarPublicacion_noExiste_lanzaExcepcion() {
        Long id = 99L;

        when(repositorio.findById(id)).thenReturn(Optional.empty());

        var ex = assertThrows(RuntimeException.class, () ->
                servicio.eliminarPublicacion(id)
        );

        assertEquals("Publicación no encontrada con ID: " + id, ex.getMessage());
        verify(repositorio).findById(id);
        verifyNoInteractions(mapper);
    }
}
