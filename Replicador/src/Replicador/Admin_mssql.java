/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Replicador;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author angel
 */
public class Admin_mssql {
    private Connection conexion;

    // Constructor
    public Admin_mssql(String nombreInstancia, String nombreBD, int puerto, String usuario, String password) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:" + puerto + ";databaseName=" + nombreBD+ ";encrypt=false";
            conexion = DriverManager.getConnection(url, usuario, password);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void probarConexion(String nombreInstancia, String nombreBD, int puerto, String usuario, String password) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:" + puerto + ";databaseName=" + nombreBD+ ";encrypt=false";
            conexion = DriverManager.getConnection(url, usuario, password);
            JOptionPane.showMessageDialog(null, "Conexión Exitosa");
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    // Método para cerrar la conexión
    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión cerrada");
            }
        } catch (SQLException ex) {
            System.err.println("Error al cerrar la conexión: " + ex.getMessage());
        }
    }

    // Método para realizar una consulta SELECT
    public ResultSet ejecutarConsulta(String consulta) {
        try {
            PreparedStatement statement = conexion.prepareStatement(consulta);
            return statement.executeQuery();
        } catch (SQLException ex) {
            System.err.println("Error al ejecutar la consulta: " + ex.getMessage());
            return null;
        }
    }

    // Método para ejecutar una instrucción INSERT, UPDATE o DELETE
    public int ejecutarActualizacion(String consulta) {
        try {
            PreparedStatement statement = conexion.prepareStatement(consulta);
            return statement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Error al ejecutar la actualización: " + ex.getMessage());
            return -1;
        }
    }
    public List<String> compararTablas(String nombreBD_MSSQL, Admin_MariaDB adminMariaDB) {
        List<String> tablasNoExistentesEnMSSQL = new ArrayList<>();

        try {
            // Obtener lista de tablas en MSSQL
            List<String> tablasMSSQL = obtenerListaDeTablasMSSQL(nombreBD_MSSQL);

            // Obtener lista de tablas en MariaDB
            List<String> tablasMariaDB = adminMariaDB.obtenerListaDeTablas();

            // Comparar y encontrar las tablas que no existen en MSSQL
            for (String tablaMariaDB : tablasMariaDB) {
                if (!tablasMSSQL.contains(tablaMariaDB)) {
                    tablasNoExistentesEnMSSQL.add(tablaMariaDB);
                }
            }

        } catch (Exception ex) {
            System.err.println("Error al comparar las tablas: " + ex.getMessage());
        }

        return tablasNoExistentesEnMSSQL;
    }

    // Método privado para obtener la lista de tablas en MSSQL
    private List<String> obtenerListaDeTablasMSSQL(String nombreBD_MSSQL) {
        List<String> tablasMSSQL = new ArrayList<>();

        try {
            // Consulta para obtener las tablas en MSSQL
            String consulta = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_CATALOG = ?";
            
            // Ejecutar la consulta
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setString(1, nombreBD_MSSQL);
            ResultSet resultSet = statement.executeQuery();

            // Obtener nombres de las tablas y agregarlos a la lista
            while (resultSet.next()) {
                tablasMSSQL.add(resultSet.getString("TABLE_NAME"));
            }

        } catch (Exception ex) {
            System.err.println("Error al obtener la lista de tablas en MSSQL: " + ex.getMessage());
        }

        return tablasMSSQL;
    }
    public void crearTablaMSSQL(String queryCreacionTabla) {
        try {
            // Ejecutar la instrucción para crear la tabla en MSSQL
            ejecutarActualizacion(queryCreacionTabla);
            System.out.println("Tabla creada en MSSQL");
        } catch (Exception ex) {
            System.err.println("Error al crear la tabla en MSSQL: " + ex.getMessage());
        }
    }
public void realizarInsertsMSSQL(List<String> queriesInsert) {
    try {
        // Crear un Statement para ejecutar los queries
        Statement statement = conexion.createStatement();

        // Ejecutar cada query de inserción en MSSQL
        for (String queryInsert : queriesInsert) {
            statement.executeUpdate(queryInsert);
        }

        // Cerrar el Statement después de su uso
        statement.close();

        System.out.println("Inserts realizados en MSSQL");
    } catch (SQLException ex) {
        System.err.println("Error al realizar los inserts en MSSQL: " + ex.getMessage());
    }
}

}
