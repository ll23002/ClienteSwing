package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import paquete.FrmReserva;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.PnlProgramacion;

/**
 *
 * @author milag
 */
@ClientEndpoint
public class ProgramacionEndpoint {

    private FrmReserva frmReserva;

    PnlProgramacion pnlProgramacion;

    private Session session;

    @OnOpen
    public void onOpen(Session s) {
        System.out.println("conecto: " + s);
    }

    @OnMessage
    public void onMessage(String mensaje, Session sesion) {
        System.out.println("Recibio datos: " + mensaje + "sesion: " + sesion);
        frmReserva.actualizarListaPeliculas(mensaje);
        pnlProgramacion.cargarDatos();
        pnlProgramacion.invalidate();
        pnlProgramacion.getParent().validate();
        pnlProgramacion.getParent().repaint();
        if (frmReserva != null) {
            frmReserva.actualizarListaPeliculas(mensaje); // Llama al método de FrmReserva
        }
    }

    // Método para cerrar la conexión WebSocket
    public void cerrarConexion() {
        try {
            if (session != null && session.isOpen()) {
                session.close();
                System.out.println("Conexión cerrada.");
            }
        } catch (Exception e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

  


    public PnlProgramacion getPnlProgramacion() {
        return pnlProgramacion;
    }

    public void setPnlProgramacion(PnlProgramacion pnlProgramacion) {
        this.pnlProgramacion = pnlProgramacion;
    }

    public FrmReserva getFrmReserva() {
        return frmReserva;
    }

    public void setFrmReserva(FrmReserva frmReserva) {
        this.frmReserva = frmReserva;
    }
}
