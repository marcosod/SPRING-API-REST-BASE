package com.sistema.blog.sistemablogspringbootapirest.controller;

import com.sistema.blog.sistemablogspringbootapirest.dto.LoginDTO;
import com.sistema.blog.sistemablogspringbootapirest.dto.UsuarioDTO;
import com.sistema.blog.sistemablogspringbootapirest.entity.Rol;
import com.sistema.blog.sistemablogspringbootapirest.entity.Usuario;
import com.sistema.blog.sistemablogspringbootapirest.repositorio.IRolRepositorio;
import com.sistema.blog.sistemablogspringbootapirest.repositorio.IUsuarioRepositorio;
import com.sistema.blog.sistemablogspringbootapirest.seguridad.JWTAuthResonseDTO;
import com.sistema.blog.sistemablogspringbootapirest.seguridad.JwtAuthenticationEntryPoint;
import com.sistema.blog.sistemablogspringbootapirest.seguridad.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private IUsuarioRepositorio usuarioRepositorio;
    @Autowired
    private IRolRepositorio rolRepositorio;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @PostMapping("/iniciarSesion")
    public ResponseEntity<JWTAuthResonseDTO> authenticateUser(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //obtenemos el token del jwtTokenProvider
        String token = jwtTokenProvider.generarToken(authentication);

        return ResponseEntity.ok(new JWTAuthResonseDTO(token));
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody UsuarioDTO registroDTO){
        if(usuarioRepositorio.existsByUsername(registroDTO.getUsername())) {
            return new ResponseEntity<>("Ese nombre de usuario ya existe",HttpStatus.BAD_REQUEST);
        }

        if(usuarioRepositorio.existsByEmail(registroDTO.getEmail())) {
            return new ResponseEntity<>("Ese email de usuario ya existe",HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(registroDTO.getNombre());
        usuario.setUsername(registroDTO.getUsername());
        usuario.setEmail(registroDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));

        Rol roles = rolRepositorio.findByNombre("ROLE_ADMIN").get();

        usuario.setRoles(Collections.singleton(roles).stream().collect(Collectors.toList()));

        usuarioRepositorio.save(usuario);
        return new ResponseEntity<>("Usuario registrado exitosamente",HttpStatus.OK);
    }
}
