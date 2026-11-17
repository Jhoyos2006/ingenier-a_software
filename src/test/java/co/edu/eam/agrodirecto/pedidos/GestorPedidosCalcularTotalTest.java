package co.edu.eam.agrodirecto.pedidos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static co.edu.eam.agrodirecto.pedidos.DatosDePrueba.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Pruebas de GestorPedidos.calcularTotal: verifican que el total a pagar sea la cantidad
 * pedida por el precio unitario, y que un precio no positivo sea rechazado.
 */
@DisplayName("calcularTotal: total a pagar")
class GestorPedidosCalcularTotalTest {

    private GestorPedidos gestor;

    @BeforeEach
    void setUp() {
        gestor = new GestorPedidos();
    }

    // @CsvSource: los datos llegan como filas de valores separados por comas dentro de la propia prueba.
    @ParameterizedTest(name = "{0} kg x {1} por kg = {2}")
    @CsvSource({
            "5.0, 3000.0, 15000.0",
            "2.5, 4000.0, 10000.0",
            "0.5, 3000.0, 1500.0"
    })
    @DisplayName("El total es la cantidad por el precio unitario")
    void totalEsCantidadPorPrecioUnitario(double cantidad, double precioUnitario, double totalEsperado) {
        Pedido pedido = new Pedido(COMPRADOR, "tomate", cantidad, MARCA_TIEMPO);

        // assertEquals con delta: los double se comparan con una tolerancia porque la aritmética decimal no es exacta.
        assertEquals(totalEsperado, gestor.calcularTotal(pedido, precioUnitario), 0.001);
    }

    // @ValueSource: los datos llegan como una lista simple de valores sueltos.
    @ParameterizedTest
    @ValueSource(doubles = {0.0, -100.0})
    @DisplayName("Un precio unitario no positivo lanza IllegalArgumentException")
    void precioUnitarioNoPositivoLanzaExcepcion(double precioUnitario) {
        Pedido pedido = new Pedido(COMPRADOR, "tomate", 5.0, MARCA_TIEMPO);

        // assertThrows: verifica que el método lance la excepción esperada.
        assertThrows(IllegalArgumentException.class, () -> gestor.calcularTotal(pedido, precioUnitario));
    }
}
