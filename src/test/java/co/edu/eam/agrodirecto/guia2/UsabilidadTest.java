package co.edu.eam.agrodirecto.guia2;

import co.edu.eam.agrodirecto.registro.ServicioRegistro;
import co.edu.eam.agrodirecto.soporte.Prueba;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * ============================================================================
 * GUÍA 2 - PRUEBA DE USABILIDAD (no funcional)
 * ============================================================================
 * ¿QUÉ ES?  Mide la FACILIDAD DE USO. La prueba real NO es código: se observa a
 *           2 o 3 personas AJENAS al equipo usando el sistema SIN ayuda, y se anota
 *           la planilla de abajo. (Una persona del equipo no puede ser su propio
 *           evaluador: ya sabe cómo funciona.)
 *
 * PLANILLA DE OBSERVACIÓN (llenar a mano con cada participante):
 *   | Tarea                                   | ¿La completó? | Equivocaciones | Comentario                     |
 *   | Registrarse como comprador con datos OK | Sí / No       | Número         | Dónde dudó                     |
 *   | Registrarse con celular de 9 dígitos    | Sí / No       | Número         | ¿Entendió por qué se rechazó?  |
 *
 * ¿QUÉ HACE ESTE CÓDIGO ENTONCES?  Una revisión AUTOMÁTICA de apoyo (heurística):
 *   - USA-01: cada mensaje de error debe NOMBRAR el campo que falló, para que el
 *             usuario sepa qué corregir. -> se cumple.
 *   - USA-02: imprime los mensajes tal como los vería el usuario y deja anotado el
 *             hallazgo: "ERROR: celular invalido" no dice CUÁL es la regla (10 dígitos,
 *             empieza por 3). Igual que "edad fuera de rango" en la guía: es un hallazgo
 *             de USABILIDAD, no de funcionalidad (el sistema rechaza bien, pero no orienta).
 *
 * EVIDENCIA DE ÉXITO: observación de usuarios ajenos (la planilla) + esta revisión.
 * ============================================================================
 */
@DisplayName("Guía 2 - USABILIDAD: los mensajes de error orientan al usuario")
class UsabilidadTest {

    @Test
    @DisplayName("USA-01 cada mensaje de error nombra el campo que debe corregirse")
    void usa01MensajeNombraCampo() {
        ServicioRegistro s = new ServicioRegistro();
        // Cada línea provoca un error distinto (RF-01 a RF-04) y revisa la palabra clave del campo.
        Prueba.verificar("USA-01a menciona 'rol'", true,
                s.registrar("ADMIN", "", "", "3001234567", true, "u@mail.com").contains("rol"));
        Prueba.verificar("USA-01b menciona 'finca'", true,
                s.registrar("AGRICULTOR", "", "", "3001234567", true, "u@mail.com").contains("finca"));
        Prueba.verificar("USA-01c menciona 'celular'", true,
                s.registrar("COMPRADOR", "", "", "123", true, "u@mail.com").contains("celular"));
        Prueba.verificar("USA-01d menciona 'politica'", true,
                s.registrar("COMPRADOR", "", "", "3001234567", false, "u@mail.com").contains("politica"));
    }

    @Test
    @DisplayName("USA-02 hallazgo: el mensaje de celular no explica la regla válida")
    void usa02MensajeNoExplicaRegla() {
        String mensaje = new ServicioRegistro()
                .registrar("COMPRADOR", "", "", "300123456", true, "u@mail.com");

        // ¿El mensaje le dice al usuario cuántos dígitos se esperan? Hoy NO.
        boolean explicaRegla = mensaje.contains("10");
        System.out.println("USA-02 mensaje que ve el usuario: \"" + mensaje + "\"");
        System.out.println("USA-02 HALLAZGO DE USABILIDAD: no indica la regla. Sugerido: "
                + "\"ERROR: el celular debe tener 10 digitos y empezar por 3\"");

        // Se registra lo observado (false). Si el equipo mejora el mensaje, este caso
        // fallará y recordará cerrar el hallazgo.
        Prueba.verificar("USA-02 mensaje explica la regla (observado)", false, explicaRegla);
    }
}
