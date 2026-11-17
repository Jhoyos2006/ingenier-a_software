package co.edu.eam.agrodirecto.guia2;

import co.edu.eam.agrodirecto.registro.ServicioRegistro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * ============================================================================
 * GUÍA 2 - PRUEBA DE RENDIMIENTO (no funcional)
 * ============================================================================
 * ¿QUÉ ES?  Comprueba CÓMO de rápido lo hace el sistema. El criterio no es un valor
 *           exacto sino una cualidad con UMBRAL: "debe tardar menos de X ms".
 *
 * ¿QUÉ HACE AQUÍ?  Mide con System.nanoTime() cuánto tarda ServicioRegistro en
 *           registrar 5 000 usuarios distintos. Se eligió el servicio completo (y no un
 *           validador suelto) porque RF-05 recorre TODA la lista de correos en cada
 *           registro: el costo crece con cada usuario (n^2/2 comparaciones), así que es
 *           el punto del sistema que más se puede degradar.
 *
 * UMBRAL:   1000 ms para 5 000 registros. (Con 10 000 tardaba ~900 ms: como el costo es
 *           cuadrático, duplicar usuarios cuadruplica el tiempo. Hallazgo a reportar.)
 *
 * REPETICIÓN: se mide 3 veces porque el tiempo varía según la máquina y la carga;
 *           se exige que las 3 mediciones queden por debajo del umbral.
 *
 * LIMITACIÓN (decirla en la sustentación): mide un método en UNA máquina; NO
 *           representa muchos usuarios simultáneos (eso sería una prueba de carga).
 *
 * EVIDENCIA DE ÉXITO: tiempo medido frente al umbral (se imprime en consola).
 * ============================================================================
 */
@DisplayName("Guía 2 - RENDIMIENTO: 5 000 registros en menos de 1000 ms")
class RendimientoTest {

    private static final int USUARIOS = 5_000;
    private static final long UMBRAL_MS = 1_000;
    private static final int REPETICIONES = 3;

    @Test
    @DisplayName("REN-01 registrar 5 000 usuarios (3 mediciones) < 1000 ms")
    void ren01RegistroMasivo() {
        System.out.println("\n--- PRUEBA DE RENDIMIENTO: " + USUARIOS + " registros, umbral " + UMBRAL_MS + " ms ---");

        for (int rep = 1; rep <= REPETICIONES; rep++) {
            ServicioRegistro servicio = new ServicioRegistro(); // lista vacía en cada medición

            long inicio = System.nanoTime();                    // 1) marca de tiempo inicial
            for (int i = 0; i < USUARIOS; i++) {                // 2) trabajo a medir
                servicio.registrar("COMPRADOR", "", "", "3001234567", true, "usuario" + i + "@mail.com");
            }
            long ms = (System.nanoTime() - inicio) / 1_000_000; // 3) nanosegundos -> milisegundos

            boolean ok = ms < UMBRAL_MS;
            System.out.println("REN-01 medicion " + rep + ": " + ms + " ms -> " + (ok ? "APROBO" : "FALLO"));

            // Comprobación extra: el trabajo realmente se hizo (no medimos un ciclo vacío).
            assertTrue(servicio.cantidadCorreos() == USUARIOS, "no se registraron todos los usuarios");
            assertTrue(ok, "Medición " + rep + " tardó " + ms + " ms, umbral " + UMBRAL_MS + " ms");
        }
    }
}
