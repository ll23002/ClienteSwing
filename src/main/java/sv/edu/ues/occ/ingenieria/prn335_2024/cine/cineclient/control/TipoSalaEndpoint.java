package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.PnlTipoSala;

/**
 *
 * @author alexander
 */
@ClientEndpoint
public class TipoSalaEndpoint {
    
    PnlTipoSala pnlTipoSala;
    
    @OnOpen
    public void onOpen(Session s){
        System.out.println("conecto: " + s);
    }
    
    @OnMessage
    public void onMessage(String mensaje, Session sesion){
        System.out.println("Recibio datos: " + mensaje + "sesion: " + sesion);
        pnlTipoSala.cargarDatos();
        pnlTipoSala.invalidate();
        pnlTipoSala.getParent().validate();
        pnlTipoSala.getParent().repaint();
    }

    public PnlTipoSala getPnlTipoSala() {
        return pnlTipoSala;
    }

    public void setPnlTipoSala(PnlTipoSala pnlTipoSala) {
        this.pnlTipoSala = pnlTipoSala;
    }
}
