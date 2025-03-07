package co.edu.uniandes.dse.parcial1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EstadioService {

    @Autowired
    EstadioRepository estadioRepository;
    // Complete
    @Transactional
    public EstadioEntity crearEstadio(EstadioEntity estadio) throws IllegalOperationException
    {
        log.info("Creación de un estadio");
        if (estadio.getNombreCiudad().length()<3)
        {
            throw new IllegalOperationException("El nombre de la ciudad tiene que ser de mínimo 3 minutos");
        }
        else if (estadio.getCapacidadMax()<= 1000)
        {
            throw new IllegalOperationException("La capacidad del estadio debe ser superior a 1000 personas");
        }
        else if (estadio.getPrecioAlquiler()<=100000)
        {
            throw new IllegalOperationException("El precio del alquiler del estadio debe ser de mínimo 100000 dólares");
        }

        return estadioRepository.save(estadio);
    }

}
