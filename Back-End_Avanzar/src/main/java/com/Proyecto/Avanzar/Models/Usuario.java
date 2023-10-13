package com.Proyecto.Avanzar.Models;

import com.Proyecto.Avanzar.Security.Authority;
import com.Proyecto.Avanzar.Services.implement.UsuarioServiceImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")

public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String avatar;
    private String password;
    private boolean enabled = true;


    @ManyToOne(fetch = FetchType.EAGER)
    private Persona persona;

    // Columna para el eliminado logico no borrar
    private boolean visible;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "rol")
    @JsonIgnore
    private Set<UsuarioRol> usuarioRoles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "usuario")
    @JsonIgnore
    private Set<Destacados> listaLikes = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "usuario")
    @JsonIgnore
    private Set<Vendedor> listaVendedor = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "usuario")

    @JsonIgnore
    private Set<Comentarios> listacomentarios = new HashSet<>();


    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Set<Authority> autoridades = new HashSet<>();
//        this.usuarioRoles.forEach(usuarioRol -> {
//            autoridades.add(new Authority(usuarioRol.getRol().getRolNombre()));
//        });
//        return autoridades;
//    }

    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.usuarioRoles.stream()
                .map(usuarioRol -> new Authority(usuarioRol.getRol().getRolNombre()))
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", persona=" + persona +
                ", visible=" + visible +
                ", usuarioRoles=" + usuarioRoles +
                ", listaLikes=" + listaLikes +
                ", listavendedor=" + listaVendedor +
                ", listacomentarios=" + listacomentarios +
                '}';
    }

}