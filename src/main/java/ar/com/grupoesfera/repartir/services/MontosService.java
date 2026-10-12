package ar.com.grupoesfera.repartir.services;

import ar.com.grupoesfera.repartir.model.Gasto;
import ar.com.grupoesfera.repartir.model.Grupo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MontosService {

    public void inicializarTotal(Grupo grupo) {

        grupo.setTotal(BigDecimal.valueOf(0,2));
    }

    public void acumularAlTotal(Grupo grupo, Gasto gasto) {
        BigDecimal nuevoTotal = grupo.getTotal().add(gasto.getMonto());
    
        if (nuevoTotal.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "El total del grupo no debe ser negativo"
            );
        }
    
        grupo.setTotal(nuevoTotal);
    }

}
