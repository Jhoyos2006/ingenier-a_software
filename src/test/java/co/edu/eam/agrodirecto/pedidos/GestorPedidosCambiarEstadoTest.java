package co.edu.eam.agrodirecto.pedidos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static co.edu.eam.agrodirecto.pedidos.DatosDePrueba.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Pruebas de GestorPedidos.cambiarEstado: verifican que el cambio de estado se aplique y quede
 * guardado en el pedido, y que una transición no permitida termine en una invariante rota.
 */
@DisplayName("cambiarEstado: aplicación del cambio de estado")
class GestorPedidosCambiarEstadoTest {

    private GestorPedidos gestor;

    @BeforeEach
    void setUp() {
        gestor = new GestorPedidos();
    }

    // @ParameterizedTest + @CsvSource: la misma prueba para cada transición de la tabla del ciclo de vida.
    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource({
            "PENDIENTE, PAGADO",
            "PAGADO, ENVIADO",
            "PENDIENTE, CANCELADO"
    })
    @DisplayName("Una transición permitida devuelve y guarda el estado nuevo")
    void transicionPermitida(EstadoPedido actual, EstadoPedido destino) {
        Pedido pedido = new Pedido(COMPRADOR, "tomate", 5.0, MARCA_TIEMPO);
        pedido.setEstado(actual);

        EstadoPedido nuevo = gestor.cambiarEstado(pedido, destino);

        // assertAll: agrupa las verificaciones de un mismo caso y reporta todos los fallos de una vez, no solo el primero.
        assertAll(
                () -> assertEquals(destino, nuevo),
                () -> assertEquals(destino, pedido.getEstado(), "el pedido debe quedar en el estado nuevo")
        );
    }

    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource({
            "PAGADO, PENDIENTE",
            "ENVIADO, CANCELADO"
    })
    @DisplayName("Una transición no permitida lanza IllegalArgumentException")
    void transicionNoPermitida(EstadoPedido actual, EstadoPedido destino) {
        Pedido pedido = new Pedido(COMPRADOR, "tomate", 5.0, MARCA_TIEMPO);
        pedido.setEstado(actual);

        assertThrows(IllegalArgumentException.class, () -> gestor.cambiarEstado(pedido, destino));
    }
}
