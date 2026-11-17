package co.edu.eam.agrodirecto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static co.edu.eam.agrodirecto.DatosDePrueba.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas de GestorPedidos.esValido: verifican que un pedido tenga producto, cantidad,
 * comprador y fecha válidos, y que se rechace cualquier pedido con uno de esos campos roto
 * o el pedido nulo.
 */
@DisplayName("esValido: datos del pedido")
class GestorPedidosEsValidoTest {

    private GestorPedidos gestor;

    @BeforeEach
    void setUp() {
        gestor = new GestorPedidos();
    }

    @Test
    @DisplayName("Un pedido completo es válido")
    void pedidoCompletoEsValido() {
        Pedido completo = new Pedido(COMPRADOR, "tomate", 5.0, MARCA_TIEMPO);

        assertTrue(gestor.esValido(completo), "un pedido completo debe ser válido");
    }

    // @ParameterizedTest: ejecuta la misma prueba varias veces con datos distintos en vez de copiar el cuerpo.
    @ParameterizedTest(name = "rechaza el pedido con {1}")
    // @MethodSource: los datos los arma un método de la propia clase de prueba, aquí pedidos con un campo roto.
    @MethodSource("pedidosInvalidos")
    @DisplayName("Un pedido inválido no es válido")
    void rechazaPedidosInvalidos(Pedido pedido, String defecto) {
        assertFalse(gestor.esValido(pedido), "el pedido con " + defecto + " no debería ser válido");
    }

    /**
     * Proveedor de los seis pedidos que no son válidos: cinco con un campo roto y el pedido nulo.
     */
    static Stream<Arguments> pedidosInvalidos() {
        return Stream.of(
                Arguments.of(new Pedido(COMPRADOR, "", 5.0, MARCA_TIEMPO), "producto vacío"),
                Arguments.of(new Pedido(COMPRADOR, "tomate", 0.0, MARCA_TIEMPO), "cantidad en cero"),
                Arguments.of(new Pedido(COMPRADOR, "tomate", -2.0, MARCA_TIEMPO), "cantidad negativa"),
                Arguments.of(new Pedido("", "tomate", 5.0, MARCA_TIEMPO), "comprador vacío"),
                Arguments.of(new Pedido(COMPRADOR, "tomate", 5.0, 0L), "sin marca de tiempo"),
                Arguments.of(null, "pedido nulo")
        );
    }
}
