package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.PnlSucursal;

/**
 *
 * @author milag
 */
@ClientEndpoint
public class SucursalEndpoint {
    PnlSucursal pnlSucursal;

    @OnOpen
    public void onOpen(Session s) {
        System.out.println("conecto: " + s);
    }

    @OnMessage
    public void onMessage(String mensaje, Session sesion) {
        System.out.println("Recibio datos: " + mensaje + "sesion: " + sesion);
        pnlSucursal.cargarDatos();
        pnlSucursal.invalidate();
        pnlSucursal.getParent().validate();
        pnlSucursal.getParent().repaint();
    }

    public PnlSucursal getPnlSucursal() {
        return pnlSucursal;
    }

    public void setPnlSucursal(PnlSucursal pnlSucursal) {
        this.pnlSucursal = pnlSucursal;
    }
}
