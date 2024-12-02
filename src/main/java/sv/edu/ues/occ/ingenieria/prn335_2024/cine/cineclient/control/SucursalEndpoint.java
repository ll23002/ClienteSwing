package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.FrmSucursal;

/**
 *
 * @author milag
 */
@ClientEndpoint
public class SucursalEndpoint {

    FrmSucursal frmSucursal;

    @OnOpen
    public void onOpen(Session s) {
        System.out.println("conecto: " + s);
    }

    @OnMessage
    public void onMessage(String mensaje, Session sesion) {
        System.out.println("Recibio datos: " + mensaje + "sesion: " + sesion);
        frmSucursal.cargarDatos();
        frmSucursal.invalidate();
        frmSucursal.getParent().validate();
        frmSucursal.getParent().repaint();
    }

    public FrmSucursal getFrmSucursal() {
        return frmSucursal;
    }

    public void setFrmSucursal(FrmSucursal frmSucursal) {
        this.frmSucursal = frmSucursal;
    }
}
