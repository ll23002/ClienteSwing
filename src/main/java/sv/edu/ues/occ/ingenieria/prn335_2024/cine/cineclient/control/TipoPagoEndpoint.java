package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control;
import javax.websocket.ClientEndpoint; 
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.PnlTipoPago;
/**
 *
 * @author milag
 */
@ClientEndpoint
public class TipoPagoEndpoint {
    PnlTipoPago pnlTipoPago;

    @OnOpen
    public void onOpen(Session s) {
        System.out.println("conecto: " + s);
    }

    @OnMessage
    public void onMessage(String mensaje, Session sesion) {
        System.out.println("Recibio datos: " + mensaje + "sesion: " + sesion);
        pnlTipoPago.cargarDatos();
        pnlTipoPago.invalidate();
        pnlTipoPago.getParent().validate();
        pnlTipoPago.getParent().repaint();
    }

    public PnlTipoPago getPnlTipoPago() {
        return pnlTipoPago;
    }

    public void setPnlTipoPago(PnlTipoPago pnlTipoPago) {
        this.pnlTipoPago = pnlTipoPago;
    }
}
