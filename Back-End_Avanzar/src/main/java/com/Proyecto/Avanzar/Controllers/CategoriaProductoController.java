package com.Proyecto.Avanzar.Controllers;


import com.Proyecto.Avanzar.Models.CategoriaProducto;

import com.Proyecto.Avanzar.Models.Publicaciones;
import com.Proyecto.Avanzar.Models.Rol;
import com.Proyecto.Avanzar.Services.service.CategoriaProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@CrossOrigin(origins = {"http://164.90.153.70:4200"})
@RestController
@RequestMapping("/api/categoriaProducto")
public class CategoriaProductoController {
    @Autowired
    CategoriaProductoService categoriaProductoService;

    @PostMapping("/registrar")
    public ResponseEntity<CategoriaProducto> crear(@RequestBody CategoriaProducto r) {
        try {
            return new ResponseEntity<>(categoriaProductoService.save(r), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<CategoriaProducto>> obtenerLista() {
        try {
            return new ResponseEntity<>(categoriaProductoService.findByAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestBody CategoriaProducto categoriaProducto) {
        return categoriaProductoService.delete(id);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<CategoriaProducto> getById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<CategoriaProducto>(categoriaProductoService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostConstruct
    public void init() {
        CategoriaProducto categoriaProducto1 = new CategoriaProducto(1L, "Gastronomia");
        CategoriaProducto categoriaProducto2 = new CategoriaProducto(2L, "Vestimenta");
        CategoriaProducto categoriaProducto3 = new CategoriaProducto(3L, "Hogar");
        CategoriaProducto categoriaProducto4 = new CategoriaProducto(4L, "Manualidades");
        CategoriaProducto categoriaProducto5 = new CategoriaProducto(5L, "Costura");
        CategoriaProducto categoriaProducto6 = new CategoriaProducto(6L, "Otros");


        categoriaProductoService.save(categoriaProducto1);
        categoriaProductoService.save(categoriaProducto2);
        categoriaProductoService.save(categoriaProducto3);
        categoriaProductoService.save(categoriaProducto4);
        categoriaProductoService.save(categoriaProducto5);
        categoriaProductoService.save(categoriaProducto6);
    }
}

