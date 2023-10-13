package com.Proyecto.Avanzar.Repository;

import com.Proyecto.Avanzar.Models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "SELECT *\n"
            + "\tFROM usuarios WHERE username = :username and visible = true", nativeQuery = true)
    public Usuario findByUsername(String username);

    @Query(value = "SELECT *\n"
            + "\tFROM usuarios WHERE username = :username", nativeQuery = true)
    public Usuario findAllByUsername(String username);

    @Query(value = "SELECT *\n"
            + "\tFROM usuarios WHERE enabled = true AND visible=true", nativeQuery = true)
    public List<Usuario> listar();

    @Query(value = "SELECT * FROM usuarios WHERE username=:user", nativeQuery = true)
    public Usuario buscarId(String user);

    @Query("SELECT u FROM Usuario u "
            + "JOIN Persona per ON u.persona.id_persona = per.id_persona "
            + "JOIN UsuarioRol us ON us.usuario.id = u.id "
            + "WHERE us.rol.rolId = 2")
    List<Usuario> findAllUsuariosWithPersonaAndRol();

    @Query("SELECT u FROM Usuario u "
            + "JOIN Persona per ON u.persona.id_persona = per.id_persona "
            + "JOIN UsuarioRol us ON us.usuario.id = u.id "
            + "WHERE us.rol.rolId = 3")
    List<Usuario> findAllUsuariosWithPersonaAndEmprendedor();

    @Query("SELECT u FROM Usuario u "
            + "JOIN Persona per ON u.persona.id_persona = per.id_persona "
            + "JOIN UsuarioRol us ON us.usuario.id = u.id "
            + "WHERE us.rol.rolId = 4")
    List<Usuario> findAllUsuariosWithPersonaAndCliente();
    ///// Querys para los filtrados de listas//////
//Estado del responsable

    @Query("SELECT u FROM Usuario u "
            + "JOIN Persona per ON u.persona.id_persona = per.id_persona "
            + "JOIN UsuarioRol us ON us.usuario.id = u.id "
            + "WHERE us.rol.rolId = 2 ORDER By u.visible asc")
    List<Usuario> FiltradoUserxEstadoActivo();

    @Query("SELECT u FROM Usuario u "
            + "JOIN Persona per ON u.persona.id_persona = per.id_persona "
            + "JOIN UsuarioRol us ON us.usuario.id = u.id "
            + "WHERE us.rol.rolId = 2 ORDER By u.visible desc")
    List<Usuario> FiltradoUserxEstadoInactivo();
    //Estado del emprendedor

    @Query("SELECT u FROM Usuario u "
            + "JOIN Persona per ON u.persona.id_persona = per.id_persona "
            + "JOIN UsuarioRol us ON us.usuario.id = u.id "
            + "WHERE us.rol.rolId = 3 ORDER By u.visible asc")
    List<Usuario> FiltradoEmpxEstadoActivo();

    @Query("SELECT u FROM Usuario u "
            + "JOIN Persona per ON u.persona.id_persona = per.id_persona "
            + "JOIN UsuarioRol us ON us.usuario.id = u.id "
            + "WHERE us.rol.rolId = 3 ORDER By u.visible desc")
    List<Usuario> FiltradoEmpxEstadoInactivo();
    //Estado del cliente

    @Query("SELECT u FROM Usuario u "
            + "JOIN Persona per ON u.persona.id_persona = per.id_persona "
            + "JOIN UsuarioRol us ON us.usuario.id = u.id "
            + "WHERE us.rol.rolId = 4 ORDER By u.visible asc")
    List<Usuario> FiltradoClientexEstadoActivo();

    @Query("SELECT u FROM Usuario u "
            + "JOIN Persona per ON u.persona.id_persona = per.id_persona "
            + "JOIN UsuarioRol us ON us.usuario.id = u.id "
            + "WHERE us.rol.rolId = 4 ORDER By u.visible desc")
    List<Usuario> FiltradoClientexEstadoInactivo();

    @Query("SELECT u FROM Usuario u JOIN FETCH u.persona p WHERE p.id_persona = :idPersona")
    List<Usuario> findUsuariosByPersonaId(@Param("idPersona") Long idPersona);


    @Transactional
    @Modifying
    @Query(value = "UPDATE usuarios u SET password = :newPassword "
            + "FROM Persona p WHERE p.id_persona = u.persona_id_persona AND p.correo = :email", nativeQuery = true)
    int resetPass(@Param("email") String correo, @Param("newPassword") String newPassword);

}
