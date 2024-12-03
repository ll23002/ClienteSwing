package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.PnlPeliculaCaracteristica;

/**
 *
 * @author milag
 */
@ClientEndpoint
public class PeliculaCaracteristicaEndpoint {
    PnlPeliculaCaracteristica pnlPeliculaCaracteristica;

    @OnOpen
    public void onOpen(Session s) {
        System.out.println("conecto: " + s);
    }

    @OnMessage
    public void onMessage(String mensaje, Session sesion) {
        System.out.println("Recibio datos: " + mensaje + "sesion: " + sesion);
        pnlPeliculaCaracteristica.cargarDatos();
        pnlPeliculaCaracteristica.invalidate();
        pnlPeliculaCaracteristica.getParent().validate();
        pnlPeliculaCaracteristica.getParent().repaint();
    }

    public PnlPeliculaCaracteristica getPnlPeliculaCaracteristica() {
        return pnlPeliculaCaracteristica;
    }

    public void setPnlPeliculaCaracteristica(PnlPeliculaCaracteristica pnlPeliculaCaracteristica) {
        this.pnlPeliculaCaracteristica = pnlPeliculaCaracteristica;
    }
}
