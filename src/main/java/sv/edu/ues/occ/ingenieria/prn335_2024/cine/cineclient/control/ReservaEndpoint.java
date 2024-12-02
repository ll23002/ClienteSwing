package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.FrmReserva;
import javax.websocket.ClientEndpoint;

/**
 *
 * @author milag
 */
@ClientEndpoint
public class ReservaEndpoint {

    FrmReserva frmReserva;

    @OnOpen
    public void onOpen(Session s) {
        System.out.println("conecto: " + s);
    }

    @OnMessage
    public void onMessage(String mensaje, Session sesion) {
        System.out.println("Recibio datos: " + mensaje + "sesion: " + sesion);
        frmReserva.cargarDatos();
        frmReserva.invalidate();
        frmReserva.getParent().validate();
        frmReserva.getParent().repaint();
    }

    public FrmReserva getFrmReserva() {
        return frmReserva;
    }

    public void setFrmReserva(FrmReserva frmReserva) {
        this.frmReserva = frmReserva;
    }
}
