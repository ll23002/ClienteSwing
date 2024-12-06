package paquete;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import org.glassfish.tyrus.client.ClientManager;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control.AsientoEndpoint;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.control.ProgramacionEndpoint;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.entity.Asiento;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.entity.Programacion;

public class FrmReserva extends javax.swing.JFrame {

    private final CardLayout cardLayout;
    private DefaultListModel<String> modeloAsientos; // Modelo para jList1 (asientos disponibles)
    private DefaultListModel<Asiento> modeloAsientosSeleccionados; // Modelo para jList2 (asientos seleccionados)

    private DefaultListModel<Asiento> ObjetoAsientoModelo;
    private List<Asiento> Seleccionados; // Lista para guardar los asientos seleccionados
    private DefaultComboBoxModel<String> ModeloCmbPelicualas;
    private ProgramacionEndpoint programacionEndpoint;
    private AsientoEndpoint asientoEndpoint;

    public FrmReserva() {
        initComponents();
        cardLayout = (CardLayout) pnlPrincipal.getLayout();
        pnlPrincipal.add(jpnlReserva, "card1"); // Cambié de "Tarjeta1" a "card2"
        pnlPrincipal.add(pnlAsientos, "card2"); // Cambié de "Tarjeta2" a "card3"
        pnlPrincipal.add(Confirmacion, "card3");

        // Inicializar modelos para las listas
        modeloAsientos = new DefaultListModel<>();
        modeloAsientosSeleccionados = new DefaultListModel<>();
        ObjetoAsientoModelo = new DefaultListModel<>();
        ModeloCmbPelicualas = new DefaultComboBoxModel<>();

        Seleccionados = new ArrayList<>();
        jList1.setModel(modeloAsientos);

        // Configurar WebSocket
        conectarWebSocketPeliculas();

        txtF.setVisible(false);
        // Agregar un listener para detectar cambios en la fecha del JDateChooser
        txtFecha.getDateEditor().addPropertyChangeListener(evt -> {
            if ("date".equals(evt.getPropertyName())) {
                // Obtener la fecha seleccionada
                Date date = txtFecha.getDate();
                if (date != null) {
                    // Formatear la fecha a un String
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String fecha = sdf.format(date);

                    // Colocar la fecha en el JTextField
                    txtF.setText(fecha);
                }
            }
        });
    }

    public void actualizarListaAsientos(String mensaje) {
        try {
            // Configura ObjectMapper para procesar el JSON
            ObjectMapper mapper = new ObjectMapper();

            // Especifica que se espera una lista de objetos `Asiento`
            List<Asiento> asientos = mapper.readValue(mensaje, new TypeReference<List<Asiento>>() {
            });

            // Limpia el modelo de la lista antes de actualizar
            modeloAsientos.clear();

            // Agrega los nombres de los asientos al modelo
            for (Asiento asiento : asientos) {
                modeloAsientos.addElement(asiento.getNombre());
                ObjetoAsientoModelo.addElement(asiento);
            }

        } catch (Exception e) {
            System.err.println("Error actualizando la lista de asientos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void conectarWebSocket() {
        try {
            
             if (asientoEndpoint != null) {
            asientoEndpoint.cerrarConexion();
        }
            ClientManager clientManager = ClientManager.createClient();
            URI uri = new URI("ws://localhost:9080/cineprn335-1.0-SNAPSHOT/notificadorasiento");

            asientoEndpoint = new AsientoEndpoint(); // Crea y asigna la instancia
            asientoEndpoint.setFrmReserva(this);

            clientManager.connectToServer(asientoEndpoint, uri);
        } catch (Exception e) {
            System.err.println("Error conectando al WebSocket: " + e.getMessage());
        }
    }

    public void conectarWebSocketPeliculas() {
        try {
            
             if (programacionEndpoint != null) {
            programacionEndpoint.cerrarConexion();
        }
            ClientManager clientManager = ClientManager.createClient();
            URI uri = new URI("ws://localhost:9080/cineprn335-1.0-SNAPSHOT/notificadorprogramacion");
            //URI uri = new URI("ws://localhost:9080/cineprn335/notificadorprogramacion");

            programacionEndpoint = new ProgramacionEndpoint();
            programacionEndpoint.setFrmReserva(this);

            clientManager.connectToServer(programacionEndpoint, uri);
        } catch (Exception e) {
            System.err.println("Error conectando al WebSocket de películas: " + e.getMessage());
        }
    }

    public void actualizarListaPeliculas(String mensaje) {
        try {
            // Configura ObjectMapper para procesar el JSON
            ObjectMapper mapper = new ObjectMapper();

            // Especifica que se espera una lista de objetos Programacion
            List<Programacion> programaciones = mapper.readValue(mensaje, new TypeReference<List<Programacion>>() {
            });

            // Limpia el modelo del ComboBox antes de actualizar
            ModeloCmbPelicualas.removeAllElements();

            // Agrega las películas al modelo
            if (programaciones.isEmpty()) {
                ModeloCmbPelicualas.addElement("No hay películas disponibles");
            } else {
                for (Programacion programacion : programaciones) {
                    String nombrePelicula = programacion.getIdPelicula().getNombre(); // Asumiendo que Programacion tiene este campo
                    System.out.println("Nombre:" + nombrePelicula);
                    if (nombrePelicula != null && !nombrePelicula.isEmpty()) {
                        ModeloCmbPelicualas.addElement(nombrePelicula);
                    }
                }
            }

            // Asignar el modelo al JComboBox
            cmbNombrePeliculap1.setModel(ModeloCmbPelicualas);

        } catch (Exception e) {
            System.err.println("Error actualizando la lista de películas: " + e.getMessage());
            e.printStackTrace();

            // Manejo de error: mostrar un mensaje genérico en el ComboBox
            ModeloCmbPelicualas.removeAllElements();
            ModeloCmbPelicualas.addElement("Error al cargar películas");
            cmbNombrePeliculap1.setModel(ModeloCmbPelicualas);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlPrincipal = new javax.swing.JPanel();
        jpnlReserva = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNombrePeliculap1 = new javax.swing.JTextField();
        cmbNombrePeliculap1 = new javax.swing.JComboBox<>();
        txtFecha = new com.toedter.calendar.JDateChooser();
        txtF = new javax.swing.JTextField();
        NextUno = new javax.swing.JButton();
        pnlAsientos = new javax.swing.JPanel();
        NextDos = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        btnAgregar = new javax.swing.JButton();
        btnQuitar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        Confirmacion = new javax.swing.JPanel();
        Reservar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlPrincipal.setLayout(new java.awt.CardLayout());

        jpnlReserva.setBackground(new java.awt.Color(0, 51, 153));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Datos generales de la reserva");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Datos Generales");

        txtNombrePeliculap1.setText("Pelicula");

        txtFecha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFechaKeyReleased(evt);
            }
        });

        txtF.setEditable(false);
        txtF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFActionPerformed(evt);
            }
        });

        NextUno.setText("Next");
        NextUno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextUnoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnlReservaLayout = new javax.swing.GroupLayout(jpnlReserva);
        jpnlReserva.setLayout(jpnlReservaLayout);
        jpnlReservaLayout.setHorizontalGroup(
            jpnlReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlReservaLayout.createSequentialGroup()
                .addGroup(jpnlReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlReservaLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(jpnlReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addGroup(jpnlReservaLayout.createSequentialGroup()
                                .addComponent(txtF, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jpnlReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNombrePeliculap1)
                                    .addComponent(cmbNombrePeliculap1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)))))
                    .addGroup(jpnlReservaLayout.createSequentialGroup()
                        .addGap(433, 433, 433)
                        .addComponent(NextUno)))
                .addContainerGap(589, Short.MAX_VALUE))
            .addGroup(jpnlReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpnlReservaLayout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addComponent(jLabel3)
                    .addContainerGap(908, Short.MAX_VALUE)))
        );
        jpnlReservaLayout.setVerticalGroup(
            jpnlReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlReservaLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addGroup(jpnlReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addComponent(txtNombrePeliculap1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(cmbNombrePeliculap1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                .addComponent(NextUno)
                .addGap(35, 35, 35))
            .addGroup(jpnlReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpnlReservaLayout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(418, Short.MAX_VALUE)))
        );

        pnlPrincipal.add(jpnlReserva, "card1");

        NextDos.setText("Next");
        NextDos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextDosActionPerformed(evt);
            }
        });

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnQuitar.setText("Quitar");

        jScrollPane2.setViewportView(jList2);

        javax.swing.GroupLayout pnlAsientosLayout = new javax.swing.GroupLayout(pnlAsientos);
        pnlAsientos.setLayout(pnlAsientosLayout);
        pnlAsientosLayout.setHorizontalGroup(
            pnlAsientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAsientosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(NextDos)
                .addGap(487, 487, 487))
            .addGroup(pnlAsientosLayout.createSequentialGroup()
                .addGap(270, 270, 270)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlAsientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAgregar)
                    .addComponent(btnQuitar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(401, Short.MAX_VALUE))
        );
        pnlAsientosLayout.setVerticalGroup(
            pnlAsientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAsientosLayout.createSequentialGroup()
                .addGroup(pnlAsientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAsientosLayout.createSequentialGroup()
                        .addGap(180, 180, 180)
                        .addComponent(btnAgregar)
                        .addGap(18, 18, 18)
                        .addComponent(btnQuitar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 140, Short.MAX_VALUE))
                    .addGroup(pnlAsientosLayout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addGroup(pnlAsientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addComponent(jScrollPane2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(NextDos)
                .addGap(61, 61, 61))
        );

        pnlPrincipal.add(pnlAsientos, "card2");

        Reservar.setText("Reservar");
        Reservar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReservarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ConfirmacionLayout = new javax.swing.GroupLayout(Confirmacion);
        Confirmacion.setLayout(ConfirmacionLayout);
        ConfirmacionLayout.setHorizontalGroup(
            ConfirmacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConfirmacionLayout.createSequentialGroup()
                .addGap(428, 428, 428)
                .addComponent(Reservar)
                .addContainerGap(576, Short.MAX_VALUE))
        );
        ConfirmacionLayout.setVerticalGroup(
            ConfirmacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ConfirmacionLayout.createSequentialGroup()
                .addContainerGap(405, Short.MAX_VALUE)
                .addComponent(Reservar)
                .addGap(50, 50, 50))
        );

        pnlPrincipal.add(Confirmacion, "card3");

        jLabel1.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel1.setText("Reservacion");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(pnlPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(412, 412, 412)
                        .addComponent(jLabel1)))
                .addContainerGap(937, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFActionPerformed

    }//GEN-LAST:event_txtFActionPerformed

    private void txtFechaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaKeyReleased

    }//GEN-LAST:event_txtFechaKeyReleased

    private void NextUnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextUnoActionPerformed

        cardLayout.show(pnlPrincipal, "card2"); // Cambia a la tarjeta "card3"

        // Cerrar la conexión de ProgramacionEndpoint si está activa
        if (programacionEndpoint != null) {
            programacionEndpoint.cerrarConexion();
        } else {
            System.err.println("programacionEndpoint no estaba inicializado.");
        }

        // Conectar a AsientoEndpoint
        conectarWebSocket();


    }//GEN-LAST:event_NextUnoActionPerformed

    private void NextDosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextDosActionPerformed

        cardLayout.show(pnlPrincipal, "card3"); // Cambia a la tarjeta "card3"

        // Cerrar la conexión de AsientoEndpoint si está activa
        if (asientoEndpoint != null) {
            asientoEndpoint.cerrarConexion();
        } else {
            System.err.println("asientoEndpoint no estaba inicializado.");
        }
    }//GEN-LAST:event_NextDosActionPerformed

    private void ReservarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReservarActionPerformed

        cardLayout.show(pnlPrincipal, "card1"); // Cambia a la tarjeta "card3"
    }//GEN-LAST:event_ReservarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // Obtener los índices seleccionados en jList1
        int[] selectedIndices = jList1.getSelectedIndices();

        if (selectedIndices.length > 0) {
            for (int index : selectedIndices) {
                // Obtener el asiento seleccionado del modelo de la lista
                Asiento asientoSeleccionado = ObjetoAsientoModelo.getElementAt(index);

                // Verificar si ya está seleccionado (opcional)
                if (!Seleccionados.contains(asientoSeleccionado)) {
                    Seleccionados.add(asientoSeleccionado); // Guardar en la lista auxiliar
                }
            }
            JOptionPane.showMessageDialog(this, "Asientos seleccionados guardados.");

            // Mostrar en consola los asientos seleccionados
            System.out.println("Asientos seleccionados:");
            for (Asiento asiento : Seleccionados) {
                System.out.println("ID: " + asiento.getIdAsiento() + ", Nombre: " + asiento.getNombre());
            }

        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione al menos un asiento.");
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    // txtFecha.getDate();
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmReserva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmReserva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmReserva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmReserva.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmReserva().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Confirmacion;
    private javax.swing.JButton NextDos;
    private javax.swing.JButton NextUno;
    private javax.swing.JButton Reservar;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JComboBox<String> cmbNombrePeliculap1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel jpnlReserva;
    private javax.swing.JPanel pnlAsientos;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JTextField txtF;
    private com.toedter.calendar.JDateChooser txtFecha;
    private javax.swing.JTextField txtNombrePeliculap1;
    // End of variables declaration//GEN-END:variables
}
