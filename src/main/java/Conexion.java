import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

public class Conexion {
    public static void obtenerPeliculas() {
        try {
            // Obtener la conexión desde la base de datos configurada
            DataSource ds = (DataSource) javax.naming.InitialContext.doLookup("jdbc/postgres_localhost_5432_cine_prn335");
            Connection conexion = ds.getConnection();
            
            // Ejecutar una consulta
            String query = "SELECT * FROM pelicula";
            PreparedStatement pst = conexion.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            // Procesar los resultados
            while (rs.next()) {
                System.out.println("Película: " + rs.getString("nombre"));
            }
            
            // Cerrar la conexión
            rs.close();
            pst.close();
            conexion.close();

        } catch (SQLException | javax.naming.NamingException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        obtenerPeliculas();
    }
}
