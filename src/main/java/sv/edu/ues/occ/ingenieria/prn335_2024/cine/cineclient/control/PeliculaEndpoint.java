package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.FrmPelicula;

/**
 *
 * @author milag
 */
@ClientEndpoint
public class PeliculaEndpoint {
    FrmPelicula frmPelicula;

    @OnOpen
    public void onOpen(Session s) {
        System.out.println("conecto: " + s);
    }

    @OnMessage
    public void onMessage(String mensaje, Session sesion) {
        System.out.println("Recibio datos: " + mensaje + "sesion: " + sesion);
        frmPelicula.cargarDatos();
        frmPelicula.invalidate();
        frmPelicula.getParent().validate();
        frmPelicula.getParent().repaint();
    }

    public FrmPelicula getFrmPelicula() {
        return frmPelicula;
    }

    public void setFrmSucursal(FrmPelicula frmPelicula) {
        this.frmPelicula = frmPelicula;
    }
}
