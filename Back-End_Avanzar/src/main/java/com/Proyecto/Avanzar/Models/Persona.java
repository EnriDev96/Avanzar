package com.Proyecto.Avanzar.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "persona",uniqueConstraints = {@UniqueConstraint(columnNames = "cedula")})

public class Persona implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_persona;
    private String cedula;
    private String primer_nombre;
    private String segundo_nombre;
    private String primer_apellido;
    private String segundo_apellido;
    private String genero;
    private String fecha_nacimiento;
    private String nacionalidad;
    private String descripcion;
    private String correo;
    private String direccion;
    private String celular;
    private boolean estado;

    //relacion
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "persona")
    @JsonIgnore
    private Set<Usuario> listausuarios = new HashSet<>();
}
