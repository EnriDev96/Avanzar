package com.Proyecto.Avanzar.Services.service;

import com.Proyecto.Avanzar.Models.Destacados;
import org.springframework.http.ResponseEntity;

public interface DestacadoService extends GenericService<Destacados, Long>{

    ResponseEntity<?> borrarFavorito(Long idFavorito);

}
