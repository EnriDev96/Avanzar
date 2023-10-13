package com.Proyecto.Avanzar.Services.implement;

import com.Proyecto.Avanzar.Models.Categoria;
import com.Proyecto.Avanzar.Repository.CategoriaRepository;
import com.Proyecto.Avanzar.Services.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServiceImpl extends GenericServiceImpl<Categoria, Long> implements CategoriaService {
    @Autowired
    private CategoriaRepository categoriaDao;

    @Override
    public CrudRepository<Categoria, Long> getDao() {
        return categoriaDao;
    }
}
