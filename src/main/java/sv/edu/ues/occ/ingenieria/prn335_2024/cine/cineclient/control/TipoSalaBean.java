
package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import java.util.List;

/**
 *
 * @author alexander
 */
public class TipoSalaBean {
    
    Client cliente;
    WebTarget webTarget;
    
    public TipoSalaBean(){
        cliente = ClientBuilder.newClient();
        webTarget = cliente.target("http://localhost:9080/cineprn335-1.0-SNAPSHOT/v1/");
    }
    
    
    public List<TipoSala> findRange() {
       
        Response respuesta = webTarget.path("tiposala")
                .queryParam("first", 0)
                .queryParam("max", 3)
                .request(MediaType.APPLICATION_JSON).get();
        List<TipoSala> listaRespuesta = respuesta.readEntity(new GenericType<List<TipoSala>>() {

        });
        
    }
    
}