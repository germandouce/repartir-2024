
package ar.com.grupoesfera.repartir.steps.gastosGrupales;

import ar.com.grupoesfera.repartir.model.Gasto;
import ar.com.grupoesfera.repartir.model.Grupo;
import ar.com.grupoesfera.repartir.services.MontosService;
import ar.com.grupoesfera.repartir.steps.FastCucumberSteps;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class TotalDeGastosGrupalesSteps extends FastCucumberSteps {

    private Grupo grupo;
    private MontosService montosService;
    private Gasto gasto;
    private IllegalArgumentException excepcion;

    @Dado("que un grupo tiene un total de {int} pesos")
    public void queUnGrupoTieneUnTotalDePesos(int total) {
        grupo = new Grupo();
        grupo.setTotal(BigDecimal.valueOf(total, 0));
        montosService = new MontosService();
    }

    @Cuando("se registra un gasto de {int} pesos")
    public void seRegistraUnGastoDePesos(int monto) {
        gasto = new Gasto();
        gasto.setMonto(BigDecimal.valueOf(monto, 0));
        montosService.acumularAlTotal(grupo, gasto);
    }
    
    @Cuando("se intenta registrar un gasto de {int} pesos")
    public void seIntentaRegistrarUnGastoDePesos(int monto) {
        gasto = new Gasto();
        gasto.setMonto(BigDecimal.valueOf(monto, 0));

        try {
            montosService.acumularAlTotal(grupo, gasto);
        } catch (IllegalArgumentException e) {
            excepcion = e;
        }
    }

    @Entonces("el gasto no se registra")
    public void elGastoNoSeRegistra() {
        assertThat(excepcion).isNotNull();
    }
    
    @Entonces("el total del grupo es de {int} pesos")
    public void elTotalDelGrupoEsDePesos(int totalEsperado) {
        assertThat(grupo.getTotal())
                .isEqualByComparingTo(BigDecimal.valueOf(totalEsperado, 0));
    }

}
