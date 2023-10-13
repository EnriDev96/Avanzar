package com.Proyecto.Avanzar.Models;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comentarios")
public class Comentarios implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComentario;
    @NotNull(message = "Comentario vacío")
    @Size(max = 230, message = "El comentario no debe tener más de 250 caracteres")
    private String texto;
    private Date fecha;

    public Comentarios(Long id, Date fecha,String texto,String avatar, String name){
        this.idComentario=id;
        this.fecha= fecha;
        this.texto=texto;
        this.usuario=new Usuario();
        this.usuario.setAvatar(avatar);
        this.usuario.setName(name);
        
    }
    
    //Relaciones
    //Relacion Usuario
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;

    //Relacion Publicacion
    @ManyToOne(fetch = FetchType.EAGER)
    private Publicaciones publicaciones;
}
