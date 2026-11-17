package co.edu.eam.agrodirecto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static co.edu.eam.agrodirecto.DatosDePrueba.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Pruebas de GestorPedidos.aplicarDescuento: verifican que el total baje en la proporción
 * del descuento aplicado, y que un porcentaje fuera del rango de 0 a 100 sea rechazado.
 */
@DisplayName("aplicarDescuento: descuento sobre el total")
class GestorPedidosAplicarDescuentoTest {

    private GestorPedidos gestor;

    @BeforeEach
    void setUp() {
        gestor = new GestorPedidos();
    }

    @ParameterizedTest(name = "{0} kg con {1} % de descuento = {2}")
    @CsvSource({
            "5.0, 0.0, 15000.0",
            "5.0, 10.0, 13500.0",
            "5.0, 100.0, 0.0"
    })
    @DisplayName("El total baja la proporción del descuento")
    void aplicaElDescuentoSobreElTotal(double cantidad, double porcentaje, double totalEsperado) {
        Pedido pedido = new Pedido(COMPRADOR, "tomate", cantidad, MARCA_TIEMPO);

        assertEquals(totalEsperado, gestor.aplicarDescuento(pedido, PRECIO_TOMATE, porcentaje), 0.001);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-10.0, 150.0})
    @DisplayName("Un porcentaje fuera de 0 a 100 lanza IllegalArgumentException")
    void porcentajeFueraDeRangoLanzaExcepcion(double porcentaje) {
        Pedido pedido = new Pedido(COMPRADOR, "tomate", 5.0, MARCA_TIEMPO);

        assertThrows(IllegalArgumentException.class,
                () -> gestor.aplicarDescuento(pedido, PRECIO_TOMATE, porcentaje));
    }
}
