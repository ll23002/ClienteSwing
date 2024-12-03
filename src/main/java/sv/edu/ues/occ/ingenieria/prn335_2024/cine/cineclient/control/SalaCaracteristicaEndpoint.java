package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.PnlSalaCaracteristica;

/**
 *
 * @author milag
 */
@ClientEndpoint
public class SalaCaracteristicaEndpoint {

    PnlSalaCaracteristica pnlSalaCaracteristica;

    @OnOpen
    public void onOpen(Session s) {
        System.out.println("conecto: " + s);
    }

    @OnMessage
    public void onMessage(String mensaje, Session sesion) {
        System.out.println("Recibio datos: " + mensaje + "sesion: " + sesion);
        pnlSalaCaracteristica.cargarDatos();
        pnlSalaCaracteristica.invalidate();
        pnlSalaCaracteristica.getParent().validate();
        pnlSalaCaracteristica.getParent().repaint();
    }

    public PnlSalaCaracteristica getPnlSalaCaracteristica() {
        return pnlSalaCaracteristica;
    }

    public void setPnlSalaCaracteristica(PnlSalaCaracteristica pnlSalaCaracteristica) {
        this.pnlSalaCaracteristica = pnlSalaCaracteristica;
    }
}
