package co.edu.eam.agrodirecto.guia2;

import co.edu.eam.agrodirecto.pedidos.GestorPedidos;
import co.edu.eam.agrodirecto.pedidos.Pedido;
import co.edu.eam.agrodirecto.registro.ServicioRegistro;
import co.edu.eam.agrodirecto.soporte.Prueba;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * ============================================================================
 * GUÍA 2 - PRUEBA DE HUMO (smoke test)
 * ============================================================================
 * ¿QUÉ ES?  Lo MÍNIMO para saber si vale la pena seguir probando una versión nueva.
 *           Si lo básico ni funciona, no tiene sentido ejecutar los demás casos.
 *
 * ¿CUÁNDO?  Con cada versión nueva o despliegue, ANTES que las pruebas detalladas.
 *           Se puede ejecutar sola:  mvn test -Dtest=HumoTest
 *
 * ¿QUÉ HACE AQUÍ?  Recorre el "camino feliz" de los dos módulos de AgroDirecto:
 *           1) registrar un usuario válido  -> debe responder "OK"
 *           2) crear un pedido válido y calcular su total -> debe dar el valor correcto
 *
 * EVIDENCIA DE ÉXITO: pocos casos esenciales aprobados.
 *           Si pasa  -> se siguen las pruebas detalladas (funcional, regresión...).
 *           Si falla -> la versión se devuelve al equipo de desarrollo.
 * ============================================================================
 */
@DisplayName("Guía 2 - HUMO: lo básico de AgroDirecto funciona")
class HumoTest {

    @Test
    @DisplayName("HUMO-01 registrar un comprador válido -> OK")
    void humo01RegistroBasico() {
        // ARRANGE: servicio nuevo, sin correos registrados.
        ServicioRegistro servicio = new ServicioRegistro();

        // ACT: datos 100% válidos (rol correcto, celular de 10 dígitos que empieza por 3,
        //      política aceptada, correo nuevo). Comprador no necesita finca ni municipio.
        String obtenido = servicio.registrar("COMPRADOR", "", "", "3001234567", true, "ana@mail.com");

        // ASSERT: el flujo básico de registro responde OK.
        Prueba.verificar("HUMO-01 registro basico", "OK", obtenido);
    }

    @Test
    @DisplayName("HUMO-02 crear pedido válido y calcular total -> 5 kg x 3000 = 15000")
    void humo02PedidoBasico() {
        GestorPedidos gestor = new GestorPedidos();
        Pedido pedido = new Pedido("A001", "Tomate", 5, System.currentTimeMillis());

        // Lo esencial del módulo de pedidos: el pedido es válido y su total se calcula.
        Prueba.verificar("HUMO-02a pedido valido", true, gestor.esValido(pedido));
        Prueba.verificar("HUMO-02b total del pedido", 15_000.0, gestor.calcularTotal(pedido, 3_000.0));
    }
}
