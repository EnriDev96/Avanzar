package com.Proyecto.Avanzar.Repository;

import com.Proyecto.Avanzar.Models.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductosRepository extends JpaRepository<Productos, Long> {
    @Query("SELECT p FROM Productos p " +
            "WHERE p.idProducto = :idProducto and p.estadoProducto = true")
    public Productos BuscarProductoActivoxId(Long idProducto);

    @Query("SELECT p FROM Productos p ORDER BY p.estadoProducto desc")
    List<Productos> FiltradoProdxEstadoInactivo();
    @Query("SELECT p FROM Productos p ORDER BY p.estadoProducto asc")
    List<Productos> FiltradoProdxEstadoActivo();
    //listar producto x emprendedora
    @Query("SELECT p FROM Productos p LEFT JOIN p.listapublicaciones pb LEFT JOIN pb.vendedor v LEFT JOIN v.usuario us where p.estadoProducto = true and us.id = :id")
    List<Productos> ProductosxEmprendedora(@Param("id") Long id);
}
