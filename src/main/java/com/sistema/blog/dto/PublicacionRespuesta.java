package com.sistema.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import com.sistema.blog.entidades.Publicacion;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicacionRespuesta {
    private List<PublicacionDTO> contenido;
    private int numeroPagina;
    private int medidaPagina;
    private long totalElementos;
    private int totalPaginas;
    private boolean ultima;
    public static PublicacionRespuesta desdePagina(Page<Publicacion> page, List<PublicacionDTO> contenido) {
        PublicacionRespuesta respuesta = new PublicacionRespuesta();
        respuesta.setContenido(contenido);
        respuesta.setNumeroPagina(page.getNumber());
        respuesta.setMedidaPagina(page.getSize());
        respuesta.setTotalElementos(page.getTotalElements());
        respuesta.setTotalPaginas(page.getTotalPages());
        respuesta.setUltima(page.isLast());
        return respuesta;
    }
}