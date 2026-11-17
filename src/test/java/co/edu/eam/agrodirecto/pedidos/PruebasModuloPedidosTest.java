package co.edu.eam.agrodirecto.pedidos;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * Suite del módulo de pedidos: el nivel superior que agrupa y ejecuta las ocho clases de prueba
 * de la funcionalidad, más la prueba del flujo completo. Cada clase verifica una parte del
 * módulo (registro, validación, totales, descuentos, envío, transiciones, estados e historial)
 * y desde aquí se invocan todas, garantizando que la
 * funcionalidad completa está probada y no solo piezas aisladas.
 */
@Suite
@SuiteDisplayName("Funcionalidad completa del módulo de pedidos de AgroDirecto")
@SelectClasses({
        GestorPedidosEsDuplicadoTest.class,
        GestorPedidosEsValidoTest.class,
        GestorPedidosCalcularTotalTest.class,
        GestorPedidosAplicarDescuentoTest.class,
        GestorPedidosCalcularCostoEnvioTest.class,
        GestorPedidosPuedePasarATest.class,
        GestorPedidosCambiarEstadoTest.class,
        GestorPedidosTotalKgDeTest.class,
        GestorPedidosFuncionalidadCompletaTest.class
})
class PruebasModuloPedidosTest {
}
