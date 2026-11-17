por# Laboratorio 8 — Abstracción, interfaces y polimorfismo

Ampliación de `Lab7 (1).zip`, sin dependencias externas, con Java 21. Se conserva el recetario, sus paquetes, constructores, métodos, validaciones y cálculos. La opción 7 incorpora las tres partes y conserva honorarios, registros, contadores y tipos estáticos. La opción 8 sigue cerrando el programa.

## Ejecutar desde IntelliJ IDEA

1. Abrir esta carpeta `Laboratorio08` mediante **File > Open**.
2. En **File > Project Structure**, seleccionar un JDK 21 y nivel de lenguaje 21. El módulo `Laboratorio08.iml` marca `src` como fuentes y `tests` como pruebas.
3. Usar **Build > Rebuild Project**.
4. Ejecutar `src/recetario/app/Main.java` desde su método `main`.
5. Ingresar **7** para la demostración; ingresar **8** para salir.

Para la demostración anterior, ejecutar `recetario.app.TallerStaticFinalMain` en una ejecución independiente: sus comprobaciones de contador 3/4 presuponen una JVM nueva, igual que en el original. No se reinicia el contador global artificialmente.

## Evidencias para entregar

Capturar la consola de la opción 7:

1. **PARTE A: CLASE ABSTRACTA**: datos de Ana y Luis y las frases `Prepara recetas del hotel` y `Supervisa la preparación de recetas`.
2. **PARTE B: INTERFAZ**: nombres y tareas `Preparar menú del evento` y `Verificar calidad de platos`.
3. **PARTE C: POLIMORFISMO**: los tres trabajadores (dos chefs y un supervisor), las responsabilidades mediante interfaz y ambas recetas con sus responsables concretos. Ampliar la consola o reducir la fuente para incluir las tres subsecciones.

`docs/evidencia-consola.txt` contiene una ejecución real de referencia (entrada 7 y 8); las capturas deben tomarse en la consola del estudiante.

## Cambios

- `src/recetario/modelo/Persona.java`: clase abstracta, `realizarFuncion()` y reparación del método final faltante `obtenerTipoRegistro()`.
- `Chef.java` y `Supervisor.java`: implementación de función y de `ResponsableReceta`, con tarea privada y nombre obtenido mediante `getNombre()`.
- `src/recetario/interfaces/ResponsableReceta.java`: contrato nuevo de dos métodos.
- `src/recetario/app/Main.java`: extensión de la opción 7; un arreglo `Persona[3]` y otro `ResponsableReceta[2]`, sin casts ni `instanceof` en la demostración nueva. Se conserva el método `demostrarLaboratorio7()` como entrada existente y se extrae la presentación de recetas y honorarios por responsabilidad.
- `src/recetario/modelo/Receta.java`: conserva `Responsable: ` y llama a `mostrarDatos()` dentro de la comprobación de no nulo. Los algoritmos de ingredientes y costos no cambian.
- `TallerStaticFinalMain.java`: cuarto registro concreto mediante un constructor existente de Chef. Se mantiene el total 3/4 y la demostración de `final`.
- `tests/recetario/tests/MenuIntegrationTest.java`: salida con 8 y comprobaciones nuevas de las tres partes y salida inmediata.
- `tests/recetario/tests/Laboratorio8Test.java`: contratos, reasignación, responsables nulos, constructores, contador, honorarios, límites y entradas inválidas.
- `scripts/test.sh`, `scripts/test-access.sh` y fixtures: compilación Java 21 y recorridos reparados; se incorpora `scripts/test-abstract.sh` para comprobar el rechazo de instanciación abstracta.
- Módulo y nombre IntelliJ actualizados a Laboratorio08; documentación y evidencia añadidas. Se excluyen binarios y salidas antiguas del ZIP original.

## Diagnóstico y decisiones

La compilación inicial de todos los Java del ZIP original falló en `TallerStaticFinalMain`: `chefPrincipal.obtenerTipoRegistro()` no existía. La prueba `TallerStaticFinalTest` y los documentos originales de `docs/superpowers/` exigían un método final heredado que devolviera `Personal del recetario`; se incorporó exactamente ese contrato. Esos documentos se conservan como antecedentes del Laboratorio 7, no como instrucciones actuales de instanciar Persona.

Se localizaron dos instanciaciones directas de Persona (Main y TallerStaticFinalMain). Se sustituyeron por Chef con tarifa 10 y cargo 0 mediante su constructor completo, conservando la tarifa anterior y un solo incremento por objeto. El ejemplo inválido solicitado permanece comentado en Main.

El script original dejaba las redirecciones en una línea separada del comando Java, por lo que no ejecutaba correctamente los fixtures. Se agregó la continuación de línea. Las pruebas antiguas usaban 7 como salida, aunque Main ya salía con 8; se actualizó la entrada, conservando sus aserciones. `TallerStaticFinalTest` existía pero no se ejecutaba en el script: ahora se ejecuta en su propia JVM.

Se consultó la copia legible de `guiadecodigo.pdf` en `Documentos/Proyectos/GongFLow/docs/GUIADECODIGO`. Se aplican claridad, encapsulamiento, cambios pequeños y pruebas proporcionales. Prevalece el requisito académico: se conservan los arreglos, los tipos numéricos y el sistema de construcción; no se añaden Maven, base de datos ni frameworks. Las lambdas ya existentes en las pruebas originales se conservan; el código nuevo no las utiliza.

## Verificación reproducible

Desde esta carpeta, en Bash con Java 21 disponible:

```bash
bash scripts/test.sh
```

El script compila **todos** los Java de `src` y `tests` con `--release 21 -encoding UTF-8 -Xlint:all -Werror`. Usa directorios temporales y los elimina al terminar. La instrucción inválida de la prueba abstracta sólo se genera temporalmente fuera de las fuentes del proyecto.

Resultados ejecutados con OpenJDK 21.0.12.1:

- Compilación sin errores ni advertencias después de cada parte.
- ModeloTest: 25 comprobaciones.
- MenuIntegrationTest: 42 comprobaciones contadas y validación adicional de dos funciones Chef y una Supervisor.
- Accesos: 8 permitidos y 4 rechazos de compilación esperados.
- TallerStaticFinalTest: 7 comprobaciones.
- Laboratorio8Test: 27 comprobaciones.
- Recorrido del menú: 14 resultados visibles comprobados.
- Registro fallido: conservación de receta e ingredientes comprobada.
- Persona abstracta: rechazo de instanciación directa comprobado por javac.

La prueba nueva del menú se ejecutó antes de los cambios y falló por faltar la sección A; pasó después de implementar el laboratorio. La suite completa pasa. IntelliJ no se abrió en este entorno: se verificaron sus archivos de proyecto y la ejecución real con el JDK 21.
