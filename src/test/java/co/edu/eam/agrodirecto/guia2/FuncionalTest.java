package co.edu.eam.agrodirecto.guia2;

import co.edu.eam.agrodirecto.registro.ServicioRegistro;
import co.edu.eam.agrodirecto.soporte.Prueba;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * ============================================================================
 * GUÍA 2 - PRUEBA FUNCIONAL
 * ============================================================================
 * ¿QUÉ ES?  Comprueba QUÉ hace el sistema: que un requisito se cumpla, con entradas
 *           y salidas concretas. (Ejemplo de la guía: "la edad 17 se rechaza").
 *
 * REQUISITO PROBADO:  RF-03 -> "El celular debe tener exactamente 10 dígitos y
 *           empezar por 3". Se prueba a través de ServicioRegistro.registrar(...),
 *           que es lo que usaría el formulario real (no el método aislado).
 *
 * TÉCNICA:  valores límite + partición de equivalencia:
 *           - 10 dígitos que empiezan por 3  -> válido   (límite exacto)
 *           - 9 dígitos                      -> inválido (justo por debajo)
 *           - 11 dígitos                     -> inválido (justo por encima)
 *           - 10 dígitos que empiezan por 2  -> inválido (regla del primer dígito)
 *           - 10 caracteres con una letra    -> inválido (solo dígitos)
 *
 * EVIDENCIA DE ÉXITO: resultado ESPERADO igual al OBTENIDO en cada caso.
 * ============================================================================
 */
@DisplayName("Guía 2 - FUNCIONAL: RF-03 celular de 10 dígitos que empieza por 3")
class FuncionalTest {

    private static final String ERROR_CELULAR = "ERROR: celular invalido";
    private ServicioRegistro servicio;

    @BeforeEach
    void setUp() {
        // Servicio nuevo en cada prueba para que un caso no afecte a otro.
        servicio = new ServicioRegistro();
    }

    /** Atajo: registra un comprador válido cambiando SOLO el celular (el dato bajo prueba). */
    private String registrarConCelular(String celular) {
        return servicio.registrar("COMPRADOR", "", "", celular, true, "func@mail.com");
    }

    @Test
    @DisplayName("FUN-01 celular 3001234567 (10 dígitos, empieza por 3) -> OK")
    void fun01CelularValido() {
        Prueba.verificar("FUN-01 celular 10 digitos", "OK", registrarConCelular("3001234567"));
    }

    @Test
    @DisplayName("FUN-02 celular de 9 dígitos -> ERROR")
    void fun02NueveDigitos() {
        Prueba.verificar("FUN-02 celular 9 digitos", ERROR_CELULAR, registrarConCelular("300123456"));
    }

    @Test
    @DisplayName("FUN-03 celular de 11 dígitos -> ERROR")
    void fun03OnceDigitos() {
        Prueba.verificar("FUN-03 celular 11 digitos", ERROR_CELULAR, registrarConCelular("30012345678"));
    }

    @Test
    @DisplayName("FUN-04 celular que empieza por 2 -> ERROR")
    void fun04EmpiezaPorDos() {
        Prueba.verificar("FUN-04 celular empieza por 2", ERROR_CELULAR, registrarConCelular("2001234567"));
    }

    @Test
    @DisplayName("FUN-05 celular con una letra -> ERROR")
    void fun05ConLetra() {
        Prueba.verificar("FUN-05 celular con letra", ERROR_CELULAR, registrarConCelular("30012345a7"));
    }
}
