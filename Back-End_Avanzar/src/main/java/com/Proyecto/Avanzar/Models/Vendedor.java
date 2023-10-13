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
@Table(name = "vendedor")
public class Vendedor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVendedor;
    private String nombreEmprendimiento;

    //Relaciones
    //Relacion Usuario
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER ,mappedBy = "vendedor")
    @JsonIgnore
    private Set<Publicaciones> listapublicaciones = new HashSet<>();


    @JsonIgnore
    @OneToOne(mappedBy = "vendedor", cascade = CascadeType.ALL)
    private Detalle_Subscripcion detalleSubscripcion;

}
