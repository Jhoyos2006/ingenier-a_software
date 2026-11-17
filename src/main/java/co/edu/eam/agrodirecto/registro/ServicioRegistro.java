package co.edu.eam.agrodirecto.registro;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio de registro de usuarios: valida los requisitos RF-01 a RF-05 en orden
 * y, si todo pasa, guarda el correo registrado.
 * Se eligió que el servicio mantenga la lista de correos en memoria porque RF-05 necesita
 * comparar contra lo ya registrado; con un almacenamiento real solo se reemplazaría esa
 * lista, sin tocar las reglas de validación.
 */
public class ServicioRegistro {

    /** Correos ya registrados en este servicio (comparación sin distinguir mayúsculas). */
    private final List<String> correos = new ArrayList<>();

    /**
     * Registra un usuario validando en orden RF-01 → RF-05.
     * Se eligió devolver el mensaje de error en vez de un booleano o una excepción porque,
     * en un formulario de registro, el usuario necesita saber qué regla falló para corregirla;
     * "OK" significa que las cinco reglas pasaron y el correo quedó guardado.
     */
    public String registrar(String rol, String finca, String municipio,
                            String celular, boolean aceptaPolitica, String correo) {
        if (!ValidadorRegistro.rolValido(rol)) {
            return "ERROR: rol invalido";
        }
        if (!ValidadorRegistro.camposAgricultorValidos(rol, finca, municipio)) {
            return "ERROR: finca y municipio obligatorios";
        }
        if (!ValidadorRegistro.celularValido(celular)) {
            return "ERROR: celular invalido";
        }
        if (!ValidadorRegistro.politicaAceptada(aceptaPolitica)) {
            return "ERROR: politica no aceptada";
        }
        if (!ValidadorRegistro.correoNoRegistrado(correos, correo)) {
            return "ERROR: correo ya registrado";
        }
        correos.add(correo);
        return "OK";
    }

    /**
     * Cantidad de correos registrados hasta el momento.
     */
    public int cantidadCorreos() {
        return correos.size();
    }
}
