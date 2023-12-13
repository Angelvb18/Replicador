package Replicador;
import java.util.ArrayList;
import java.util.List;

public class Columna {
    private String nombre;
    private String tipoDato;
    private int longitudMaxima; // Nuevo campo para la longitud máxima
    private boolean esClavePrimaria;

    // Constructor sin longitud máxima
    public Columna(String nombre, String tipoDato, boolean esClavePrimaria) {
        this(nombre, tipoDato, 0, esClavePrimaria);
    }

    // Constructor con longitud máxima
    public Columna(String nombre, String tipoDato, int longitudMaxima, boolean esClavePrimaria) {
        this.nombre = nombre;
        this.tipoDato = tipoDato;
        this.longitudMaxima = longitudMaxima;
        this.esClavePrimaria = esClavePrimaria;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipoDato() {
        // Agregar la longitud máxima si es un tipo de dato que lo permite
        if (tipoDato.equals("VARCHAR") || tipoDato.equals("NVARCHAR")) {
            return tipoDato + "(" + longitudMaxima + ")";
        } else {
            return tipoDato;
        }
    }

    public int getLongitudMaxima() {
        return longitudMaxima;
    }

    public boolean esClavePrimaria() {
        return esClavePrimaria;
    }
}
