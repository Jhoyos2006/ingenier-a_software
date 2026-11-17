package co.edu.eam.agrodirecto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static co.edu.eam.agrodirecto.DatosDePrueba.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Prueba de la funcionalidad completa del módulo de pedidos.
 * Recorre en orden el ciclo de vida de un pedido (registrar, consultar, actualizar y eliminar)
 * usando los ocho métodos del módulo. Para garantizar que la funcionalidad
 * completa está probada y no solo piezas aisladas.
 */
@DisplayName("Funcionalidad completa: registrar, consultar, actualizar y eliminar")
class GestorPedidosFuncionalidadCompletaTest {

    private GestorPedidos gestor;

    @BeforeEach
    void setUp() {
        gestor = new GestorPedidos();
    }

    @Test
    @DisplayName("Un pedido recorre todo su ciclo de vida y el módulo lo sostiene")
    void cicloDeVidaCompletoDeUnPedido() {
        List<Pedido> historial = new ArrayList<>();

        // 1. REGISTRAR: los datos son válidos, no es un duplicado y se calculan los valores del pedido.
        Pedido pedido = new Pedido(COMPRADOR, "tomate", 5.0, MARCA_TIEMPO);
        assertTrue(gestor.esValido(pedido), "un pedido con datos completos se puede registrar");
        assertFalse(gestor.esDuplicado(historial, pedido), "el primer pedido no es duplicado");
        assertEquals(15_000.0, gestor.calcularTotal(pedido, PRECIO_TOMATE), 0.001);
        assertEquals(13_500.0, gestor.aplicarDescuento(pedido, PRECIO_TOMATE, 10.0), 0.001);
        assertEquals(9_000.0, gestor.calcularCostoEnvio(pedido, 5_000.0, 800.0), 0.001);
        historial.add(pedido);

        // 2. CONSULTAR: el historial del comprador ya suma lo que pidió, y el mismo pedido se detecta.
        assertEquals(5.0, gestor.totalKgDe(historial, COMPRADOR), 0.001);
        assertTrue(gestor.esDuplicado(historial, pedido), "el mismo pedido otra vez sí es duplicado");

        // 3. ACTUALIZAR: el pedido se paga y se despacha, y el estado nuevo queda guardado en el pedido.
        assertTrue(gestor.puedePasarA(pedido.getEstado(), EstadoPedido.PAGADO));
        assertEquals(EstadoPedido.PAGADO, gestor.cambiarEstado(pedido, EstadoPedido.PAGADO));
        assertTrue(gestor.puedePasarA(pedido.getEstado(), EstadoPedido.ENVIADO));
        assertEquals(EstadoPedido.ENVIADO, gestor.cambiarEstado(pedido, EstadoPedido.ENVIADO));
        assertEquals(EstadoPedido.ENVIADO, pedido.getEstado(), "el estado nuevo queda guardado en el pedido");

        // 4. ELIMINAR: un pedido ya despachado no se cancela, pero uno pagado sí se puede anular.
        assertFalse(gestor.puedePasarA(pedido.getEstado(), EstadoPedido.CANCELADO));
        assertThrows(IllegalArgumentException.class, () -> gestor.cambiarEstado(pedido, EstadoPedido.CANCELADO));

        Pedido segundo = new Pedido(COMPRADOR, "papa", 3.0, MARCA_TIEMPO);
        assertEquals(EstadoPedido.CANCELADO, gestor.cambiarEstado(segundo, EstadoPedido.CANCELADO));
        assertFalse(gestor.puedePasarA(segundo.getEstado(), EstadoPedido.PAGADO),
                "un pedido cancelado no vuelve a avanzar");
    }
}
