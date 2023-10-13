package com.Proyecto.Avanzar.Services.implement;

import com.Proyecto.Avanzar.Models.Productos;
import com.Proyecto.Avanzar.Models.Servicios;
import com.Proyecto.Avanzar.Repository.ServiciosRepository;
import com.Proyecto.Avanzar.Services.service.ServiciosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiciosServiceImpl extends GenericServiceImpl<Servicios, Long> implements ServiciosService {
    @Autowired
    private ServiciosRepository serviciosDao;

    @Override
    public CrudRepository<Servicios, Long> getDao() {
        return serviciosDao;
    }
    @Override
    public Servicios BuscarServicioActivoxId(Long idServicio) {
        return serviciosDao.BuscarServicioActivoxId(idServicio);
    }
    public List<Servicios> FiltradoServxEstadoInactivo() {
        return serviciosDao.FiltradoServxEstadoInactivo();
    }
    public List<Servicios> FiltradoServxEstadoActivo() {
        return serviciosDao.FiltradoServxEstadoActivo();
    }
    public List<Servicios> ServicioxEmprendedora(Long id) {
        return serviciosDao.ServicioxEmprendedora(id);
    }

}
