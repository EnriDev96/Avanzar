package com.Proyecto.Avanzar.Services.implement;

import com.Proyecto.Avanzar.Services.service.GenericService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public abstract class GenericServiceImpl <T, ID extends Serializable> implements GenericService<T, ID> {

    public abstract CrudRepository<T, ID> getDao();

    @Override
    public List<T> findByAll() {
        List<T> list = new ArrayList<>();
        getDao().findAll().forEach(data -> list.add(data));
        return list;
    }

    @Override
    public T save(T entity) {
        return getDao().save(entity);
    }

    @Override
    public T findById(ID id) {
        Optional<T> data = getDao().findById(id);
        if(data.isPresent()){
            return data.get();
        }
        return null;
    }

    @Override
    public ResponseEntity<?> delete(ID id) {
        getDao().deleteById(id);
        return null;
    }

    @Override
    public void deleteEntity(ID id) {
        getDao().deleteById(id);
    }
}