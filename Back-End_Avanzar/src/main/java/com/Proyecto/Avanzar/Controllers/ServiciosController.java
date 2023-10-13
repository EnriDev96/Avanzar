package com.Proyecto.Avanzar.Controllers;

import com.Proyecto.Avanzar.Models.Productos;
import com.Proyecto.Avanzar.Models.Servicios;
import com.Proyecto.Avanzar.Models.Vendedor;
import com.Proyecto.Avanzar.Services.service.ServiciosService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/servicios")
public class ServiciosController {
    @Autowired
    ServiciosService serviciosService;
     @PostMapping("/registrar")
    public ResponseEntity<Servicios> crear(@RequestBody Servicios r) {
        try {
            return new ResponseEntity<>(serviciosService.save(r), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Servicios>> obtenerLista() {
        try {
            return new ResponseEntity<>(serviciosService.findByAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestBody Servicios servicios) {
        return serviciosService.delete(id);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Servicios> actualizar(@PathVariable Long id,@RequestBody Servicios p) {
        Servicios productos = serviciosService.findById(id);
        if (productos == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                productos.setNombreServicio(p.getNombreServicio());
                productos.setDescripcionServicio(p.getDescripcionServicio());
                productos.setPrecioInicialServicio(p.getPrecioInicialServicio());
                productos.setPrecioFinalServicio(p.getPrecioFinalServicio());
                productos.setPrecioFijoServicio(p.getPrecioFijoServicio());
                productos.setEstado(p.isEstado());
                productos.setListapublicaciones(p.getListapublicaciones());
                return new ResponseEntity<>(serviciosService.save(productos), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }
    @GetMapping("/buscarServicioActivo/{idServicio}")
    public Servicios BuscarServicioActivoxId(@PathVariable("idServicio") Long idServicio) {
        return serviciosService.BuscarServicioActivoxId(idServicio);
    }

    @PutMapping("/eliminadoLogico/{id}")
    public ResponseEntity<?> eliminarlogicoServicio(@PathVariable Long id) {
        Servicios a = serviciosService.findById(id);
        if (a == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                a.setEstado(false);
                return new ResponseEntity<>(serviciosService.save(a), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }
    @GetMapping("/listarServicioEstadoActivo")
    public ResponseEntity<List<Servicios>> FiltradoServxEstadoInactivo() {
        List<Servicios> serv = serviciosService.FiltradoServxEstadoInactivo();
        return new ResponseEntity<>(serv, HttpStatus.OK);
    }
    @GetMapping("/listarServicioEstadoInactivo")
    public ResponseEntity<List<Servicios>> FiltradoServxEstadoActivo() {
        List<Servicios> serv = serviciosService.FiltradoServxEstadoActivo();
        return new ResponseEntity<>(serv, HttpStatus.OK);
    }
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Servicios> getById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(serviciosService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/ServicioxEmprendedora/{id}")
    public ResponseEntity<List<Servicios>> ServicioxEmprendedora(@PathVariable("id") Long id) {
        List<Servicios> serv = serviciosService.ServicioxEmprendedora(id);
        return new ResponseEntity<>(serv, HttpStatus.OK);
    }
}
