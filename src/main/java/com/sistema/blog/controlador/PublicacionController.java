package com.sistema.blog.controlador;

import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.service.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionServicio;

    @GetMapping
    public List<PublicacionDTO> obtenerTodas() {
        return publicacionServicio.obtenerTodasLasPublicaciones();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicacionDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(publicacionServicio.obtenerPublicacionPorId(id));
    }

    @PostMapping
    public ResponseEntity<PublicacionDTO> crear(@RequestBody PublicacionDTO publicacionDTO) {
        return ResponseEntity.ok(publicacionServicio.crearPublicacion(publicacionDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicacionDTO> actualizar(@PathVariable Long id, @RequestBody PublicacionDTO publicacionDTO) {
        return ResponseEntity.ok(publicacionServicio.actualizarPublicacion(id, publicacionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        publicacionServicio.eliminarPublicacion(id);
        return ResponseEntity.noContent().build();
    }

}
