package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.FrmTipoAsiento;

/**
 *
 * @author milag
 */
@ClientEndpoint
public class TipoAsientoEndpoint {

    FrmTipoAsiento frmTipoAsiento;

    @OnOpen
    public void onOpen(Session s) {
        System.out.println("conecto: " + s);
    }

    @OnMessage
    public void onMessage(String mensaje, Session sesion) {
        System.out.println("Recibio datos: " + mensaje + "sesion: " + sesion);
        frmTipoAsiento.cargarDatos();
        frmTipoAsiento.invalidate();
        frmTipoAsiento.getParent().validate();
        frmTipoAsiento.getParent().repaint();
    }

    public FrmTipoAsiento getFrmTipoAsiento() {
        return frmTipoAsiento;
    }

    public void setFrmTipoAsiento(FrmTipoAsiento frmTipoAsiento) {
        this.frmTipoAsiento = frmTipoAsiento;
    }
}
