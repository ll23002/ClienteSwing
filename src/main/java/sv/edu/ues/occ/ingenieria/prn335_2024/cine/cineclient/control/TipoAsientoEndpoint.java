package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.PnlTipoAsiento;

/**
 *
 * @author milag
 */
@ClientEndpoint
public class TipoAsientoEndpoint {

    PnlTipoAsiento pnlTipoAsiento;

    @OnOpen
    public void onOpen(Session s) {
        System.out.println("conecto: " + s);
    }

    @OnMessage
    public void onMessage(String mensaje, Session sesion) {
        System.out.println("Recibio datos: " + mensaje + "sesion: " + sesion);
        pnlTipoAsiento.cargarDatos();
        pnlTipoAsiento.invalidate();
        pnlTipoAsiento.getParent().validate();
        pnlTipoAsiento.getParent().repaint();
    }

    public PnlTipoAsiento getPnlTipoAsiento() {
        return pnlTipoAsiento;
    }

    public void setPnlTipoAsiento(PnlTipoAsiento pnlTipoAsiento) {
        this.pnlTipoAsiento = pnlTipoAsiento;
    }
}
