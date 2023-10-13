package com.Proyecto.Avanzar.Repository;

import com.Proyecto.Avanzar.Models.Publicaciones;
import java.util.Date;

import com.Proyecto.Avanzar.Models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;

public interface PublicacionesRepository extends JpaRepository<Publicaciones, Long> {

    //Query para listar publicaciones activas
    @Query(value = "SELECT *\n" +
            "\tFROM publicaciones WHERE estado=true order by fecha_publicacion desc", nativeQuery = true)
    public List<Publicaciones> listar();

    @Query(value = "SELECT * FROM publicaciones WHERE productos_id_producto IS NOT NULL AND visible=true AND vendedor_id_vendedor = :vendedorId ORDER BY fecha_publicacion DESC", nativeQuery = true)
    public List<Publicaciones> listarPublicacionesConProductos(@Param("vendedorId") Long vendedorId);

    @Query(value = "SELECT * FROM publicaciones WHERE productos_id_producto IS NOT NULL AND visible=true order by fecha_publicacion desc", nativeQuery = true)
    public List<Publicaciones> listarPublicacionesConProductos();
    
    //contar las publicaciones visibles 
    Long countByVendedor_idVendedorAndVisibleTrueAndEstadoTrue(Long vendedorId);
    
    //contar las publicaciones que no estan eliminadas
    Long countByVendedor_idVendedorAndVisibleTrue(Long vendedorId);

  //otra forma 
  //Long countByFechaPublicacionBetweenAndVendedor_Id(Date fechaInicio, Date fechaFin, Long vendedorId);
    @Query(value = "SELECT * FROM publicaciones WHERE servicios_id_servicio IS NOT NULL AND visible=true AND vendedor_id_vendedor = :vendedorId ORDER BY fecha_publicacion DESC", nativeQuery = true)
    public List<Publicaciones> listarPublicacionesConServicios(@Param("vendedorId") Long vendedorId);


    @Query(value = "SELECT * FROM publicaciones WHERE servicios_id_servicio IS NOT NULL ORDER BY fecha_publicacion DESC", nativeQuery = true)
    public List<Publicaciones> listarServicios();

    @Query(value = "SELECT * FROM publicaciones WHERE productos_id_producto IS NOT NULL ORDER BY fecha_publicacion DESC", nativeQuery = true)
    public List<Publicaciones> listarProductos();
    
    @Query(value="SELECT * FROM publicaciones p JOIN destacados d ON p.id_publicacion=d.publicaciones_id_publicacion AND d.usuario_id = :idUser AND estado=true", nativeQuery = true)
    List<Publicaciones> listarDestacados(@Param("idUser")Long id);
    
   //paso una lista de id vendedor para ver quien tiene mas de 3 publicacione activas
    @Query("SELECT p.vendedor.idVendedor FROM Publicaciones p WHERE p.vendedor.idVendedor IN :idVen AND p.estado=true AND p.visible=true GROUP BY p.vendedor.idVendedor HAVING COUNT(p)>3")
    List<Long> listarIdVenDesacPublicFree(@Param("idVen")List<Long> idVendedor );
    
    //listar publicaciones para ser desactivadas 
    @Query("SELECT p.idPublicacion FROM Publicaciones p WHERE  p.vendedor.idVendedor = :idVen AND p.estado=true AND p.visible=true ORDER BY p.fechaPublicacion DESC")
    List<Long> listarIdPublicDesac(@Param("idVen")Long idVendedor);
    
    //Desactivas las publicaciones que cuando se acaba la suscripcion
    @Modifying
    @Transactional
    @Query(value = "UPDATE Publicaciones  SET estado = false WHERE id_publicacion IN (:idPubli)", nativeQuery = true)
    void updateStateSuscripCaducado(@Param("idPubli") List<Long> idPublicacion);      
    
    //recupero la informa cion de de la publicacion y la cantidad de comentarios 
    //para poder graficar
    @Query("SELECT NEW Publicaciones(p.idPublicacion, p.tituloPublicacion,p.categoria.nombreCategoria ,COUNT(c.publicaciones.idPublicacion) ) FROM Publicaciones p LEFT JOIN Comentarios c ON p.idPublicacion="
            + "c.publicaciones.idPublicacion JOIN Vendedor v ON v.idVendedor= p.vendedor.idVendedor AND p.visible=true AND v.usuario.id= :idUsu GROUP BY p.idPublicacion, p.tituloPublicacion ,p.categoria.nombreCategoria")
    List<Publicaciones> informacionPublicacionCommentarios(@Param("idUsu")Long id,Pageable pageable);
    
    @Query(value=" select * from publicaciones where vendedor_id_vendedor =:idVendedor", nativeQuery = true)
    List<Publicaciones> listarPublicacionesVendedor(@Param("idVendedor")Long id);

    @Query(value = "SELECT *\n"
            + "\tFROM publicaciones WHERE titulo_publicacion = :tituloPublicacion and visible = true", nativeQuery = true)
    public Publicaciones findBytituloPublicacion(String tituloPublicacion);
}
