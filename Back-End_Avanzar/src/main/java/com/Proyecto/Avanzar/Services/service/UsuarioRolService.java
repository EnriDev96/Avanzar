package com.Proyecto.Avanzar.Services.service;

import com.Proyecto.Avanzar.Models.UsuarioRol;

import java.util.List;

public interface UsuarioRolService extends GenericService<UsuarioRol, Long>{

    public List<UsuarioRol> listarv();
    public UsuarioRol findByUsuario_UsuarioId(Long usuarioId);
}
