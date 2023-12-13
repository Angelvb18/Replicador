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
import javax.swing.JOptionPane;
import java.sql.DatabaseMetaData;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 *
 * @author angel
 */
public class Admin_MariaDB {
    private Connection conexion;


    // Constructor
    public Admin_MariaDB(String nombreInstancia, String nombreBD, int puerto, String usuario, String password) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String url = "jdbc:mariadb://127.0.0.1:" +puerto + "/" + nombreBD;
            conexion = DriverManager.getConnection(url, usuario, password);
        } catch (ClassNotFoundException | SQLException ex) {
        }
    }
    public void ProbarConexion(String nombreInstancia, String nombreBD, int puerto, String usuario, String password){
        
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String url = "jdbc:mariadb://127.0.0.1:" +puerto + "/" + nombreBD;
            conexion = DriverManager.getConnection(url, usuario, password);
            JOptionPane.showMessageDialog(null, "Conexion Exitosa");
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage());
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
     public List<String> obtenerListaDeTablas() {
        List<String> listaDeTablas = new ArrayList<>();

        try {
            DatabaseMetaData metaData = conexion.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "%", null);

            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                listaDeTablas.add(tableName);
            }

            rs.close();
        } catch (SQLException ex) {
            System.err.println("Error al obtener la lista de tablas: " + ex.getMessage());
        }

        return listaDeTablas;
    }
    public Tabla obtenerInfoColumnasTabla(String nombreTabla) {
        Tabla tabla = new Tabla(nombreTabla);

        try {
            DatabaseMetaData metaData = conexion.getMetaData();
            ResultSet rs = metaData.getColumns(null, null, nombreTabla, null);

            while (rs.next()) {
                String nombreColumna = rs.getString("COLUMN_NAME");
                String tipoDato = rs.getString("TYPE_NAME");
                boolean esClavePrimaria = esClavePrimaria(metaData, nombreTabla, nombreColumna);

                Columna columna = new Columna(nombreColumna, tipoDato, esClavePrimaria);
                tabla.agregarColumna(columna);
            }

            rs.close();
        } catch (SQLException ex) {
            System.err.println("Error al obtener la información de columnas: " + ex.getMessage());
        }

        return tabla;
    }
        private boolean esClavePrimaria(DatabaseMetaData metaData, String nombreTabla, String nombreColumna) throws SQLException {
        ResultSet rs = metaData.getPrimaryKeys(null, null, nombreTabla);

        while (rs.next()) {
            String nombreClavePrimaria = rs.getString("COLUMN_NAME");
            if (nombreClavePrimaria.equals(nombreColumna)) {
                return true;
            }
        }

        rs.close();
        return false;
    }
public List<String> construirQueriesInsertDesdeTabla(String nombreTabla) {
    List<String> queries = new ArrayList<>();

    // Realizar un SELECT * para obtener todos los registros de la tabla
    String selectQuery = "SELECT * FROM " + nombreTabla;

    try (Statement statement = conexion.createStatement();
         ResultSet resultSet = statement.executeQuery(selectQuery)) {

        // Obtener metadatos de la tabla para saber los tipos de datos
        ResultSetMetaData metaData = resultSet.getMetaData();
        int numColumnas = metaData.getColumnCount();

        // Iterar sobre los resultados y construir los queries de inserción
        while (resultSet.next()) {
            StringBuilder queryInsert = new StringBuilder();
            queryInsert.append("INSERT INTO ").append(nombreTabla).append(" (");

            // Agregar nombres de columnas al query
            for (int i = 1; i <= numColumnas; i++) {
                queryInsert.append(metaData.getColumnName(i)).append(", ");
            }

            // Eliminar la coma extra al final y cerrar paréntesis
            queryInsert.delete(queryInsert.length() - 2, queryInsert.length()).append(") VALUES (");

            // Agregar valores para cada registro
            for (int i = 1; i <= numColumnas; i++) {
                Object valor = resultSet.getObject(i);

                if (valor instanceof String || valor instanceof Date) {
                    queryInsert.append("'").append(valor).append("'");
                } else {
                    queryInsert.append(valor);
                }

                queryInsert.append(", ");
            }

            // Eliminar la coma extra al final y cerrar paréntesis
            queryInsert.delete(queryInsert.length() - 2, queryInsert.length()).append(");");

            // Agregar el query de inserción al conjunto de queries
            queries.add(queryInsert.toString());
        }

    } catch (Exception ex) {
        System.err.println("Error al construir los queries de inserción: " + ex.getMessage());
    }

    return queries;
}


}
