package co.edu.eam.agrodirecto.registro;

import co.edu.eam.agrodirecto.soporte.Prueba;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

/**
 * Guía 1 - Pruebas UNITARIAS del módulo de registro (RF-01 a RF-05).
 * Cada caso llama a un solo método de {@link ValidadorRegistro}, sin usar ninguna otra clase:
 * si falla, el defecto está en ese método. Se privilegian los valores límite (el menor y el
 * mayor permitido, y los que quedan justo por fuera), como recomienda la guía.
 * Cada prueba imprime en consola: id, resultado esperado, obtenido y APROBO/FALLO.
 */
@DisplayName("Guía 1 - Unitarias de ValidadorRegistro (RF-01 a RF-05)")
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
class ValidadorRegistroUnitariasTest {

    @Nested
    @Order(1)
    @DisplayName("RF-01 rol AGRICULTOR o COMPRADOR")
    @TestMethodOrder(MethodOrderer.MethodName.class)
    class Rf01Rol {

        @Test
        @DisplayName("U-01 rol AGRICULTOR -> true")
        void u01Agricultor() {
            Prueba.verificar("U-01 rol AGRICULTOR", true, ValidadorRegistro.rolValido("AGRICULTOR"));
        }

        @Test
        @DisplayName("U-02 rol COMPRADOR -> true")
        void u02Comprador() {
            Prueba.verificar("U-02 rol COMPRADOR", true, ValidadorRegistro.rolValido("COMPRADOR"));
        }

        @Test
        @DisplayName("U-03 rol en minúsculas 'agricultor' -> false")
        void u03Minusculas() {
            Prueba.verificar("U-03 rol agricultor (minusculas)", false, ValidadorRegistro.rolValido("agricultor"));
        }

        @Test
        @DisplayName("U-04 rol nulo -> false")
        void u04Nulo() {
            Prueba.verificar("U-04 rol null", false, ValidadorRegistro.rolValido(null));
        }
    }

    @Nested
    @Order(2)
    @DisplayName("RF-02 finca y municipio obligatorios para AGRICULTOR")
    @TestMethodOrder(MethodOrderer.MethodName.class)
    class Rf02CamposAgricultor {

        @Test
        @DisplayName("U-05 agricultor con finca y municipio -> true")
        void u05Completo() {
            Prueba.verificar("U-05 agricultor con finca y municipio", true,
                    ValidadorRegistro.camposAgricultorValidos("AGRICULTOR", "Finca El Cafe", "Armenia"));
        }

        @Test
        @DisplayName("U-06 agricultor con municipio vacío -> false")
        void u06MunicipioVacio() {
            Prueba.verificar("U-06 agricultor con municipio vacio", false,
                    ValidadorRegistro.camposAgricultorValidos("AGRICULTOR", "Finca El Cafe", ""));
        }

        @Test
        @DisplayName("U-07 agricultor con finca nula -> false")
        void u07FincaNula() {
            Prueba.verificar("U-07 agricultor con finca null", false,
                    ValidadorRegistro.camposAgricultorValidos("AGRICULTOR", null, "Armenia"));
        }

        @Test
        @DisplayName("U-08 agricultor con municipio de tabulador y espacio -> false")
        void u08MunicipioSoloBlancos() {
            Prueba.verificar("U-08 agricultor con municipio solo blancos", false,
                    ValidadorRegistro.camposAgricultorValidos("AGRICULTOR", "Finca El Cafe", "\t "));
        }

        @Test
        @DisplayName("U-09 agricultor con finca de 1 carácter (mínimo con contenido) -> true")
        void u09FincaUnCaracter() {
            Prueba.verificar("U-09 agricultor con finca de 1 caracter", true,
                    ValidadorRegistro.camposAgricultorValidos("AGRICULTOR", "X", "Armenia"));
        }

        @Test
        @DisplayName("U-10 comprador sin finca ni municipio (nulos) -> true")
        void u10CompradorNoAplica() {
            Prueba.verificar("U-10 comprador sin finca ni municipio", true,
                    ValidadorRegistro.camposAgricultorValidos("COMPRADOR", null, null));
        }
    }

    @Nested
    @Order(3)
    @DisplayName("RF-03 celular de 10 dígitos que empieza por 3")
    @TestMethodOrder(MethodOrderer.MethodName.class)
    class Rf03Celular {

        @Test
        @DisplayName("U-11 celular 3000000000 (menor valor permitido) -> true")
        void u11LimiteInferior() {
            Prueba.verificar("U-11 celular 3000000000", true, ValidadorRegistro.celularValido("3000000000"));
        }

        @Test
        @DisplayName("U-12 celular 3999999999 (mayor valor permitido) -> true")
        void u12LimiteSuperior() {
            Prueba.verificar("U-12 celular 3999999999", true, ValidadorRegistro.celularValido("3999999999"));
        }

        @Test
        @DisplayName("U-13 celular de 9 dígitos (justo por debajo) -> false")
        void u13NueveDigitos() {
            Prueba.verificar("U-13 celular 312345678 (9 digitos)", false,
                    ValidadorRegistro.celularValido("312345678"));
        }

        @Test
        @DisplayName("U-14 celular de 11 dígitos (justo por encima) -> false")
        void u14OnceDigitos() {
            Prueba.verificar("U-14 celular 31234567890 (11 digitos)", false,
                    ValidadorRegistro.celularValido("31234567890"));
        }

        @Test
        @DisplayName("U-15 celular 2999999999 (empieza por 2, justo antes del 3) -> false")
        void u15EmpiezaPorDos() {
            Prueba.verificar("U-15 celular 2999999999", false, ValidadorRegistro.celularValido("2999999999"));
        }

        @Test
        @DisplayName("U-16 celular 4000000000 (empieza por 4, justo después del 3) -> false")
        void u16EmpiezaPorCuatro() {
            Prueba.verificar("U-16 celular 4000000000", false, ValidadorRegistro.celularValido("4000000000"));
        }

        @Test
        @DisplayName("U-17 celular con espacio '312 456789' (10 caracteres) -> false")
        void u17ConEspacio() {
            Prueba.verificar("U-17 celular '312 456789' (con espacio)", false,
                    ValidadorRegistro.celularValido("312 456789"));
        }

        @Test
        @DisplayName("U-18 celular nulo -> false")
        void u18Nulo() {
            Prueba.verificar("U-18 celular null", false, ValidadorRegistro.celularValido(null));
        }

        /**
         * Dígitos de ancho completo (U+FF10..U+FF19): se ven como números y llegan al copiar y pegar
         * desde algunos teclados o documentos, pero no son los dígitos 0-9 que exige RF-03.
         */
        @Test
        @DisplayName("U-19 celular '3' + 9 dígitos de ancho completo (１２３...) -> false")
        void u19DigitosAnchoCompleto() {
            Prueba.verificar("U-19 celular 3 + digitos de ancho completo (U+FF11..U+FF19)", false,
                    ValidadorRegistro.celularValido("3１２３４５６７８９"));
        }
    }

    @Nested
    @Order(4)
    @DisplayName("RF-04 aceptación de la política de datos")
    @TestMethodOrder(MethodOrderer.MethodName.class)
    class Rf04Politica {

        @Test
        @DisplayName("U-20 política aceptada -> true")
        void u20Aceptada() {
            Prueba.verificar("U-20 politica aceptada", true, ValidadorRegistro.politicaAceptada(true));
        }

        @Test
        @DisplayName("U-21 política no aceptada -> false")
        void u21NoAceptada() {
            Prueba.verificar("U-21 politica no aceptada", false, ValidadorRegistro.politicaAceptada(false));
        }
    }

    @Nested
    @Order(5)
    @DisplayName("RF-05 correo no duplicado sin importar mayúsculas")
    @TestMethodOrder(MethodOrderer.MethodName.class)
    class Rf05Correo {

        @Test
        @DisplayName("U-22 lista vacía, correo ana@mail.com -> true (no registrado)")
        void u22ListaVacia() {
            Prueba.verificar("U-22 lista vacia, ana@mail.com", true,
                    ValidadorRegistro.correoNoRegistrado(List.of(), "ana@mail.com"));
        }

        @Test
        @DisplayName("U-23 ya existe ana@mail.com, llega ANA@MAIL.COM -> false")
        void u23MismoCorreoMayusculas() {
            Prueba.verificar("U-23 existe ana@mail.com, llega ANA@MAIL.COM", false,
                    ValidadorRegistro.correoNoRegistrado(List.of("ana@mail.com"), "ANA@MAIL.COM"));
        }

        @Test
        @DisplayName("U-24 ya existe ana@mail.com, llega ana2@mail.com -> true")
        void u24CorreoDistinto() {
            Prueba.verificar("U-24 existe ana@mail.com, llega ana2@mail.com", true,
                    ValidadorRegistro.correoNoRegistrado(List.of("ana@mail.com"), "ana2@mail.com"));
        }

        @Test
        @DisplayName("U-25 el duplicado es el último de una lista de tres -> false")
        void u25DuplicadoAlFinal() {
            Prueba.verificar("U-25 duplicado al final de 3, llega Luis@Mail.com", false,
                    ValidadorRegistro.correoNoRegistrado(
                            List.of("a@mail.com", "b@mail.com", "luis@mail.com"), "Luis@Mail.com"));
        }
    }
}
