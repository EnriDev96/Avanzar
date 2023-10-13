package com.Proyecto.Avanzar.Services.implement;

import com.Proyecto.Avanzar.Models.Rol;
import com.Proyecto.Avanzar.Repository.RolRepository;
import com.Proyecto.Avanzar.Services.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class RolServiceImpl extends GenericServiceImpl<Rol, Long> implements RolService {
    @Autowired
    private RolRepository rolRepository;
    @Override
    public CrudRepository<Rol, Long> getDao() {
        return rolRepository;
    }

    public String obtenerRolNombreDeUsuario(Long usuarioId) {
        return rolRepository.findRolNombreByUsuarioId(usuarioId);
    }


}
