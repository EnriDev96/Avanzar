package com.Proyecto.Avanzar.Services.implement;

import com.Proyecto.Avanzar.Models.Productos;
import com.Proyecto.Avanzar.Repository.ProductosRepository;
import com.Proyecto.Avanzar.Services.service.ProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductosServiceImpl extends GenericServiceImpl<Productos, Long> implements ProductosService {
    @Autowired
    private ProductosRepository productosDao;

    @Override
    public CrudRepository<Productos, Long> getDao() {
        return productosDao;
    }

    @Override
    public Productos BuscarProductoActivoxId(Long idProducto) {
        return productosDao.BuscarProductoActivoxId(idProducto);
    }
    public List<Productos> FiltradoProdxEstadoActivo() {
        return productosDao.FiltradoProdxEstadoActivo();
    }
    public List<Productos> FiltradoProdxEstadoInactivo() {
        return productosDao.FiltradoProdxEstadoInactivo();
    }

    @Override
    public List<Productos> ProductosxEmprendedora(Long id) {
        return productosDao.ProductosxEmprendedora(id);
    }

}
