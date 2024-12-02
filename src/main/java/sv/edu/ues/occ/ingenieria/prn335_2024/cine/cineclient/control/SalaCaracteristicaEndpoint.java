package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.FrmSalaCaracteristica;

/**
 *
 * @author milag
 */
@ClientEndpoint
public class SalaCaracteristicaEndpoint {

    FrmSalaCaracteristica frmSalaCaracteristica;

    @OnOpen
    public void onOpen(Session s) {
        System.out.println("conecto: " + s);
    }

    @OnMessage
    public void onMessage(String mensaje, Session sesion) {
        System.out.println("Recibio datos: " + mensaje + "sesion: " + sesion);
        frmSalaCaracteristica.cargarDatos();
        frmSalaCaracteristica.invalidate();
        frmSalaCaracteristica.getParent().validate();
        frmSalaCaracteristica.getParent().repaint();
    }

    public FrmSalaCaracteristica getFrmSalaCaracteristica() {
        return frmSalaCaracteristica;
    }

    public void setFrmSalaCaracteristica(FrmSalaCaracteristica frmSalaCaracteristica) {
        this.frmSalaCaracteristica = frmSalaCaracteristica;
    }
}
