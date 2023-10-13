package com.Proyecto.Avanzar.Services.service;
import com.Proyecto.Avanzar.Models.Comentarios;
import com.Proyecto.Avanzar.Models.Publicaciones;
import com.Proyecto.Avanzar.Models.TiempoTranscurridoUtil;

import java.util.Date;
import java.util.List;

import com.Proyecto.Avanzar.Models.Usuario;
import org.springframework.http.ResponseEntity;

public interface PublicacionesService extends GenericService<Publicaciones, Long>{

    Long countPubli(Long idVenFK);
    
    Long countPubliEstatus(Long idVenFk);

    ResponseEntity<List<Publicaciones>> informacionPublicacionCommentarios(Long idUsu);
    
    List<Publicaciones> listarPublicacionesVendedor(Long idVendedor);

    public Publicaciones obtenerPublicaciones(String tituloPublicacion);
}
