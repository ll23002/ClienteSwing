package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.PnlTipoProducto;

/**
 *
 * @author milag
 */
@ClientEndpoint
public class TipoProductoEndpoint {
    PnlTipoProducto pnlTipoProducto;

    @OnOpen
    public void onOpen(Session s) {
        System.out.println("conecto: " + s);
    }

    @OnMessage
    public void onMessage(String mensaje, Session sesion) {
        System.out.println("Recibio datos: " + mensaje + "sesion: " + sesion);
        pnlTipoProducto.cargarDatos();
        pnlTipoProducto.invalidate();
        pnlTipoProducto.getParent().validate();
        pnlTipoProducto.getParent().repaint();
    }

    public PnlTipoProducto getPnlTipoProducto() {
        return pnlTipoProducto;
    }

    public void setPnlTipoProducto(PnlTipoProducto pnlTipoProducto) {
        this.pnlTipoProducto = pnlTipoProducto;
    }
}
