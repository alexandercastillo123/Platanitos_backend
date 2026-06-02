package platanitos.web.models;

import jakarta.persistence.*;

@Entity
@Table(name="marca")

public class marca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private String logo_url;


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getLogo_url() {
        return logo_url;
    }
    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    
}
