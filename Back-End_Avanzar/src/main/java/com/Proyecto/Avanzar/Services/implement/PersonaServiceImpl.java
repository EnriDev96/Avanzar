package com.Proyecto.Avanzar.Services.implement;

import com.Proyecto.Avanzar.Models.Persona;
import com.Proyecto.Avanzar.Repository.PersonaRepository;
import com.Proyecto.Avanzar.Services.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PersonaServiceImpl extends GenericServiceImpl<Persona, Long> implements PersonaService {
    @Autowired
    private PersonaRepository repository;
    @Override
    public CrudRepository<Persona, Long > getDao() {

        return repository;
    }
    @Override
    public Persona obtenerPersona(String username) {
        return repository.obtenerPersona(username);
    }

    @Override
    public Persona obtenerPersonaPorIdUsuario(Long id) {
        return repository.obtenerPersonaUsuario(id);
    }

    @Override
    public Persona findByCedula(String cedula) {
        return repository.findByCedula(cedula);
    }

    @Override
    public Persona findByCorreo(String correo) {
        return repository.findByCorreo(correo);
    }

    @Override
    public Map<String, Object> contarRegistrosEnTablas() {
        return repository.contarRegistrosEnTablas();
    }

    @Override
    public Map<String, Object> contarRegistrosEnTablasE(Long vendedorId) {
        return repository.contarRegistrosEnTablasE(vendedorId);
    }
}
