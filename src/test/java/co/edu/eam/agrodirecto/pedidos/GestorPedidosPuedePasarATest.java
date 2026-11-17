package co.edu.eam.agrodirecto.pedidos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Pruebas de GestorPedidos.puedePasarA: verifican la tabla de transiciones del ciclo de vida
 * del pedido, comprobando tanto las transiciones permitidas como las prohibidas.
 */
@DisplayName("puedePasarA: transiciones de estado")
class GestorPedidosPuedePasarATest {

    private GestorPedidos gestor;

    @BeforeEach
    void setUp() {
        gestor = new GestorPedidos();
    }

    @ParameterizedTest(name = "{0} -> {1}: {2}")
    @CsvSource({
            // transiciones permitidas por la tabla del ciclo de vida
            "PENDIENTE, PAGADO, true",
            "PENDIENTE, CANCELADO, true",
            "PAGADO, ENVIADO, true",
            "PAGADO, CANCELADO, true",
            "ENVIADO, ENTREGADO, true",
            // transiciones prohibidas: saltarse el pago, retroceder, cancelar tarde, quedarse igual
            "PENDIENTE, PENDIENTE, false",
            "PENDIENTE, ENVIADO, false",
            "PAGADO, PENDIENTE, false",
            "PAGADO, ENTREGADO, false",
            "ENVIADO, CANCELADO, false",
            "ENTREGADO, CANCELADO, false",
            "CANCELADO, PENDIENTE, false"
    })
    @DisplayName("La tabla de transiciones decide si el cambio de estado es válido")
    void transicionValidaOInvalida(EstadoPedido actual, EstadoPedido destino, boolean permitido) {
        assertEquals(permitido, gestor.puedePasarA(actual, destino));
    }
}
