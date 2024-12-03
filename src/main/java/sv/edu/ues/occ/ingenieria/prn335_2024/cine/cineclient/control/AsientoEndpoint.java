package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.PnlAsiento;

/**
 *
 * @author milag
 */
@ClientEndpoint
public class AsientoEndpoint {

    PnlAsiento pnlAsiento;

    @OnOpen
    public void onOpen(Session s) {
        System.out.println("conecto: " + s);
    }

    @OnMessage
    public void onMessage(String mensaje, Session sesion) {
        System.out.println("Recibio datos: " + mensaje + "sesion: " + sesion);
        pnlAsiento.cargarDatos();
        pnlAsiento.invalidate();
        pnlAsiento.getParent().validate();
        pnlAsiento.getParent().repaint();
    }

    public PnlAsiento getPnlAsiento() {
        return pnlAsiento;
    }

    public void setPnlAsiento(PnlAsiento pnlAsiento) {
        this.pnlAsiento = pnlAsiento;
    }
}
