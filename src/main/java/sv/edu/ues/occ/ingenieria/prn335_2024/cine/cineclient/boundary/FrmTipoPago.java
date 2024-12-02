package sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.tyrus.client.ClientManager;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.boundary.modelos.TipoPagoModel;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control.TipoPagoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control.TipoPagoEndpoint;

/**
 *
 * @author milag
 */
public class FrmTipoPago extends javax.swing.JPanel {

    TipoPagoBean tpBean = new TipoPagoBean();
    TipoPagoModel modelo = new TipoPagoModel();

    public FrmTipoPago() {
        try {
            ClientManager manager = ClientManager.createClient();
            URI uri = new URI("ws://localhost:9080/cineprn335-1.0-SNAPSHOT/notificadortipopago");
            TipoPagoEndpoint endpoint = new TipoPagoEndpoint();
            endpoint.setFrmTipoPago(this);
            manager.connectToServer(endpoint, uri);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        cargarDatos();
        initComponents();
    }

    public void cargarDatos() {
        try {
            this.modelo.setListaRegistros(tpBean.findRange(0, 50));

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public TipoPagoModel getModelo() {
        return modelo;
    }

    public void setModelo(TipoPagoModel modelo) {
        this.modelo = modelo;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTipoPago = new javax.swing.JTable();

        jLabel1.setText("TipoPago");

        tblTipoPago.setModel(this.getModelo());
        jScrollPane1.setViewportView(tblTipoPago);

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
    private javax.swing.JTable tblTipoPago;
    // End of variables declaration//GEN-END:variables
}
