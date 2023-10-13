package com.Proyecto.Avanzar.Services.service;

import com.Proyecto.Avanzar.Models.Persona;
import com.Proyecto.Avanzar.Models.Usuario;

import java.util.List;

public interface UsuarioService extends GenericService<Usuario, Long> {
    public Usuario obtenerUsuario(String username);

    public Usuario obtenerId(String username);

    public Usuario findAllByUsername(String username);

    List<Usuario> obtenerUsuariosConPersonaYRol();

    List<Usuario> obtenerUsuariosConPersonaYEmprendedor();

    List<Usuario> obtenerUsuariosConPersonaYCliente();

    boolean verificarContrasena(String username, String contrasenaActual);

    void actualizarContrasena(String username, String contrasenaNueva);
    //filtrado
    //responsable
    List<Usuario> FiltradoUserxEstadoActivo();
    List<Usuario> FiltradoUserxEstadoInactivo();
    //emprendedor
    List<Usuario> FiltradoEmpxEstadoActivo();
    List<Usuario> FiltradoEmpxEstadoInactivo();
    //cliente
    List<Usuario> FiltradoClientexEstadoActivo();
    List<Usuario> FiltradoClientexEstadoInactivo();

    List<Usuario> findUsuariosByPersonaId(Long idPersona);
    
    int resetPass(String email, String newPass);
}
