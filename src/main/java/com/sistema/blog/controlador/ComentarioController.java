package com.sistema.blog.controlador;

import com.sistema.blog.dto.ComentarioDTO;
import com.sistema.blog.dto.ResponseDTO;
import com.sistema.blog.service.ComentarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @PostMapping("/publicaciones/{publicacionId}/comentarios")
    public ResponseEntity<ResponseDTO<ComentarioDTO>> crearComentario(@PathVariable Long publicacionId,
            @Valid @RequestBody ComentarioDTO comentarioDTO) {
        ComentarioDTO comentarioCreado = comentarioService.crearComentario(publicacionId, comentarioDTO);
        ResponseDTO<ComentarioDTO> response = new ResponseDTO<>();
        response.setMessage("Comentario creado en publicación " + publicacionId);
        response.setData(comentarioCreado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/publicaciones/{publicacionId}/comentarios")
    public ResponseEntity<ResponseDTO<List<ComentarioDTO>>> listarComentariosPorPublicacionId(
            @PathVariable Long publicacionId) {

        List<ComentarioDTO> comentarios = comentarioService.listarComentariosPorPublicacionId(publicacionId);

        ResponseDTO<List<ComentarioDTO>> response = new ResponseDTO<>();
        response.setMessage(comentarios.isEmpty() ?
                "No se encontraron comentarios para la publicación " + publicacionId :
                "Comentarios obtenidos exitosamente");
        response.setData(comentarios);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
    public ResponseEntity<ResponseDTO<ComentarioDTO>> obtenerComentarioPorId(
            @PathVariable(value = "publicacionId") Long publicacionId,
            @PathVariable(value = "comentarioId") Long comentarioId) {

        ComentarioDTO comentario = comentarioService.obtenerComentarioPorId(publicacionId, comentarioId);

        ResponseDTO<ComentarioDTO> response = new ResponseDTO<>();
        response.setMessage("Comentario obtenido exitosamente");
        response.setData(comentario);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
    public ResponseEntity<ResponseDTO<ComentarioDTO>> actualizarComentario(
            @PathVariable Long publicacionId,
            @PathVariable Long comentarioId,
            @Valid @RequestBody ComentarioDTO comentarioDTO) {

        ComentarioDTO comentarioActualizado = comentarioService.actualizarComentario(publicacionId, comentarioId, comentarioDTO);

        ResponseDTO<ComentarioDTO> response = new ResponseDTO<>();
        response.setMessage("Comentario actualizado exitosamente");
        response.setData(comentarioActualizado);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
    public ResponseEntity<ResponseDTO<String>> eliminarComentario(
            @PathVariable Long publicacionId,
            @PathVariable Long comentarioId) {

        comentarioService.eliminarComentario(publicacionId, comentarioId);
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setMessage("Comentario eliminado exitosamente");
        response.setData("Comentario con ID " + comentarioId + " de la publicación " + publicacionId + " fue eliminada");
        return ResponseEntity.ok(response);
    }
}