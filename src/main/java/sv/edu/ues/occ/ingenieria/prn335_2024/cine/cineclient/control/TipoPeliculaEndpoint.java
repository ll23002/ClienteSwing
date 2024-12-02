package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.FrmTipoPelicula;
/**
 *
 * @author milag
 */
@ClientEndpoint
public class TipoPeliculaEndpoint {
    FrmTipoPelicula frmTipoPelicula;

    @OnOpen
    public void onOpen(Session s) {
        System.out.println("conecto: " + s);
    }

    @OnMessage
    public void onMessage(String mensaje, Session sesion) {
        System.out.println("Recibio datos: " + mensaje + "sesion: " + sesion);
        frmTipoPelicula.cargarDatos();
        frmTipoPelicula.invalidate();
        frmTipoPelicula.getParent().validate();
        frmTipoPelicula.getParent().repaint();
    }

    public FrmTipoPelicula getFrmTipoPelicula() {
        return frmTipoPelicula;
    }

    public void setFrmTipoPelicula(FrmTipoPelicula frmTipoPelicula) {
        this.frmTipoPelicula = frmTipoPelicula;
    }
}
