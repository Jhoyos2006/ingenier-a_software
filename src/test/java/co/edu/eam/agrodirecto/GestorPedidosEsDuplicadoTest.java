package co.edu.eam.agrodirecto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static co.edu.eam.agrodirecto.DatosDePrueba.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas de GestorPedidos.esDuplicado: verifican si un pedido nuevo repite a otro pedido
 * reciente del mismo comprador, con el mismo producto y la misma cantidad.
 */
@DisplayName("esDuplicado: detección de pedidos duplicados")
class GestorPedidosEsDuplicadoTest {

    private GestorPedidos gestor;

    // @BeforeEach: crea un GestorPedidos nuevo para cada prueba para que ninguna dependa de otra.
    @BeforeEach
    void setUp() {
        gestor = new GestorPedidos();
    }

    @Test
    @DisplayName("Mismo pedido en 30 segundos se detecta como duplicado")
    void mismoPedidoEn30Segundos() {
        long ahora = System.currentTimeMillis();
        Pedido existente = new Pedido(COMPRADOR, "tomate", 5.0, ahora - 30_000); // hace 30 s
        Pedido nuevo     = new Pedido(COMPRADOR, "tomate", 5.0, ahora);

        assertTrue(gestor.esDuplicado(List.of(existente), nuevo));
    }

    @Test
    @DisplayName("Mismo pedido a los 10 minutos no es duplicado")
    void mismoPedidoA10Minutos() {
        long ahora = System.currentTimeMillis();
        Pedido existente = new Pedido(COMPRADOR, "tomate", 5.0, ahora - 600_000); // hace 10 min
        Pedido nuevo     = new Pedido(COMPRADOR, "tomate", 5.0, ahora);

        assertFalse(gestor.esDuplicado(List.of(existente), nuevo));
    }

    @Test
    @DisplayName("Mismo comprador con diferente producto no es duplicado")
    void mismoCompradorProductoDiferente() {
        long ahora = System.currentTimeMillis();
        Pedido existente = new Pedido(COMPRADOR, "tomate", 5.0, ahora - 30_000);
        Pedido nuevo     = new Pedido(COMPRADOR, "papa",   5.0, ahora);

        assertFalse(gestor.esDuplicado(List.of(existente), nuevo));
    }
}
