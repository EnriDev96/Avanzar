package com.Proyecto.Avanzar.Repository;

import com.Proyecto.Avanzar.Models.Destacados;
import com.Proyecto.Avanzar.Models.Publicaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface DestacadoRepository extends JpaRepository<Destacados, Long> {
    boolean existsByPublicacionesIdPublicacionAndUsuarioId(Long publicacionId, Long usuarioId);


    @Modifying
    @Transactional
    @Query("DELETE FROM Destacados c WHERE c.idDestacado = :id")
    void borrarFavorito(@Param("id") Long id);


    @Query(value="SELECT * FROM destacados d WHERE usuario_id = :id", nativeQuery = true)
    List<Destacados> listDestacados(@Param("id")Long id);

}
