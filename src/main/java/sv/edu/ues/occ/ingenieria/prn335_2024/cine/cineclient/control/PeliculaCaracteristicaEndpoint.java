package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.FrmPeliculaCaracteristica;

/**
 *
 * @author milag
 */
@ClientEndpoint
public class PeliculaCaracteristicaEndpoint {
    FrmPeliculaCaracteristica frmPeliculaCaracteristica;

    @OnOpen
    public void onOpen(Session s) {
        System.out.println("conecto: " + s);
    }

    @OnMessage
    public void onMessage(String mensaje, Session sesion) {
        System.out.println("Recibio datos: " + mensaje + "sesion: " + sesion);
        frmPeliculaCaracteristica.cargarDatos();
        frmPeliculaCaracteristica.invalidate();
        frmPeliculaCaracteristica.getParent().validate();
        frmPeliculaCaracteristica.getParent().repaint();
    }

    public FrmPeliculaCaracteristica getFrmPeliculaCaracteristica() {
        return frmPeliculaCaracteristica;
    }

    public void setFrmPeliculaCaracteristica(FrmPeliculaCaracteristica frmPeliculaCaracteristica) {
        this.frmPeliculaCaracteristica = frmPeliculaCaracteristica;
    }
}
