package co.edu.eam.agrodirecto.registro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Guía 1 - Pruebas de INTEGRACIÓN del módulo de registro.
 * Ejercitan {@link ServicioRegistro} junto con {@link ValidadorRegistro} y la lista de correos
 * guardados: comprueban propiedades que ninguna de las piezas demuestra por separado, como el
 * orden en que se aplican las reglas o que un registro fallido no deje el correo guardado.
 */
@DisplayName("Guía 1 - Integración ServicioRegistro + ValidadorRegistro")
class ServicioRegistroIntegracionTest {

    private ServicioRegistro servicio;

    @BeforeEach
    void setUp() {
        servicio = new ServicioRegistro();
    }

    @Test
    @DisplayName("I-01 agricultor con todos los datos válidos -> OK y 1 correo guardado")
    void i01RegistroCompleto() {
        String obtenido = servicio.registrar("AGRICULTOR", "Finca La Esperanza", "Calarca",
                "3104567890", true, "rosa@mail.com");

        assertEquals("OK", obtenido);
        assertEquals(1, servicio.cantidadCorreos());
    }

    @Test
    @DisplayName("I-02 rol y celular inválidos a la vez -> se informa primero el rol (orden RF-01..RF-05)")
    void i02OrdenDeReglas() {
        String obtenido = servicio.registrar("VENDEDOR", "", "", "123", false, "x@mail.com");

        assertEquals("ERROR: rol invalido", obtenido);
    }

    @Test
    @DisplayName("I-03 un registro rechazado no guarda el correo; al corregir el dato sí se registra")
    void i03RechazoNoGuardaCorreo() {
        String primero = servicio.registrar("COMPRADOR", "", "", "312345678", true, "leo@mail.com");
        String segundo = servicio.registrar("COMPRADOR", "", "", "3123456789", true, "leo@mail.com");

        assertEquals("ERROR: celular invalido", primero);
        assertEquals("OK", segundo);
        assertEquals(1, servicio.cantidadCorreos());
    }

    @Test
    @DisplayName("I-04 mismo correo con otras mayúsculas -> ERROR: correo ya registrado y sigue 1 guardado")
    void i04CorreoDuplicado() {
        servicio.registrar("COMPRADOR", "", "", "3124567890", true, "owen@mail.com");
        String obtenido = servicio.registrar("AGRICULTOR", "Finca Owen", "Armenia",
                "3134567890", true, "OWEN@mail.COM");

        assertEquals("ERROR: correo ya registrado", obtenido);
        assertEquals(1, servicio.cantidadCorreos());
    }

    @Test
    @DisplayName("I-05 celular con dígitos de ancho completo en el flujo -> ERROR: celular invalido")
    void i05CelularAnchoCompleto() {
        String obtenido = servicio.registrar("COMPRADOR", "", "",
                "3１２３４５６７８９", true, "juan@mail.com");

        assertEquals("ERROR: celular invalido", obtenido);
    }
}
