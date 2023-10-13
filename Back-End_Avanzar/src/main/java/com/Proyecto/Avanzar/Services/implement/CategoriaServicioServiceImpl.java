package com.Proyecto.Avanzar.Services.implement;

import com.Proyecto.Avanzar.Models.CategoriaServicio;
import com.Proyecto.Avanzar.Repository.CategoriaServicioRepository;
import com.Proyecto.Avanzar.Services.service.CategoriaServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServicioServiceImpl extends GenericServiceImpl<CategoriaServicio, Long> implements CategoriaServicioService {
    @Autowired
    private CategoriaServicioRepository categoriaServicioDao;

    @Override
    public CrudRepository<CategoriaServicio, Long> getDao() {
        return categoriaServicioDao;
    }
}
