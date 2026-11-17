package co.edu.eam.agrodirecto.registro;

import java.util.List;

/**
 * Validaciones del módulo de registro (RF-01 a RF-05).
 * Sin dependencias externas: cada método aplica una regla de negocio de forma
 * aislada.
 */
public final class ValidadorRegistro {

    private ValidadorRegistro() {
    }

    /**
     * RF-01: el rol debe ser exactamente "AGRICULTOR" o "COMPRADOR".
     */
    public static boolean rolValido(String rol) {
        return "AGRICULTOR".equals(rol) || "COMPRADOR".equals(rol);
    }

    /**
     * RF-02: si el rol es AGRICULTOR, finca y municipio son obligatorios
     * (no nulos, no vacíos ni solo espacios). Para otros roles no aplica.
     */
    public static boolean camposAgricultorValidos(String rol, String finca, String municipio) {
        if (!"AGRICULTOR".equals(rol)) {
            return true; // RF-02 no aplica al rol COMPRADOR
        }
        return noVacio(finca) && noVacio(municipio);
    }

    /**
     * RF-03: el celular debe tener exactamente 10 dígitos y empezar por '3'.
     */
    public static boolean celularValido(String celular) {
        if (celular == null || celular.length() != 10) {
            return false;
        }
        if (celular.charAt(0) != '3') {
            return false;
        }
        for (int i = 0; i < celular.length(); i++) {
            char c = celular.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    /**
     * RF-04: el usuario debe aceptar la política de tratamiento de datos
     * personales.
     * Se eligió un método propio para que las cinco reglas del registro vivan
     * siempre
     * en esta clase, en lugar de que una quede incrustada en el servicio.
     */
    public static boolean politicaAceptada(boolean aceptada) {
        return aceptada;
    }

    /**
     * RF-05: el correo no debe estar ya registrado, ignorando
     * mayúsculas/minúsculas.
     */
    public static boolean correoNoRegistrado(List<String> correos, String correo) {
        for (String existente : correos) {
            if (existente.equalsIgnoreCase(correo)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Indica si el texto tiene contenido real (no nulo, no vacío ni solo espacios).
     */
    private static boolean noVacio(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }
}
