package com.api.platanitos.models;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.api.platanitos.enums.RolUsuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = "codigoVerificacion")
@Table(name = "usuario")
public class Usuario extends EntidadBase implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombres;
    private String apellidos;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(name = "password_hash")
    private String passwordHash;
    @Column(name = "foto_url")
    private String fotoUrl;
    private String dni;
    private String telefono;
    private String genero;
    @Column(name = "talla_calzado")
    private String tallaCalzado;
    @Column(name = "talla_ropa")
    private String tallaRopa;
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    private Boolean verificado;
    private Boolean estado;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private RolUsuario rol = RolUsuario.ROLE_CLIENTE;

    @Builder.Default
    @OneToMany(mappedBy = "usuario")
    private Set<CodigoVerificacion> codigoVerificacion = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id != null && id.equals(usuario.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(rol.name()));
    }
    @Override
    public String getPassword() {
        return this.passwordHash;
    }
    @Override
    public String getUsername() {
        return this.email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return this.estado != null ? this.estado : false;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return this.estado != null ? this.estado : false;
    }
}
