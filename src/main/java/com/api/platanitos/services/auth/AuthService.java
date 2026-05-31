package com.api.platanitos.services.auth;

import com.api.platanitos.repositories.codigo_verificacion.CodigoVerificacionRepository;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.platanitos.dtos.auth.request.CompletarDatosRequest;
import com.api.platanitos.dtos.auth.request.LoginRequest;
import com.api.platanitos.dtos.auth.request.RegisterRequest;
import com.api.platanitos.dtos.auth.response.LoginResponse;
import com.api.platanitos.dtos.auth.response.RegistroResponse;
import com.api.platanitos.enums.RolUsuario;
import com.api.platanitos.jwt.JwtUtil;
import com.api.platanitos.models.CodigoVerificacion;
import com.api.platanitos.models.Usuario;
import com.api.platanitos.repositories.usuario.UsuarioRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final CodigoVerificacionRepository codigoVerificacionRepository;
    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest req, HttpServletResponse res){
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.identificador(), req.passwordHash())
        );
        Usuario usuario = (Usuario) auth.getPrincipal();
        String rol = usuario.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("El usuario no tiene roles asignados"));
        String token = jwtUtil.generarToken(usuario.getUsername(), rol);
        Cookie cookie = jwtUtil.generarCookie(token);
        res.addCookie(cookie);
        return LoginResponse.builder()
                .id(usuario.getId())
                .email(usuario.getEmail())
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                .rol(rol)
                .build();
    }

    public RegistroResponse registrar(RegisterRequest req){
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmailOrTelefono(req.identificador(), req.identificador());
        if(usuarioOpt.isEmpty()){
            if(req.tipo() != null){
                Usuario.UsuarioBuilder usuarioBuilder = Usuario.builder()
                    .dni(req.dni())
                    .nombres(req.nombres())
                    .apellidos(req.apellidos())
                    .rol(RolUsuario.ROLE_CLIENTE)
                    .estado(true)
                    .verificado(false);
                if("email".equals(req.tipo())) usuarioBuilder.email(req.identificador());
                else if("tel".equals(req.tipo())) usuarioBuilder.telefono(req.identificador());
                else throw new RuntimeException("Tipo de login no valido: "+ req.tipo());
                Usuario usuario = usuarioBuilder.build();
                usuarioRepository.save(usuario);
                return RegistroResponse.builder()
                    .identificador(req.identificador())
                    .build();
            }
            throw new RuntimeException("Tipo de login vacio");
        }
        throw new RuntimeException("Usuario existente");
    }

    public LoginResponse completarDatos(CompletarDatosRequest req, HttpServletResponse res){
        CodigoVerificacion codigo = codigoVerificacionRepository.findByTokenAndUsuarioIdWithUsuario(req.token(), req.id())
                .orElseThrow(() -> new RuntimeException("Enlace invalido para este usuario"));
        Usuario usuario = codigo.getUsuario();
        if("email".equals(req.tipo())) usuario.setEmail(req.identificador());
        if("tel".equals(req.tipo())) usuario.setTelefono(req.identificador());
        usuario.setPasswordHash(passwordEncoder.encode(req.passwordHash()));
        usuario.setVerificado(true);

        usuarioRepository.saveAndFlush(usuario);
        codigoVerificacionRepository.delete(codigo);
        LoginRequest login = LoginRequest.builder()
            .tipo(req.tipo())
            .identificador(req.identificador())
            .passwordHash(req.passwordHash())
            .build();
        return login(login, res);
    }
}
