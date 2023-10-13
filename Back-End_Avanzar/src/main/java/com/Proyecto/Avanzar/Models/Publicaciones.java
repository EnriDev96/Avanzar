package com.Proyecto.Avanzar.Models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "publicaciones")
public class Publicaciones implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPublicacion;
    private String tituloPublicacion;
    private String descripcionPublicacion;
    private boolean estado;
    private Date fechaPublicacion;
    private String tiempoTranscurrido;
    private boolean visible;

    public Publicaciones(Long idPublicacion, String tituloPublicacion,String nombreCategoria, Long comenntCount) {
        this.idPublicacion = idPublicacion;
        this.tituloPublicacion = tituloPublicacion;
        this.tiempoTranscurrido=comenntCount+"";
        this.categoria= new Categoria();
        this.categoria.setNombreCategoria(nombreCategoria);
    
    }
    
    @ElementCollection
    private List<String> imagenes = new ArrayList<>();
    //Relaciones
    //Relacion Vendedor
    @ManyToOne(fetch = FetchType.EAGER)
    private Vendedor vendedor;

    //Relacion Categoria
    @ManyToOne(fetch = FetchType.EAGER)
    private Categoria categoria;

    //Relacion Producto
    @ManyToOne(fetch = FetchType.EAGER)
    private Productos productos;

    //Relacion Servicio
    @ManyToOne(fetch = FetchType.EAGER)
    private Servicios servicios;

    //Relacion TipoPost no se ha definido aun

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "publicaciones")
    @JsonIgnore
    private Set<Comentarios> listacomentarios = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "publicaciones")
    @JsonIgnore
    private Set<Destacados> listadestacados = new HashSet<>();


}
