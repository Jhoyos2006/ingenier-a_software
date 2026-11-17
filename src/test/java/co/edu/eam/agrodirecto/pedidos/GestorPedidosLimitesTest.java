package co.edu.eam.agrodirecto.pedidos;

import co.edu.eam.agrodirecto.soporte.Prueba;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static co.edu.eam.agrodirecto.pedidos.DatosDePrueba.*;

/**
 * Guía 1 - Pruebas UNITARIAS de valores límite en el módulo de pedidos.
 * Las pruebas existentes usan valores "cómodos" (30 segundos, 10 minutos, 10 %); estas tocan
 * exactamente los bordes de cada regla, que es donde la guía advierte que aparecen los defectos.
 * Cada prueba imprime en consola: id, resultado esperado, obtenido y APROBO/FALLO.
 */
@DisplayName("Guía 1 - Unitarias de límites en GestorPedidos")
@TestMethodOrder(MethodOrderer.MethodName.class)
class GestorPedidosLimitesTest {

    private GestorPedidos gestor;
    private Pedido cincoKgTomate;

    @BeforeEach
    void setUp() {
        gestor = new GestorPedidos();
        cincoKgTomate = new Pedido(COMPRADOR, "tomate", 5.0, MARCA_TIEMPO);
    }

    @Test
    @DisplayName("U-26 descuento 0 % (límite inferior) -> 15000.0")
    void u26DescuentoCero() {
        Prueba.verificar("U-26 descuento 0% sobre 5 kg x 3000", 15_000.0,
                gestor.aplicarDescuento(cincoKgTomate, PRECIO_TOMATE, 0));
    }

    @Test
    @DisplayName("U-27 descuento 100 % (límite superior) -> 0.0")
    void u27DescuentoCien() {
        Prueba.verificar("U-27 descuento 100%", 0.0,
                gestor.aplicarDescuento(cincoKgTomate, PRECIO_TOMATE, 100));
    }

    @Test
    @DisplayName("U-28 descuento -0.01 % (justo por debajo) -> IllegalArgumentException")
    void u28DescuentoNegativo() {
        Prueba.verificarExcepcion("U-28 descuento -0.01%", IllegalArgumentException.class,
                () -> gestor.aplicarDescuento(cincoKgTomate, PRECIO_TOMATE, -0.01));
    }

    @Test
    @DisplayName("U-29 descuento 100.01 % (justo por encima) -> IllegalArgumentException")
    void u29DescuentoMayorACien() {
        Prueba.verificarExcepcion("U-29 descuento 100.01%", IllegalArgumentException.class,
                () -> gestor.aplicarDescuento(cincoKgTomate, PRECIO_TOMATE, 100.01));
    }

    @Test
    @DisplayName("U-30 pedido repetido 1 min 59.999 s después (dentro de la ventana) -> duplicado")
    void u30DentroDeVentana() {
        Pedido nuevo = new Pedido(COMPRADOR, "tomate", 5.0, MARCA_TIEMPO + 119_999);

        Prueba.verificar("U-30 pedido repetido a los 119999 ms", true,
                gestor.esDuplicado(List.of(cincoKgTomate), nuevo));
    }

    @Test
    @DisplayName("U-31 pedido repetido exactamente 2 min después (borde de la ventana) -> no duplicado")
    void u31BordeDeVentana() {
        Pedido nuevo = new Pedido(COMPRADOR, "tomate", 5.0, MARCA_TIEMPO + 120_000);

        Prueba.verificar("U-31 pedido repetido a los 120000 ms", false,
                gestor.esDuplicado(List.of(cincoKgTomate), nuevo));
    }

    @Test
    @DisplayName("U-32 precio unitario 0 (justo fuera) -> IllegalArgumentException")
    void u32PrecioCero() {
        Prueba.verificarExcepcion("U-32 precio unitario 0", IllegalArgumentException.class,
                () -> gestor.calcularTotal(cincoKgTomate, 0));
    }

    @Test
    @DisplayName("U-33 precio unitario 0.01 (menor positivo usado) -> 0.05")
    void u33PrecioMinimo() {
        Prueba.verificar("U-33 precio unitario 0.01 x 5 kg", 0.05,
                gestor.calcularTotal(cincoKgTomate, 0.01));
    }
}
