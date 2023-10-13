package com.Proyecto.Avanzar.Repository;

import com.Proyecto.Avanzar.Models.Persona;
import com.Proyecto.Avanzar.Models.UsuarioRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface PersonaRepository extends JpaRepository<Persona, Long> {

    @Query(value = "SELECT * FROM persona p JOIN usuarios u ON p.id_persona = u.persona_id_persona WHERE u.username = :username", nativeQuery = true)
    public Persona obtenerPersona(String username);

    @Query(value = "SELECT * FROM persona p JOIN usuarios u ON p.id_persona = u.persona_id_persona WHERE u.id = :id", nativeQuery = true)
    public Persona obtenerPersonaUsuario(Long id);

    public Persona findByCedula(String cedula);

    public Persona findByCorreo(String correo);

    @Query(value
            = "SELECT "
            + "  COUNT(DISTINCT p.id) AS Usuarios, "
            + "  (SELECT COUNT(*) FROM usuario_rol WHERE rol_rol_id = 3) AS Emprendedoras, "
            + "  (SELECT COUNT(*) FROM usuarios WHERE visible = true AND enabled = true) AS UsuariosActivos, "
            + "  (SELECT COUNT(*) FROM usuario_rol WHERE rol_rol_id = 4) AS Clientes, "
            + "  (SELECT COUNT(*) FROM publicaciones WHERE categoria_id_categoria = 1) AS PublicacionProductos, "
            + "  (SELECT COUNT(*) FROM publicaciones WHERE categoria_id_categoria = 2) AS PublicacionServicios, "
            + "  (SELECT COUNT(*) FROM publicaciones) AS TotalPublicaciones "
            + "FROM "
            + "  usuarios p", nativeQuery = true)
    Map<String, Object> contarRegistrosEnTablas();

    //verificar subscripciones por caducar
    @Query(value = "SELECT p.correo "
            + "FROM persona p JOIN usuarios u ON p.id_persona = u.persona_id_persona JOIN vendedor v ON v.usuario_id = u.id "
            + "JOIN detalle_subscripcion d ON d.vendedor_id_vendedor = v.id_vendedor WHERE d.fecha_fin  = CURRENT_DATE +INTERVAL '3 DAY';", nativeQuery = true)
    List<String> obtenerCorreosNotificacionCadu();

    @Query(value =
            "SELECT " +
            "COUNT(*) AS TotalEmprendedoras, " +
            "SUM(CASE WHEN u.visible = true AND u.enabled = true THEN 1 ELSE 0 END) AS EmprendedorasActivas, " +
            "(SELECT COUNT(*) FROM publicaciones p WHERE p.vendedor_id_vendedor = :vendedorId) AS TotalPublicaciones, " +
            "(SELECT COUNT(*) FROM publicaciones p WHERE p.vendedor_id_vendedor = :vendedorId AND p.categoria_id_categoria = 1) AS PublicacionesProductos, "+
             "(SELECT COUNT(*) FROM publicaciones p WHERE p.vendedor_id_vendedor = :vendedorId AND p.categoria_id_categoria = 2) AS PublicacionesServicios "+
            "FROM " +
            "usuario_rol ur " +
            "INNER JOIN usuarios u ON ur.usuario_id = u.id " +
            "WHERE " +
            "ur.rol_rol_id = 3", nativeQuery = true)
    Map<String, Object> contarRegistrosEnTablasE(@Param("vendedorId") Long vendedorId);
}
