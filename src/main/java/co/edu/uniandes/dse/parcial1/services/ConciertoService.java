package co.edu.uniandes.dse.parcial1.services;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConciertoService {

    @Autowired
    ConciertoRepository conciertoRepository;
    // Complete
    @Transactional
    public ConciertoEntity crearConcierto(ConciertoEntity concierto) throws IllegalOperationException
    {
        log.info("Inicia proceso de creación un concierto");
        //Se le suman dos minutos para ahorrarse problemas con que el .now() no sea muy literal
        if (concierto.getFechaConcierto().isBefore(LocalDateTime.now().plusMinutes(2)))
        {
            throw new IllegalOperationException("No se puede crear un concierto en el pasado");
        }
        else if (concierto.getAforo()<=10)
        {
            throw new IllegalOperationException("Un concierto debe tneer un aforo de más de diez personas");        
        }
        else if (concierto.getPresupuesto()<=1000)
        {
            throw new IllegalOperationException("Un concierto necesita un presupuesto superior a 1000 dólares");
        }

        return conciertoRepository.save(concierto);
    }

}
