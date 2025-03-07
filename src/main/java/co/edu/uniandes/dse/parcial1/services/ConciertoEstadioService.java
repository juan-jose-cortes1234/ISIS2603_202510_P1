package co.edu.uniandes.dse.parcial1.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConciertoEstadioService {

    @Autowired
    EstadioRepository estadioRepository;

    @Autowired
    ConciertoRepository conciertoRepository;

    // Complete
    @Transactional
    public void asignarConciertoAEstadio(Long conciertoId, Long estadioId) throws IllegalOperationException
    {
        Optional<ConciertoEntity> conciertoPackage = conciertoRepository.findById(conciertoId);
        Optional<EstadioEntity> estadioPackage = estadioRepository.findById(estadioId);
        if (conciertoPackage.isEmpty())
        {
            throw new EntityNotFoundException();
        }
        if (estadioPackage.isEmpty())
        {
            throw new EntityNotFoundException();
        }
        ConciertoEntity concierto = conciertoPackage.get();
        EstadioEntity estadio = estadioPackage.get();

        if (concierto.getAforo()>estadio.getCapacidadMax())
        {
            throw new IllegalOperationException("La capacidad de un concierto no puede superar la de su estadio asignado");
        }
        else if (estadio.getPrecioAlquiler()>concierto.getPresupuesto())
        {
            throw new IllegalOperationException("Con este presupuesto no se puede asignar un concierto a este estadio");
        }
        else if (Duration.between(LocalDateTime.now(), concierto.getFechaConcierto()).toDays()<2)
        {
            throw new IllegalOperationException("Siempre debe existir un tiempo mínimo de 2 días entre los conciertos asociados a un estadio");
        }
        List<ConciertoEntity> conciertos = estadio.getConciertos();
        conciertos.add(concierto);
        concierto.setEstadioAsignado(estadio);
        estadioRepository.save(estadio);
        conciertoRepository.save(concierto);
        
    }


}
