package com.sistema.blog.controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.dto.PublicacionRespuesta;
import com.sistema.blog.service.PublicacionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PublicacionController.class)
public class PublicacionControladorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublicacionService publicacionServicio;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Debería retornar todas las publicaciones con paginación y ordenamiento")
    public void testObtenerTodasLasPublicaciones() throws Exception {
        List<PublicacionDTO> publicaciones = Arrays.asList(
                new PublicacionDTO(1L, "Título 1", "Resumen 1", "Contenido 1"),
                new PublicacionDTO(2L, "Título 2", "Resumen 2", "Contenido 2")
        );

        PublicacionRespuesta respuesta = new PublicacionRespuesta(
                publicaciones,
                0,     // numeroPagina
                10,    // medidaPagina
                2,     // totalElementos
                1,     // totalPaginas
                true   // ultima
        );

        // Corrige: ahora pasamos también el ordenarPor y sortDir
        when(publicacionServicio.obtenerTodasLasPublicaciones(0, 10, "id", "asc"))
                .thenReturn(respuesta);

        mockMvc.perform(get("/api/publicaciones")
                        .param("pageNo", "0")
                        .param("pageSize", "10")
                        .param("sortBy", "id")
                        .param("sortDir", "asc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.contenido.length()").value(2))
                .andExpect(jsonPath("$.contenido[0].titulo").value("Título 1"))
                .andExpect(jsonPath("$.contenido[1].titulo").value("Título 2"))
                .andExpect(jsonPath("$.numeroPagina").value(0))
                .andExpect(jsonPath("$.medidaPagina").value(10))
                .andExpect(jsonPath("$.totalElementos").value(2))
                .andExpect(jsonPath("$.totalPaginas").value(1))
                .andExpect(jsonPath("$.ultima").value(true));
    }

    @Test
    @DisplayName("Debería retornar la publicación por ID")
    public void testObtenerPublicacionPorId() throws Exception {
        PublicacionDTO publicacion = new PublicacionDTO(1L, "Título 1", "Resumen 1", "Contenido 1");

        when(publicacionServicio.obtenerPublicacionPorId(1L)).thenReturn(publicacion);

        mockMvc.perform(get("/api/publicaciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Título 1"));
    }

    @Test
    @DisplayName("Debería crear una nueva publicación")
    public void testCrearPublicacion() throws Exception {
        PublicacionDTO nueva = new PublicacionDTO(null, "Nuevo Título", "Nuevo Resumen", "Nuevo Contenido");
        PublicacionDTO creada = new PublicacionDTO(1L, "Nuevo Título", "Nuevo Resumen", "Nuevo Contenido");

        when(publicacionServicio.crearPublicacion(any(PublicacionDTO.class))).thenReturn(creada);

        mockMvc.perform(post("/api/publicaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nueva)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Nuevo Título"));
    }

    @Test
    @DisplayName("Debería actualizar una publicación existente")
    public void testActualizarPublicacion() throws Exception {
        PublicacionDTO actualizada = new PublicacionDTO(1L, "Título Actualizado", "Resumen Actualizado", "Contenido Actualizado");

        when(publicacionServicio.actualizarPublicacion(eq(1L), any(PublicacionDTO.class))).thenReturn(actualizada);

        mockMvc.perform(put("/api/publicaciones/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Título Actualizado"));
    }

    @Test
    @DisplayName("Debería eliminar la publicación por ID")
    public void testEliminarPublicacion() throws Exception {
        doNothing().when(publicacionServicio).eliminarPublicacion(1L);

        mockMvc.perform(delete("/api/publicaciones/1"))
                .andExpect(status().isNoContent());
    }
}
