package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.tyrus.client.ClientManager;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.modelos.ReservaModel;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control.ReservaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control.ReservaEndpoint;

/**
 *
 * @author milag
 */
public class PnlReserva extends javax.swing.JPanel {

    ReservaBean rBean = new ReservaBean();
    ReservaModel modelo = new ReservaModel();

    public PnlReserva() {
        try {
            ClientManager manager = ClientManager.createClient();
            URI uri = new URI("ws://localhost:9080/cineprn335-1.0-SNAPSHOT/notificadorreserva");
            ReservaEndpoint endpoint = new ReservaEndpoint();
            endpoint.setPnlReserva(this);
            manager.connectToServer(endpoint, uri);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        cargarDatos();
        initComponents();
    }

    public ReservaModel getModelo() {
        return modelo;
    }

    public void setModelo(ReservaModel modelo) {
        this.modelo = modelo;
    }

    public void cargarDatos() {
        try {
            this.modelo.setListaRegistros(rBean.findRange(0, 50));
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblReserva = new javax.swing.JTable();

        jLabel1.setText("Reserva");

        tblReserva.setModel(this.getModelo());
        jScrollPane1.setViewportView(tblReserva);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 597, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(296, 296, 296)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblReserva;
    // End of variables declaration//GEN-END:variables
}
