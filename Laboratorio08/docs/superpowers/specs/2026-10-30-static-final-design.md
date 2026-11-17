# Diseño: taller `static` y `final`

## Intención

Completar el taller sobre `static` y `final` sin alterar el menú ni los cálculos del Laboratorio 7. El resultado debe demostrar herencia, métodos y clases finales, constantes, ocultamiento estático y el conteo exacto de instancias.

## Alcance y restricciones

- Añadir `Persona.obtenerTipoRegistro()` como método público de instancia y `final`.
- Añadir la clase final no instanciable `Configuracion` con `MAX_RECETAS = 100`.
- Añadir `TallerStaticFinalMain` como ejecución independiente que crea tres objetos, valida el contador, muestra los resultados requeridos y luego crea un cuarto objeto.
- Conservar constructores, honorarios, ingredientes, `Main`, `Supervisor` y `ConfiguracionRecetario`.
- Compilar con Java 21 y demostrar mediante fuentes temporales los tres rechazos de compilación requeridos.

## Enfoques considerados

1. **Demostración independiente (elegido):** mantiene el menú intacto y garantiza una JVM nueva con contador inicial en cero.
2. Extender la opción 7 de `Main`: reutiliza una entrada existente, pero acopla el taller al estado previo del menú y contradice la preferencia explícita por una clase independiente.
3. Sustituir la demostración existente: elimina comportamiento previo y no cumple la restricción de preservar el laboratorio.

## Diseño elegido

`TallerStaticFinalMain` construirá exactamente dos `Chef` y un `Supervisor`, validará `Persona.getTotalPersonas() == 3`, imprimirá honorarios y los elementos `static`/`final`, y sólo entonces construirá una `Persona` y validará el total 4. Una prueba de integración ejecutará esa clase en una JVM separada. Los experimentos inválidos se crearán en un directorio temporal y nunca permanecerán en el proyecto.
