# Static y Final Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Completar y demostrar los requisitos `static` y `final` conservando el Laboratorio 7.

**Architecture:** Se mantienen las clases existentes y se añaden sólo el contrato final faltante, una configuración independiente y una aplicación de demostración. Las comprobaciones positivas se ejecutan en JVM separadas y las negativas se compilan desde fuentes temporales.

**Tech Stack:** Java 21, `javac`, Bash y pruebas Java sin dependencias.

**Spec:** `docs/superpowers/specs/2026-10-30-static-final-design.md`

## Global Constraints

- No cambiar constructores ni cálculos existentes.
- No modificar el menú principal.
- No añadir frameworks ni dependencias.
- Compilar con Java 21, `-Xlint:all` y `-Werror`.

## Review Focus

- El encadenamiento de constructores debe incrementar `totalPersonas` una sola vez.
- La demostración debe validar 3 antes de construir el cuarto objeto.
- La ejecución debe ser independiente del estado del menú.
- Los tres experimentos negativos deben fallar por el modificador `final` correcto.
- El runner completo debe alimentar las entradas de menú mediante redirección en la misma orden `java`.

---

### Task 1: Restaurar el runner existente

**Files:**
- Modify: `scripts/test.sh`

**Interfaces:**
- Consumes: archivos de entrada existentes en `tests/fixtures`.
- Produces: ejecución no interactiva completa del menú.

- [ ] Reproducir `bash scripts/test.sh` y confirmar que el `java` de la etapa 5 recibe EOF.
- [ ] Añadir continuación de línea a los dos comandos `java` afectados.
- [ ] Ejecutar el runner y comprobar sus seis etapas.

### Task 2: Probar e implementar el contrato heredado

**Files:**
- Modify: `tests/recetario/tests/ModeloTest.java`
- Modify: `src/recetario/modelo/Persona.java`

**Interfaces:**
- Produces: `public final String obtenerTipoRegistro()`.

- [ ] Añadir una prueba que invoque el método desde `Chef` y espere `Personal del recetario`.
- [ ] Compilar y comprobar el fallo por método ausente.
- [ ] Implementar el método mínimo documentado.
- [ ] Ejecutar las pruebas del modelo.

### Task 3: Configuración y demostración independiente

**Files:**
- Create: `src/recetario/modelo/Configuracion.java`
- Create: `src/recetario/app/TallerStaticFinalMain.java`
- Create: `tests/recetario/tests/TallerStaticFinalTest.java`
- Modify: `scripts/test.sh`

**Interfaces:**
- Consumes: `Persona`, `Chef`, `Supervisor` y sus APIs existentes.
- Produces: `Configuracion.MAX_RECETAS` y una demostración ejecutable aislada.

- [ ] Añadir una prueba de salida que exija contador 3/4, honorarios, tipo heredado, máximo y tipos estáticos.
- [ ] Compilar y comprobar el fallo por clases ausentes.
- [ ] Crear las dos clases mínimas y documentadas.
- [ ] Incorporar la prueba y la demostración al runner.
- [ ] Ejecutar el runner completo.

### Task 4: Experimentos negativos y verificación final

**Files:**
- Temporary only: fuentes bajo `/tmp`.

**Interfaces:**
- Consumes: artefactos compilados del proyecto.
- Produces: diagnósticos de `javac` sin dejar clases inválidas.

- [ ] Compilar por separado la reasignación de `Persona.MONEDA` y registrar el error.
- [ ] Compilar por separado la sobrescritura de `obtenerTipoRegistro()` y registrar el error.
- [ ] Compilar por separado `ConfiguracionEspecial extends Configuracion` y registrar el error.
- [ ] Ejecutar compilación limpia, demostración, pruebas, menú y revisar duplicaciones/cambios finales.
