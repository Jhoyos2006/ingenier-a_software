package co.edu.eam.agrodirecto.registro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas de registro de AgroDirecto: los 10 casos CP-01 a CP-10 de la Guía 1.
 * CP-01 a CP-06 ejercen {@link ValidadorRegistro} directamente; CP-07 a CP-10
 * ejercen el flujo completo de {@link ServicioRegistro}.
 */
@DisplayName("Registro: casos CP-01 a CP-10")
class ServicioRegistroTest {

    private ServicioRegistro servicio;

    @BeforeEach
    void setUp() {
        servicio = new ServicioRegistro();
    }

    @Test
    @DisplayName("CP-01: acepta los dos roles permitidos")
    void cp01RolesPermitidos() {
        assertTrue(ValidadorRegistro.rolValido("AGRICULTOR")
                        && ValidadorRegistro.rolValido("COMPRADOR"),
                "AGRICULTOR y COMPRADOR deben ser roles válidos");
    }

    @Test
    @DisplayName("CP-02: rechaza rol vacío o no permitido")
    void cp02RolesInvalidos() {
        assertTrue(!ValidadorRegistro.rolValido("")
                        && !ValidadorRegistro.rolValido("Vendedor"),
                "el rol vacío y 'Vendedor' deben ser rechazados");
    }

    @Test
    @DisplayName("CP-03: agricultor con finca vacía o solo espacios")
    void cp03AgricultorSinFinca() {
        String resultado = servicio.registrar("AGRICULTOR", "   ", "Armenia",
                "3124567890", true, "luis@mail.com");

        assertEquals("ERROR: finca y municipio obligatorios", resultado,
                "finca de solo espacios debe bloquear el registro");
    }

    @Test
    @DisplayName("CP-04: comprador sin finca ni municipio (no se exigen)")
    void cp04CompradorSinFinca() {
        String resultado = servicio.registrar("COMPRADOR", "", "",
                "3124567890", true, "marta@mail.com");

        assertEquals("OK", resultado,
                "al comprador no se le exige finca ni municipio");
    }

    @Test
    @DisplayName("CP-05: celular válido (10 dígitos que empieza por 3)")
    void cp05CelularValido() {
        assertTrue(ValidadorRegistro.celularValido("3124567890"),
                "el celular 3124567890 debe ser válido");
    }

    @Test
    @DisplayName("CP-06: celulares inválidos (9 dígitos, 11 dígitos, con letra)")
    void cp06CelularesInvalidos() {
        assertFalse(ValidadorRegistro.celularValido("312456789"),
                "9 dígitos deben ser rechazados");
        assertFalse(ValidadorRegistro.celularValido("31245678901"),
                "11 dígitos deben ser rechazados");
        assertFalse(ValidadorRegistro.celularValido("31A4567890"),
                "un celular con letra debe ser rechazado");
    }

    @Test
    @DisplayName("CP-07: no acepta la política → impide finalizar el registro")
    void cp07PoliticaNoAceptada() {
        String resultado = servicio.registrar("AGRICULTOR", "Finca El Cafe",
                "Armenia", "3124567890", false, "pedro@mail.com");

        assertEquals("ERROR: politica no aceptada", resultado,
                "no aceptar la política debe bloquear el registro");
    }

    @Test
    @DisplayName("CP-08: acepta la política y datos válidos → permite finalizar")
    void cp08PoliticaAceptada() {
        String resultado = servicio.registrar("AGRICULTOR", "Finca El Cafe",
                "Armenia", "3124567890", true, "pedro@mail.com");

        assertEquals("OK", resultado,
                "con política aceptada y datos válidos el registro debe pasar");
    }

    @Test
    @DisplayName("CP-09: correo repetido ignorando mayúsculas/minúsculas")
    void cp09CorreoRepetido() {
        servicio.registrar("COMPRADOR", "", "", "3124567890", true, "ana@mail.com");

        String resultado = servicio.registrar("COMPRADOR", "", "", "3109876543",
                true, "ANA@Mail.com");

        assertEquals("ERROR: correo ya registrado", resultado,
                "ANA@Mail.com debe chocar con ana@mail.com");
        assertEquals(1, servicio.cantidadCorreos(),
                "el correo duplicado no debe contarse de nuevo");
    }

    @Test
    @DisplayName("CP-10: correo nuevo si se permite registrar")
    void cp10CorreoNuevo() {
        String resultado = servicio.registrar("COMPRADOR", "", "", "3124567890",
                true, "juan@mail.com");

        assertEquals("OK", resultado, "un correo nuevo debe registrarse");
        assertEquals(1, servicio.cantidadCorreos(),
                "el correo exitoso debe quedar guardado");
    }
}
