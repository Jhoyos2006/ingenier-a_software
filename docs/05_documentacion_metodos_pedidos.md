# Documentación: métodos del Módulo de Pedidos y sus pruebas unitarias

**Proyecto:** AgroDirecto
**Materia:** Ingeniería De Software 2
**Docente:** David Cano Baquero
**Equipo:** Jonathan David Ceballos Jiménez, Leonardo Argoty, Owen Fernando Bareño, Juan Andrés Hoyos Cruz
**Institución:** Institución Universitaria EAM — Armenia, Quindío — 2026
**Continuación de:** `doc/04_documentacion_metodo.md` (primer método del módulo, ya implementado)

---

## 1. Contexto

El **Módulo de Pedidos** es el corazón del marketplace: es donde se concentran los pedidos duplicados, las cantidades equivocadas y los registros incorrectos, es decir, el riesgo **R8** de la matriz de riesgos del primer corte (nivel alto: probabilidad media × impacto alto = 6). El plan de contingencia ya lo tenía contemplated: *"realizar pruebas del módulo de pedidos, validaciones de datos y pruebas antes de publicar cambios"*.

La entrega anterior (`doc/04_documentacion_metodo.md`) probó **un** método del módulo (`esDuplicado`) con 3 pruebas, para demostrar el ciclo de Maven + JUnit 5. Esta entrega amplía la misma idea: **8 métodos del mismo módulo**, es decir, todo lo que ocurre en la vida de un pedido (se registra, se valida, se le calcula el total, se le aplica un descuento, se le cobra el envío, cambia de estado y se acumula en el historial del comprador).

No son ocho módulos distintos: es **una sola feature** con sus ocho métodos, para que la socialización muestre cómo se cubre una funcionalidad completa con pruebas unitarias, y no un-method-per-class que se ve disperso.

**Lo que pide el segundo parcial** (y cómo está cubierto):

| Requisito del parcial | Cómo está resuelto |
|---|---|
| Un conjunto de al menos 7 u 8 métodos de prueba, cada uno verificando una parte de la funcionalidad | 8 clases de prueba, una por método del módulo, con 17 métodos de prueba en total |
| Que los métodos de prueba sean invocados desde un método más grande que los agrupe y ejecute | `GestorPedidosFuncionalidadCompletaTest` recorre en orden la funcionalidad completa (registrar, consultar, actualizar y eliminar) usando los ocho métodos del módulo |
| Evidenciar que la funcionalidad completa está probada y no solo piezas aisladas | `PruebasModuloPedidosTest` es la suite que agrupa y ejecuta las nueve clases de prueba; el reporte de Surefire muestra la estructura anidada |

---

## 2. Alcance

**Clase bajo prueba:** `GestorPedidos` (ya existe, se le agregan los métodos nuevos).
**Datos de apoyo:** `Pedido` (ya existe) y `EstadoPedido` (nuevo, enumeración de estados).

| # | Método | Qué decide | JUnit que se destaca |
|---|--------|-----------|----------------------|
| 1 | `esDuplicado(List<Pedido> recientes, Pedido nuevo)` | Si el pedido que acaba de llegar ya existe en el historial reciente del comprador | `assertTrue` / `assertFalse` |
| 2 | `esValido(Pedido pedido)` | Si los datos del pedido son válidos para quedar registrados | `@ParameterizedTest` con `@MethodSource` |
| 3 | `calcularTotal(Pedido pedido, double precioUnitario)` | Cuánto debe pagar el comprador | `assertEquals` con `delta` |
| 4 | `aplicarDescuento(Pedido pedido, double precioUnitario, double porcentaje)` | Cuánto queda pagar tras el descuento, y qué pasa si el descuento no es válido | `@ValueSource` + `assertThrows` |
| 5 | `calcularCostoEnvio(Pedido pedido, double costoBase, double costoPorKilo)` | Cuánto cuesta despachar el pedido según su peso | `assertEquals` con `delta` |
| 6 | `puedePasarA(EstadoPedido actual, EstadoPedido destino)` | Si un cambio de estado es válido | `@ParameterizedTest` con `@CsvSource` (matriz de transiciones) |
| 7 | `cambiarEstado(Pedido pedido, EstadoPedido destino)` | Nuevo estado del pedido, o error si la transición no existe | `assertEquals` + `assertThrows` |
| 8 | `totalKgDe(List<Pedido> pedidos, String compradorId)` | Cuánto ha pedido en total un comprador en su historial | `assertAll` + `assertEquals` con `delta` |

---

## 3. Los ocho métodos

Todos los métodos llevan un **Javadoc corto en español** que explica tres cosas: qué hace, para qué sirve dentro del marketplace y por qué se escogió esa solución frente a las alternativas.

### 3.1 `esDuplicado` — detección de duplicados *(ya implementado)*

**Regla.** Un pedido es duplicado si existe otro pedido del mismo comprador, con el mismo producto y la misma cantidad, creado en los últimos 2 minutos.

**Casos ya cubiertos:** mismo pedido a los 30 s → duplicado; mismo pedido a los 10 min → no duplicado; mismo comprador con otro producto → no duplicado.

---

### 3.2 `esValido` — datos del pedido

**Regla.** Un pedido es válido si tiene producto no vacío, cantidad mayor que cero, comprador identificado y marca de tiempo de creación.

**Casos a probar**

- Pedido completo → válido.
- Producto vacío o solo con espacios → inválido.
- Cantidad en cero o negativa → inválido.
- Comprador vacío → inválido.
- Sin marca de tiempo (valor 0) → inválido.
- Pedido nulo → inválido.

**JUnit.** `@ParameterizedTest` con `@MethodSource`: los seis casos inválidos (cinco con un campo roto y el pedido nulo) se construyen como objetos `Pedido` distintos desde un método proveedor estático, porque aquí no basta con pasar valores sueltos sino que hay que armar el pedido completo con un campo defectuoso. El caso válido va como `@Test` normal.

---

### 3.3 `calcularTotal` — total a pagar

**Regla.** El total es la cantidad pedida por el precio unitario del producto.

**Casos a probar**

- 5 kg × $3.000/kg → $15.000.
- 2,5 kg × $4.000/kg → $10.000.
- Cantidades decimales (0,5 kg × $3.000/kg → $1.500) → el total no se trunca.
- Precio unitario en cero o negativo → `IllegalArgumentException` (es una invariante del módulo, no un dato de negocio inválido). Son dos casos separados: precio en cero y precio negativo.

**JUnit.** `@ParameterizedTest` con `@CsvSource` para los tres casos numéricos y `assertEquals(esperado, real, 0.001)`: los `double` no se comparan con `==` porque los decimales acumulan error de representación. El precio inválido se prueba con `assertThrows`.

---

### 3.4 `aplicarDescuento` — descuento sobre el total

**Regla.** El total con descuento es el total multiplicado por `(1 - porcentaje/100)`. El total se calcula con el precio unitario igual que en `calcularTotal`, al que este método llama para no duplicar la fórmula. El porcentaje solo puede estar entre 0 y 100; fuera de ese rango el módulo lanza `IllegalArgumentException`.

**Casos a probar**

- 0 % → el total no cambia.
- 10 % → el total baja la proporción correspondiente.
- 100 % → el total queda en cero.
- Porcentaje negativo → excepción.
- Porcentaje mayor que 100 → excepción.

**JUnit.** `@ParameterizedTest` con `@CsvSource` para los tres descuentos válidos y `@ParameterizedTest` con `@ValueSource(doubles = { -10.0, 150.0 })` para los inválidos, verificando la excepción con `assertThrows`. Se separa en dos pruebas porque son dos intenciones distintas: "aplica bien" y "rechaza lo que no puede aplicar".

---

### 3.5 `calcularCostoEnvio` — costo de despacho

**Regla.** El envío cuesta una base fija más el costo por kilo pesado, y el peso del pedido es su cantidad.

**Casos a probar**

- 5 kg con base $5.000 y $800/kg → $9.000.
- 0,5 kg → solo se cobra la base y medio kilo.
- 20 kg → el cálculo escala de forma lineal.
- Cantidad cero → el envío es el valor base (no hay peso que cobrar).

**JUnit.** `@ParameterizedTest` con `@CsvSource` y `assertEquals(esperado, real, 0.001)`, por la misma razón de los decimales que en el método 3. Aquí los valores de base y costo por kilo llegan de la configuración del sistema, así que no se validan: el módulo no es responsable de esos números.

---

### 3.6 `puedePasarA` — transiciones de estado

**Regla.** Un pedido solo puede avanzar por su ciclo de vida; cualquier otra transición está prohibida (principio *deny by default*).

| Desde \ Hacia | PENDIENTE | PAGADO | ENVIADO | ENTREGADO | CANCELADO |
|---|---|---|---|---|---|
| **PENDIENTE** | no | **sí** | no | no | **sí** |
| **PAGADO** | no | no | **sí** | no | **sí** |
| **ENVIADO** | no | no | no | **sí** | no |
| **ENTREGADO** | no | no | no | no | no |
| **CANCELADO** | no | no | no | no | no |

**Casos a probar.** Las 5 transiciones válidas de la tabla y 7 inválidas que representen los caminos que un error de programación podría intentar: saltarse el pago, retroceder de estado, cancelar después de despachar, cambiar un pedido ya entregado o cancelado, y el "no hacer nada" (dejar el estado igual).

**JUnit.** `@ParameterizedTest` con `@CsvSource` de 12 filas: cada fila es `estadoActual, estadoDestino, permitido`. Una sola prueba recorre toda la matriz, que es la forma estándar de probar tablas de reglas sin duplicar el mismo cuerpo de prueba 12 veces.

---

### 3.7 `cambiarEstado` — aplicar el cambio de estado

**Regla.** Devuelve el nuevo estado del pedido **solo** si la transición está permitida en la tabla anterior; si no, lanza `IllegalArgumentException` indicando la transición rechazada. El método se apoya en `puedePasarA`, es decir, la tabla de transiciones vive en un solo lugar.

**Casos a probar**

- PENDIENTE → PAGADO → devuelve `PAGADO`.
- PAGADO → ENVIADO → devuelve `ENVIADO`.
- PENDIENTE → CANCELADO → devuelve `CANCELADO`.
- PAGADO → PENDIENTE (retroceso) → excepción.
- ENVIADO → CANCELADO (cancelar después de despachar) → excepción.

**JUnit.** Dos pruebas parametrizadas con `@CsvSource`: una para las tres transiciones permitidas y otra para las dos prohibidas. En la primera se usa `assertEquals` para comparar el estado devuelto y el que quedó guardado en el pedido (los enums se comparan con `equals`, no con `==`), y `assertAll` para agrupar esas dos verificaciones de un mismo caso. En la segunda, `assertThrows` para confirmar la excepción.

---

### 3.8 `totalKgDe` — historial del comprador

**Regla.** Suma las cantidades de todos los pedidos de un comprador dentro de una lista. Si el comprador no tiene pedidos, el total es 0.

**Casos a probar**

- Un comprador con dos pedidos (10 kg y 5 kg) → 15 kg.
- Un comprador con un pedido de cantidad decimal → la suma conserva el decimal.
- Un comprador que no está en la lista → 0.
- Lista de pedidos vacía → 0.

**JUnit.** `assertAll` para agrupar las verificaciones de un caso y que el reporte muestre todos los fallos de una vez en lugar de solo el primero, y `assertEquals(esperado, real, 0.001)` por la suma con decimales.

---

## 4. Modelo de datos del módulo

**`Pedido`** (ya existe: `compradorId`, `producto`, `cantidad`, `timestamp`).
Se le agrega un campo **`estado`** de tipo `EstadoPedido`, con valor inicial `PENDIENTE`, su getter `getEstado()` y su setter `setEstado()`, porque `cambiarEstado` guarda el nuevo estado dentro del pedido. El constructor de cuatro parámetros que ya usan las pruebas de `esDuplicado` se conserva intacto, para no romper la entrega anterior; el estado de un pedido que ya está pagado o enviado se arma con `setEstado`.

**`EstadoPedido`** (nueva enumeración): `PENDIENTE`, `PAGADO`, `ENVIADO`, `ENTREGADO`, `CANCELADO`.

---

## 5. Cómo se organizan las pruebas

Cada método tiene **su propio archivo de prueba**, con el nombre `GestorPedidos<NombreDelMétodo>Test.java`. Un archivo por método se lee mejor que un archivo gigante y además Surefire las reporta por separado.

Los datos que comparten todas las pruebas (el comprador, la marca de tiempo, el precio) viven en una clase aparte, `DatosDePrueba.java`, para no repetir las mismas constantes en los ocho archivos.

```
src/test/java/co/edu/eam/agrodirecto/
├── DatosDePrueba.java                       datos compartidos (comprador, marca de tiempo, precio)
├── GestorPedidosEsDuplicadoTest.java        3 casos
├── GestorPedidosEsValidoTest.java           7 casos
├── GestorPedidosCalcularTotalTest.java      5 casos
├── GestorPedidosAplicarDescuentoTest.java   5 casos
├── GestorPedidosCalcularCostoEnvioTest.java 4 casos
├── GestorPedidosPuedePasarATest.java        12 casos
├── GestorPedidosCambiarEstadoTest.java      5 casos
├── GestorPedidosTotalKgDeTest.java          3 casos
├── GestorPedidosFuncionalidadCompletaTest.java  1 caso (flujo completo)
└── PruebasModuloPedidosTest.java            suite que agrupa y ejecuta todo lo anterior
```

Son **17 métodos de prueba** repartidos en 9 clases (entre uno y tres por clase, ninguno con más de tres), más la suite que las agrupa y los datos compartidos.

### 5.1 El flujo completo: la funcionalidad probada de punta a punta

`GestorPedidosFuncionalidadCompletaTest` es el **método más grande** que agrupa la funcionalidad. Es una sola prueba que recorre, en el orden en que ocurre en el negocio, el ciclo de vida completo de un pedido usando los ocho métodos del módulo:

1. **Registrar:** el pedido es válido, no es duplicado, y se calculan el total, el total con descuento y el costo de envío.
2. **Consultar:** el historial del comprador ya suma lo que pidió, y al repetir el pedido el módulo lo detecta como duplicado.
3. **Actualizar:** el pedido se paga y se despacha, y cada cambio de estado queda guardado en el pedido.
4. **Eliminar:** un pedido ya despachado no se puede cancelar (el módulo lo rechaza con excepción) y uno pagado sí se puede anular, y anulado no vuelve a avanzar.

Es la evidencia de que la funcionalidad está probada completa y no solo por partes sueltas.

### 5.2 La suite: el contenedor que agrupa y ejecuta

`PruebasModuloPedidosTest` es el nivel superior que el parcial pide: una clase que **agrega las nueve clases de prueba y las ejecuta**. Se declara con `@Suite` y `@SelectClasses` de JUnit 5, y su reporte en `target/surefire-reports/` muestra la estructura anidada (la suite y, debajo, cada clase con sus casos). No tiene métodos de prueba propios: es un contenedor, y por eso se llama igual que las demás clases para que Surefire la encuentre.

Para que `mvn test` ejecute la suite y no las clases por separado, el `pom.xml` declara el motor de suites y le dice a Surefire que corra la suite:

```xml
<dependency>
  <groupId>org.junit.platform</groupId>
  <artifactId>junit-platform-suite-engine</artifactId>
  <version>1.10.2</version>
  <scope>test</scope>
</dependency>
```

```xml
<configuration>
  <includes>
    <include>**/PruebasModuloPedidosTest.java</include>
  </includes>
</configuration>
```

Consecuencia práctica: **si se agrega una clase de prueba nueva, hay que agregarla también a la suite**, porque la suite es lo que se ejecuta. Aun así se puede correr una parte sola con `mvn test -Dtest=GestorPedidosEsValidoTest`.

| Elemento de JUnit 5 | Dónde se usa | Por qué |
|---|---|---|
| `@Test` + `@DisplayName` | todas | el nombre legible es la evidencia que se muestra en el reporte |
| `@BeforeEach` | cada archivo de prueba | un `GestorPedidos` nuevo en cada prueba: ninguna depende de otra |
| `@ParameterizedTest` + `@CsvSource` | `calcularTotal`, `aplicarDescuento`, `calcularCostoEnvio`, `puedePasarA`, `cambiarEstado` | una prueba con muchas combinaciones de datos, sin copiar el mismo cuerpo |
| `@ParameterizedTest` + `@ValueSource` | `calcularTotal`, `aplicarDescuento` | lista de valores que deben ser rechazados |
| `@ParameterizedTest` + `@MethodSource` | `esValido` | hay que construir objetos `Pedido`, no pasar valores sueltos |
| `assertTrue` / `assertFalse` | métodos booleanos | la decisión del módulo es sí o no |
| `assertEquals` con `delta` | `calcularTotal`, `aplicarDescuento`, `calcularCostoEnvio`, `totalKgDe` | los `double` no se comparan con `==` |
| `assertEquals` de enumeración | `cambiarEstado`, `puedePasarA` | los enums se comparan con `equals` |
| `assertThrows` | `aplicarDescuento`, `cambiarEstado`, `calcularTotal` | el módulo lanza excepción ante invariantes rotas |
| `assertAll` | `cambiarEstado`, `totalKgDe` | muestra todos los fallos de una vez, no solo el primero |
| `@Suite` + `@SelectClasses` + `@SuiteDisplayName` | `PruebasModuloPedidosTest` | el contenedor que agrupa y ejecuta el conjunto de pruebas del módulo |

Cada elemento nuevo lleva en el código un comentario de una línea que explica qué es y por qué se usa ahí, para que quien lea las pruebas no tenga que buscar la documentación de JUnit.

Detalle de JUnit 5 a tener presente: en un `@CsvSource`, un valor completamente vacío se interpreta como `null` y un valor entre comillas simples (`''`) como cadena vacía; por eso los casos de "campo vacío" del método 2 se escriben con comillas.

---

## 6. Cobertura esperada

| Método | Archivo de prueba | Casos ejecutados | Casos principales |
|---|---|---|---|
| `esDuplicado` | `GestorPedidosEsDuplicadoTest` | 3 | duplicado en 30 s, no duplicado en 10 min, producto diferente |
| `esValido` | `GestorPedidosEsValidoTest` | 7 | válido + 6 pedidos inválidos |
| `calcularTotal` | `GestorPedidosCalcularTotalTest` | 5 | 3 casos numéricos + 2 precios no positivos |
| `aplicarDescuento` | `GestorPedidosAplicarDescuentoTest` | 5 | 3 descuentos válidos + 2 porcentajes inválidos |
| `calcularCostoEnvio` | `GestorPedidosCalcularCostoEnvioTest` | 4 | 4 casos de peso y base |
| `puedePasarA` | `GestorPedidosPuedePasarATest` | 12 | 5 transiciones válidas + 7 prohibidas |
| `cambiarEstado` | `GestorPedidosCambiarEstadoTest` | 5 | 3 transiciones válidas + 2 prohibidas |
| `totalKgDe` | `GestorPedidosTotalKgDeTest` | 3 | sumas, decimales y cero sin datos |
| flujo completo | `GestorPedidosFuncionalidadCompletaTest` | 1 | registrar → consultar → actualizar → eliminar |
| **Total** | **9 clases de prueba + la suite** | **45** | 8 métodos de una misma feature, probados de forma aislada y de punta a punta |

Ojo con este número: **45 casos ejecutados no son 45 pruebas escritas**. El proyecto tiene **17 métodos de prueba** repartidos en 9 archivos (entre uno y tres por archivo); el resto son ejecuciones adicionales de esos mismos métodos, porque una prueba parametrizada se repite con un conjunto de datos distinto en cada ejecución (por ejemplo, la tabla de transiciones son 12 ejecuciones de un solo método de prueba). Esa es justamente la ventaja de `@ParameterizedTest`: se escriben 17 pruebas y se cubren 45 escenarios.

Los tres niveles que pide el parcial, entonces:

| Nivel | Qué es | Dónde |
|---|---|---|
| Las partes | 8 clases, cada una verifica un método del módulo | `GestorPedidos*Test` |
| La funcionalidad | un método que recorre el ciclo de vida completo en orden | `GestorPedidosFuncionalidadCompletaTest` |
| El agrupador | la suite que agrupa y ejecuta todo lo anterior | `PruebasModuloPedidosTest` |

---

## 7. Ejecución

```bash
cd agrodirecto
mvn clean test
```

Salida esperada: primero aparece la suite y, debajo, cada clase de prueba que la suite invoca.

```
[INFO] Running co.edu.eam.agrodirecto.PruebasModuloPedidosTest
[INFO] Running co.edu.eam.agrodirecto.GestorPedidosEsDuplicadoTest
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running co.edu.eam.agrodirecto.GestorPedidosEsValidoTest
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running co.edu.eam.agrodirecto.GestorPedidosCalcularTotalTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running co.edu.eam.agrodirecto.GestorPedidosAplicarDescuentoTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running co.edu.eam.agrodirecto.GestorPedidosCalcularCostoEnvioTest
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running co.edu.eam.agrodirecto.GestorPedidosPuedePasarATest
[INFO] Tests run: 12, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running co.edu.eam.agrodirecto.GestorPedidosCambiarEstadoTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running co.edu.eam.agrodirecto.GestorPedidosTotalKgDeTest
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running co.edu.eam.agrodirecto.GestorPedidosFuncionalidadCompletaTest
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] Results:
[INFO] Tests run: 45, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

Para revisar un método aislado durante la socialización:

```bash
mvn test -Dtest=GestorPedidosPuedePasarATest   # solo el archivo de un método
```

Los reportes de cada prueba quedan en `target/surefire-reports/` (`.txt` y `.xml`): son la evidencia de que los 45 casos se ejecutaron. Esa carpeta se genera sola con `mvn test` y no se versiona en git (está en el `.gitignore`).

---

## 8. Decisiones de diseño

1. **Un solo módulo.** Los ocho métodos viven en `GestorPedidos` porque responden a la misma pregunta de negocio sobre el mismo objeto. Repartirlos en ocho clases distintas habría que justificar módulos que el proyecto todavía no tiene.
2. **Sin base de datos, sin red, sin reloj.** Los métodos no consultan nada externo y no llaman a `System.currentTimeMillis()`: el tiempo llega como parámetro cuando hace falta. Por eso las pruebas son rápidas y siempre dan el mismo resultado.
3. **Las excepciones son para invariantes rotas.** Un dato de negocio inválido (producto vacío, descuento imposible) se maneja con `false` o con un valor por defecto; una excepción se lanza solo cuando la llamada no debería ocurrir (precio no positivo, transición inexistente). Es la diferencia entre "el sistema funciona con un dato raro" y "el programador se equivocó".
4. **Los `double` se comparan con `delta`.** Es la razón de ser de la sobrecarga `assertEquals(esperado, real, delta)`: comparar `0.1 + 0.2` con `0.3` de forma exacta falla aunque el cálculo sea correcto.
5. **La tabla de transiciones vive en un solo lugar.** `puedePasarA` la define y `cambiarEstado` la usa, de modo que la regla no está duplicada ni puede quedar inconsistente entre un método y otro.
6. **Una limitación conocida de `esDuplicado`.** El método calcula la ventana como `nuevo.getTimestamp() - p.getTimestamp() < VENTANA_MS`, así que un pedido del historial con marca de tiempo futura daría una diferencia negativa y se consideraría duplicado. No ocurre con el `System.currentTimeMillis()` del constructor, por lo que no se modifica el módulo ya entregado; queda anotado para cuando se conecte el historial real.
7. **Tres niveles de pruebas, no uno.** Las ocho clases verifican cada parte, la prueba de flujo completo verifica la funcionalidad junta y la suite agrupa y ejecuta todo. Es lo que pide el parcial: evidenciar que la funcionalidad completa está probada, no solo piezas aisladas. El único costo es una dependencia de prueba (`junit-platform-suite-engine`) y una línea de configuración en Surefire.

---

## 9. Referencias

- JUnit 5 User Guide — Pruebas parametrizadas: https://docs.junit.org/5.13.4/user-guide/#writing-tests-parameterized-tests
- JUnit 5 User Guide — Suites (agrupar y ejecutar clases de prueba): https://docs.junit.org/5.13.4/user-guide/#test-suites
- JUnit 5 User Guide — Clases anidadas: https://docs.junit.org/5.13.4/user-guide/#nested-tests
- JUnit 5 User Guide — Aserciones: https://docs.junit.org/5.13.4/user-guide/#assertions
- JUnit 5 User Guide — Anotaciones del ciclo de vida: https://docs.junit.org/5.13.4/user-guide/#writing-tests
- OWASP, "Input Validation Cheat Sheet": https://cheatsheetseries.owasp.org/cheatsheets/Input_Validation_Cheat_Sheet.html
- Documentos internos: `doc/01_investigacion_maven.md`, `doc/02_parcial1_agrodirecto.md`, `doc/03_balanced_scorecard.md`, `doc/04_documentacion_metodo.md`
