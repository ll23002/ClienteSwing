package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import paquete.FrmReserva;

/**
 *
 * @author milag
 */
@ClientEndpoint
public class AsientoEndpoint {

    private FrmReserva frmReserva;
      private Session session;

    @OnOpen
    public void onOpen(Session s) {
        System.out.println("Conectado al WebSocket: " + s);
    }

    @OnMessage
    public void onMessage(String mensaje, Session sesion) {
        System.out.println("Recibió datos: " + mensaje + " Sesión: " + sesion);
        frmReserva.actualizarListaPeliculas(mensaje);
        if (frmReserva != null) {
            frmReserva.actualizarListaAsientos(mensaje); // Llama al método de FrmReserva
        }
    }

    public FrmReserva getFrmReserva() {
        return frmReserva;
    }

    public void setFrmReserva(FrmReserva frmReserva) {
        this.frmReserva = frmReserva;
    }
    
     public void cerrarConexion() {
        try {
            if (session != null && session.isOpen()) {
                session.close();
                System.out.println("Conexión de AsientoEndpoint cerrada.");
            }
        } catch (Exception e) {
            System.err.println("Error al cerrar conexión en AsientoEndpoint: " + e.getMessage());
        }
    }
}
