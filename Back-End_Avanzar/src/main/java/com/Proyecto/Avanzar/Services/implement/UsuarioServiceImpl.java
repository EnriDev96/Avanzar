package com.Proyecto.Avanzar.Services.implement;

import com.Proyecto.Avanzar.Models.Persona;
import com.Proyecto.Avanzar.Models.Usuario;
import com.Proyecto.Avanzar.Repository.PersonaRepository;
import com.Proyecto.Avanzar.Repository.UsuarioRepository;
import com.Proyecto.Avanzar.Services.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl extends GenericServiceImpl<Usuario, Long> implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public CrudRepository<Usuario, Long> getDao() {
        return usuarioRepository;
    }

    @Override
    public Usuario obtenerUsuario(String username) {
        return usuarioRepository.findByUsername(username);
    }



    @Override
    public Usuario findAllByUsername(String username) {
        return usuarioRepository.findAllByUsername(username);
    }

    @Override
    public Usuario obtenerId(String username) {
        return usuarioRepository.buscarId(username);
    }

    public List<Usuario> obtenerUsuariosConPersonaYRol() {
        return usuarioRepository.findAllUsuariosWithPersonaAndRol();
    }

    ///// querys para los filtrados de listas//////
    //responsable
    public List<Usuario> FiltradoUserxEstadoActivo() {
        return usuarioRepository.FiltradoUserxEstadoActivo();
    }

    public List<Usuario> FiltradoUserxEstadoInactivo() {
        return usuarioRepository.FiltradoUserxEstadoInactivo();
    }

    //responsable
    public List<Usuario> FiltradoEmpxEstadoActivo() {
        return usuarioRepository.FiltradoEmpxEstadoActivo();
    }

    public List<Usuario> FiltradoEmpxEstadoInactivo() {
        return usuarioRepository.FiltradoEmpxEstadoInactivo();
    }

    //cliente
    public List<Usuario> FiltradoClientexEstadoActivo() {
        return usuarioRepository.FiltradoClientexEstadoActivo();
    }

    public List<Usuario> FiltradoClientexEstadoInactivo() {
        return usuarioRepository.FiltradoClientexEstadoInactivo();
    }

    ///////////////////////////////////////
    public List<Usuario> obtenerUsuariosConPersonaYEmprendedor() {
        return usuarioRepository.findAllUsuariosWithPersonaAndEmprendedor();
    }

    public List<Usuario> obtenerUsuariosConPersonaYCliente() {
        return usuarioRepository.findAllUsuariosWithPersonaAndCliente();
    }

    public boolean verificarContrasena(String username, String contrasena) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        return BCrypt.checkpw(contrasena, usuario.getPassword()); // Verificar la contraseña usando BCrypt
    }

    public void actualizarContrasena(String username, String nuevaContrasena) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        String contrasenaHash = BCrypt.hashpw(nuevaContrasena, BCrypt.gensalt());
        usuario.setPassword(contrasenaHash);
        usuarioRepository.save(usuario);
    }

    public List<Usuario> findUsuariosByPersonaId(Long idPersona) {
        return usuarioRepository.findUsuariosByPersonaId(idPersona);
    }

    @Override
    public int resetPass(String email, String newPass) {

        String contrasenaHash = BCrypt.hashpw(newPass, BCrypt.gensalt());
        return usuarioRepository.resetPass(email, contrasenaHash);

    }

}
