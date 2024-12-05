/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paquete;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author milag
 */
class PeliculaDatos {
     private static final String URL="jdbc:postgresql://localhost:5432/cine_prn335";
    private static final String USUARIO= "postgres";
    private static final String CONTRASEÑA="abc123";
    
    public List<String>obtenerNombresPeliculas() throws SQLException{
        List<String> nombres= new ArrayList<>();
        try(Connection conexion= DriverManager.getConnection(URL,USUARIO,CONTRASEÑA)){
            System.out.println("Conexión exitosa");
            String query = "SELECT nombre FROM pelicula";
            PreparedStatement pst= conexion.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
        }catch(SQLException e){
            System.out.println("Error al conectar"+e.getMessage());
    }
        return nombres;
    }
}
