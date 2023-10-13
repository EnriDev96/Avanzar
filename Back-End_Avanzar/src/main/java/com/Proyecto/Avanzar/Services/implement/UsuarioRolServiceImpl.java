package com.Proyecto.Avanzar.Services.implement;

import com.Proyecto.Avanzar.Models.UsuarioRol;
import com.Proyecto.Avanzar.Repository.UsuarioRolRepository;
import com.Proyecto.Avanzar.Services.service.UsuarioRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioRolServiceImpl extends GenericServiceImpl<UsuarioRol, Long> implements UsuarioRolService {
    @Autowired
    private UsuarioRolRepository usuarioRolRepository;
    @Override
    public CrudRepository<UsuarioRol, Long> getDao() {
        return usuarioRolRepository;
    }

    @Override
    public List<UsuarioRol> listarv() {
        return usuarioRolRepository.listarv();
    }

    @Override
    public UsuarioRol findByUsuario_UsuarioId(Long usuarioId) {
        return usuarioRolRepository.findByUsuario_Id(usuarioId);
    }


}
