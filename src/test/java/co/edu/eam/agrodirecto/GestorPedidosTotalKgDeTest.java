package co.edu.eam.agrodirecto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static co.edu.eam.agrodirecto.DatosDePrueba.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Pruebas de GestorPedidos.totalKgDe: verifican la suma de kilos que un comprador ha pedido
 * en el historial recibido, incluyendo los casos de comprador ausente e historial vacío.
 */
@DisplayName("totalKgDe: historial del comprador")
class GestorPedidosTotalKgDeTest {

    private GestorPedidos gestor;

    @BeforeEach
    void setUp() {
        gestor = new GestorPedidos();
    }

    @Test
    @DisplayName("Suma los kilos de todos los pedidos del comprador")
    void sumaLosKilosDelComprador() {
        Pedido primero = new Pedido(COMPRADOR, "tomate", 10.0, MARCA_TIEMPO);
        Pedido segundo = new Pedido(COMPRADOR, "papa", 5.0, MARCA_TIEMPO);

        assertEquals(15.0, gestor.totalKgDe(List.of(primero, segundo), COMPRADOR), 0.001);
    }

    @Test
    @DisplayName("La suma conserva el decimal de las cantidades")
    void conservaElDecimalDeLaCantidad() {
        Pedido primero = new Pedido(COMPRADOR, "tomate", 2.5, MARCA_TIEMPO);
        Pedido segundo = new Pedido(COMPRADOR, "papa", 2.5, MARCA_TIEMPO);

        assertEquals(5.0, gestor.totalKgDe(List.of(primero, segundo), COMPRADOR), 0.001);
    }

    @Test
    @DisplayName("Sin pedidos del comprador el total es cero")
    void sinPedidosDelCompradorElTotalEsCero() {
        Pedido deOtro = new Pedido(OTRO_COMPRADOR, "tomate", 5.0, MARCA_TIEMPO);

        // assertAll: agrupa las verificaciones de un mismo caso y reporta todos los fallos de una vez, no solo el primero.
        assertAll(
                () -> assertEquals(0.0, gestor.totalKgDe(List.of(deOtro), COMPRADOR), 0.001),
                () -> assertEquals(0.0, gestor.totalKgDe(List.of(), COMPRADOR), 0.001),
                () -> assertEquals(0.0, gestor.totalKgDe(null, COMPRADOR), 0.001)
        );
    }
}
