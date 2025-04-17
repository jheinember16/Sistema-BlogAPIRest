package com.sistema.blog.controlador;

import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.dto.PublicacionRespuesta;
import com.sistema.blog.service.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sistema.blog.utileria.AppConstantes;


@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionServicio;

    @GetMapping
    public ResponseEntity<PublicacionRespuesta> obtenerTodas(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int numeroDePagina,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int medidaDePagina,
            @RequestParam(value = "sortBy", defaultValue = AppConstantes.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
            @RequestParam(value = "sortDir", defaultValue = AppConstantes.ORDENAR_DIRECCION_POR_DEFECTO, required = false) String sortDir) {

        // Llamar al servicio con los parámetros de paginación y ordenación
        PublicacionRespuesta respuesta = publicacionServicio.obtenerTodasLasPublicaciones(
                numeroDePagina,
                medidaDePagina,
                ordenarPor,
                sortDir);

        return ResponseEntity.ok(respuesta);
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
