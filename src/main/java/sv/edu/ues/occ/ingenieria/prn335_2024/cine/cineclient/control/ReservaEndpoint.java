package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.PnlReserva;
import javax.websocket.ClientEndpoint;

/**
 *
 * @author milag
 */
@ClientEndpoint
public class ReservaEndpoint {

    PnlReserva pnlReserva;

    @OnOpen
    public void onOpen(Session s) {
        System.out.println("conecto: " + s);
    }

    @OnMessage
    public void onMessage(String mensaje, Session sesion) {
        System.out.println("Recibio datos: " + mensaje + "sesion: " + sesion);
        pnlReserva.cargarDatos();
        pnlReserva.invalidate();
        pnlReserva.getParent().validate();
        pnlReserva.getParent().repaint();
    }

    public PnlReserva getPnlReserva() {
        return pnlReserva;
    }

    public void setPnlReserva(PnlReserva pnlReserva) {
        this.pnlReserva = pnlReserva;
    }
}
