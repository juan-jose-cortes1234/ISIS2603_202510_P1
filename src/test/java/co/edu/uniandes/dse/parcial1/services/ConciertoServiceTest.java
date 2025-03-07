package co.edu.uniandes.dse.parcial1.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;

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
public class ConciertoServiceTest {
    
    @Autowired
	private ConciertoService conciertoService;

	

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
		estadio = factory.manufacturePojo(EstadioEntity.class);
        estadio.setCapacidadMax(100);
        estadio.setNombreCiudad("lituania");
        entityManager.persist(estadio);
        
	}

    @Test
    public void crearConciertoTest() throws IllegalOperationException
    {
        concierto = factory.manufacturePojo(ConciertoEntity.class);
        concierto.setFechaConcierto(LocalDateTime.now().plusDays(4));
        concierto.setEstadioAsignado(estadio);
        concierto.setAforo(90);
        ConciertoEntity result = conciertoService.crearConcierto(concierto);
        assertEquals(concierto.getAforo(), result.getAforo());
        assertEquals(concierto.getEstadioAsignado().getId(), result.getEstadioAsignado().getId());
        assertEquals(concierto.getFechaConcierto(), result.getFechaConcierto());
        assertEquals(concierto.getNombre(), result.getNombre());
        assertEquals(concierto.getNombreCantante(), result.getNombreCantante());
        assertEquals(concierto.getPresupuesto(), result.getPresupuesto());
        
    }

    @Test
    public void crearConciertoConMenorAforoTest() throws IllegalOperationException
    {
        concierto = factory.manufacturePojo(ConciertoEntity.class);
        concierto.setFechaConcierto(LocalDateTime.now().plusDays(4));
        concierto.setEstadioAsignado(estadio);
        concierto.setAforo(9);
        
        assertThrows(IllegalOperationException.class, ()->{
			conciertoService.crearConcierto(concierto);
		});
        
        
    }

	
}
