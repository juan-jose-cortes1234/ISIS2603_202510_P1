package co.edu.uniandes.dse.parcial1.entities;

import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class EstadioEntity extends BaseEntity {

    private String nombre;
    private Long precioAlquiler;
    private String nombreCiudad;
    private String capacidadMax;

    @OneToMany(mappedBy = "estadioAsignado", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private ArrayList<ConciertoEntity> conciertos;
    
}
