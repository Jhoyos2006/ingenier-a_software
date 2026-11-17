# Documentación: método `esDuplicado` y prueba unitaria con Maven

**Proyecto:** AgroDirecto  
**Materia:** Ingeniería De Software 2  
**Docente:** David Cano Baquero  
**Equipo:** Jonathan David Ceballos Jiménez, Leonardo Argoty, Owen Fernando Bareño, Juan Andrés Hoyos Cruz  
**Institución:** Institución Universitaria EAM — Armenia, Quindío — 2026

---

## 1. Contexto y justificación del método

En la matriz de riesgos del proyecto (parcial 1) se identificó el **error en el sistema de pedidos** como un riesgo de nivel alto (probabilidad media × impacto alto = 6). Entre sus causas están los errores de programación, fallas en la lógica del sistema y registros incorrectos; y entre sus consecuencias, los **pedidos duplicados** y cantidades equivocadas. El plan de contingencia estableció como acción preventiva "realizar pruebas del módulo de pedidos y validaciones de datos antes de publicar cambios".

Escogimos el método `esDuplicado` porque ataca directamente esa causa: detecta si un pedido que llega al sistema ya existe en el historial reciente del mismo comprador, para el mismo producto y la misma cantidad, dentro de una ventana de 2 minutos. Si el método falla, el sistema acepta pedidos duplicados, lo cual genera inconsistencias entre lo que registra el comprador y lo que recibe el agricultor, deteriora la confianza en la plataforma y puede ocasionar pérdidas económicas a ambas partes.

Además, la lógica es concreta y aislada (no depende de base de datos ni de red), lo que la hace ideal para una prueba unitaria pura con JUnit 5 y Maven, que es exactamente lo que estamos trabajando en la materia.

---

## 2. Descripción del método

### Firma

```java
public boolean esDuplicado(List<Pedido> recientes, Pedido nuevo)
```

### Qué hace

Recibe la lista de pedidos recientes del sistema y un pedido nuevo que acaba de llegar. Recorre la lista y, para cada pedido existente, verifica si coinciden los tres campos clave: `compradorId`, `producto` y `cantidad`. Si hay coincidencia, compara los timestamps: si el pedido existente fue creado hace menos de 2 minutos respecto al pedido nuevo, lo considera duplicado y devuelve `true`. Si ningún pedido en la lista cumple las tres condiciones dentro del tiempo límite, devuelve `false`.

### Regla de negocio

> Un pedido es duplicado si existe otro pedido del **mismo comprador**, con el **mismo producto** y la **misma cantidad**, creado en los **últimos 2 minutos**.

### Clase auxiliar `Pedido`

Para que el método funcione, se necesita una clase `Pedido` con los siguientes campos:

```java
public class Pedido {
    private String compradorId;
    private String producto;
    private double cantidad;
    private long timestamp; // System.currentTimeMillis() al momento de crear el pedido
    // constructor, getters
}
```

---

## 3. Implementación del método

```java
package co.edu.eam.agrodirecto;

import java.util.List;

public class GestorPedidos {

    private static final long VENTANA_MS = 2 * 60 * 1000; // 2 minutos en milisegundos

    public boolean esDuplicado(List<Pedido> recientes, Pedido nuevo) {
        for (Pedido p : recientes) {
            boolean mismoComprador = p.getCompradorId().equals(nuevo.getCompradorId());
            boolean mismoProducto  = p.getProducto().equals(nuevo.getProducto());
            boolean mismaCantidad  = p.getCantidad() == nuevo.getCantidad();
            boolean dentroVentana  = (nuevo.getTimestamp() - p.getTimestamp()) < VENTANA_MS;

            if (mismoComprador && mismoProducto && mismaCantidad && dentroVentana) {
                return true;
            }
        }
        return false;
    }
}
```

---

## 4. Pruebas unitarias

Se definen tres pruebas que cubren los escenarios clave del método.

### Prueba 1 — Mismo pedido en 30 segundos → debe detectarlo como duplicado

El comprador A pide 5 kg de tomate. Treinta segundos después hace exactamente el mismo pedido. Como 30 s < 2 min, el método debe devolver `true`.

### Prueba 2 — Mismo pedido a los 10 minutos → NO es duplicado

El comprador A pide 5 kg de tomate. Diez minutos después hace el mismo pedido. Como 10 min > 2 min, ya no se considera duplicado y el método debe devolver `false`.

### Prueba 3 — Mismo comprador con otro producto → NO es duplicado

El comprador A pide 5 kg de tomate. Treinta segundos después pide 5 kg de papa. Como el producto es diferente, no es duplicado y el método debe devolver `false`.

### Clase de prueba

```java
package co.edu.eam.agrodirecto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GestorPedidosTest {

    private GestorPedidos gestor;

    @BeforeEach
    void setUp() {
        gestor = new GestorPedidos();
    }

    @Test
    @DisplayName("Mismo pedido en 30 segundos se detecta como duplicado")
    void mismoPedidoEn30Segundos() {
        long ahora = System.currentTimeMillis();
        Pedido existente = new Pedido("A001", "tomate", 5.0, ahora - 30_000); // hace 30 s
        Pedido nuevo     = new Pedido("A001", "tomate", 5.0, ahora);

        assertTrue(gestor.esDuplicado(List.of(existente), nuevo));
    }

    @Test
    @DisplayName("Mismo pedido a los 10 minutos no es duplicado")
    void mismoPedidoA10Minutos() {
        long ahora = System.currentTimeMillis();
        Pedido existente = new Pedido("A001", "tomate", 5.0, ahora - 600_000); // hace 10 min
        Pedido nuevo     = new Pedido("A001", "tomate", 5.0, ahora);

        assertFalse(gestor.esDuplicado(List.of(existente), nuevo));
    }

    @Test
    @DisplayName("Mismo comprador con diferente producto no es duplicado")
    void mismoCompradorProductoDiferente() {
        long ahora = System.currentTimeMillis();
        Pedido existente = new Pedido("A001", "tomate", 5.0, ahora - 30_000);
        Pedido nuevo     = new Pedido("A001", "papa",   5.0, ahora);

        assertFalse(gestor.esDuplicado(List.of(existente), nuevo));
    }
}
```

---

## 5. Estructura del proyecto Maven

```
agrodirecto/
├── pom.xml
└── src/
    ├── main/java/co/edu/eam/agrodirecto/
    │   ├── Pedido.java
    │   └── GestorPedidos.java
    └── test/java/co/edu/eam/agrodirecto/
        └── GestorPedidosTest.java
```

---

## 6. pom.xml completo

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
             http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>co.edu.eam.agrodirecto</groupId>
  <artifactId>agrodirecto</artifactId>
  <version>1.0-SNAPSHOT</version>

  <properties>
    <maven.compiler.release>17</maven.compiler.release>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
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
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>3.2.5</version>
      </plugin>
    </plugins>
  </build>
</project>
```

---

## 7. Instrucciones para OpenCode (modo agéntico)

> Estas instrucciones están redactadas para que OpenCode las ejecute paso a paso en tu PC de forma autónoma.

### Lo que debe hacer OpenCode

1. **Crear la estructura del proyecto** con el comando Maven archetype o creando los archivos manualmente:
   - `agrodirecto/pom.xml` con el contenido de la sección 6.
   - `src/main/java/co/edu/eam/agrodirecto/Pedido.java`
   - `src/main/java/co/edu/eam/agrodirecto/GestorPedidos.java`
   - `src/test/java/co/edu/eam/agrodirecto/GestorPedidosTest.java`

2. **Escribir el código** de cada clase exactamente como se describe en las secciones 2, 3 y 4.

3. **Ejecutar las pruebas** con:
   ```bash
   cd agrodirecto
   mvn clean test
   ```

4. **Verificar** que la salida muestre:
   ```
   Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
   BUILD SUCCESS
   ```

5. **Reportar** el resultado completo de la consola.

### Requisitos previos en la máquina

- JDK 17 o superior instalado (`java -version`)
- Maven instalado (`mvn -version`)
- Si no están instalados, instalarlos primero antes de crear el proyecto.

### Notas importantes para OpenCode

- La clase `Pedido` debe tener un constructor con los cuatro parámetros en orden: `compradorId`, `producto`, `cantidad`, `timestamp`. Los getters deben llamarse `getCompradorId()`, `getProducto()`, `getCantidad()` y `getTimestamp()`.
- En `esDuplicado`, la comparación de `cantidad` con `==` funciona para este caso porque los valores se crean con el mismo literal. Si en el futuro se necesita mayor precisión, se puede usar `Math.abs(p.getCantidad() - nuevo.getCantidad()) < 0.001`.
- `List.of(...)` requiere Java 9 o superior, lo cual ya está cubierto por `maven.compiler.release=17`.
- Las versiones de JUnit (5.10.2) y Surefire (3.2.5) son de referencia. Si Maven no las encuentra, verificar las versiones disponibles en https://mvnrepository.com.
