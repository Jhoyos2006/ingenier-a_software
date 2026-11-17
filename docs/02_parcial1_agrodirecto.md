# Parcial Final Primer Corte

# AgroDirecto

- Jonathan David Ceballos Jiménez
- Leonardo Argoty
- Owen Fernando Bareño
- Juan Andrés Hoyos Cruz

- David Cano Baquero

Ingeniería De Software 2  

Institución Universitaria EAM  
Facultad De Ingeniería  
Ingeniería De Software  
Armenia, Quindío  
2026  

## 2. Introducción
El proyecto que se planea desarrollar es una plataforma web/app (marketplace) donde
agricultores publican sus productos y compradores urbanos las compran mediante este sistema
que conecta al vendedor o proveedor y al comprador siendo este último el intermediario por el
cual los clientes o usuarios de zonas urbanas adquieren dicho producto de manera ya directa.
La problemática principal que se plantea abordar es que los pequeños agricultores no tienen un
canal directo para vender sus productos, dependen de intermediarios que les pagan poco, y el
comprador final paga caro sin saber el origen del producto. Ya que estos disponen de la
dificultad de vender sus productos por un precio asequible ya que los compradores de estos son
intermediarios que por medio de dicha plataforma adquieren los productos por un pago menor
mientras que los consumidores (usuarios finales) en la ciudad adquieren dichos productos por
un pago aún mayor sin saber del lugar de procedencia del producto que adquirió, por lo que el
agricultor depende si o si de su conexión con el intermediario que en zonas urbanas provee su
producto más caro del valor en que lo obtuvo primeramente.
Esta problemática afecta principalmente a los agricultores ya que estos proveen sus productos
a los intermediarios por una escasa cifra y los otros afectados son los consumidores urbanos ya
que estos compran dichos productos por una cifra alta y con la desventaja de no saber de donde
provienen sus productos comprados.
La propuesta que se planea establecer es crear un canal o software para conectar directamente
al agricultor con los compradores finales en zonas urbanas para reducir la falta de paga de los
intermediarios y mejorar la eficiencia en la venta de los productos agrícolas y ganaderas a los
principales consumidores para así facilitar y mejorar su producción y evitar que estos
microempresarios tengan pérdidas tanto económicas como de recursos naturales, cultivos
valiosos, tiempo, esfuerzo, motivación y uso de materias primas dignificando así el trabajo
fundamental que realizan en el campo.
## 3. Descripción del problema y contexto del proyecto
### 3.1. Contexto: El sector agrícola tradicional presenta una fuerte dependencia de los
intermediarios comerciales, debido a que los pequeños agricultores carecen de canales de venta
directa para comercializar sus productos en las zonas urbanas. Esta desconexión estructural en
la cadena de suministro genera un escenario de ineficiencia económica que afecta
negativamente a los dos extremos de la cadena: los productores y los consumidores finales
generando consigo vulnerabilidad del agricultor, afectación al consumidor urbano y necesidad
de la creación del proyecto para optimizar y mejorar la distribución agrícola y ganadera.
### 3.2. Problema identificado: La dependencia económica de los intermediarios y la ausencia de
canales directos de comercialización constituyen el problema central que afecta al sector
agrícola rural. Debido a limitaciones logísticas y de conectividad, los pequeños agricultores se
ven obligados a vender sus productos a precios excesivamente bajos, lo que desencadena
pérdidas económicas recurrentes, desperdicio de recursos naturales y desmotivación laboral.
Paralelamente, esta desconexión provoca que los consumidores en las zonas urbanas adquieran

los alimentos con un sobrecosto inflado por la cadena de reventa, sumado a una total falta de
transparencia respecto al origen y procedencia de lo que consumen.
### 3.3. Actores involucrados: Los actores que son los principalmente afectados o que afectan a
los demás actores directa o indirectamente están conformados por:
- Pequeños agricultores y ganaderos: Productores rurales afectados directamente por
la baja remuneración de su trabajo, la pérdida de insumos y la falta de control sobre el
precio final de sus cosechas.
- Consumidores urbanos: Usuarios finales ubicados en las ciudades que asumen el alto
costo financiero de los productos y se ven privados de conocer la trazabilidad del
alimento.
- Intermediarios tradicionales: Agentes comerciales que compran la producción en las
fincas a bajo costo y la distribuyen en las ciudades elevando los precios de venta.
### 3.4. Propuesta inicial de solución: El proyecto consiste en el desarrollo de un software
multiplataforma (plataforma web y aplicación móvil) bajo el modelo de marketplace, diseñado
para conectar de manera directa a los agricultores con los compradores finales en zonas
urbanas. Esta solución tecnológica permitirá a los productores publicar autónomamente sus
catálogos agrícolas y ganaderos, definir precios justos y gestionar las solicitudes de demanda.
Mediante la eliminación de los eslabones intermediarios innecesarios, el sistema optimizará la
eficiencia logística de la distribución, garantizará un pago digno para el campo y ofrecerá a los
usuarios urbanos una herramienta para verificar la procedencia geográfica exacta de cada
producto adquirido.
## 4. Matriz de riesgos
### 4.1. Objetivo de la matriz de riesgos: La matriz de riesgos tiene como objetivo identificar,
analizar y valorar las situaciones que podrían afectar el desarrollo y funcionamiento de la
plataforma web y aplicación móvil propuesta para conectar directamente a pequeños
agricultores y ganaderos con consumidores urbanos. Mediante esta herramienta se busca
determinar cuáles son los riesgos tecnológicos que pueden presentarse durante el desarrollo y
posterior funcionamiento del sistema, considerando sus posibles causas y consecuencias. De
esta manera, el equipo puede establecer cuáles riesgos requieren mayor atención y cuáles
pueden ser monitoreados durante las diferentes etapas del proyecto.
La matriz permite además establecer una base para la elaboración del plan de contingencia, ya
que los riesgos con mayor nivel serán considerados prioritarios para definir acciones
preventivas y procedimientos de respuesta.
### 4.2. Identificación de riesgos: Para el proyecto se identificaron once riesgos principalmente
relacionados con la infraestructura tecnológica, seguridad de la información, base de datos,

disponibilidad de la plataforma, procesamiento de pedidos, pagos y funcionamiento general del
marketplace.
- R1. Fallo en la pasarela de pagos
Causa: Errores en la integración con el servicio de pagos, indisponibilidad del proveedor,
problemas de conexión o fallos en el procesamiento de las transacciones.
Consecuencia: Los compradores podrían no completar sus pagos, generando interrupciones
en las compras, pérdida de ventas y afectación de la confianza de los usuarios.
- R2. Filtración de datos personales
Causa: Vulnerabilidades de seguridad, accesos no autorizados, contraseñas inseguras o
configuraciones incorrectas en la plataforma y base de datos.
Consecuencia: Exposición de información personal de agricultores y compradores, afectación
de la privacidad, pérdida de confianza y posibles problemas legales.
- R3. Caída de la plataforma
Causa: Fallos del servidor, problemas de alojamiento, errores de infraestructura,
mantenimiento inadecuado o interrupciones del servicio.
Consecuencia: Los usuarios no podrían acceder al marketplace, consultar productos, realizar
pedidos o gestionar sus publicaciones.
- R4. Pérdida de información en la base de datos
Causa: Fallos técnicos, eliminación accidental de registros, corrupción de datos o errores
durante procesos de actualización y mantenimiento.
Consecuencia: Pérdida de información relacionada con usuarios, productos, pedidos, precios,
agricultores y registros de transacciones.
- R5. Ataque de ransomware
Causa: Ingreso de software malicioso al sistema mediante archivos infectados, credenciales
comprometidas, vulnerabilidades o accesos no autorizados.
Consecuencia: Los archivos y sistemas podrían quedar bloqueados o cifrados, impidiendo el
acceso a información importante y afectando la continuidad del servicio.
- R6. Sobrecarga del servidor o ataque DDoS
Causa: Una cantidad excesiva de solicitudes al servidor, crecimiento inesperado de usuarios o
un ataque intencional que genere tráfico masivo.
Consecuencia: La plataforma puede presentar lentitud, errores de acceso o quedar
temporalmente fuera de servicio.

- R7. Fallo de las copias de seguridad
Causa: Configuración incorrecta de los respaldos, copias incompletas, archivos corruptos o
falta de comprobación periódica de los respaldos.
Consecuencia: En caso de pérdida o daño de información, el equipo podría no contar con una
copia funcional para recuperar los datos del sistema.
- R8. Error en el sistema de pedidos
Causa: Errores de programación, fallas en la lógica del sistema, problemas de sincronización
o registros incorrectos.
Consecuencia: Pueden generarse pedidos duplicados, pedidos que no sean registrados
correctamente, cantidades equivocadas o problemas en la gestión de las compras.
- R9. Falla durante una actualización del sistema
Causa: Cambios incompatibles, errores de programación, despliegues incorrectos o
actualización de componentes sin realizar pruebas suficientes.
Consecuencia: Algunas funciones podrían dejar de funcionar correctamente o la plataforma
podría quedar temporalmente indisponible.
- R10. Fraude o incumplimiento del servicio
Causa: Usuarios falsos, cuentas comprometidas, información incorrecta sobre productos o
fallas en los mecanismos de autenticación y validación.
Consecuencia: Los compradores podrían sufrir pérdidas económicas o recibir un producto
diferente al ofrecido, afectando la confianza y reputación de la plataforma.
- R11. Fallo en la trazabilidad del producto
Causa: Errores en el registro de la ubicación, productor, información del producto o
almacenamiento incorrecto de los datos de procedencia.
Consecuencia: Los consumidores podrían no obtener información correcta sobre el origen de
los productos agrícolas o ganaderos adquiridos, afectando una de las principales propuestas de
valor del proyecto.

### 4.3. Valoración de los riesgos: Para valorar los riesgos identificados se utilizaron dos criterios
principales: probabilidad e impacto.
Probabilidad: La probabilidad representa la posibilidad de que el riesgo llegue a presentarse
durante el desarrollo o funcionamiento de la plataforma.

Valor

Nivel

Descripción

1

Baja

poco probable

2

Media

posibilidad moderada

3

Alta

alta posibilidad

Impacto: El impacto representa el nivel de afectación que tendría el proyecto si el riesgo
llegara a materializarse.
valor

nivel

descripción

1

bajo

La afectación sería pequeña y podría solucionarse rápidamente.

2

medio

Generaría una afectación considerable, pero controlable.

3

alto

Podría comprometer gravemente el funcionamiento, información,
seguridad o viabilidad del proyecto.

Cálculo del nivel de riesgo: El nivel de riesgo se obtiene mediante la multiplicación de la
probabilidad por el impacto: Nivel de riesgo = Probabilidad × Impacto
La clasificación utilizada es la siguiente:
Resultado

Nivel de riesgo

Prioridad

1-2

Bajo

Monitorear

3-4

Medio

Controlar y realizar seguimiento

6-9

Alto

Atención prioritaria

### 4.4. Matriz de riesgos: A partir de los criterios anteriores se realizó la valoración de los once
riesgos identificados.
ID

Riesgo

Tipo

Probabilidad

Impacto

Cálculo

Clasificación

R1

Fallo en la
pasarela de
pagos

Tecnológico

Media (2)

Alto (3)

6

Alto

R2

Filtración de
datos
personales

Seguridad de
la información

Media (2)

Alto (3)

6

Alto

R3

Caída de la
plataforma

Tecnológico

Media (2)

Alto (3)

6

Alto

R4

Pérdida de

Tecnológico

Media (2)

Alto (3)

6

Alto

información en
la base de
datos
R5

Ataque de
ransomware

Ciberseguridad Media (2)

Alto (3)

6

Alto

R6

Sobrecarga del Ciberseguridad Media (2)
servidor o
ataque DDoS

Alto (3)

6

Alto

R7

Fallo de las
copias de
seguridad

Tecnológico

Media (2)

Alto (3)

6

Alto

R8

Error en el
sistema de
pedidos

Tecnológico

Media (2)

Alto (3)

6

Alto

R9

Falla durante
una
actualización

Tecnológico /
Operativo

Media (2)

Medio (2)

4

Medio

R10 Fraude o
incumplimient
o del servicio

Operativo /
Legal

Media (2)

Alto (3)

6

Alto

R11 Fallo en la
trazabilidad
del producto

Operativo

Media (2)

Medio (2)

4

Medio

### 4.5. Interpretación de la matriz: Después de realizar la valoración de los riesgos se encontró
que nueve de los once riesgos identificados presentan un nivel alto, mientras que los dos
restantes presentan un nivel medio. Los riesgos que requieren mayor atención son aquellos
relacionados con la seguridad, disponibilidad e integridad de la información, debido a que
pueden afectar directamente el funcionamiento del marketplace y la confianza de sus usuarios.
Entre los riesgos de mayor importancia se encuentra la filtración de datos personales (R2),
debido a que la plataforma manejará información de agricultores y compradores. La
materialización de este riesgo podría comprometer la privacidad de los usuarios y afectar la
confianza en el proyecto.
También resulta prioritario el riesgo de pérdida de información en la base de datos (R4), ya
que el sistema dependerá de la información almacenada para gestionar usuarios, productos,
pedidos y demás procesos del marketplace. La pérdida de estos datos podría afectar
directamente la operación de la plataforma.
La caída de la plataforma (R3) y la sobrecarga del servidor o ataque DDoS (R6) también
requieren atención prioritaria porque afectan directamente la disponibilidad del sistema. Si los

usuarios no pueden ingresar al marketplace, consultar productos o realizar pedidos, la finalidad
principal del proyecto se vería afectada.
El fallo en la pasarela de pagos (R1) representa otro riesgo importante, debido a que impediría
completar las compras realizadas por los consumidores. Esto afectaría tanto a los compradores
como a los agricultores que utilizan la plataforma como canal de comercialización.
El ataque de ransomware (R5) y el fallo de las copias de seguridad (R7) también presentan
un nivel alto porque podrían dificultar o impedir la recuperación de la información ante un
incidente de seguridad.
Por otra parte, el error en el sistema de pedidos (R8) puede afectar directamente la relación
entre compradores y productores, ya que errores en cantidades, productos o registros pueden
generar conflictos y pérdida de confianza.
El fraude o incumplimiento del servicio (R10) también tiene una importancia elevada, puesto
que el modelo del proyecto depende de la confianza entre agricultores y compradores. Si se
presentan usuarios falsos, información engañosa o incumplimientos, la reputación de la
plataforma podría verse afectada.
Finalmente, los riesgos relacionados con una falla durante una actualización (R9) y con la
trazabilidad del producto (R11) fueron clasificados como medios. Estos riesgos deben
mantenerse bajo seguimiento y contar con controles preventivos, aunque su nivel de prioridad
es menor frente a los riesgos clasificados como altos.
### 4.6. Conclusiones del análisis de riesgos: A partir de la matriz de riesgos, el equipo determinó
que la seguridad y disponibilidad de la plataforma deben ser consideradas como aspectos
fundamentales durante el desarrollo del proyecto. Los riesgos relacionados con la filtración de
datos, pérdida de información, ransomware y fallos en las copias de seguridad requieren
mecanismos de protección y recuperación que permitan disminuir las posibles consecuencias
de un incidente tecnológico.
De igual manera, los riesgos relacionados con la caída de la plataforma, sobrecarga del
servidor, pasarela de pagos y sistema de pedidos deben ser considerados debido a que afectan
directamente la capacidad de los usuarios para utilizar el marketplace y realizar sus compras.
Por último, el análisis permitió establecer que los riesgos clasificados como altos deberán
recibir atención prioritaria dentro del posterior diseño de la solución y serán la base para la
elaboración del plan de contingencia, mientras que los riesgos medios deberán ser
monitoreados y controlados durante el desarrollo y funcionamiento del sistema

## 5. Plan De Contingencia

### 5.1. Objetivo del plan de contingencia
El plan de contingencia tiene como objetivo establecer las acciones que deberá realizar el
equipo ante la materialización de los riesgos tecnológicos identificados en la matriz de riesgos
del proyecto. Este plan busca reducir las consecuencias que puedan afectar el funcionamiento
de la plataforma web y aplicación móvil, especialmente en situaciones relacionadas con la
seguridad de la información, disponibilidad del sistema, pérdida de datos, procesamiento de
pagos y gestión de pedidos.
Además, permite definir de manera anticipada las acciones preventivas y de respuesta, los
responsables de ejecutarlas y los recursos necesarios para recuperar el funcionamiento normal
de la plataforma. De esta manera, el equipo podrá actuar de forma organizada ante una situación
que pueda interrumpir los servicios del marketplace o afectar a agricultores y consumidores.
### 5.2. Riesgos priorizados
Para la elaboración del plan de contingencia se tomaron como referencia los resultados
obtenidos en la matriz de riesgos del punto 4. Se priorizaron los riesgos clasificados como altos,
debido a que presentan una mayor posibilidad de afectar significativamente el funcionamiento
y la viabilidad del proyecto.
Los riesgos priorizados son:
●
●
●
●
●
●
●
●
●

R1. Fallo en la pasarela de pagos.
R2. Filtración de datos personales.
R3. Caída de la plataforma.
R4. Pérdida de información en la base de datos.
R5. Ataque de ransomware.
R6. Sobrecarga del servidor o ataque DDoS.
R7. Fallo de las copias de seguridad.
R8. Error en el sistema de pedidos.
R10. Fraude o incumplimiento del servicio.

### 5.3. Estrategias de contingencia
Para cada uno de los riesgos priorizados se establecieron acciones preventivas y acciones de
contingencia. Las acciones preventivas buscan disminuir la posibilidad de que el riesgo ocurra,
mientras que las acciones de contingencia establecen qué debe hacer el equipo cuando el riesgo
ya se haya materializado.

ID

Riesgo

Acción preventiva

Acción de contingencia

R1

Fallo en la pasarela

Realizar pruebas de

Verificar el estado del servicio

de pagos

integración y contar con un
proveedor de respaldo o
método de pago alternativo.

de pagos, suspender
temporalmente las
transacciones afectadas y
habilitar un método alternativo
mientras se soluciona el
problema.

R2

Filtración de datos
personales

Utilizar contraseñas seguras,
control de acceso, cifrado y
actualizaciones de
seguridad.

Bloquear los accesos
comprometidos, identificar la
vulnerabilidad, proteger la
información afectada y realizar
las correcciones de seguridad
necesarias.

R3

Caída de la
plataforma

Realizar mantenimiento
preventivo, monitorear el
servidor y contar con una
infraestructura de respaldo.

Identificar la causa de la caída,
restaurar el servicio y habilitar
la infraestructura alternativa si
la principal continúa fuera de
servicio.

R4

Pérdida de
información en la
base de datos

Realizar copias de seguridad
periódicas y verificar que
los respaldos puedan ser
restaurados.

Detener las operaciones que
puedan aumentar la pérdida,
identificar los datos afectados
y restaurar la información
desde el respaldo más reciente
disponible.

R5

Ataque de
ransomware

Mantener sistemas
actualizados, utilizar
mecanismos de seguridad y
realizar copias de seguridad
aisladas.

Aislar los equipos afectados,
bloquear el acceso al sistema
comprometido, identificar el
incidente y recuperar la
información mediante
respaldos seguros.

R6

Sobrecarga del
servidor o ataque
DDoS

Implementar monitoreo del
tráfico, límites de
solicitudes y mecanismos de
protección contra tráfico
malicioso.

Identificar el tráfico anormal,
activar mecanismos de
protección, limitar las
solicitudes y restaurar
progresivamente el acceso
normal.

R7

Fallo de las copias
de seguridad

Programar respaldos
periódicos y comprobar
regularmente su integridad
mediante pruebas de
restauración.

Buscar el respaldo funcional
más reciente, recuperar la
información disponible y
establecer un nuevo sistema de
respaldo para evitar que vuelva
a ocurrir.

R8

Error en el sistema
de pedidos

Realizar pruebas del módulo Detener temporalmente el
de pedidos, validaciones de procesamiento afectado,
datos y pruebas antes de
revisar los pedidos registrados

R10

Fraude o
incumplimiento del
servicio

publicar cambios.

y corregir o recuperar las
transacciones afectadas.

Implementar mecanismos
de autenticación, validación
de usuarios y verificación
de información de los
productos.

Bloquear la cuenta
involucrada, revisar la
transacción o publicación,
recopilar evidencias y atender
el caso con el comprador y
agricultor afectados.

### 5.4. Procedimiento de actuación
El procedimiento de actuación establece las actividades que deberá realizar el equipo cuando
alguno de los riesgos prioritarios llegue a materializarse. La respuesta debe seguir un orden que
permita identificar el problema, contener sus efectos, recuperar el servicio y posteriormente
analizar las causas del incidente.
ID

Riesgo

Procedimiento

Responsable

Tiempo de Recursos
respuesta necesarios

R1

Fallo en la
pasarela de
pagos

Verificar el estado del
servicio, identificar el
error, informar
temporalmente a los
usuarios y activar el
método de pago
alternativo.

Equipo de
desarrollo

Inmediato

Plataforma,
proveedor de
pagos, método
alternativo y
registros de
transacciones

R2

Filtración de Bloquear accesos
datos
comprometidos,
personales
identificar el origen de
la filtración, corregir la
vulnerabilidad y revisar
los sistemas afectados.

Equipo de
desarrollo y
seguridad

Inmediato

Servidor,
registros de
acceso,
herramientas
de seguridad y
copias de
respaldo

R3

Caída de la
plataforma

Equipo
técnico

Inmediato

Servidor,
servicio de
alojamiento,
herramientas
de monitoreo y
respaldo de
configuración

R4

Pérdida de
Detener temporalmente
información los procesos afectados,
en la base de identificar la

Administrad
or de base
de datos /

Inmediato

Copias de
seguridad, base
de datos y

Identificar la causa,
revisar el servidor,
recuperar los servicios
afectados y utilizar
infraestructura
alternativa si es
necesario.

datos

información perdida y
restaurar la base de
datos desde un respaldo
funcional.

R5

Ataque de
ransomware

Aislar los equipos
Equipo
afectados, desconectar técnico y de
los sistemas
seguridad
comprometidos,
analizar el incidente y
restaurar la información
desde respaldos
seguros.

Inmediato

Copias de
seguridad,
herramientas
de seguridad y
equipos
aislados

R6

Sobrecarga
del servidor
o ataque
DDoS

Detectar el tráfico
anormal, activar
mecanismos de
protección, limitar
solicitudes y recuperar
progresivamente el
servicio.

Equipo
técnico

Inmediato

Servidor,
herramientas
de monitoreo y
protección del
tráfico

R7

Fallo de las
copias de
seguridad

Comprobar los
respaldos disponibles,
identificar el último
respaldo funcional y
recuperar la
información.
Posteriormente se debe
corregir el proceso de
respaldo.

Administrad
or de base
de datos /
equipo
técnico

Menos de
4 horas

Respaldos,
almacenamient
o alternativo y
herramientas
de
recuperación

R8

Error en el
sistema de
pedidos

Suspender
temporalmente el
módulo afectado,
revisar los pedidos
registrados, corregir el
error y verificar las
transacciones antes de
reactivar el servicio.

Equipo de
desarrollo

Menos de
4 horas

Código fuente,
base de datos,
registros del
sistema y
entorno de
pruebas

R10

Fraude o
incumplimie
nto del
servicio

Bloquear la cuenta
Administrad
sospechosa, revisar la
or de la
transacción, verificar la plataforma
información del usuario
y atender el caso para
determinar las acciones
correspondientes.

Menos de
24 horas

Registros de
usuarios,
pedidos,
comprobantes
y sistema de
autenticación

Procedimiento general ante un incidente

equipo de
desarrollo

herramientas
de restauración

Independientemente del riesgo que se presente, el equipo deberá seguir un procedimiento
general de actuación:
a. Detección: identificar la existencia del problema mediante reportes de usuarios, alertas del
sistema o monitoreo tecnológico.
b. Evaluación: determinar qué sistema o información fue afectada y establecer el alcance del
incidente.
c. Contención: realizar las acciones necesarias para evitar que el problema continúe
extendiéndose o genere mayores daños.
d. Recuperación: restaurar los servicios, información o funcionalidades afectadas utilizando
los mecanismos de respaldo disponibles.
e. Verificación: comprobar que la plataforma y sus funcionalidades vuelvan a operar
correctamente antes de permitir nuevamente el funcionamiento normal.
f. Registro: documentar el incidente, sus causas, consecuencias y acciones realizadas.
g. Prevención: analizar lo sucedido y establecer mejoras para reducir la posibilidad de que el
mismo incidente vuelva a ocurrir.
### 5.5. Interpretación del plan de contingencia
Los riesgos fueron seleccionados para el plan de contingencia principalmente debido a que
fueron clasificados como altos en la matriz de riesgos y pueden afectar directamente la
continuidad, seguridad y funcionamiento del marketplace.
Los riesgos relacionados con la seguridad de la información, como la filtración de datos
personales (R2) y el ataque de ransomware (R5), pueden comprometer información importante
de agricultores y compradores. Por esta razón, requieren medidas preventivas y procedimientos
de respuesta que permitan contener el incidente y recuperar la información cuando sea
necesario.
Los riesgos relacionados con la disponibilidad de la plataforma, como la caída del sistema
(R3) y la sobrecarga del servidor o ataque DDoS (R6), pueden impedir que los usuarios
ingresen al marketplace, consulten productos o realicen pedidos. En estos casos, la respuesta
debe concentrarse en identificar rápidamente la causa y recuperar el servicio.
La pérdida de información en la base de datos (R4) y el fallo de las copias de seguridad
(R7) están relacionados directamente, ya que los respaldos constituyen uno de los principales
mecanismos para recuperar la información cuando se presenta una pérdida de datos. Por esta
razón, es necesario realizar copias periódicas y comprobar que puedan ser restauradas
correctamente.

El fallo de la pasarela de pagos (R1) puede afectar directamente las transacciones realizadas
por los compradores. Por ello, se plantea contar con mecanismos alternativos que permitan
continuar con las operaciones mientras se soluciona el problema del proveedor principal.
El error en el sistema de pedidos (R8) también requiere atención porque puede provocar
inconsistencias entre la información registrada por el comprador y la información recibida por
el agricultor. La revisión de los registros y la suspensión temporal del módulo afectado
permitirán evitar que el error genere nuevos pedidos incorrectos.
Por último, el fraude o incumplimiento del servicio (R10) debe ser controlado mediante
mecanismos de autenticación, validación y revisión de las operaciones. En caso de presentarse,
será necesario bloquear la cuenta involucrada y analizar la información disponible para resolver
la situación.
Algunos riesgos pueden prevenirse en gran medida mediante buenas prácticas de desarrollo y
seguridad, como las fallas de programación, filtraciones de información y errores en los
pedidos. Sin embargo, otros riesgos no pueden eliminarse completamente, por lo que deben ser
mitigados y controlados, como las caídas de servidores, ataques DDoS, fallos de proveedores
externos y posibles ataques de ransomware.
Por lo tanto, el plan no busca garantizar que los riesgos nunca ocurran, sino permitir que el
equipo pueda reducir su probabilidad, disminuir sus consecuencias y recuperar el
funcionamiento del sistema en el menor tiempo posible.
### 5.6. Conclusiones del plan de contingencia
El plan de contingencia permitió establecer las acciones que deberá realizar el equipo ante los
riesgos tecnológicos que pueden afectar el funcionamiento del marketplace agrícola. La
identificación previa de responsables, recursos y procedimientos permite evitar respuestas
improvisadas frente a situaciones que puedan comprometer la plataforma. Los riesgos
relacionados con la seguridad de la información, disponibilidad del sistema, pérdida de datos,
pagos y pedidos requieren especial atención debido a que pueden afectar directamente la
confianza de los usuarios y la continuidad de las operaciones entre agricultores y compradores.
Las medidas preventivas, como las copias de seguridad, pruebas del sistema, monitoreo,
controles de acceso y mantenimiento de la infraestructura, permiten disminuir la probabilidad
de que ocurran los incidentes. Por otra parte, las acciones de contingencia permiten recuperar
los servicios y reducir las consecuencias cuando un riesgo finalmente se materializa.
Finalmente, el plan de contingencia complementa la matriz de riesgos, ya que utiliza los
resultados obtenidos en ella para establecer una respuesta concreta frente a los riesgos
prioritarios. De esta manera, el equipo cuenta con una base para incorporar posteriormente
mecanismos de seguridad, recuperación, monitoreo y continuidad dentro del desarrollo de la
plataforma.

## 6. Matriz Vester
### 6.1. Objetivo de la matriz Vester
La matriz Vester se utiliza para analizar las principales problemáticas relacionadas con el
marketplace agrícola y determinar cómo unas problemáticas influyen sobre otras. Con esta
herramienta se busca identificar cuáles situaciones tienen mayor capacidad de generar o afectar
otros problemas, cuáles son principalmente consecuencias y cuáles deben ser priorizadas
dentro del desarrollo de la solución.
### 6.2. Identificación de problemáticas
Las problemáticas se definieron tomando como base el contexto, el problema identificado, los
actores involucrados y la propuesta inicial de solución desarrollados en los puntos anteriores.

ID

Problemática

P1

Dependencia de intermediarios para vender los productos agrícolas

P2

Falta de canales directos entre agricultores y consumidores urbanos.

P3

Bajo precio de venta recibido por los agricultores.

P4

Alto precio que pagan los consumidores urbanos por los productos.

P5

Falta de información sobre el origen y procedencia de los productos.

P6

Dificultades logísticas para llevar los productos del campo a las zonas
urbanas

P7

Limitaciones de conectividad y acceso a herramientas tecnológicas por parte
de algunos agricultores.

P8

Desperdicio de productos y recursos debido a ineficiencias en la
comercialización.

P9

Poca transparencia en la cadena de comercialización y en la formación de los
precios.

P10

Pérdidas económicas y desmotivación de los pequeños agricultores.

### 6.3. Valoración de la influencia entre problemáticas
Se utilizó una escala de 0 a 3 para valorar cuánto influye la problemática ubicada en la fila
sobre la problemática ubicada en la columna.
Valor

Nivel

Descripción

0

Sin influencia

No existe una influencia directa.

1

Baja

La influencia es pequeña.

2

Media

La influencia es importante.

3

Alta

La influencia es fuerte o directa.

### 6.4. Matriz Vester diligenciada
P1

P2

P3

P4

P5

P6

P7

P8

P9

P10

Total
activo

P1

—

3

2

2

2

2

1

2

3

3

20

P2

3

—

2

2

2

2

2

2

3

3

21

P3

2

2

—

3

1

1

0

2

3

3

17

P4

1

1

0

—

1

2

0

1

2

2

10

P5

0

1

0

1

—

1

0

1

2

1

7

P6

1

2

1

2

2

—

2

3

2

2

17

P7

1

2

1

1

1

2

—

2

1

2

13

P8

2

2

3

2

2

1

1

—

2

3

18

P9

2

3

2

2

3

2

1

2

—

3

20

P10

2

3

3

2

2

2

1

2

2

—

19

Total
pasivo

14

19

14

17

16

15

8

17

20

22

Promedio de influencia activa: 16.2. Promedio de influencia pasiva: 16.2.

### 6.5. Clasificación de las problemáticas

ID

Problemática

Influencia
activa

Influencia Clasificación
pasiva

P1

Dependencia de intermediarios para vender
los productos agrícolas.

20

14

Activa

P2

Falta de canales directos entre agricultores y 21
consumidores urbanos.

19

Crítica

P3

Bajo precio de venta recibido por los 17
agricultores.

14

Activa

P4

Alto precio que pagan los consumidores 10
urbanos por los productos.

17

Pasiva

P5

Falta de información sobre el origen y 7
procedencia de los productos.

16

Indiferente

P6

Dificultades logísticas para llevar los 17
productos del campo a las zonas urbanas.

15

Activa

P7

Limitaciones de conectividad y acceso a 13
herramientas tecnológicas por parte de
algunos agricultores.

8

Indiferente

P8

Desperdicio de productos y recursos debido 18
a ineficiencias en la comercialización.

17

Crítica

P9

Poca transparencia en la cadena de 20
comercialización y en la formación de los
precios.

20

Crítica

P10

Pérdidas económicas y desmotivación de los 19
pequeños agricultores.

22

Crítica

La clasificación se realizó comparando la influencia activa y pasiva de cada problemática con
los promedios obtenidos. Las problemáticas que superan ambos promedios son críticas; las que
superan el promedio activo son activas; las que superan el promedio pasivo son pasivas; y las
que no superan ninguno de los dos promedios son indiferentes.

### 6.6. Interpretación de la matriz Vester
El análisis muestra que las problemáticas críticas son P1. Dependencia de intermediarios para
vender los productos agrícolas y P2. Falta de canales directos entre agricultores y consumidores
urbanos. Ambas presentan una influencia activa y pasiva superior a los promedios, por lo que
están relacionadas con varias de las demás situaciones identificadas.
P1 y P2 son especialmente importantes porque el proyecto parte de la necesidad de reducir la
dependencia de los intermediarios y crear un canal directo de comercialización. Esta situación
se relaciona con el bajo precio recibido por los agricultores, el mayor precio pagado por los
consumidores, la poca transparencia de la cadena comercial y las pérdidas económicas.
Las problemáticas activas P3, P6, P8 y P9 tienen una capacidad importante de influir sobre
otras situaciones. Por ejemplo, las dificultades logísticas pueden afectar la distribución y
generar desperdicio, mientras que la poca transparencia dificulta conocer cómo se forma el
precio y de dónde provienen los productos.
Las problemáticas pasivas P4, P5 y P10 reciben principalmente la influencia de otras
problemáticas. El alto precio para los consumidores, la falta de información sobre el origen y
las pérdidas económicas de los agricultores pueden estar relacionadas con la forma actual de
comercialización.
La problemática que debe abordarse primero es la falta de canales directos entre agricultores y
consumidores urbanos, junto con la dependencia de intermediarios. Esto influye directamente
en la propuesta del marketplace, ya que la plataforma busca conectar ambos actores, facilitar
la comercialización y ofrecer mayor transparencia sobre la procedencia de los productos.

### 6.7. Conclusiones de la matriz Vester
La matriz Vester permitió organizar las problemáticas del marketplace agrícola y comprender
que no todas tienen el mismo nivel de influencia. El análisis mostró que la dependencia de
intermediarios y la falta de un canal directo entre productores y consumidores son las
problemáticas que deben recibir mayor atención.
También se identificó que los bajos precios para los agricultores, las dificultades logísticas, la
poca transparencia, el desperdicio y las pérdidas económicas están relacionados entre sí. Por
esta razón, una solución que facilite la conexión directa entre los actores puede contribuir a
disminuir varias de estas situaciones de manera conjunta.
Finalmente, el resultado de la matriz Vester respalda la propuesta del proyecto de desarrollar
una plataforma web y aplicación móvil tipo marketplace que conecte directamente a pequeños
agricultores y ganaderos con consumidores urbanos. El análisis sirve como base para orientar
las funcionalidades y prioridades del desarrollo posterior. Esta relación entre las problemáticas
identificadas, los riesgos tecnológicos que podrían impedir su solución y las estrategias
definidas para mitigarlos se desarrolla de manera integrada en la sección 7.
## 7. Conclusiones generales
El desarrollo de esta primera entrega permitió al equipo comprender que el problema del
proyecto no es aislado, sino que las tres herramientas utilizadas (matriz de riesgos, plan de
contingencia y matriz Vester) convergen en un mismo diagnóstico: la dependencia de
intermediarios y la ausencia de un canal directo entre agricultores y consumidores urbanos. A
partir de este análisis integral, el equipo llega a las siguientes conclusiones:
1. Sobre los principales hallazgos de la problemática: el análisis confirmó que la causa
estructural del proyecto es la dependencia de intermediarios (P1) y la falta de canales directos
de comercialización (P2), identificadas como las problemáticas críticas de mayor influencia en
la matriz Vester. Estas dos situaciones no son hechos aislados, sino que originan directamente
los demás efectos negativos detectados: bajos precios para el agricultor, sobreprecios para el
consumidor, poca transparencia en la cadena y pérdidas económicas para el sector rural. Esto
confirma que la propuesta de un marketplace agrícola directo ataca la causa del problema y no
solo sus consecuencias.
2. Sobre los riesgos que pueden afectar el proyecto: la matriz de riesgos evidenció que nueve
de los once riesgos identificados corresponden a un nivel alto, concentrados principalmente en
seguridad de la información, disponibilidad de la plataforma y procesamiento de pagos. Lo más
relevante es que estos riesgos tecnológicos no amenazan al proyecto de forma abstracta, sino
que amenazan directamente la capacidad del marketplace para resolver las problemáticas
críticas identificadas en la matriz Vester: si la plataforma falla, cae o es vulnerada, el canal
directo entre agricultor y consumidor (que es la solución misma) deja de existir y el agricultor
queda nuevamente en manos del intermediario.
3. Sobre las acciones de contingencia necesarias: dado que los riesgos altos están
relacionados con la continuidad y confianza del sistema, las acciones de contingencia

priorizadas se enfocaron en garantizar la disponibilidad de la plataforma (respaldo de
infraestructura y monitoreo), la protección de la información (copias de seguridad, cifrado y
control de accesos) y la continuidad de los pagos (proveedor alternativo). Estas acciones no
buscan eliminar por completo la posibilidad de que ocurra un incidente, sino asegurar que, en
caso de presentarse, el equipo pueda recuperar el servicio en el menor tiempo posible y sin
comprometer la confianza de agricultores y compradores.
4. Sobre las problemáticas que deben ser priorizadas: las problemáticas clasificadas como
críticas y activas en la matriz Vester (P1, P2, P6, P8, P9 y P10) deben orientar el desarrollo
posterior de la plataforma, ya que son las que mayor influencia tienen sobre el resto del sistema.
En particular, la falta de canales directos (P2) y la dependencia de intermediarios (P1) deben
resolverse desde las primeras funcionalidades del software, mientras que problemáticas como
la trazabilidad (P5) o la conectividad rural (P7), aunque menos influyentes en esta etapa,
deberán tenerse en cuenta en fases posteriores del proyecto.
En conjunto, el equipo concluye que la matriz Vester permitió justificar por qué el proyecto es
necesario, la matriz de riesgos permitió identificar qué podría impedir que el software cumpla
ese propósito, y el plan de contingencia estableció cómo actuar para que la solución siga
funcionando ante un incidente. Esta coherencia entre las tres herramientas será la base sobre la
cual el equipo continuará el diseño y desarrollo de la plataforma durante el resto del semestre.
## 8. Referencias
- ANEIA – Universidad de los Andes. (2024). *El papel que juegan los intermediarios en la
agroindustria*. https://aneia.uniandes.edu.co/el-papel-que-juegan-los-intermediarios-en-laagroindustria/
- El Colombiano. (2021). *Agricultura sin intermediarios, la apuesta para apoyar al campo*.
https://www.elcolombiano.com/negocios/el-sistema-de-intermediarios-en-el-campocolombiano-LE14890281
- Revista Ciencias Administrativas (SciELO). (2021). *Análisis del papel de los intermediarios
en el mercado agrícola tradicional. Caso de estudio Antioquia, Colombia*.
http://www.scielo.org.co/scielo.php?script=sci_arttext&pid=S1794-44492021000100007
- CONtexto Ganadero. (2023). *Intermediarios y volatilidad de precios, problemas de nunca
acabar del agro colombiano*. https://www.contextoganadero.com/economia/intermediarios-yprecios-problemas-de-nunca-acabar-del-agro-colombiano
- Ingenio Empresa. (2022). *Matriz de Vester para detectar causas y efectos*.
https://www.ingenioempresa.com/matriz-de-vester/
- Asana. (2026). *Matriz de riesgos: qué
https://asana.com/es/resources/risk-matrix-template

es,

cómo

crearla

y

ejemplos*.

- Recursos en Project Management. (2026). *Matriz de riesgos: plantilla de probabilidad e
impacto (PMBOK)*. https://recursosenprojectmanagement.com/inicio/plantillas/matriz-deimpacto-de-riesgos/
- Congreso de Colombia. (2012). *Ley 1581 de 2012, por la cual se dictan disposiciones
generales para la protección de datos personales*. Función Pública – Gestor Normativo.
https://www.funcionpublica.gov.co/eva/gestornormativo/norma.php?i=49981

.

