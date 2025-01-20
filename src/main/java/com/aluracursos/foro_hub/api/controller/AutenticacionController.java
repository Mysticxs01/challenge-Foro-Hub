package com.aluracursos.foro_hub.api.controller;


import com.aluracursos.foro_hub.api.infra.security.DatosJWTToken;
import com.aluracursos.foro_hub.api.infra.security.TokenService;
import com.aluracursos.foro_hub.api.model.dto.usuario.DatosAutenticacionUsuario;
import com.aluracursos.foro_hub.api.model.Usuario;
import com.aluracursos.foro_hub.api.model.dto.usuario.DatosRegistroUsuario;
import com.aluracursos.foro_hub.api.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AutenticacionController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario){
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.login(), datosAutenticacionUsuario.clave());
        var datosAutenticados = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken((Usuario) datosAutenticados.getPrincipal());
        return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
    }

    @PostMapping("/register")
    public ResponseEntity registrarUsuario(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario) {
        // Verificar si el usuario ya existe
        if (usuarioRepository.findByLogin(datosRegistroUsuario.login()) != null) {
            return ResponseEntity.badRequest().body("El nombre de usuario ya está en uso");
        }

        // Cifrar la contraseña antes de guardarla
        String contraCifrada = passwordEncoder.encode(datosRegistroUsuario.clave());


        // Crear y guardar el nuevo usuario
        Usuario nuevoUsuario = new Usuario(
                datosRegistroUsuario.login(),
                contraCifrada
        );
        usuarioRepository.save(nuevoUsuario);

        return ResponseEntity.ok("Usuario registrado exitosamente");
    }

}
