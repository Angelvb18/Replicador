/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Replicador;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author angel
 */
public class Tabla {
    private String nombre;
    private List<Columna> columnas;

    public Tabla(String nombre) {
        this.nombre = nombre;
        this.columnas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public List<Columna> getColumnas() {
        return columnas;
    }

    public void agregarColumna(Columna columna) {
        columnas.add(columna);
    }
}
