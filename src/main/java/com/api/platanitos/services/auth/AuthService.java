package com.api.platanitos.services.auth;

import com.api.platanitos.repositories.codigo_verificacion.CodigoVerificacionRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.platanitos.dtos.auth.request.CompletarDatosRequest;
import com.api.platanitos.dtos.auth.request.IdentificadorRequest;
import com.api.platanitos.dtos.auth.request.LoginRequest;
import com.api.platanitos.dtos.auth.request.RegisterRequest;
import com.api.platanitos.dtos.auth.response.LoginResponse;
import com.api.platanitos.dtos.auth.response.RegistroResponse;
import com.api.platanitos.enums.RolUsuario;
import com.api.platanitos.enums.TipoRegistro;
import com.api.platanitos.enums.TipoToken;
import com.api.platanitos.jwt.JwtUtil;
import com.api.platanitos.models.CodigoVerificacion;
import com.api.platanitos.models.Usuario;
import com.api.platanitos.repositories.usuario.UsuarioRepository;
import com.api.platanitos.services.send.EmailService;
import com.api.platanitos.services.send.SmsService;
import com.api.platanitos.util.auth.ValidacionFormato;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final CodigoVerificacionRepository codigoVerificacionRepository;
    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final SmsService smsService;
    private final ValidacionFormato validacionFormato;

    public LoginResponse login(LoginRequest req, HttpServletResponse res){
        // Validar los campos antes de consultar e iniciar sesion
        validacionFormato.validarCampos(req.tipo(), req.identificador());
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.identificador(), req.password())
        );
        User userDetails = (User) auth.getPrincipal();
        Usuario usuario = usuarioRepository.findByEmailOrTelefono(req.identificador(), req.identificador())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        String rol = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("El usuario no tiene roles asignados"));
        String token = jwtUtil.generarToken(userDetails.getUsername(), rol);
        Cookie cookie = jwtUtil.generarCookie(token);
        res.addCookie(cookie);
        return LoginResponse.builder()
                .id(usuario.getId())
                .email(userDetails.getUsername())
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                .rol(rol)
                .build();
    }

    @Transactional
    public RegistroResponse registrar(RegisterRequest req){
        // Validar los campos antes de consultar
        validacionFormato.validarCampos(req.tipo(), req.identificador());
        // Se pone optional para evitar la excepcion nullpointer, haciendo una busqueda segura y que permita la caja vacia
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmailOrTelefono(req.identificador(), req.identificador());
        // Validar en caso exista y tenga verificacion o no
        if(usuarioOpt.isPresent()) {
            Usuario user = usuarioOpt.get();
            // Si esta verificado se le contara como usuario existente
            if(Boolean.TRUE.equals(user.getVerificado()))               
                throw new RuntimeException("Usuario existente");
            String tokenCadena = UUID.randomUUID().toString(); 
            // Si esta registrado en la bd pero no esta verificado, buscar su tipo de registro para enviar por email o telefono
            if(TipoRegistro.EMAIL.equals(user.getTipoRegistro()))
                emailService.enviarTokenVerificacion(user.getEmail(), tokenCadena, user);
            else if(TipoRegistro.TELEFONO.equals(user.getTipoRegistro()))
                smsService.enviarSmsVerificacion(user.getTelefono(), tokenCadena, user.getId());
            crearTokenVerificacion(user, tokenCadena, TipoToken.VERIFICACION);
            return RegistroResponse.builder()
                .tipo(req.tipo())
                .identificador(req.identificador())
                .build();
        }

        // Establecer el tipo de registro dependiendo del que envie el frontend
        TipoRegistro tipoReg = "email".equals(req.tipo()) ? TipoRegistro.EMAIL : TipoRegistro.TELEFONO;

        // Preconstruir el usuario
        Usuario.UsuarioBuilder usuarioBuilder = Usuario.builder()
            .dni(req.dni())
            .nombres(req.nombres())
            .apellidos(req.apellidos())
            .rol(RolUsuario.ROLE_CLIENTE)
            .estado(true)
            .verificado(false)
            .tipoRegistro(tipoReg);
        
        // Añadir al plano el email o el telefono dependiendo el tipo de registro
        if("email".equals(req.tipo())) usuarioBuilder.email(req.identificador()); 
        else if("tel".equals(req.tipo())) usuarioBuilder.telefono(req.identificador());
        // Tirar un error si es que el tipo no es email o tel
        else throw new RuntimeException("Tipo de login no valido: "+ req.tipo());

        Usuario usuario = usuarioBuilder.build(); // Construir el usuario final y guardar
        usuarioRepository.save(usuario);
        // Crear token de verificacion
        String tokenCadena = UUID.randomUUID().toString(); // generar una cadena UUID random
        crearTokenVerificacion(usuario, tokenCadena, TipoToken.VERIFICACION);
        // Enviar por email o sms dependiendo del tipo de registro
        if("email".equals(req.tipo()))emailService.enviarTokenVerificacion(req.identificador(), tokenCadena, usuario);
        else if("tel".equals(req.tipo()))smsService.enviarSmsVerificacion(req.identificador(), tokenCadena, usuario.getId());
        
        // Enviar la respuesta al controlador, para que el frontend guardes los datos en el localstorage
        return RegistroResponse.builder()
            .tipo(req.tipo())
            .identificador(req.identificador())
            .build();
    }

    @Transactional
    public LoginResponse completarDatos(CompletarDatosRequest req, HttpServletResponse res){
        CodigoVerificacion codigo = codigoVerificacionRepository.findByTokenAndUsuarioIdWithUsuario(req.token(), req.id())
                .orElseThrow(() -> new RuntimeException("Enlace invalido para este usuario"));
        if(codigo.isExpirado()){
            codigoVerificacionRepository.delete(codigo);
            throw new RuntimeException("Token expirado e invalido");
        }
        // Validar los campos antes de consultar e iniciar sesion
        validacionFormato.validarCamposInverso(req.tipo(), req.identificador());
        Usuario usuario = codigo.getUsuario();
        if("email".equals(req.tipo())) usuario.setTelefono(req.identificador());
        if("tel".equals(req.tipo())) usuario.setEmail(req.identificador());
        usuario.setPasswordHash(passwordEncoder.encode(req.password()));
        usuario.setVerificado(true);

        usuarioRepository.saveAndFlush(usuario);
        codigoVerificacionRepository.delete(codigo);
        LoginRequest login = LoginRequest.builder()
            .tipo(req.tipo())
            .identificador(req.identificador())
            .password(req.password())
            .build();
        return login(login, res);
    }

    public RegistroResponse obtenerEmailoTelefono(IdentificadorRequest req){
        // validar el tipo de autenticacion previa
        if(req.tipo() == null || (!"email".equals(req.tipo()) || !"tel".equals(req.tipo())))
            throw new RuntimeException("Tipo de autenticación inválido: " + req.tipo());
        CodigoVerificacion codigo = codigoVerificacionRepository.findByTokenAndUsuarioIdWithUsuario(req.token(), req.id())
                .orElseThrow(() -> new RuntimeException("Usuario no valido"));
        if(codigo.isExpirado()) {
            codigoVerificacionRepository.delete(codigo);
            throw new RuntimeException("Token expirado e invalido");
        }
        Usuario usuario = codigo.getUsuario();
        String identificador = "email".equals(req.tipo()) ? usuario.getEmail() : usuario.getTelefono();
        return RegistroResponse.builder()
                .tipo(req.tipo())
                .identificador(identificador)
                .build();
    }

    // Metodo privado para generar el token de verificacion o recuperacion
    private void crearTokenVerificacion(Usuario usuario, String token ,TipoToken tipo){
        CodigoVerificacion codigoVerificacion = CodigoVerificacion.builder()
                .usuario(usuario)
                .token(token)
                .tipo(tipo)
                .fechaExpiracion(LocalDateTime.now().plusMinutes(15))
                .build();
        codigoVerificacionRepository.save(codigoVerificacion); // guardar token en la bd
    }
}
