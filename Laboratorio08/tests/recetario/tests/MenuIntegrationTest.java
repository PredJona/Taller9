package recetario.tests;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import recetario.app.Main;

public final class MenuIntegrationTest {
    private static int pruebas;

    private MenuIntegrationTest() {
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        probarLaboratorio8();
        probarOpcionesOriginales();
        probarPostreConChef();
        probarPlatoPrincipalConSupervisor();
        probarDemostracionDeAccesos();
        probarRegistroRegularFallidoConservaDatos();
        probarRegistroEspecializadoFallidoConservaDatos();
        probarPorcionesInvalidasConservanDatos();
        probarAumentoInvalidoConservaPrecios();
        probarRolInvalidoConservaDatos();
        probarEntradaNoNumericaYFinDeEntrada();

        System.out.println("MenuIntegrationTest: " + pruebas + " pruebas superadas");
    }

    private static void probarLaboratorio8() {
        String salida = ejecutar("7", "8", "6");
        assertContiene(salida, "========== PARTE A: CLASE ABSTRACTA ==========", "Parte A");
        assertContiene(salida, "========== PARTE B: INTERFAZ ==========", "Parte B");
        assertContiene(salida, "========== PARTE C: POLIMORFISMO ==========", "Parte C");
        assertContiene(salida, "Ana: Preparar menú del evento", "Tarea del chef");
        assertContiene(salida, "Luis: Verificar calidad de platos", "Tarea del supervisor");
        String personal = salida.substring(salida.indexOf("--- Polimorfismo mediante Persona ---"),
                salida.indexOf("--- Polimorfismo mediante ResponsableReceta ---"));
        assertContiene(personal, "Nombre: Ana", "Primer chef");
        assertContiene(personal, "Nombre: Elena", "Segundo chef");
        assertContiene(personal, "Nombre: Luis", "Supervisor");
        if (personal.split("Prepara recetas del hotel", -1).length != 3
                || personal.split("Supervisa la preparación de recetas", -1).length != 2) {
            throw new AssertionError("Deben actuar dos chefs y un supervisor");
        }
        assertContiene(salida, "Responsable: Ana", "Primera receta");
        assertContiene(salida, "Responsable: Luis", "Segunda receta");
        assertContiene(salida, "Honorarios 4 horas: B/.80.00", "Honorarios chef");
        assertContiene(salida, "Honorarios 4 horas: B/.63.00", "Honorarios supervisor");
        assertContiene(salida, "Costo de ingredientes (separado): B/.9.50", "Costo chef");
        assertContiene(salida, "Costo de ingredientes (separado): B/.11.00", "Costo supervisor");
        assertNoContiene(salida, "Desde Main, getNombre(): Flan", "8 debe salir inmediatamente");
    }

    private static void probarOpcionesOriginales() {
        String salida = ejecutar(
                "1", "R1", "Arroz con pollo", "4.50", "porción", "1", "4",
                "A", "Arroz", "kg", "2", "1.25",
                "B", "Pollo", "kg", "3", "2.50",
                "C", "Sal", "g", "1", "0.50",
                "2", "3", "4", "10", "8");

        assertContiene(salida, "¡Receta y productos registrados exitosamente!",
                "La opción 1 debe registrar la receta");
        assertContiene(salida, "Receta: Arroz con pollo",
                "La opción 2 debe mostrar la receta");
        assertContiene(salida, "Costo total de la receta: B/.10.50",
                "La opción 3 debe calcular todos los ingredientes");
        assertContiene(salida, "Ingrediente 1: B/.1.38",
                "La opción 4 debe aplicar el porcentaje");
        assertContiene(salida, "Programa finalizado",
                "La opción 8 debe terminar el programa");
    }

    private static void probarPostreConChef() {
        String salida = ejecutar(
                "5", "1", "P1", "Flan", "5", "unidad", "1", "4", "s",
                "2", "Ana", "ana@example.com", "Pastelería",
                "A", "Leche", "l", "1", "2",
                "B", "Huevo", "unidad", "2", "1",
                "C", "Azúcar", "kg", "1", "3",
                "2", "3", "8");

        assertContiene(salida, "Especialidad: Pastelería",
                "Debe integrar Chef como responsable");
        assertContiene(salida, "Categoría: Postre",
                "Debe conservar el tipo especializado");
        assertContiene(salida, "Responsable: Ana",
                "La asociación debe consultarse al mostrar");
        assertContiene(salida, "Conservar refrigerado",
                "Debe mostrar el comportamiento propio del postre");
        assertContiene(salida, "Costo total de la receta: B/.7.00",
                "El postre debe reutilizar el cálculo de Receta");
    }

    private static void probarPlatoPrincipalConSupervisor() {
        String salida = ejecutar(
                "5", "2", "PP1", "Pasta", "6", "plato", "1", "2", "Ensalada",
                "1", "Luis", "luis@example.com", "Sistemas",
                "A", "Pasta", "kg", "1", "3",
                "B", "Salsa", "l", "1", "2",
                "C", "Queso", "kg", "1", "1",
                "8");

        assertContiene(salida, "Carrera: Sistemas",
                "Debe integrar Supervisor como responsable");
        assertContiene(salida, "Categoría: Plato principal",
                "Debe construir la subclase de plato principal");
        assertContiene(salida, "Acompañamiento: Ensalada",
                "Debe mostrar el comportamiento propio del plato");
        assertContiene(salida, "Costo heredado: B/.6.00",
                "El plato debe reutilizar el cálculo de Receta");
    }

    private static void probarDemostracionDeAccesos() {
        String salida = ejecutar("6", "8");
        assertContiene(salida, "this.porciones: 4",
                "La subclase de otro paquete debe acceder a protected mediante this");
        assertContiene(salida, "Desde Main, getNombre(): Flan",
                "Main debe usar el método público");
    }

    private static void probarRegistroRegularFallidoConservaDatos() {
        String salida = ejecutar(conRegistroBase(
                "1", "R2", "Receta fallida", "8", "unidad", "1", "2",
                "N1", "Ingrediente nuevo", "kg", "1", "4",
                "N2", "No termina", "kg", "texto",
                "2", "8"));
        String despuesDelError = salida.substring(salida.indexOf("Error: debe ingresar un número válido."));

        assertContiene(despuesDelError, "Receta: Receta anterior",
                "Un registro regular fallido debe conservar la receta anterior");
        assertNoContiene(despuesDelError, "Primero debe registrar la receta y sus ingredientes",
                "Un error no debe borrar el registro anterior");
        assertNoContiene(despuesDelError, "Descripción: Ingrediente nuevo",
                "Un error no debe publicar ingredientes parciales");
    }

    private static void probarRegistroEspecializadoFallidoConservaDatos() {
        String salida = ejecutar(conRegistroBase(
                "5", "1", "P2", "Postre fallido", "5", "unidad", "1", "2", "n",
                "2", "Eva", "eva@example.com", "Postres",
                "N1", "Ingrediente nuevo", "kg", "1", "4",
                "N2", "Inválido", "kg", "texto",
                "2", "8"));
        String despuesDelError = salida.substring(salida.indexOf("Error:"));

        assertContiene(despuesDelError, "Receta: Receta anterior",
                "Un registro especializado fallido debe conservar la receta anterior");
        assertNoContiene(despuesDelError, "Descripción: Ingrediente nuevo",
                "El registro especializado tampoco debe mezclar ingredientes");
    }

    private static void probarPorcionesInvalidasConservanDatos() {
        String salida = ejecutar(conRegistroBase(
                "1", "R2", "Porciones inválidas", "8", "unidad", "1", "0",
                "2", "8"));
        String despuesDelError = salida.substring(salida.indexOf("Error:"));
        assertContiene(despuesDelError, "Receta: Receta anterior",
                "Las porciones inválidas no deben reemplazar datos válidos");
    }

    private static void probarAumentoInvalidoConservaPrecios() {
        String salida = ejecutar(conRegistroBase("4", "-10", "3", "8"));
        String despuesDelError = salida.substring(salida.indexOf("Error:"));
        assertContiene(despuesDelError,
                "El porcentaje debe ser finito y no negativo",
                "La opción de aumento debe rechazar porcentajes negativos");
        assertContiene(despuesDelError, "Costo total de la receta: B/.6.00",
                "Un aumento inválido no debe alterar ningún precio");
    }

    private static void probarRolInvalidoConservaDatos() {
        String salida = ejecutar(conRegistroBase(
                "5", "1", "P2", "Postre fallido", "5", "unidad", "1", "2", "n",
                "9", "Eva", "eva@example.com",
                "2", "8"));
        String despuesDelError = salida.substring(salida.indexOf("Error:"));
        assertContiene(despuesDelError, "Receta: Receta anterior",
                "Un rol inválido no debe reemplazar la receta anterior");
    }

    private static void probarEntradaNoNumericaYFinDeEntrada() {
        String salidaNoNumerica = ejecutar("abc", "8");
        assertContiene(salidaNoNumerica, "Error: debe ingresar un número válido.",
                "El menú debe recuperarse de texto donde espera una opción");

        String salidaFin = ejecutarSinTerminador("6\n");
        assertContiene(salidaFin, "Programa finalizado",
                "El fin de entrada debe terminar sin repetir el menú indefinidamente");
    }

    private static String[] conRegistroBase(String... continuacion) {
        String[] base = {
            "1", "R1", "Receta anterior", "4.50", "unidad", "1", "4",
            "A", "Anterior 1", "kg", "1", "1",
            "B", "Anterior 2", "kg", "1", "2",
            "C", "Anterior 3", "kg", "1", "3"
        };
        String[] resultado = new String[base.length + continuacion.length];
        System.arraycopy(base, 0, resultado, 0, base.length);
        System.arraycopy(continuacion, 0, resultado, base.length, continuacion.length);
        return resultado;
    }

    private static String ejecutar(String... lineas) {
        return ejecutarSinTerminador(String.join("\n", lineas) + "\n");
    }

    private static String ejecutarSinTerminador(String entrada) {
        ByteArrayInputStream bytesEntrada = new ByteArrayInputStream(
                entrada.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream bytesSalida = new ByteArrayOutputStream();
        PrintStream entradaOriginal = System.out;
        java.io.InputStream sistemaEntradaOriginal = System.in;
        try {
            System.setIn(bytesEntrada);
            System.setOut(new PrintStream(bytesSalida, true, StandardCharsets.UTF_8));
            Main.main(new String[0]);
        } finally {
            System.setIn(sistemaEntradaOriginal);
            System.setOut(entradaOriginal);
        }
        return bytesSalida.toString(StandardCharsets.UTF_8);
    }

    private static void assertContiene(String texto, String esperado, String mensaje) {
        pruebas++;
        if (!texto.contains(esperado)) {
            throw new AssertionError(mensaje + ": no se encontró <" + esperado + ">\n" + texto);
        }
    }

    private static void assertNoContiene(String texto, String inesperado, String mensaje) {
        pruebas++;
        if (texto.contains(inesperado)) {
            throw new AssertionError(mensaje + ": se encontró <" + inesperado + ">\n" + texto);
        }
    }
}
