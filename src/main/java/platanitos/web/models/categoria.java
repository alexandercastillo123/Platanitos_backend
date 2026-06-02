package platanitos.web.models;

import jakarta.persistence.*;

@Entity
@Table(name= "categoria")

public class categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String slug;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private categoria padre;
    private Integer nivel;
    private String icono;
    private Boolean activa;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getSlug() {
        return slug;
    }
    public void setSlug(String slug) {
        this.slug = slug;
    }
    public categoria getPadre() {
        return padre;
    }
    public void setPadre(categoria padre) {
        this.padre = padre;
    }
    public Integer getNivel() {
        return nivel;
    }
    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }
    public String getIcono() {
        return icono;
    }
    public void setIcono(String icono) {
        this.icono = icono;
    }
    public Boolean getActiva() {
        return activa;
    }
    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    
}
