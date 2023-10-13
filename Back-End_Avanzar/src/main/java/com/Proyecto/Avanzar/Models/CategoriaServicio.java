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
@Table(name = "categoriaServicio",uniqueConstraints = {@UniqueConstraint(columnNames = "nombreCategoria")})
public class CategoriaServicio implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategoriaServicio;
    private String nombreCategoria;

    public CategoriaServicio(Long idCategoriaServicio, String nombreCategoria) {
        this.idCategoriaServicio = idCategoriaServicio;
        this.nombreCategoria = nombreCategoria;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "categoriaServicio")
    @JsonIgnore
    private Set<Servicios> listaServicios = new HashSet<>();
}
