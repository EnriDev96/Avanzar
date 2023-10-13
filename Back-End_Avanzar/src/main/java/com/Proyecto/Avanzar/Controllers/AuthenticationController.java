package com.Proyecto.Avanzar.Controllers;

import com.Proyecto.Avanzar.Models.Usuario;
import com.Proyecto.Avanzar.Security.JwtRequest;
import com.Proyecto.Avanzar.Security.JwtResponse;
import com.Proyecto.Avanzar.Security.JwtUtils;
import com.Proyecto.Avanzar.Security.MessageResponse;
import com.Proyecto.Avanzar.Services.implement.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"*"})

@RequestMapping("/api/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/generartoken")
    public ResponseEntity<?> generarToken(@RequestBody JwtRequest jwtRequest) {
        try {
            autenticar(jwtRequest.getUsername(), jwtRequest.getPassword());
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
            String token = this.jwtUtils.generateToken(userDetails);
            JwtResponse response = new JwtResponse(token, (Usuario) userDetails);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }


    private void autenticar(String username,String password) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }catch (DisabledException exception){
            throw  new Exception("USUARIO DESHABILITADO " + exception.getMessage());
        }catch (BadCredentialsException e){
            throw  new Exception("Credenciales invalidas " + e.getMessage());
        }
    }

    /*
    @GetMapping("/usuarioActual")
    public Usuario obtenerUsuarioActual(Principal principal){
        return (Usuario) this.userDetailsService.loadUserByUsername(principal.getName());
    }*/

    //Nuevo metodo para saber que usuario esta logeado
    @GetMapping("/usuarioActual")
    public Usuario obtenerUsuarioActual(@RequestHeader("Authorization") String token) {
        // Extraer el token del encabezado (se debe encontrar en el formato "Bearer <token>")

        String extractedToken = token.replace("Bearer ", "");

        // Utilizar el token para obtener el nombre de usuario
        String username = this.jwtUtils.extractUsername(extractedToken);

        // Utilizar el nombre de usuario para cargar los detalles del usuario
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

        // Devolver los detalles del usuario
        return (Usuario) userDetails;
    }


    @PostMapping("/signInWithToken")
    public ResponseEntity<?> signInUsingToken(@RequestBody JwtRequest jwtRequest) {
        String accessToken = jwtRequest.getAccessToken();
        String username = jwtUtils.extractUsername(accessToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Verificar si el token de acceso es válido y corresponde al usuario correcto
        if (jwtUtils.validateToken(accessToken, userDetails)) {
            Usuario usuario = (Usuario) userDetails;

            // Crear un objeto JwtResponse con el token y el usuario
            JwtResponse response = new JwtResponse(accessToken, usuario);

            // Devolver el objeto JwtResponse en la respuesta
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid access token");
        }
    }

    

//    @PostMapping("/signout")
//    public ResponseEntity<?> logoutUser() {
//        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
//        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
//                .body(new MessageResponse("Se cerro la sesion!"));
//    }
}
