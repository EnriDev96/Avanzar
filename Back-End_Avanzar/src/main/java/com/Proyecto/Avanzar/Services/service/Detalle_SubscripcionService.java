package com.Proyecto.Avanzar.Services.service;

import com.Proyecto.Avanzar.Models.Detalle_Subscripcion;
import com.Proyecto.Avanzar.Models.dto.mensajeAlertasDto;
import org.springframework.http.ResponseEntity;

public interface Detalle_SubscripcionService extends GenericService<Detalle_Subscripcion,Long> {
    
    ResponseEntity<mensajeAlertasDto> dataSuscripUser(String username);
    
    ResponseEntity<mensajeAlertasDto> limitPubAct(String username);

    Detalle_Subscripcion obtenerDetalleSubscripcionPorVendedorId(Long vendedorId);
}
