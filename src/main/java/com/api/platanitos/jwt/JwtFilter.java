package com.api.platanitos.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.api.platanitos.services.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = null;
        String email = null;
        
        // Verificar si en la peticion hay cookies
        if(request.getCookies() != null){
            for(Cookie cookie: request.getCookies()){ // Bucle para encontrar la cookie de autenticacion
                if("AUTH-TOKEN".equals(cookie.getName())){
                    token = cookie.getValue(); // Setear el valor de cookie a la variable token
                    break;
                }
            }
        }

        if(token != null && SecurityContextHolder.getContext().getAuthentication() == null){
            try{
                email = jwtUtil.extraerEmail(token);
                if(email != null){ // Verificar que si haya un email
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(email); // Obtener el usuario 
                    if(jwtUtil.validarToken(token, email)){
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()); // obtener objeto de autenticacion principal con sus roles del usuario
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // detalles extras de la autenticacion como la ip
                        // setear autenticacion en el contexto de seguridad global de esta peticion
                        SecurityContextHolder.getContext().setAuthentication(authToken); 
                    }
                }
            } catch(Exception e){
                logger.error("Error a la hora de procesar la firma del token" + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
    
}
