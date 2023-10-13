package com.Proyecto.Avanzar.Security;

import com.Proyecto.Avanzar.Models.Usuario;

public class JwtResponse {

    private String token;
    private Usuario usuario;

    public JwtResponse(String token, Usuario usuario) {
        this.token = token;
        this.usuario = usuario;
    }

    public JwtResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
