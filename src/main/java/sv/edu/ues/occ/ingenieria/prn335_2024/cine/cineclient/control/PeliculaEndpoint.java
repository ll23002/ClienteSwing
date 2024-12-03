package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.PnlPelicula;

/**
 *
 * @author milag
 */
@ClientEndpoint
public class PeliculaEndpoint {
    PnlPelicula pnlPelicula;

    @OnOpen
    public void onOpen(Session s) {
        System.out.println("conecto: " + s);
    }

    @OnMessage
    public void onMessage(String mensaje, Session sesion) {
        System.out.println("Recibio datos: " + mensaje + "sesion: " + sesion);
        pnlPelicula.cargarDatos();
        pnlPelicula.invalidate();
        pnlPelicula.getParent().validate();
        pnlPelicula.getParent().repaint();
    }

    public PnlPelicula getPnlPelicula() {
        return pnlPelicula;
    }

    public void setPnlPelicula(PnlPelicula pnlPelicula) {
        this.pnlPelicula = pnlPelicula;
    }
}
