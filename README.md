# Taller N.º 9 — Envíos Express Panamá

Implementación exclusiva de la Parte II en Java 21, sin dependencias externas.

## Archivos del proyecto

- `src/envios/app/Main.java`: demostración con una motocicleta, un automóvil y una bicicleta.
- `src/envios/modelo/`: clase abstracta `MedioTransporte` y sus tres subclases.
- `src/envios/interfaces/ConsumoCombustible.java`: contrato implementado por motocicleta y automóvil.
- `.idea/`: configuración de IntelliJ IDEA con Java 21 y `src` como raíz de fuentes.


## Ejecutar en IntelliJ IDEA

1. Abrir la carpeta actual `Taller9`.
2. En **File > Project Structure > Project**, seleccionar un JDK 21 instalado. El nivel del lenguaje es 21.
3. Verificar en **Modules > Sources** que `src` aparezca como carpeta de fuentes.
4. Ejecutar `envios.app.Main` desde su método `main`.

La demostración no solicita entrada por teclado. Muestra las dos sobrecargas del registro, las tres entregas, el consumo de los dos vehículos motorizados y `Total de transportes: 3`.

También se puede compilar y ejecutar desde la raíz con Bash y JDK 21:

```bash
mkdir -p out
javac --release 21 -encoding UTF-8 -Xlint:all -Werror -d out $(find src -name '*.java')
java -cp out envios.app.Main
```

## Condiciones de uso

- Los tres códigos de la demostración son distintos e inmutables. El constructor recibe el código; quien crea la flota debe asignar códigos distintos. No se incorpora un registro global para detectar duplicados.
- Código, responsable y destino deben contener texto.
- Capacidad y peso indicado deben ser finitos y positivos. El peso puede igualar la capacidad, pero no superarla.
- El registro sin peso informa «peso no indicado» y no presume una carga.
- El consumo acumula litros finitos y no negativos; también se rechaza un total que desborde el rango numérico.
- El contador es compartido durante la ejecución de la JVM y aumenta después de validar los datos comunes. Las subclases sólo delegan la construcción mediante `super`.

## Verificación realizada

Con OpenJDK y javac 21.0.12.1 se compilaron los seis archivos fuente con `--release 21 -encoding UTF-8 -Xlint:all -Werror`, sin errores ni advertencias, y se ejecutó Main.

Se ejecutaron pruebas auxiliares en un directorio temporal para comprobar capacidades inválidas, contador de construcciones válidas, getters, registro sin peso, capacidad exacta, peso cero/negativo/no finito/excesivo, destinos vacíos o nulos y combustible cero/negativo/no finito y acumulado. Se verificó una acumulación de 2 + 3 = 5 litros en ambos vehículos. Estas pruebas no forman parte de la demostración de tres objetos.

Se revisaron la encapsulación, el código final sin setter, las implementaciones de la interfaz y la ausencia de casts, `instanceof` y dependencias del recetario. IntelliJ no se abrió durante esta verificación; se configuraron sus archivos y se comprobó la ejecución con el JDK.
