package com.Proyecto.Avanzar.Repository;

import com.Proyecto.Avanzar.Models.Productos;
import com.Proyecto.Avanzar.Models.Servicios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiciosRepository extends JpaRepository<Servicios, Long> {
    @Query("SELECT s FROM Servicios s " +
            "WHERE s.idServicio = :idServicio and s.estado = true")
    public Servicios BuscarServicioActivoxId(Long idServicio);

    @Query("SELECT s FROM Servicios s ORDER BY s.estado desc")
    List<Servicios> FiltradoServxEstadoInactivo();
    @Query("SELECT s FROM Servicios s ORDER BY s.estado asc")
    List<Servicios> FiltradoServxEstadoActivo();
    //listar servicio x emprendedora
    @Query("SELECT s FROM Servicios s LEFT JOIN s.listapublicaciones pb LEFT JOIN pb.vendedor v LEFT JOIN v.usuario us where s.estado = true and us.id = :id")
    List<Servicios> ServicioxEmprendedora(@Param("id") Long id);
}
