package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control;

import javax.websocket.ClientEndpoint; 
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.FrmTipoReserva;

/**
 *
 * @author milag
 */
@ClientEndpoint
public class TipoReservaEndpoint {
    FrmTipoReserva frmTipoReserva;

    @OnOpen
    public void onOpen(Session s) {
        System.out.println("conecto: " + s);
    }

    @OnMessage
    public void onMessage(String mensaje, Session sesion) {
        System.out.println("Recibio datos: " + mensaje + "sesion: " + sesion);
        frmTipoReserva.cargarDatos();
        frmTipoReserva.invalidate();
        frmTipoReserva.getParent().validate();
        frmTipoReserva.getParent().repaint();
    }

    public FrmTipoReserva getFrmTipoReserva() {
        return frmTipoReserva;
    }

    public void setFrmTipoReserva(FrmTipoReserva frmTipoReserva) {
        this.frmTipoReserva = frmTipoReserva;
    }
}