package Replicador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author angel
 */
public class Archivo {
    private String nombreArchivo;

    public Archivo() {
        this.nombreArchivo = "./Conection.txt";
    }
    // Método para escribir información en el archivo
    public void escribirInformacion(String nombreInstancia1, String nombreBD1, int puerto1, String usuario1, String password1,
                                    String nombreInstancia2, String nombreBD2, int puerto2, String usuario2, String password2) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            // Escribir información de la primera base de datos
            writer.write(String.format("%s,%s,%d,%s,%s", nombreInstancia1, nombreBD1, puerto1, usuario1, password1));
            writer.newLine();

            // Escribir información de la segunda base de datos
            writer.write(String.format("%s,%s,%d,%s,%s", nombreInstancia2, nombreBD2, puerto2, usuario2, password2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   // Método para obtener información de la primera base de datos como arreglo de cadenas
    public String[] obtenerInformacionBase1() {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea = reader.readLine();
            if (linea != null) {
                return linea.split(",");
            } else {
                return new String[]{"No hay información disponible para la Base de Datos 1."};
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new String[]{"Error al leer la información de la Base de Datos 1."};
        }
    }

    // Método para obtener información de la segunda base de datos como arreglo de cadenas
    public String[] obtenerInformacionBase2() {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            // Leer la primera línea (información de la Base de Datos 1)
            reader.readLine();

            // Leer la segunda línea (información de la Base de Datos 2)
            String linea = reader.readLine();
            if (linea != null) {
                return linea.split(",");
            } else {
                return new String[]{"No hay información disponible para la Base de Datos 2."};
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new String[]{"Error al leer la información de la Base de Datos 2."};
        }
    }
}
