package com.Proyecto.Avanzar.Services.implement;

import com.Proyecto.Avanzar.Models.CategoriaProducto;
import com.Proyecto.Avanzar.Repository.CategoriaProductoRepository;
import com.Proyecto.Avanzar.Services.service.CategoriaProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoriaProductosServiceImpl extends GenericServiceImpl<CategoriaProducto, Long> implements CategoriaProductoService {
    @Autowired
    private CategoriaProductoRepository categoriaProductoDao;

    @Override
    public CrudRepository<CategoriaProducto, Long> getDao() {
        return categoriaProductoDao;
    }
}
