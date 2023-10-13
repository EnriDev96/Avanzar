package com.Proyecto.Avanzar.Services.implement;

import com.Proyecto.Avanzar.Models.Publicaciones;
import com.Proyecto.Avanzar.Models.Usuario;
import com.Proyecto.Avanzar.Repository.PublicacionesRepository;
import com.Proyecto.Avanzar.Services.service.PublicacionesService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PublicacionesServiceImpl extends GenericServiceImpl<Publicaciones, Long> implements PublicacionesService {
    @Autowired
    private PublicacionesRepository publicacionesDao;

    @Override
    public CrudRepository<Publicaciones, Long> getDao() {
        return publicacionesDao;
    }

    @Override
    public Long countPubli(Long idVenFK) {
    
        return publicacionesDao.countByVendedor_idVendedorAndVisibleTrue(idVenFK);
    
    }

    @Override
    public Long countPubliEstatus(Long idVenFk) {
        return publicacionesDao.countByVendedor_idVendedorAndVisibleTrueAndEstadoTrue(idVenFk);
    }

    @Override
    public ResponseEntity<List<Publicaciones>> informacionPublicacionCommentarios(Long idUsu) {
        
        Pageable pageable= PageRequest.of(0, 12);
        
        return new ResponseEntity<>(publicacionesDao.informacionPublicacionCommentarios(idUsu, pageable), HttpStatus.OK);
        
        
    }

    @Override
    public List<Publicaciones> listarPublicacionesVendedor(Long idVendedor) {
        return publicacionesDao.listarPublicacionesVendedor(idVendedor);
    }

    @Override
    public Publicaciones obtenerPublicaciones(String tituloPublicacion) {
        return publicacionesDao.findBytituloPublicacion(tituloPublicacion);
    }
}
