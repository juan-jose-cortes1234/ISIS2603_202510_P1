package co.edu.uniandes.dse.parcial1.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({ConciertoService.class, EstadioService.class})
public class EstadioServiceTest {
    
    @Autowired
	private ConciertoService conciertoService;

    @Autowired
	private EstadioService estadioService;

	private PodamFactory factory = new PodamFactoryImpl();

    @Autowired
	private TestEntityManager entityManager;

	private EstadioEntity estadio ; 

    private ConciertoEntity concierto;

	/**
	 * Configuración inicial de la prueba.
	 */
	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

	/**
	 * Limpia las tablas que están implicadas en la prueba.
	 */
	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from ConciertoEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from EstadioEntity").executeUpdate();
		
	}

	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */
	private void insertData() {
		concierto = factory.manufacturePojo(ConciertoEntity.class);
        
        
        
	}

    @Test
    public void crearEstadioTest() throws IllegalOperationException
    {
        estadio = factory.manufacturePojo(EstadioEntity.class);
        estadio.setNombreCiudad("Bogotá");
        estadio.setPrecioAlquiler(Long.valueOf(1000000));
        estadio.setCapacidadMax(20000);
        List<ConciertoEntity> conciertos = new ArrayList<ConciertoEntity>();
        estadio.setConciertos(conciertos);
        EstadioEntity result = estadioService.crearEstadio(estadio);
        
        assertEquals(estadio.getCapacidadMax(), result.getCapacidadMax());
        assertEquals(estadio.getNombre(), result.getNombre());
        assertEquals(estadio.getNombreCiudad(),result.getNombreCiudad());
        assertEquals(estadio.getPrecioAlquiler(),result.getPrecioAlquiler());
    }

    @Test
    public void crearEstadioConCapacidadMenorTest() throws IllegalOperationException
    {
        estadio = factory.manufacturePojo(EstadioEntity.class);
        estadio.setNombreCiudad("Bogotá");
        estadio.setPrecioAlquiler(Long.valueOf(1000000));
        estadio.setCapacidadMax(500);
        List<ConciertoEntity> conciertos = new ArrayList<ConciertoEntity>();
        estadio.setConciertos(conciertos);
        
        assertThrows(IllegalOperationException.class, ()->{
			estadioService.crearEstadio(estadio);
		});
    }
}
