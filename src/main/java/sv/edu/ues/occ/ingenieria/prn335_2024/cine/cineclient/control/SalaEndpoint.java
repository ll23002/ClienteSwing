package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.FrmSala;

/**
 *
 * @author milag
 */
@ClientEndpoint
public class SalaEndpoint {

    FrmSala frmSala;

    @OnOpen
    public void onOpen(Session s) {
        System.out.println("conecto: " + s);
    }

    @OnMessage
    public void onMessage(String mensaje, Session sesion) {
        System.out.println("Recibio datos: " + mensaje + "sesion: " + sesion);
        frmSala.cargarDatos();
        frmSala.invalidate();
        frmSala.getParent().validate();
        frmSala.getParent().repaint();
    }

    public FrmSala getFrmSala() {
        return frmSala;
    }

    public void setFrmSala(FrmSala frmSala) {
        this.frmSala = frmSala;
    }
}
