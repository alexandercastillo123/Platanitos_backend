package com.api.platanitos.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;



@Component
public class JwtUtil {
    
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    @Value("${jwt.expiration}")
    private Long EXPIRATION;

    private SecretKey key;

    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    // Funcion para generar el token
    public String generarToken(String email, String rol){
        return Jwts.builder()
                .subject(email)
                .claim("role", rol)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key)
                .compact();
    }

    // Funcion para generar la cookie
    public Cookie generarCookie(String token){
        Cookie cookie = new Cookie("AUTH-TOKEN", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge((int)(EXPIRATION / 1000));
        return cookie;
    }

    // Funcion para extraer el email del token jwt
    public String extraerEmail(String token){
        return extraerClaims(token).getSubject();
    }

    // Funcion para validar el token por su email y expiracion
    public Boolean validarToken(String token, String email){
        try{
            String tokenEmail = extraerEmail(token);
            return (tokenEmail.equals(email));
        } catch(Exception e){
            return false;
        }
    }

    // Funcion de ayuda para obtener todas las claims del token
    private Claims extraerClaims(String token){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
