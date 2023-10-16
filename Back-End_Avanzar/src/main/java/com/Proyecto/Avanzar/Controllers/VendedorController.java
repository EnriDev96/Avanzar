package com.Proyecto.Avanzar.Controllers;

import com.Proyecto.Avanzar.Models.*;
import com.Proyecto.Avanzar.Repository.Detalle_SubscripcionRepository;
import com.Proyecto.Avanzar.Repository.SubscripcionRepository;
import com.Proyecto.Avanzar.Repository.VendedorRepository;
import com.Proyecto.Avanzar.Services.service.RolService;
import com.Proyecto.Avanzar.Services.service.SubscripcionService;
import com.Proyecto.Avanzar.Services.service.UsuarioService;
import com.Proyecto.Avanzar.Services.service.VendedorService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://164.90.153.70:4200"})
@RestController
@RequestMapping("/api/vendedor")
public class VendedorController {
    @Autowired
    VendedorService vendedorService;
    @Autowired
    private SubscripcionService subscripcionService;
    @Autowired
    private VendedorRepository vendedorRepository;
    @Autowired
    private Detalle_SubscripcionRepository detalleSubscripcionRepository;

@PostMapping("/registrar")
    public ResponseEntity<Vendedor> crear(@RequestBody Vendedor r) {
        try {
            return new ResponseEntity<>(vendedorService.save(r), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/registrar/{idSubscripcion}")
    public ResponseEntity<Vendedor> registrarVendedor(@RequestBody Vendedor vendedor, @PathVariable Long idSubscripcion) {
        try {
            Detalle_Subscripcion detalleSubscripcion = new Detalle_Subscripcion();

            // Configurar la fecha de inicio como la fecha actual
            detalleSubscripcion.setFechaInicio(new Date());

            // Configurar la fecha de fin sumando 30 días a la fecha actual
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(detalleSubscripcion.getFechaInicio());
            calendar.add(Calendar.DAY_OF_MONTH, 30);
            detalleSubscripcion.setFechaFin(calendar.getTime());

            Subscripcion subscripcion = subscripcionService.findById(idSubscripcion);
            detalleSubscripcion.setSubscripcion(subscripcion);
            detalleSubscripcion.setEstado(true);

            vendedorRepository.save(vendedor);

            detalleSubscripcion.setVendedor(vendedor);
            detalleSubscripcionRepository.save(detalleSubscripcion);

            return new ResponseEntity<>(vendedor, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/listar")
    public ResponseEntity<List<Vendedor>> obtenerLista() {
        try {
            return new ResponseEntity<>(vendedorService.findByAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestBody Vendedor vendedor) {
        return vendedorService.delete(id);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Vendedor> actualizar(@PathVariable Long id,@RequestBody Vendedor p) {
        Vendedor subscripcion = vendedorService.findById(id);
        if (subscripcion == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                subscripcion.setNombreEmprendimiento(p.getNombreEmprendimiento());
                subscripcion.setListapublicaciones(p.getListapublicaciones());
                subscripcion.setDetalleSubscripcion(p.getDetalleSubscripcion());
                
                return new ResponseEntity<>(vendedorService.save(subscripcion), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Vendedor> getById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<Vendedor>(vendedorService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/usuario/{userId}")
    public ResponseEntity<Vendedor> getVendedoresByUserId(@PathVariable Long userId) {
        try {
            Vendedor vendedor = vendedorService.getVendedoresByUsuarioId(userId);
            if (vendedor != null) {
                return new ResponseEntity<>(vendedor, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Cambiar el código de respuesta si no se encuentra el vendedor
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}