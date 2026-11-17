package co.edu.eam.agrodirecto.pedidos;

/**
 * Datos compartidos por las pruebas del módulo de pedidos: el comprador, el segundo comprador,
 * la marca de tiempo fija y el precio del tomate viven aquí para que las mismas constantes
 * no se repitan en los ocho archivos de prueba del módulo.
 */
final class DatosDePrueba {

    static final String COMPRADOR = "A001";
    static final String OTRO_COMPRADOR = "B002";
    static final long MARCA_TIEMPO = 1_700_000_000_000L;
    static final double PRECIO_TOMATE = 3_000.0;

    /**
     * Constructor privado: es una clase utilitaria que solo agrupa constantes, nunca se instancia.
     */
    private DatosDePrueba() {
    }
}
