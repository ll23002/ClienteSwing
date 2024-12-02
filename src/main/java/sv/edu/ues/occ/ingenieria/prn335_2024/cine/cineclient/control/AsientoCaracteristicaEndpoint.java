package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.FrmAsientoCaracteristica;

/**
 *
 * @author milag
 */
@ClientEndpoint
public class AsientoCaracteristicaEndpoint {

    FrmAsientoCaracteristica AsientoCaracteristica;

    @OnOpen
    public void onOpen(Session s) {
        System.out.println("conecto: " + s);
    }

    @OnMessage
    public void onMessage(String mensaje, Session sesion) {
        System.out.println("Recibio datos: " + mensaje + "sesion: " + sesion);
        AsientoCaracteristica.cargarDatos();
        AsientoCaracteristica.invalidate();
        AsientoCaracteristica.getParent().validate();
        AsientoCaracteristica.getParent().repaint();
    }

    public FrmAsientoCaracteristica getFrmAsientoCaracteristica() {
        return AsientoCaracteristica;
    }

    public void setFrmAsientoCaracteristica(FrmAsientoCaracteristica AsientoCaracteristica) {
        this.AsientoCaracteristica = AsientoCaracteristica;
    }
}
