package paquete;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.entity.Reserva;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.cineclient.entity.TipoReserva;

public class FrmReserva extends javax.swing.JFrame {

    private String FechaSeleccionada;

    private final CardLayout cardLayout;
    private DefaultListModel<String> modeloAsientos; // Modelo para jList1 (asientos disponibles)
    private DefaultListModel<Asiento> modeloAsientosSeleccionados; // Modelo para jList2 (asientos seleccionados)

    private DefaultListModel<Programacion> modeloObjetoProgramacion; // Modelo para jList2 (asientos seleccionados)

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
        modeloObjetoProgramacion = new DefaultListModel<>();

        Seleccionados = new ArrayList<>();
        jList1.setModel(modeloAsientos);

        // Configurar WebSocket
        //conectarWebSocketPeliculas();
        conectarWebSocket();
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

    public int consultaIdMaxReserva() {
        String url = "jdbc:postgresql://localhost:5432/cine_prn335"; // Cambia "tu_base_datos" por el nombre de tu base de datos
        String user = "postgres"; // Cambia esto si tu usuario es diferente
        String password = "abc123"; // Cambia esto si tu contraseña es diferente

        int maxId = -1; // Inicializa con un valor inválido para verificar posibles errores

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // Consulta para obtener el ID más alto de la tabla Reserva
            String query = "SELECT MAX(id_reserva) AS max_id FROM reserva";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) { // Si hay resultados
                maxId = rs.getInt("max_id"); // Obtener el valor del ID más alto
                System.out.println("El ID más alto en la tabla Reserva es: " + maxId);
            } else {
                System.out.println("No se encontraron reservas en la tabla.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maxId; // Retorna el ID más alto, o -1 si ocurrió algún problema
    }

    public void actualizarListaPeliculas(String mensaje) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Programacion> programaciones = mapper.readValue(mensaje, new TypeReference<List<Programacion>>() {
            });
            ModeloCmbPelicualas.removeAllElements();
            if (programaciones.isEmpty()) {
                ModeloCmbPelicualas.addElement("No hay películas disponibles");
            } else {
                for (Programacion programacion : programaciones) {
                    String nombrePelicula = programacion.getIdPelicula().getNombre();
                    String nombrePF = programacion.getIdPelicula().getNombre() + "    " + programacion.getDesde();
                    System.out.println("Nombre:" + nombrePelicula);
                    if (nombrePelicula != null && !nombrePelicula.isEmpty()) {
                        ModeloCmbPelicualas.addElement(nombrePelicula);
                        modeloObjetoProgramacion.addElement(programacion);
                        lblFechap4.setText(nombrePF);
                    }
                }
            }
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
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblAsientosp3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblFechap4 = new javax.swing.JLabel();
        btnConfirmarR = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        idReserva = new javax.swing.JLabel();
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

        txtNombrePeliculap1.setEditable(false);
        txtNombrePeliculap1.setText("Pelicula");
        txtNombrePeliculap1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombrePeliculap1ActionPerformed(evt);
            }
        });

        cmbNombrePeliculap1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbNombrePeliculap1ActionPerformed(evt);
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
                            .addGroup(jpnlReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtNombrePeliculap1)
                                .addComponent(cmbNombrePeliculap1, 0, 173, Short.MAX_VALUE))))
                    .addGroup(jpnlReservaLayout.createSequentialGroup()
                        .addGap(433, 433, 433)
                        .addComponent(NextUno)))
                .addContainerGap(593, Short.MAX_VALUE))
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
                .addGap(124, 124, 124)
                .addComponent(txtNombrePeliculap1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(cmbNombrePeliculap1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 182, Short.MAX_VALUE)
                .addComponent(NextUno)
                .addGap(35, 35, 35))
            .addGroup(jpnlReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpnlReservaLayout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(514, Short.MAX_VALUE)))
        );

        pnlPrincipal.add(jpnlReserva, "card1");

        pnlAsientos.setBackground(new java.awt.Color(0, 51, 153));

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 224, Short.MAX_VALUE))
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

        Confirmacion.setBackground(new java.awt.Color(0, 51, 153));

        Reservar.setText("Reservar");
        Reservar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReservarActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Confimación");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Pelicula:");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Este es el resumen de tu orden:");

        lblAsientosp3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        lblAsientosp3.setForeground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Asientos:");

        jLabel9.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Fecha:");

        lblFechap4.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        lblFechap4.setForeground(new java.awt.Color(255, 255, 255));

        btnConfirmarR.setText("Confirmar reserva ");
        btnConfirmarR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarRActionPerformed(evt);
            }
        });

        jLabel7.setText("ID reserva");

        javax.swing.GroupLayout ConfirmacionLayout = new javax.swing.GroupLayout(Confirmacion);
        Confirmacion.setLayout(ConfirmacionLayout);
        ConfirmacionLayout.setHorizontalGroup(
            ConfirmacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ConfirmacionLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(106, 106, 106))
            .addGroup(ConfirmacionLayout.createSequentialGroup()
                .addGroup(ConfirmacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ConfirmacionLayout.createSequentialGroup()
                        .addGap(216, 216, 216)
                        .addComponent(Reservar))
                    .addGroup(ConfirmacionLayout.createSequentialGroup()
                        .addGap(171, 171, 171)
                        .addGroup(ConfirmacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(ConfirmacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(btnConfirmarR, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 133, Short.MAX_VALUE)
                                .addComponent(lblAsientosp3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(ConfirmacionLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(179, 179, 179)
                                .addComponent(jLabel9))
                            .addComponent(lblFechap4, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(ConfirmacionLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 265, Short.MAX_VALUE)
                                .addComponent(jLabel7)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(idReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(177, 177, 177))
        );
        ConfirmacionLayout.setVerticalGroup(
            ConfirmacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ConfirmacionLayout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(ConfirmacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ConfirmacionLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ConfirmacionLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(ConfirmacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(idReserva))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ConfirmacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFechap4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblAsientosp3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81)
                .addComponent(btnConfirmarR)
                .addGap(53, 53, 53)
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
                .addContainerGap(59, Short.MAX_VALUE))
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

        idReserva.setText(String.valueOf(consultaIdMaxReserva()));

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
                lblAsientosp3.setText(asiento.getNombre());
            }

        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione al menos un asiento.");
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void txtNombrePeliculap1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombrePeliculap1ActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_txtNombrePeliculap1ActionPerformed

    private void cmbNombrePeliculap1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbNombrePeliculap1ActionPerformed
        // TODO add your handling code here:
        String el = (String) cmbNombrePeliculap1.getSelectedItem();
        txtNombrePeliculap1.setText(el);

    }//GEN-LAST:event_cmbNombrePeliculap1ActionPerformed

    private void btnConfirmarRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarRActionPerformed
        int itemSeleccionado = cmbNombrePeliculap1.getSelectedIndex();
        
        Programacion elementoSeleccionado = modeloObjetoProgramacion.getElementAt(itemSeleccionado);
        
        TipoReserva TipoReserva = new TipoReserva();
        TipoReserva.setIdTipoReserva(1);
        TipoReserva.setActivo(true);
        
        Long idReservaConvertido = (long) consultaIdMaxReserva() +1;

        
      
        
       

        // Recorrer el modelo y comparar índices
      

        Reserva datosPreparados = new Reserva();

        datosPreparados.setEstado("CREADO en el Cliente");
        datosPreparados.setFechaReserva(elementoSeleccionado.getDesde());
        datosPreparados.setIdProgramacion(elementoSeleccionado);
        datosPreparados.setIdReserva(idReservaConvertido);
        datosPreparados.setIdTipoReserva(TipoReserva);
    }//GEN-LAST:event_btnConfirmarRActionPerformed

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
    private javax.swing.JButton btnConfirmarR;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JComboBox<String> cmbNombrePeliculap1;
    private javax.swing.JLabel idReserva;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel jpnlReserva;
    private javax.swing.JLabel lblAsientosp3;
    private javax.swing.JLabel lblFechap4;
    private javax.swing.JPanel pnlAsientos;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JTextField txtNombrePeliculap1;
    // End of variables declaration//GEN-END:variables
}
