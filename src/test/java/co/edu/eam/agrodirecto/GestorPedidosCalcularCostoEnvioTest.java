package co.edu.eam.agrodirecto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static co.edu.eam.agrodirecto.DatosDePrueba.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Pruebas de GestorPedidos.calcularCostoEnvio: verifican que el costo de despacho sea la base
 * fija de la configuración más el costo por kilo que pesa el pedido.
 */
@DisplayName("calcularCostoEnvio: costo de despacho")
class GestorPedidosCalcularCostoEnvioTest {

    private GestorPedidos gestor;

    @BeforeEach
    void setUp() {
        gestor = new GestorPedidos();
    }

    @ParameterizedTest(name = "{0} kg con base {1} y {2} por kg = {3}")
    @CsvSource({
            "5.0, 5000.0, 800.0, 9000.0",
            "0.5, 5000.0, 800.0, 5400.0",
            "20.0, 5000.0, 800.0, 21000.0",
            "0.0, 5000.0, 800.0, 5000.0"
    })
    @DisplayName("El envío es la base fija más el costo por kilo del pedido")
    void envioEsBaseMasCostoPorKilo(double cantidad, double costoBase, double costoPorKilo,
                                     double envioEsperado) {
        Pedido pedido = new Pedido(COMPRADOR, "tomate", cantidad, MARCA_TIEMPO);

        assertEquals(envioEsperado, gestor.calcularCostoEnvio(pedido, costoBase, costoPorKilo), 0.001);
    }
}
