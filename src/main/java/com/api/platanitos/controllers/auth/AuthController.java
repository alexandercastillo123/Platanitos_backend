package com.api.platanitos.controllers.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.platanitos.dtos.api.Response;
import com.api.platanitos.dtos.auth.request.CompletarDatosRequest;
import com.api.platanitos.dtos.auth.request.LoginRequest;
import com.api.platanitos.dtos.auth.request.RegisterRequest;
import com.api.platanitos.dtos.auth.response.LoginResponse;
import com.api.platanitos.dtos.auth.response.RegistroResponse;
import com.api.platanitos.services.auth.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Response<LoginResponse>> login(@RequestBody LoginRequest req, HttpServletResponse res) {
        LoginResponse response = authService.login(req, res);
        return ResponseEntity.ok(
            Response.<LoginResponse>builder()
                .success(true)
                .message("Se inicio sesion correctamente")
                .data(response)  
                .build()
        );
    }

    @PostMapping("/register")
    public ResponseEntity<Response<RegistroResponse>> registrar(@RequestBody RegisterRequest req) {
        RegistroResponse response = authService.registrar(req);
        return ResponseEntity.ok(
            Response.<RegistroResponse>builder()
                .success(true)
                .message("Se creo el usuario correctamente")
                .data(response)
                .build()   
        );
    }

    @PostMapping("/verificar-usuario")
    public ResponseEntity<Response<LoginResponse>> verificarUsuario(@RequestBody CompletarDatosRequest req, HttpServletResponse res) {
        LoginResponse response = authService.completarDatos(req, res);
        return ResponseEntity.ok(
            Response.<LoginResponse>builder()
                .success(true)
                .message("El usuario se verifico correctamente, inciando sesion")
                .data(response)
                .build()
        );
    }
    
    
}
