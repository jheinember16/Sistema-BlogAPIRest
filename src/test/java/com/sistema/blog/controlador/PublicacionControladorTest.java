package com.sistema.blog.controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistema.blog.configuracion.SecurityTestConfig;
import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.dto.PublicacionRespuesta;
import com.sistema.blog.exception.ResourceNotFoundException;
import com.sistema.blog.seguridad.CustomUserDetailsService;
import com.sistema.blog.seguridad.JwtTokenProvider;
import com.sistema.blog.service.PublicacionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PublicacionController.class)
@Import(SecurityTestConfig.class)
public class PublicacionControladorTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @SuppressWarnings("removal")
    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @SuppressWarnings("removal")
    @MockBean
    private PublicacionService publicacionServicio;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Debería retornar todas las publicaciones con paginación y ordenamiento")
    public void testObtenerTodasLasPublicaciones() throws Exception {
        List<PublicacionDTO> publicaciones = Arrays.asList(
                new PublicacionDTO(1L, "Título 1", "Resumen 1", "Contenido 1", new HashSet<>()),
                new PublicacionDTO(2L, "Título 2", "Resumen 2", "Contenido 2", new HashSet<>())
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
    @DisplayName("Debería retornar una publicación por ID")
    public void testObtenerPublicacionPorId() throws Exception {
        Long publicacionId = 1L;
        PublicacionDTO publicacionDTO = new PublicacionDTO(publicacionId, "Título de prueba", "Resumen", "Contenido", new HashSet<>());

        when(publicacionServicio.obtenerPublicacionPorId(publicacionId)).thenReturn(publicacionDTO);

        mockMvc.perform(get("/api/publicaciones/{id}", publicacionId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(publicacionId))
                .andExpect(jsonPath("$.titulo").value("Título de prueba"));
    }

    @Test
    @DisplayName("Debería crear una nueva publicación")
    public void testCrearPublicacion() throws Exception {
        PublicacionDTO publicacionDTO = new PublicacionDTO(null, "Nuevo título", "Nuevo resumen", "Nuevo contenido", new HashSet<>());
        PublicacionDTO respuestaDTO = new PublicacionDTO(1L, "Nuevo título", "Nuevo resumen", "Nuevo contenido", new HashSet<>());

        when(publicacionServicio.crearPublicacion(any(PublicacionDTO.class))).thenReturn(respuestaDTO);

        mockMvc.perform(post("/api/publicaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(publicacionDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titulo").value("Nuevo título"));
    }

    @Test
    @DisplayName("Debería actualizar una publicación existente")
    public void testActualizarPublicacion() throws Exception {
        Long id = 1L;
        PublicacionDTO publicacionDTO = new PublicacionDTO(id,
                "Título actualizado",
                "Resumen actualizado",
                "Contenido actualizado", new HashSet<>());

        when(publicacionServicio.actualizarPublicacion(eq(id), any(PublicacionDTO.class))).thenReturn(publicacionDTO);

        mockMvc.perform(put("/api/publicaciones/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(publicacionDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.titulo").value("Título actualizado"));
    }

    @Test
    @DisplayName("Debería eliminar una publicación por ID")
    public void testEliminarPublicacion() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/publicaciones/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string("Publicación con id " + id + " eliminada correctamente"));
    }

    @Test
    @DisplayName("Debería retornar 404 si no se encuentra la publicación")
    public void testPublicacionNoEncontrada() throws Exception {
        Long idInexistente = 99L;
        when(publicacionServicio.obtenerPublicacionPorId(idInexistente))
                .thenThrow(new ResourceNotFoundException("Publicación no encontrada"));

        mockMvc.perform(get("/api/publicaciones/{id}", idInexistente))
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("Debería retornar 400 si la publicación enviada no es válida")
    public void testCrearPublicacionInvalida() throws Exception {
        // DTO con campos vacíos/inválidos
        PublicacionDTO publicacionDTO = new PublicacionDTO(null, "", "", "", new HashSet<>());

        mockMvc.perform(post("/api/publicaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(publicacionDTO)))
                .andExpect(status().isBadRequest());
    }

}


