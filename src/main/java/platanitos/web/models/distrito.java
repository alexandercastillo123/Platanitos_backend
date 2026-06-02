package platanitos.web.models;

import jakarta.persistence.*;


@Entity
@Table(name = "distrito")

public class distrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name ="provincia_id")
    private provincia provincia;

    private String nombre;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(provincia provincia) {
        this.provincia = provincia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
}
