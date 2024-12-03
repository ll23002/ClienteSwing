package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.PnlSala;

/**
 *
 * @author milag
 */
@ClientEndpoint
public class SalaEndpoint {

    PnlSala pnlSala;

    @OnOpen
    public void onOpen(Session s) {
        System.out.println("conecto: " + s);
    }

    @OnMessage
    public void onMessage(String mensaje, Session sesion) {
        System.out.println("Recibio datos: " + mensaje + "sesion: " + sesion);
        pnlSala.cargarDatos();
        pnlSala.invalidate();
        pnlSala.getParent().validate();
        pnlSala.getParent().repaint();
    }

    public PnlSala getPnlSala() {
        return pnlSala;
    }

    public void setPnlSala(PnlSala pnlSala) {
        this.pnlSala = pnlSala;
    }
}
