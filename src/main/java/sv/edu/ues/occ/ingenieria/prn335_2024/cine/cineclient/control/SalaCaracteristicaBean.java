package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import java.util.List;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.GenericType;
import java.util.logging.Level;
import java.util.logging.Logger;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.entity.SalaCaracteristica;

/**
 *
 * @author milag
 */
public class SalaCaracteristicaBean {

    Client cliente;
    WebTarget webTarget;

    public SalaCaracteristicaBean() {
        cliente = ClientBuilder.newClient();
        webTarget = cliente.target("http://localhost:9080/cineprn335-1.0-SNAPSHOT/v1/");
    }

    public List<SalaCaracteristica> findRange(int first, int max) {
        try {
            Response respuesta = webTarget.path("salacaracteristica")
                    .queryParam("first", first)
                    .queryParam("max", max)
                    .request(MediaType.APPLICATION_JSON).get();
            if (respuesta.getStatus() == 200) {
                List<SalaCaracteristica> listaRespuesta = respuesta.readEntity(new GenericType<List<SalaCaracteristica>>() {
                });
                if (listaRespuesta != null && !listaRespuesta.isEmpty()) {
                    return listaRespuesta;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return List.of();
    }
}
