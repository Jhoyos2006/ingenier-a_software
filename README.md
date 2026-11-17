# AgroDirecto

Marketplace que conecta directamente a agricultores y ganaderos de zonas rurales con compradores urbanos, sin intermediarios. Los agricultores publican su catálogo con precios justos; los compradores adquieren productos frescos con trazabilidad del origen.

**Usuarios:** productores rurales del Eje Cafetero y consumidores de ciudades como Armenia o Pereira.

## Requisitos

- JDK 17 o superior
- Apache Maven 3.9+

## Estructura

```
agrodirecto/
├── pom.xml
├── docs/                                  # Documentación del proyecto
└── src/
    ├── main/java/co/edu/eam/agrodirecto/
    │   ├── pedidos/                       # Módulo de pedidos
    │   │   ├── Pedido.java
    │   │   ├── EstadoPedido.java
    │   │   └── GestorPedidos.java
    │   └── registro/                      # Módulo de registro
    │       ├── ValidadorRegistro.java
    │       └── ServicioRegistro.java
    └── test/java/co/edu/eam/agrodirecto/
        ├── pedidos/
        │   ├── PruebasModuloPedidosTest.java  # Suite (ejecuta las 9 clases)
        │   ├── GestorPedidos*Test.java
        │   └── DatosDePrueba.java
        └── registro/
            └── ServicioRegistroTest.java      # Casos CP-01 a CP-10
```

## Módulos

Cada módulo vive en su propio package (`co.edu.eam.agrodirecto.pedidos` y `co.edu.eam.agrodirecto.registro`); no se referencian entre sí, así que se pueden mantener y probar por separado.

**Pedidos** — gestión del ciclo de vida de un pedido: validación, totales, descuentos, costos de envío y transiciones de estado.

**Registro** — alta de usuarios validando cinco requisitos en orden:

| Código | Requisito |
|--------|-----------|
| RF-01 | El rol debe ser `AGRICULTOR` o `COMPRADOR` |
| RF-02 | Si es `AGRICULTOR`, finca y municipio son obligatorios (no vacíos ni solo espacios) |
| RF-03 | Celular de exactamente 10 dígitos que empieza por `3` |
| RF-04 | Debe aceptarse la política de tratamiento de datos personales |
| RF-05 | Sin correos duplicados, ignorando mayúsculas/minúsculas |

`ValidadorRegistro` aplica cada regla de forma aislada; `ServicioRegistro` las encadena en orden RF-01 → RF-05 y guarda el correo si todo pasa.

## Pruebas

```bash
mvn clean test
```

| Módulo | Pruebas | Nivel |
|--------|---------|-------|
| Pedidos | 45 (suite `PruebasModuloPedidosTest`) | Unitarias e integración |
| Registro | 10 (CP-01 a CP-10 en `ServicioRegistroTest`) | Unitarias e integración |
| **Total** | **55** | |

Resultado esperado: `Tests run: 55, Failures: 0, Errors: 0, Skipped: 0` → `BUILD SUCCESS`.

Solo el módulo de registro:

```bash
mvn test -Dtest=ServicioRegistroTest
```

**Nota:** surefire solo ejecuta las clases listadas en `<includes>` del `pom.xml`. Al agregar una clase de prueba nueva, agrega su `<include>` o Maven la ignorará.
