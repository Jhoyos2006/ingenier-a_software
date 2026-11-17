package co.edu.eam.agrodirecto.guia2;

import co.edu.eam.agrodirecto.pedidos.EstadoPedido;
import co.edu.eam.agrodirecto.pedidos.GestorPedidos;
import co.edu.eam.agrodirecto.pedidos.Pedido;
import co.edu.eam.agrodirecto.registro.ServicioRegistro;
import co.edu.eam.agrodirecto.registro.ValidadorRegistro;
import co.edu.eam.agrodirecto.soporte.Prueba;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

/**
 * ============================================================================
 * GUÍA 2 - PRUEBA DE REGRESIÓN
 * ============================================================================
 * ¿QUÉ ES?  Se repite la MISMA suite, COMPLETA, después de CADA cambio en el código,
 *           incluidas las pruebas que ya pasaban, para comprobar que el cambio no
 *           dañó nada que antes funcionaba.
 *
 * DIFERENCIA CLAVE (pregunta típica de sustentación):
 *   - Regresión:    se repiten TODAS las pruebas (las que pasaban y las que no).
 *   - Confirmación: se repite SOLO el caso que falló, para comprobar la corrección.
 *
 * ¿QUÉ HACE AQUÍ?  Congela en una sola clase el comportamiento ya aprobado de los
 *           métodos principales de los DOS módulos (registro y pedidos), uno por regla.
 *           Si mañana alguien modifica, por ejemplo, calcularTotal o celularValido y
 *           rompe algo, esta suite lo detecta en segundos.
 *
 * CÓMO SE USA:  después de cada cambio ->  mvn test
 *           (además, toda la suite del proyecto: Guía 1 + Guía 2, se ejecuta junta).
 *
 * EVIDENCIA DE ÉXITO: la suite completa en verde.
 * ============================================================================
 */
@DisplayName("Guía 2 - REGRESIÓN: comportamiento ya aprobado de registro y pedidos")
@TestMethodOrder(MethodOrderer.MethodName.class)
class RegresionTest {

    private final GestorPedidos gestor = new GestorPedidos();
    private static final long T = 1_700_000_000_000L; // marca de tiempo fija para que el resultado no varíe

    // ---------------- Módulo de REGISTRO ----------------

    @Test
    @DisplayName("REG-01 RF-01 rol COMPRADOR válido")
    void reg01Rol() {
        Prueba.verificar("REG-01 rol COMPRADOR", true, ValidadorRegistro.rolValido("COMPRADOR"));
    }

    @Test
    @DisplayName("REG-02 RF-02 agricultor sin finca se rechaza")
    void reg02Finca() {
        Prueba.verificar("REG-02 agricultor sin finca", false,
                ValidadorRegistro.camposAgricultorValidos("AGRICULTOR", "  ", "Armenia"));
    }

    @Test
    @DisplayName("REG-03 RF-03 celular válido")
    void reg03Celular() {
        Prueba.verificar("REG-03 celular valido", true, ValidadorRegistro.celularValido("3104567890"));
    }

    @Test
    @DisplayName("REG-04 RF-05 correo repetido con otras mayúsculas se rechaza")
    void reg04CorreoDuplicado() {
        ServicioRegistro servicio = new ServicioRegistro();
        servicio.registrar("COMPRADOR", "", "", "3001234567", true, "ana@mail.com");
        Prueba.verificar("REG-04 correo duplicado", "ERROR: correo ya registrado",
                servicio.registrar("COMPRADOR", "", "", "3001234567", true, "ANA@MAIL.COM"));
    }

    // ---------------- Módulo de PEDIDOS ----------------

    @Test
    @DisplayName("REG-05 total con 20% de descuento: 5 x 3000 x 0.8 = 12000")
    void reg05Descuento() {
        Pedido p = new Pedido("A001", "Tomate", 5, T);
        Prueba.verificar("REG-05 descuento 20%", 12_000.0, gestor.aplicarDescuento(p, 3_000.0, 20));
    }

    @Test
    @DisplayName("REG-06 costo de envío: 5000 + 5 kg x 800 = 9000")
    void reg06Envio() {
        Pedido p = new Pedido("A001", "Tomate", 5, T);
        Prueba.verificar("REG-06 costo envio", 9_000.0, gestor.calcularCostoEnvio(p, 5_000.0, 800.0));
    }

    @Test
    @DisplayName("REG-07 un pedido ENTREGADO no puede cancelarse")
    void reg07Transicion() {
        Prueba.verificar("REG-07 ENTREGADO -> CANCELADO", false,
                gestor.puedePasarA(EstadoPedido.ENTREGADO, EstadoPedido.CANCELADO));
    }

    @Test
    @DisplayName("REG-08 mismo pedido 1 minuto después es duplicado (riesgo R8)")
    void reg08Duplicado() {
        Pedido previo = new Pedido("A001", "Tomate", 5, T);
        Pedido nuevo = new Pedido("A001", "Tomate", 5, T + 60_000);
        Prueba.verificar("REG-08 pedido duplicado", true, gestor.esDuplicado(List.of(previo), nuevo));
    }
}
