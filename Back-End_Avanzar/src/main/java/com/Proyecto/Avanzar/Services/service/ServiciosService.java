package com.Proyecto.Avanzar.Services.service;

import com.Proyecto.Avanzar.Models.Productos;
import com.Proyecto.Avanzar.Models.Servicios;

import java.util.List;

public interface ServiciosService extends GenericService<Servicios, Long>{
    public Servicios BuscarServicioActivoxId(Long idServicio);
    List<Servicios> FiltradoServxEstadoInactivo();
    List<Servicios> FiltradoServxEstadoActivo();
    List<Servicios> ServicioxEmprendedora(Long id);
}
