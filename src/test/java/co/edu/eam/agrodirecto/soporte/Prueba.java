package co.edu.eam.agrodirecto.soporte;

import org.junit.jupiter.api.function.Executable;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Apoyo para las pruebas unitarias de la Guía 1: igual que la clase Prueba del Anexo de la guía,
 * imprime en consola el id del caso, el resultado esperado, el obtenido y si APROBO o FALLO.
 * Además hace la aserción de JUnit, así que un FALLO también hace fallar la prueba en Maven.
 */
public final class Prueba {

    private static final double TOLERANCIA = 1e-9;

    private Prueba() {
    }

    /** Compara dos valores cualesquiera (booleanos, textos, enteros...). */
    public static void verificar(String id, Object esperado, Object obtenido) {
        imprimir(id, esperado, obtenido, Objects.equals(esperado, obtenido));
        assertEquals(esperado, obtenido, id);
    }

    /** Compara dos números decimales con una tolerancia mínima por redondeo. */
    public static void verificar(String id, double esperado, double obtenido) {
        imprimir(id, esperado, obtenido, Math.abs(esperado - obtenido) < TOLERANCIA);
        assertEquals(esperado, obtenido, TOLERANCIA, id);
    }

    /** Ejecuta la acción y compara el nombre de la excepción esperada con la que realmente ocurrió. */
    public static void verificarExcepcion(String id, Class<? extends Throwable> esperada, Executable accion) {
        String obtenido;
        try {
            accion.execute();
            obtenido = "sin excepcion";
        } catch (Throwable t) {
            obtenido = t.getClass().getSimpleName();
        }
        verificar(id, esperada.getSimpleName(), obtenido);
    }

    private static void imprimir(String id, Object esperado, Object obtenido, boolean ok) {
        System.out.println(id + " esperado=" + esperado + " obtenido=" + obtenido
                + " -> " + (ok ? "APROBO" : "FALLO"));
    }
}
