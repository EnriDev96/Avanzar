package com.Proyecto.Avanzar.Controllers;


import com.Proyecto.Avanzar.Models.Persona;
import com.Proyecto.Avanzar.Models.Productos;
import com.Proyecto.Avanzar.Models.Usuario;
import com.Proyecto.Avanzar.Models.Vendedor;
import com.Proyecto.Avanzar.Services.service.ProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/productos")
public class ProductosController {
    @Autowired
    ProductosService productosService;

    @PostMapping("/registrar")
    public ResponseEntity<Productos> crear(@RequestBody Productos r) {
        try {
            return new ResponseEntity<>(productosService.save(r), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Productos>> obtenerLista() {
        try {
            return new ResponseEntity<>(productosService.findByAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestBody Productos productos) {
        return productosService.delete(id);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Productos> actualizar(@PathVariable Long id,@RequestBody Productos p) {
        Productos productos = productosService.findById(id);
        if (productos == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                productos.setNombreProducto(p.getNombreProducto());
                productos.setPrecioInicialProducto(p.getPrecioInicialProducto());
                productos.setPrecioFinalProducto(p.getPrecioFinalProducto());
                productos.setPrecioFijoProducto(p.getPrecioFijoProducto());
                productos.setCantidadDisponible(p.getCantidadDisponible());
                productos.setPesoProducto(p.getPesoProducto());
                productos.setCategoriaProducto(p.getCategoriaProducto());

                return new ResponseEntity<>(productosService.save(productos), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }

    @GetMapping("/buscarProductoActivo/{idProducto}")
    public Productos BuscarProductoActivoxId(@PathVariable("idProducto") Long idProducto) {
        return productosService.BuscarProductoActivoxId(idProducto);
    }

    @PutMapping("/eliminadoLogico/{id}")
    public ResponseEntity<?> eliminarlogicProducto(@PathVariable Long id) {
        Productos a = productosService.findById(id);
        if (a == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                a.setEstadoProducto(false);
                return new ResponseEntity<>(productosService.save(a), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }
    @GetMapping("/listarProductosEstadoActivo")
    public ResponseEntity<List<Productos>> FiltradoProdxEstadoActivo() {
        List<Productos> prod = productosService.FiltradoProdxEstadoActivo();
        return new ResponseEntity<>(prod, HttpStatus.OK);
    }
    @GetMapping("/listarProductosEstadoInactivo")
    public ResponseEntity<List<Productos>> FiltradoProdxEstadoInactivo() {
        List<Productos> prod = productosService.FiltradoProdxEstadoInactivo();
        return new ResponseEntity<>(prod, HttpStatus.OK);
    }
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Productos> getById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(productosService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/ProductoxEmprendedora/{id}")
    public ResponseEntity<List<Productos>> ProductosxEmprendedora(@PathVariable("id") Long id) {
        List<Productos> prod = productosService.ProductosxEmprendedora(id);
        return new ResponseEntity<>(prod, HttpStatus.OK);
    }
}
