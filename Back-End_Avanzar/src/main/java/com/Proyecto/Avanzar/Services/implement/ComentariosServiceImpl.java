package com.Proyecto.Avanzar.Services.implement;

import com.Proyecto.Avanzar.Models.Comentarios;
import com.Proyecto.Avanzar.Models.ComentariosDto;
import com.Proyecto.Avanzar.Models.TiempoTranscurridoUtil;
import com.Proyecto.Avanzar.Repository.ComentariosRepository;
import com.Proyecto.Avanzar.Services.service.ComentariosService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ComentariosServiceImpl extends GenericServiceImpl<Comentarios, Long> implements ComentariosService {
    @Autowired
    private ComentariosRepository comentariosDao;

    @Override
    public CrudRepository<Comentarios, Long> getDao() {
        return comentariosDao;
    }

    @Override
    public ResponseEntity<List<ComentariosDto>> listCommentPost(Long idPubli,int page) {
        Pageable pageable = PageRequest.of(page,10);
        List <ComentariosDto> listCDto= new ArrayList();
        try{
            
            List<Comentarios> comments=comentariosDao.listCommentPost(idPubli,pageable);
            for(Comentarios comen :comments){
                ComentariosDto comenDto= new ComentariosDto();
                comenDto.setId(comen.getIdComentario());
                comenDto.setAvatar(comen.getUsuario().getAvatar());
                comenDto.setName(comen.getUsuario().getName());
                comenDto.setTiempoTranscurrido(TiempoTranscurridoUtil.calcularTiempoTranscurridoFormateado(comen.getFecha()));
                comenDto.setTexto(comen.getTexto());
                listCDto.add(comenDto);
            }
            
            return new ResponseEntity<>(listCDto, HttpStatus.OK);
            
        }catch(Exception e){
              return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<?> borrarComentario(Long idPubli) {
    
       try{
           comentariosDao.borrarComentario(idPubli);
           return new ResponseEntity<>(HttpStatus.OK);
       }catch(Exception e){
           System.out.println(e);
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
    
    }
    
    public List<Comentarios> ComentariosxUsuario(Long id) {
        return comentariosDao.ComentariosxUsuario(id);
    }

}
