package co.edu.eam.agrodirecto.guia2;

import co.edu.eam.agrodirecto.registro.ServicioRegistro;
import co.edu.eam.agrodirecto.soporte.Prueba;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * ============================================================================
 * GUÍA 2 - PRUEBA DE SEGURIDAD, con enfoque DEFENSIVO (no funcional)
 * ============================================================================
 * ¿QUÉ ES?  Verificar que las DEFENSAS existan: que entradas nulas, vacías o
 *           desmesuradas se rechacen de forma controlada, SIN lanzar excepciones
 *           (una excepción no controlada puede tumbar el servicio o mostrar datos
 *           internos al usuario). No se ataca ningún sistema.
 *
 * ¿QUÉ HACE AQUÍ?  Envía a ServicioRegistro.registrar(...) datos maliciosos o
 *           malformados y exige que la respuesta sea un mensaje de ERROR normal.
 *           Si el método lanzara una excepción, JUnit marcaría la prueba en rojo.
 *
 * LISTA DE VERIFICACIÓN (revisión manual, no automatizable aquí):
 *   - Contraseñas: ¿se guardan sin proteger?      -> el registro aún no maneja clave.
 *   - Mensajes de error: ¿revelan rutas o datos internos? -> NO: son textos cortos
 *     como "ERROR: celular invalido" (lo comprueba SEG-06).
 *   - Acceso: ¿se llega a pantallas privadas sin sesión? -> no hay pantallas aún.
 *
 * LIMITACIÓN: estas pruebas cubren solo la VALIDACIÓN DE ENTRADAS; no bastan para
 *           decir que el sistema es seguro. Además, la prueba exploratoria encontró
 *           que un correo null SÍ se acepta (E-4/E-5): defensa pendiente de corregir.
 *
 * EVIDENCIA DE ÉXITO: casos aprobados + lista de verificación.
 * ============================================================================
 */
@DisplayName("Guía 2 - SEGURIDAD: entradas maliciosas se rechazan sin excepción")
class SeguridadTest {

    private ServicioRegistro servicio;

    @BeforeEach
    void setUp() {
        servicio = new ServicioRegistro();
    }

    @Test
    @DisplayName("SEG-01 rol null -> se rechaza, sin excepción")
    void seg01RolNull() {
        Prueba.verificar("SEG-01 rol null", "ERROR: rol invalido",
                servicio.registrar(null, "", "", "3001234567", true, "s1@mail.com"));
    }

    @Test
    @DisplayName("SEG-02 celular null -> se rechaza, sin excepción")
    void seg02CelularNull() {
        Prueba.verificar("SEG-02 celular null", "ERROR: celular invalido",
                servicio.registrar("COMPRADOR", "", "", null, true, "s2@mail.com"));
    }

    @Test
    @DisplayName("SEG-03 celular de 309 caracteres -> se rechaza")
    void seg03CelularGigante() {
        String gigante = "3" + "0".repeat(308); // entrada desmesurada (posible intento de desbordar)
        Prueba.verificar("SEG-03 celular 309 caracteres", "ERROR: celular invalido",
                servicio.registrar("COMPRADOR", "", "", gigante, true, "s3@mail.com"));
    }

    @Test
    @DisplayName("SEG-04 agricultor con finca y municipio null -> se rechaza")
    void seg04FincaNull() {
        Prueba.verificar("SEG-04 finca/municipio null", "ERROR: finca y municipio obligatorios",
                servicio.registrar("AGRICULTOR", null, null, "3001234567", true, "s4@mail.com"));
    }

    @Test
    @DisplayName("SEG-05 inyección de código en el rol -> se rechaza")
    void seg05InyeccionEnRol() {
        // Texto típico de inyección SQL: el rol solo admite valores exactos, así que se rechaza.
        Prueba.verificar("SEG-05 rol con inyeccion", "ERROR: rol invalido",
                servicio.registrar("COMPRADOR' OR '1'='1", "", "", "3001234567", true, "s5@mail.com"));
    }

    @Test
    @DisplayName("SEG-06 un registro rechazado no guarda datos y el mensaje no revela internos")
    void seg06RechazoNoGuarda() {
        String r = servicio.registrar("COMPRADOR", "", "", "123", true, "s6@mail.com");
        // El mensaje NO debe contener trazas, nombres de clases ni rutas del servidor.
        boolean mensajeLimpio = !r.contains("Exception") && !r.contains("co.edu") && !r.contains("\\");
        Prueba.verificar("SEG-06a mensaje sin datos internos", true, mensajeLimpio);
        Prueba.verificar("SEG-06b no se guardo el correo", 0, servicio.cantidadCorreos());
    }
}
