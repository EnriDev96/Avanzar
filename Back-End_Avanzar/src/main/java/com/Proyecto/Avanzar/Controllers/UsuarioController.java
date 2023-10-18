package com.Proyecto.Avanzar.Controllers;

import com.Proyecto.Avanzar.Models.*;
import com.Proyecto.Avanzar.Repository.UsuarioRepository;
import com.Proyecto.Avanzar.Services.service.RolService;
import com.Proyecto.Avanzar.Services.service.StorageService;
import com.Proyecto.Avanzar.Services.service.UsuarioRolService;
import com.Proyecto.Avanzar.Services.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = {"*"})
//@AllArgsConstructor
public class UsuarioController {
    @Autowired
    private  StorageService storageService;
    @Autowired
    private  HttpServletRequest request;
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @Autowired
    private UsuarioRepository uR;
    @Autowired
    private UsuarioRolService userrol;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${media.location}")
    private String mediaLocation;


    //Metodo para que admita un archivo
    @PostMapping("/upload")
    public Map<String,String> uploadFile(@RequestParam("file") MultipartFile multipartFile){
        String path=storageService.store(multipartFile);
        System.out.println("Ruta de almacenamiento: "+mediaLocation);
        System.out.println("Ruta de almacenamiento: "+path);
        String host=request.getRequestURL().toString().replace(request.getRequestURI(),"");
        String url= ServletUriComponentsBuilder
                .fromHttpUrl(host)
                .path("/api/usuarios/")
                .path(path)
                .toUriString();
        return Map.of("url",url);
    }


    //Metodo para recuperar la imagen desde el sistema de archivos
    @GetMapping("{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws  IOException {
        Resource file=storageService.loadAsResource(filename);
        String contentType= Files.probeContentType(file.getFile().toPath());
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE,contentType)
                .body(file);

    }


    @PostMapping("/registrar/{rolId}")
    public ResponseEntity<Usuario> crear(@RequestBody Usuario r, @PathVariable Long rolId) {
        try {
            if (usuarioService.obtenerUsuario(r.getUsername()) == null) {
                // Buscar el rol por ID
                Rol rol = rolService.findById(rolId);
                r.setPassword(this.bCryptPasswordEncoder.encode(r.getPassword()));
                r.setVisible(true);
                // Crear un nuevo UsuarioRol y establecer las referencias correspondientes
                UsuarioRol usuarioRol = new UsuarioRol();
                usuarioRol.setUsuario(r);
                usuarioRol.setRol(rol);
                // Agregar el UsuarioRol a la lista de roles del usuario
                r.getUsuarioRoles().add(usuarioRol);
                // Guardar el usuario en la base de datos
                // Usuario nuevoUsuario = usuarioService.save(r);
                return new ResponseEntity<>(usuarioService.save(r), HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Metodo para registro de usuario con foto incluida
    @PostMapping("/registrarConFoto/{rolId}")
    public ResponseEntity<Usuario> crear(
            @RequestPart("usuario") String usuarioJson,
            @RequestPart(value = "file", required = false) MultipartFile multipartFile,
            @PathVariable Long rolId,
            HttpServletRequest request
    ) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Usuario r = objectMapper.readValue(usuarioJson, Usuario.class);
            System.out.println("Usuario convertido: " + r.toString());
            if (usuarioService.obtenerUsuario(r.getUsername()) == null) {
                // Buscar el rol por ID
                Rol rol = rolService.findById(rolId);
                r.setPassword(this.bCryptPasswordEncoder.encode(r.getPassword()));
                r.setVisible(true);

                if (multipartFile != null && !multipartFile.isEmpty()) {
                    String path = storageService.store(multipartFile);
                    String host = request.getRequestURL().toString().replace(request.getRequestURI(), "");
                    String url = ServletUriComponentsBuilder
                            .fromHttpUrl(host)
                            .path("/api/usuarios/")
                            .path(path)
                            .toUriString();
                    r.setAvatar(url);
                } else {
                    // Establecer el avatar a nulo (o a una ruta predeterminada de avatar nulo)
                    r.setAvatar(null);
                }

                // Crear un nuevo UsuarioRol y establecer las referencias correspondientes
                UsuarioRol usuarioRol = new UsuarioRol();
                usuarioRol.setUsuario(r);
                usuarioRol.setRol(rol);
                // Agregar el UsuarioRol a la lista de roles del usuario
                r.getUsuarioRoles().add(usuarioRol);

                // Guardar el usuario en la base de datos
                return new ResponseEntity<>(usuarioService.save(r), HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        } catch (Exception e) {
            System.out.println("Error al convertir el JSON del usuario.");
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/actualizarUsuarioConFoto/{usuarioId}")
    public ResponseEntity<Usuario> actualizar(
            @PathVariable Long usuarioId,
            @RequestPart("usuario") String usuarioJson,
            @RequestPart(value = "file", required = false) MultipartFile multipartFile,
            HttpServletRequest request
    ) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Usuario usuarioExistente = usuarioService.findById(usuarioId); // Obtener el usuario existente por ID

            if (usuarioExistente != null) {
                Usuario usuarioActualizado = objectMapper.readValue(usuarioJson, Usuario.class);

                // Actualizar los campos del usuario existente con los datos del usuario actualizado
                usuarioExistente.setUsername(usuarioActualizado.getUsername());
                // Actualizar otros campos según sea necesario

                // Obtener la persona asociada al usuario existente
                Persona personaExistente = usuarioExistente.getPersona();
                Persona personaActualizada = usuarioActualizado.getPersona();

                // Actualizar los campos de la persona existente con los datos de la persona actualizada
                personaExistente.setPrimer_nombre(personaActualizada.getPrimer_nombre());
                personaExistente.setSegundo_nombre(personaActualizada.getSegundo_nombre());
                personaExistente.setPrimer_apellido(personaActualizada.getPrimer_apellido());
                personaExistente.setSegundo_apellido(personaActualizada.getSegundo_apellido());
                personaExistente.setCorreo(personaActualizada.getCorreo());
                personaExistente.setDireccion(personaActualizada.getDireccion());
                personaExistente.setCelular(personaActualizada.getCelular());
                personaExistente.setDescripcion(personaActualizada.getDescripcion());
                personaExistente.setNacionalidad(personaActualizada.getNacionalidad());
                personaExistente.setFecha_nacimiento(personaActualizada.getFecha_nacimiento());
                personaExistente.setGenero(personaActualizada.getGenero());

                if (multipartFile != null && !multipartFile.isEmpty()) {
                    String path = storageService.store(multipartFile);
                    String host = request.getRequestURL().toString().replace(request.getRequestURI(), "");
                    String url = ServletUriComponentsBuilder
                            .fromHttpUrl(host)
                            .path("/api/usuarios/")
                            .path(path)
                            .toUriString();

                    // Eliminar el avatar anterior si existe
                    if (usuarioExistente.getAvatar() != null) {
                        String[] avatarParts = usuarioExistente.getAvatar().split("/");
                        String avatarFilename = avatarParts[avatarParts.length - 1];
                        storageService.delete(avatarFilename); // Elimina el archivo del almacenamiento
                    }

                    usuarioExistente.setAvatar(url); // Actualiza el nuevo avatar
                } else if (usuarioExistente.getAvatar() != null) {
                        usuarioExistente.setAvatar(usuarioActualizado.getAvatar());
                    }else{

                    usuarioExistente.setAvatar(null); // Actualiza el avatar a null
                }
                // Guardar los cambios en la base de datos
                Usuario usuarioActualizadoDB = usuarioService.save(usuarioExistente);
                return new ResponseEntity<>(usuarioActualizadoDB, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Usuario no encontrado
            }

        } catch (Exception e) {
            System.out.println("Error al convertir el JSON del usuario.");
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostConstruct
    public void init() {
        Rol usuario1 = new Rol(1L, "ADMIN");
        Rol usuario2 = new Rol(2L, "RESPONSABLE_VENTAS");
        Rol usuario3 = new Rol(3L, "EMPRENDEDORA");
        Rol usuario4 = new Rol(4L, "CLIENTE");

        rolService.save(usuario1);
        rolService.save(usuario2);
        rolService.save(usuario3);
        rolService.save(usuario4);

    }


    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> obtenerLista() {
        try {

            return new ResponseEntity<>(usuarioService.findByAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscar/{username}")
    public Usuario obtenerUsuario(@PathVariable("username") String username) {
        return usuarioService.obtenerUsuario(username);
    }

    @GetMapping("/buscaruser/{username}")
    public Usuario obtenerIdUsuario(@PathVariable("username") String username) {
        return usuarioService.obtenerId(username);
    }

    @DeleteMapping("/{usuarioId}")
    public void eliminarUsuario(@PathVariable("usuarioId") Long usuarioId) {
        usuarioService.delete(usuarioId);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Usuario> actualizarCliente(@PathVariable Long id, @RequestBody Usuario p) {
        Usuario usu = usuarioService.findById(id);
        UsuarioRol urol=userrol.findByUsuario_UsuarioId(id);
        if (usu == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                usu.setId(p.getId());
                usu.setEnabled(p.isEnabled());
                usu.setName(p.getName());
                usu.setVisible(p.isVisible());
                usu.setUsername(p.getUsername());
                return new ResponseEntity<>(usuarioService.save(usu), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }



    @PutMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarlogic(@PathVariable Long id) {
        Usuario a = usuarioService.findById(id);
        if (a == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            try {
                a.setVisible(false);
                return new ResponseEntity<>(usuarioService.save(a), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
    }

    @GetMapping("/listarResponsables")
    public ResponseEntity<List<Usuario>> obtenerUsuariosConPersonaYRol() {
        List<Usuario> usuarios = usuarioService.obtenerUsuariosConPersonaYRol();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
    @GetMapping("/listarResponsablesEstadoActivo")
    public ResponseEntity<List<Usuario>> FiltradoUserxEstadoActivo() {
        List<Usuario> usuarios = usuarioService.FiltradoUserxEstadoActivo();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
    @GetMapping("/listarResponsablesEstadoInactivo")
    public ResponseEntity<List<Usuario>> FiltradoUserxEstadoInactivo() {
        List<Usuario> usuarios = usuarioService.FiltradoUserxEstadoInactivo();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
    @GetMapping("/listarClienteEstadoActivo")
    public ResponseEntity<List<Usuario>> FiltradoClientexEstadoActivo() {
        List<Usuario> usuarios = usuarioService.FiltradoClientexEstadoActivo();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
    @GetMapping("/listarClienteEstadoInactivo")
    public ResponseEntity<List<Usuario>> FiltradoClientexEstadoInactivo() {
        List<Usuario> usuarios = usuarioService.FiltradoClientexEstadoInactivo();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
    @GetMapping("/listarEmprendedorEstadoActivo")
    public ResponseEntity<List<Usuario>> FiltradoEmpxEstadoActivo() {
        List<Usuario> usuarios = usuarioService.FiltradoEmpxEstadoActivo();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
    @GetMapping("/listarEmprendedorEstadoInactivo")
    public ResponseEntity<List<Usuario>> FiltradoEmpxEstadoInactivo() {
        List<Usuario> usuarios = usuarioService.FiltradoEmpxEstadoInactivo();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/listarEmprendedores")
    public ResponseEntity<List<Usuario>> obtenerUsuariosConPersonaYEmprendedor() {
        List<Usuario> usuarios = usuarioService.obtenerUsuariosConPersonaYEmprendedor();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/listarClientes")
    public ResponseEntity<List<Usuario>> obtenerUsuariosConPersonaYClientes() {
        List<Usuario> usuarios = usuarioService.obtenerUsuariosConPersonaYCliente();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @PutMapping("/actualizar-password")
    public ResponseEntity<?> actualizarContrasena(@RequestBody CambioContrasenaDTO cambioContrasenaDTO, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        String contrasenaActual = cambioContrasenaDTO.getContrasenaActual();
        String contrasenaNueva = cambioContrasenaDTO.getContrasenaNueva();

        try {
            // Verificar si la contraseña actual coincide con la almacenada en la base de datos
            if (!usuarioService.verificarContrasena(username, contrasenaActual)) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
             
            // Actualizar la contraseña en la base de datos
            usuarioService.actualizarContrasena(username, contrasenaNueva);
            return ResponseEntity.status(HttpStatus.OK).build();
             // Respuesta exitosa (HTTP 200 OK)
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build(); // Respuesta de error (HTTP 400 Bad Request)
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Respuesta de error (HTTP 500 Internal Server Error)
        }
    }

    @GetMapping("/usuariosPorPersonaCreada/{idPersona}")
    public ResponseEntity<List<Usuario>> obtenerUsuariosPorPersona(@PathVariable Long idPersona) {
        List<Usuario> usuarios = usuarioService.findUsuariosByPersonaId(idPersona);
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }


    @GetMapping("/buscarUser/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(usuarioService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
