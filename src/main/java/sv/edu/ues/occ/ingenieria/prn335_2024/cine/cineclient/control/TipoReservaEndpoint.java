package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control;

import javax.websocket.ClientEndpoint; 
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.PnlTipoReserva;

/**
 *
 * @author milag
 */
@ClientEndpoint
public class TipoReservaEndpoint {
    PnlTipoReserva pnlTipoReserva;

    @OnOpen
    public void onOpen(Session s) {
        System.out.println("conecto: " + s);
    }

    @OnMessage
    public void onMessage(String mensaje, Session sesion) {
        System.out.println("Recibio datos: " + mensaje + "sesion: " + sesion);
        pnlTipoReserva.cargarDatos();
        pnlTipoReserva.invalidate();
        pnlTipoReserva.getParent().validate();
        pnlTipoReserva.getParent().repaint();
    }

    public PnlTipoReserva getPnlTipoReserva() {
        return pnlTipoReserva;
    }

    public void setPnlTipoReserva(PnlTipoReserva pnlTipoReserva) {
        this.pnlTipoReserva = pnlTipoReserva;
    }
}
