package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control;
import javax.websocket.ClientEndpoint; 
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.FrmTipoPago;
/**
 *
 * @author milag
 */
@ClientEndpoint
public class TipoPagoEndpoint {
    FrmTipoPago frmTipoPago;

    @OnOpen
    public void onOpen(Session s) {
        System.out.println("conecto: " + s);
    }

    @OnMessage
    public void onMessage(String mensaje, Session sesion) {
        System.out.println("Recibio datos: " + mensaje + "sesion: " + sesion);
        frmTipoPago.cargarDatos();
        frmTipoPago.invalidate();
        frmTipoPago.getParent().validate();
        frmTipoPago.getParent().repaint();
    }

    public FrmTipoPago getFrmTipoPago() {
        return frmTipoPago;
    }

    public void setFrmTipoPago(FrmTipoPago frmTipoPago) {
        this.frmTipoPago = frmTipoPago;
    }
}
