package com.Proyecto.Avanzar.Repository;


import com.Proyecto.Avanzar.Models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RolRepository extends JpaRepository<Rol,Long> {

    @Query("SELECT r.rolNombre " +
            "FROM Rol r " +
            "JOIN UsuarioRol u ON r.rolId = u.rol.rolId " +
            "WHERE u.usuario.id = :usuarioId")
    String findRolNombreByUsuarioId(@Param("usuarioId") Long usuarioId);

}
