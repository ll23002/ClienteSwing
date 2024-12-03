package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.PnlTipoPelicula;
/**
 *
 * @author milag
 */
@ClientEndpoint
public class TipoPeliculaEndpoint {
    PnlTipoPelicula pnlTipoPelicula;

    @OnOpen
    public void onOpen(Session s) {
        System.out.println("conecto: " + s);
    }

    @OnMessage
    public void onMessage(String mensaje, Session sesion) {
        System.out.println("Recibio datos: " + mensaje + "sesion: " + sesion);
        pnlTipoPelicula.cargarDatos();
        pnlTipoPelicula.invalidate();
        pnlTipoPelicula.getParent().validate();
        pnlTipoPelicula.getParent().repaint();
    }

    public PnlTipoPelicula getPnlTipoPelicula() {
        return pnlTipoPelicula;
    }

    public void setPnlTipoPelicula(PnlTipoPelicula pnlTipoPelicula) {
        this.pnlTipoPelicula = pnlTipoPelicula;
    }
}
