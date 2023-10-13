package com.Proyecto.Avanzar.Models;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "destacados")
public class Destacados implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDestacado;
    private String fecha;
    private boolean estadoDestacado;
    //Relaciones
    //Relacion Publicacion
    @ManyToOne(fetch = FetchType.EAGER)
    private Publicaciones publicaciones;

    //Relacion Usuario
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;


}
