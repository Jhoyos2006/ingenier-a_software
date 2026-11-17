# Investigación: Apache Maven y pruebas unitarias en Java

**AgroDirecto**

- Jonathan David Ceballos Jiménez
- Leonardo Argoty
- Owen Fernando Bareño
- Juan Andrés Hoyos Cruz

Ingeniería De Software 2  
David Cano Baquero (Docente)  
Institución Universitaria EAM  
Facultad De Ingeniería — Ingeniería De Software  
Armenia, Quindío — 2026

---

## 1. Introducción

Para verificar que el código de un proyecto Java funcione correctamente se necesita una herramienta que compile, gestione las librerías y ejecute las pruebas de forma automática. Este documento explica qué es Maven, cómo se implementa en un proyecto Java y cómo se usa para ejecutar pruebas unitarias, que será la base de las pruebas que desarrollaremos más adelante.

---

## 2. ¿Qué es Maven?

Apache Maven es una herramienta para gestionar proyectos Java. Se encarga de las tareas necesarias para manejar un proyecto: compilación, pruebas, empaquetado, documentación y distribución [1]. Fue desarrollada por la Apache Software Foundation, la misma que creó Ant, otra herramienta de construcción, pero Maven se considera más avanzada [2]. La idea es que, en lugar de ejecutar cada paso manualmente, el proyecto se describe en un archivo de configuración y Maven sabe qué hacer con él.

### 2.1. Conceptos clave

- **POM (pom.xml):** archivo en la raíz del proyecto donde se declaran el nombre, la versión de Java, las dependencias y los plugins.
- **Dependencias:** librerías externas que usa el proyecto. Maven las descarga automáticamente desde el repositorio Maven Central y las guarda en la máquina local [2]. Si se declaran con `scope test`, solo se usan al probar el código y no se incluyen en el producto final.
- **Estructura estándar:** el código fuente va en `src/main/java` y el de las pruebas en `src/test/java` [3]. Como todos los proyectos usan la misma estructura, Maven no necesita que se le indique dónde está cada cosa.
- **Ciclo de vida:** Maven ejecuta una secuencia de fases en orden (validate, compile, test, package, entre otras), y al llamar una fase ejecuta también todas las anteriores [4]. Por eso, al correr `mvn package` primero se ejecutan las pruebas, y si alguna falla no se genera el `.jar`.
- **Plugins y goals:** un goal es una acción concreta que pertenece a un plugin; por ejemplo, en `archetype:generate`, `archetype` es el plugin y `generate` es el goal [5]. El plugin Surefire, que ejecuta las pruebas, funciona de esta manera.

---

## 3. ¿Cómo se implementa en Java?

### 3.1. Requisitos y creación del proyecto

Maven es una herramienta de Java, por lo que se debe tener instalado el JDK [3]. Después se descarga Maven, se agrega su carpeta `bin` al PATH del sistema y se verifica la instalación con el comando `mvn -version`. Para crear el proyecto se usa un archetype (plantilla de proyecto); el comando de la guía oficial, adaptado a un proyecto de ejemplo, es [3] (en la consola se escribe en una sola línea):

```bash
mvn archetype:generate -DgroupId=co.edu.eam.ejemplo \
  -DartifactId=calculadora \
  -DarchetypeArtifactId=maven-archetype-quickstart \
  -DinteractiveMode=false
```

Esto crea la carpeta `calculadora` con el `pom.xml`, `src/main/java` y `src/test/java`. Los parámetros `groupId` y `artifactId` son las coordenadas que identifican al proyecto: el primero suele ser el paquete base y el segundo el nombre del proyecto. El archetype también genera una clase y una prueba de ejemplo (`App.java` y `AppTest.java`) y un `pom.xml` con su propia dependencia de JUnit; como usaremos JUnit 5, hay que reemplazar esa dependencia y borrar o actualizar `AppTest.java`.

### 3.2. Configuración del pom.xml para pruebas

Para trabajar con pruebas unitarias se agregan tres elementos al `pom.xml`: la versión de Java, la dependencia de JUnit 5 y el plugin Surefire.

```xml
<properties>
  <maven.compiler.release>17</maven.compiler.release>
</properties>

<dependencies>
  <dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.2</version>
    <scope>test</scope>
  </dependency>
</dependencies>

<build>
  <plugins>
    <plugin>
      <artifactId>maven-surefire-plugin</artifactId>
      <version>3.2.5</version>
    </plugin>
  </plugins>
</build>
```

Estas líneas van dentro de `<project>` en el `pom.xml` que generó el archetype, fusionándolas con las secciones que ya existan. La propiedad `maven.compiler.release` define la versión de Java con la que se compila; para Java 9 o superior la guía oficial recomienda además usar al menos la versión 3.6.0 del `maven-compiler-plugin` [3]. Las versiones de JUnit y Surefire del ejemplo deben confirmarse en Maven Central al implementarlas, porque se actualizan con frecuencia.

### 3.3. Comandos principales

- `mvn compile`: compila el código de `src/main/java`.
- `mvn test`: compila el código y las pruebas, y ejecuta las pruebas unitarias.
- `mvn package`: ejecuta las pruebas y, si todas pasan, genera el `.jar`.
- `mvn clean test`: borra la carpeta `target` (archivos generados) y ejecuta las pruebas desde cero.

---

## 4. Pruebas unitarias con Maven

### 4.1. Qué son y cómo las ejecuta Maven

Una prueba unitaria verifica que una parte pequeña del código, normalmente un método, devuelva el resultado esperado para una entrada determinada. En Maven se ejecutan automáticamente en la fase `test` del ciclo de vida [4], por lo que cada vez que se construye el proyecto se comprueba que la lógica siga funcionando.

Se necesitan dos componentes. El primero es JUnit 5, cuya dependencia recomendada es `org.junit.jupiter:junit-jupiter` con `scope test`; desde la versión 5.4.0 este artefacto agrupa `junit-jupiter-api`, `junit-jupiter-params` y `junit-jupiter-engine` [6], [7]. El segundo es el plugin Surefire, que ejecuta las pruebas y requiere la versión 2.22.0 o superior para soportar JUnit 5 de forma nativa [6], [8]. Si se usa una versión vieja de Surefire, las pruebas no se ejecutan [7]. Además, Surefire solo busca clases dentro de `src/test/java` y las identifica por su nombre, por ejemplo las que terminan en `Test` [8].

### 4.2. Anotaciones y métodos de JUnit 5

Las anotaciones están en el paquete `org.junit.jupiter.api` y los métodos de verificación (assertions) en la clase `org.junit.jupiter.api.Assertions` [9]. Los que usaremos son:

| Elemento | Para qué sirve |
|---|---|
| `@Test` | Marca un método como prueba; JUnit ejecuta los métodos con esta anotación y Surefire lo lanza desde Maven. |
| `@DisplayName("texto")` | Define un nombre legible para la prueba en los reportes [9]. |
| `@BeforeEach` | El método marcado se ejecuta antes de cada prueba; sirve para crear objetos o datos iniciales y que cada test empiece limpio [9]. |
| `assertEquals(esperado, real)` | Verifica que dos valores sean iguales. La prueba falla si no lo son. |
| `assertEquals(esperado, real, delta)` | Versión para `double` o `float`: acepta una diferencia máxima (delta), necesaria porque los decimales pueden tener errores de precisión. |
| `assertTrue(condición)` / `assertFalse(condición)` | Verifican que una condición booleana sea verdadera o falsa, por ejemplo el resultado de un método que valida datos. |
| `assertNotNull(objeto)` | Verifica que un objeto no sea null. |
| `assertThrows(Clase.class, () -> código)` | Verifica que el código lance la excepción indicada; sirve para probar que un método rechaza datos inválidos [9]. |

### 4.3. Ejemplo

El siguiente ejemplo usa una calculadora simple. La clase está en `src/main/java` y tiene dos métodos: uno que suma y otro que divide y lanza una excepción si el divisor es cero.

```java
package co.edu.eam.ejemplo;

public class Calculadora {

    public int sumar(int a, int b) {
        return a + b;
    }

    public double dividir(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("No se puede dividir por cero");
        }
        return a / b;
    }
}
```

Su clase de prueba va en `src/test/java`, en el mismo paquete y con el nombre terminado en `Test`:

```java
package co.edu.eam.ejemplo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculadoraTest {

    private Calculadora calc;

    @BeforeEach
    void iniciar() {
        calc = new Calculadora();
    }

    @Test
    @DisplayName("Suma dos numeros enteros")
    void sumaDosNumeros() {
        assertEquals(5, calc.sumar(2, 3));
    }

    @Test
    @DisplayName("Divide con resultado decimal")
    void divideConDecimales() {
        assertEquals(2.5, calc.dividir(5, 2), 0.01);
    }

    @Test
    @DisplayName("Lanza excepcion al dividir por cero")
    void divisionPorCero() {
        assertThrows(IllegalArgumentException.class,
            () -> calc.dividir(5, 0));
    }
}
```

El método `iniciar()` tiene `@BeforeEach`, por lo que se ejecuta antes de cada prueba y crea una `Calculadora` nueva; así ninguna prueba depende de otra. La primera prueba llama a `sumar(2, 3)` y compara con `assertEquals` el valor esperado (5) contra el real. La segunda usa `assertEquals` con delta (0.01) porque trabaja con `double`. La tercera no compara un valor, sino que comprueba con `assertThrows` que `dividir(5, 0)` lance `IllegalArgumentException`; el código a evaluar se pasa como expresión lambda (`() ->`). Importar `Assertions.*` con `import static` permite escribir los métodos sin anteponer el nombre de la clase.

### 4.4. Ejecución e interpretación de resultados

Al ejecutar `mvn test`, Maven compila el código y las pruebas, ejecuta Surefire y muestra un resumen con la cantidad de pruebas ejecutadas, fallidas, con error y omitidas (Tests run, Failures, Errors, Skipped). Una prueba falla (failure) cuando una verificación no se cumple, y tiene error cuando se lanza una excepción inesperada. Si hay alguna de las dos, la construcción termina con `BUILD FAILURE`; si todas pasan, termina con `BUILD SUCCESS`. Los reportes quedan en la carpeta `target/surefire-reports`.

---

## 5. Conclusiones

- Maven automatiza la compilación, la gestión de dependencias, las pruebas y el empaquetado de un proyecto Java a partir del archivo `pom.xml`.
- Gracias a la estructura estándar y al ciclo de vida, ejecutar las pruebas unitarias se reduce a correr `mvn test`.
- Para pruebas unitarias en Java se necesita JUnit 5 con `scope test` y una versión reciente de Surefire; sin esto Maven no detecta los tests.
- Las anotaciones `@Test`, `@DisplayName` y `@BeforeEach`, junto con `assertEquals`, `assertTrue`, `assertNotNull` y `assertThrows`, son suficientes para escribir pruebas unitarias básicas de métodos de lógica, incluyendo los que validan datos y lanzan excepciones.

---

## 6. Referencias

- [1] Manning Publications, "Appendix: Introduction to Maven," in *Netty in Action*. Accessed: Oct. 13, 2026. [Online]. Available: https://livebook.manning.com/book/netty-in-action/appendix
- [2] Software Testing Help, "What is Maven – Maven tutorial for beginners." Accessed: Oct. 13, 2026. [Online]. Available: https://www.softwaretestinghelp.com/maven-tutorial/
- [3] Apache Software Foundation, "Maven in 5 minutes," Apache Maven Project. Accessed: Oct. 13, 2026. [Online]. Available: https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html
- [4] Apache Software Foundation, "Introduction to the build lifecycle," Apache Maven Project. Accessed: Oct. 13, 2026. [Online]. Available: https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html
- [5] Sonatype, "A simple Maven project," in *Maven by Example*. Accessed: Oct. 13, 2026. [Online]. Available: https://www.sonatype.com/resources/guides/maven-by-example/simple-maven-project
- [6] A. Huttunen, "JUnit 5 Maven example: Running tests with Surefire." Accessed: Oct. 13, 2026. [Online]. Available: https://www.arhohuttunen.com/junit-5-maven-example/
- [7] TestMu AI, "JUnit Maven dependency: JUnit 5, JUnit 6, and Surefire setup." Accessed: Oct. 13, 2026. [Online]. Available: https://www.testmuai.com/learning-hub/junit-maven-dependency/
- [8] Mkyong, "JUnit 5 + Maven examples." Accessed: Oct. 13, 2026. [Online]. Available: https://mkyong.com/?p=15175
- [9] JournalDev, "JUnit5 tutorial." Accessed: Oct. 13, 2026. [Online]. Available: https://www.journaldev.com/20834/junit5-tutorial
