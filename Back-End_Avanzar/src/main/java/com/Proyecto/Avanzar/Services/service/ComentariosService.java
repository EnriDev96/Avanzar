package com.Proyecto.Avanzar.Services.service;

import com.Proyecto.Avanzar.Models.Comentarios;
import com.Proyecto.Avanzar.Models.ComentariosDto;
import java.util.List;

import com.Proyecto.Avanzar.Models.Productos;
import org.springframework.http.ResponseEntity;

public interface ComentariosService extends GenericService<Comentarios, Long> {

    ResponseEntity<List<ComentariosDto>> listCommentPost(Long idPubli, int page);

    ResponseEntity<?> borrarComentario(Long idPubli);
    
    List<Comentarios> ComentariosxUsuario(Long id);
}
