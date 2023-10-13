package com.Proyecto.Avanzar.Models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "servicios")
public class Servicios implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idServicio;
    private String nombreServicio;
    private String descripcionServicio;
    private double precioInicialServicio;
    private double precioFinalServicio;
    private double precioFijoServicio;
    private boolean estado;

    private int cantidadDisponible;
    private String tiempoServicio;
    private String miniaturaServicio;

    //Relaciones
    //Relacion Categoria Servicio
    @ManyToOne(fetch = FetchType.EAGER)
    private CategoriaServicio categoriaServicio;

    //Relacion con publicaciones
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "servicios")
    @JsonIgnore
    private Set<Publicaciones> listapublicaciones = new HashSet<>();
}
