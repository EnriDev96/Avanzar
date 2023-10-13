package com.Proyecto.Avanzar.Services.implement;

import com.Proyecto.Avanzar.Models.Subscripcion;
import com.Proyecto.Avanzar.Repository.SubscripcionRepository;
import com.Proyecto.Avanzar.Services.service.SubscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class SubscripcionesServiceImpl extends GenericServiceImpl<Subscripcion, Long> implements SubscripcionService {
    @Autowired
    private SubscripcionRepository subscripcionDao;

    @Override
    public CrudRepository<Subscripcion, Long> getDao() {
        return subscripcionDao;
    }
}
