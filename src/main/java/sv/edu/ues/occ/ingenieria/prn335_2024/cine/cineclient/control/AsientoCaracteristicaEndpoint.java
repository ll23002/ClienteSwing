package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.PnlAsientoCaracteristica;

/**
 *
 * @author milag
 */
@ClientEndpoint
public class AsientoCaracteristicaEndpoint {

    PnlAsientoCaracteristica pnlAsientoCaracteristica;

    @OnOpen
    public void onOpen(Session s) {
        System.out.println("conecto: " + s);
    }

    @OnMessage
    public void onMessage(String mensaje, Session sesion) {
        System.out.println("Recibio datos: " + mensaje + "sesion: " + sesion);
        pnlAsientoCaracteristica.cargarDatos();
        pnlAsientoCaracteristica.invalidate();
        pnlAsientoCaracteristica.getParent().validate();
        pnlAsientoCaracteristica.getParent().repaint();
    }

    public PnlAsientoCaracteristica getPnlAsientoCaracteristica() {
        return pnlAsientoCaracteristica;
    }

    public void setPnlAsientoCaracteristica(PnlAsientoCaracteristica pnlAsientoCaracteristica) {
        this.pnlAsientoCaracteristica = pnlAsientoCaracteristica;
    }
}
