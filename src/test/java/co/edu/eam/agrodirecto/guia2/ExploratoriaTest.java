package co.edu.eam.agrodirecto.guia2;

import co.edu.eam.agrodirecto.registro.ServicioRegistro;
import co.edu.eam.agrodirecto.soporte.Prueba;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * ============================================================================
 * GUÍA 2 - PRUEBA EXPLORATORIA
 * ============================================================================
 * ¿QUÉ ES?  Se prueba SIN casos escritos de antemano: el probador sigue una "carta"
 *           (objetivo) y va inventando entradas raras para buscar defectos NO previstos.
 *
 * CARTA:    "Explorar el registro con correos y celulares poco comunes".
 *
 * CLASIFICACIÓN DE CADA SONDEO (igual que en la guía):
 *   - Hallazgo: el resultado contradice el requisito o una norma conocida.
 *   - Pregunta: el requisito es ambiguo -> se consulta al cliente.
 *   - Correcto: el sistema se comportó como se esperaba.
 *
 * IMPORTANTE: aquí las aserciones registran lo que el sistema HACE HOY (lo observado),
 *           no lo que debería hacer. Así la prueba deja evidencia reproducible de cada
 *           hallazgo. Cuando el equipo corrija un hallazgo, este caso fallará y avisará
 *           que hay que actualizar la clasificación.
 *
 * EVIDENCIA DE ÉXITO: notas y lista de hallazgos (se imprime la tabla en consola).
 * ============================================================================
 */
@DisplayName("Guía 2 - EXPLORATORIA: correos y celulares poco comunes en el registro")
class ExploratoriaTest {

    /** Imprime una fila de la tabla de sondeos: igual al formato de la presentación. */
    private static void sondeo(String id, String dato, String resultado, String clasificacion) {
        System.out.printf("%-4s | %-38s | %-28s | %s%n", id, dato, resultado, clasificacion);
    }

    /** Registra un comprador válido cambiando solo celular y correo (los datos explorados). */
    private static String registrar(ServicioRegistro s, String celular, String correo) {
        return s.registrar("COMPRADOR", "", "", celular, true, correo);
    }

    @Test
    @DisplayName("EXP carta: correos y celulares poco comunes (8 sondeos)")
    void exploracionRegistro() {
        System.out.println("\n--- PRUEBA EXPLORATORIA: carta 'correos y celulares poco comunes' ---");
        sondeo("ID", "Dato", "Resultado", "Clasificacion");

        // E-1: correo con espacio en medio. No existe validación de formato de correo.
        String r1 = registrar(new ServicioRegistro(), "3001234567", "ana @mail.com");
        sondeo("E-1", "correo 'ana @mail.com'", r1, "Hallazgo");
        Prueba.verificar("E-1 observado", "OK", r1);

        // E-2: correo sin arroba. Se acepta: contradice la norma de un correo electrónico.
        String r2 = registrar(new ServicioRegistro(), "3001234567", "anamail.com");
        sondeo("E-2", "correo 'anamail.com' (sin @)", r2, "Hallazgo");
        Prueba.verificar("E-2 observado", "OK", r2);

        // E-3: el mismo correo con espacio al final cuenta como OTRO correo -> duplicado oculto.
        ServicioRegistro s3 = new ServicioRegistro();
        registrar(s3, "3001234567", "ana@mail.com");
        String r3 = registrar(s3, "3001234567", "ana@mail.com ");
        sondeo("E-3", "'ana@mail.com ' tras 'ana@mail.com'", r3, "Hallazgo (RF-05)");
        Prueba.verificar("E-3 observado", "OK", r3);

        // E-4: correo nulo -> se acepta y queda null guardado en la lista.
        String r4 = registrar(new ServicioRegistro(), "3001234567", null);
        sondeo("E-4", "correo null", r4, "Hallazgo");
        Prueba.verificar("E-4 observado", "OK", r4);

        // E-5: DESPUÉS de guardar un null, el siguiente registro revienta con
        //      NullPointerException en correoNoRegistrado (existente.equalsIgnoreCase).
        //      Es el hallazgo más grave: un dato malo deja el servicio inutilizable.
        ServicioRegistro s5 = new ServicioRegistro();
        registrar(s5, "3001234567", null);
        String r5;
        try {
            r5 = registrar(s5, "3001234567", "leo@mail.com");
        } catch (NullPointerException e) {
            r5 = "NullPointerException";
        }
        sondeo("E-5", "registro normal tras correo null", r5, "Hallazgo GRAVE");
        Prueba.verificar("E-5 observado", "NullPointerException", r5);

        // E-6: celular con indicativo de país. El requisito no dice si se admite -> se pregunta.
        String r6 = registrar(new ServicioRegistro(), "+573001234567", "e6@mail.com");
        sondeo("E-6", "celular '+573001234567'", r6, "Pregunta");
        Prueba.verificar("E-6 observado", "ERROR: celular invalido", r6);

        // E-7: celular con espacios típicos de escritura "300 123 4567" -> ¿se deberían limpiar?
        String r7 = registrar(new ServicioRegistro(), "300 123 4567", "e7@mail.com");
        sondeo("E-7", "celular '300 123 4567'", r7, "Pregunta");
        Prueba.verificar("E-7 observado", "ERROR: celular invalido", r7);

        // E-8: correo repetido en mayúsculas -> se rechaza bien (RF-05 ignora mayúsculas).
        ServicioRegistro s8 = new ServicioRegistro();
        registrar(s8, "3001234567", "ana@mail.com");
        String r8 = registrar(s8, "3001234567", "ANA@Mail.COM");
        sondeo("E-8", "'ANA@Mail.COM' tras 'ana@mail.com'", r8, "Correcto");
        Prueba.verificar("E-8 observado", "ERROR: correo ya registrado", r8);
    }
}
