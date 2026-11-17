package recetario.tests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import recetario.especialidades.RecetaPostre;
import recetario.modelo.Chef;
import recetario.modelo.Detalle_Receta;
import recetario.modelo.Receta;
import recetario.modelo.RecetaPlatoPrincipal;
import recetario.modelo.Supervisor;

public final class ModeloTest {
    private static int pruebas;

    private ModeloTest() {
    }

    public static void main(String[] args) {
        probarCostoTotalConDecimales();
        probarConstructorRecetaRechazaCantidadNoPositiva();
        probarConstructorRecetaRechazaPorcionesNoPositivas();
        probarSetPorcionesRechazaCero();
        probarRecibirCantidadRechazaCero();
        probarDetalleRechazaCantidadNegativa();
        probarDetalleRechazaPrecioNegativo();
        probarSetPrecioRechazaPrecioNegativo();
        probarValoresNoFinitosSonRechazados();
        probarCostoPorPorcionConservaDecimales();
        probarRecetaComponeIngredientes();
        probarComposicionProtegeElArregloInterno();
        probarCostoSinIngredientesEsRechazado();
        probarArregloIncompletoEsRechazado();
        probarAumentoNegativoEsRechazado();
        probarAumentoNoFinitoEsRechazado();
        probarCostoHeredadoCoincideConRecetaBase();
        probarResponsablesEnRecetasDiferentes();

        System.out.println("ModeloTest: " + pruebas + " pruebas superadas");
    }

    private static void probarCostoTotalConDecimales() {
        Receta receta = recetaValida(4);
        Detalle_Receta[] ingredientes = {
            new Detalle_Receta("A", "Arroz", "kg", 2, 1.25),
            new Detalle_Receta("B", "Pollo", "kg", 3, 2.50)
        };

        assertDoubleEquals(10.00, receta.calcularCostoTotal(ingredientes),
                "El costo debe sumar cantidad por precio de cada ingrediente");
    }

    private static void probarConstructorRecetaRechazaCantidadNoPositiva() {
        assertThrows(IllegalArgumentException.class,
                () -> new Receta("R", "Inválida", 4.50, "unidad", 0, 2),
                "El constructor no debe aceptar cantidad cero");
    }

    private static void probarConstructorRecetaRechazaPorcionesNoPositivas() {
        assertThrows(IllegalArgumentException.class,
                () -> new Receta("R", "Inválida", 4.50, "unidad", 1, 0),
                "El constructor no debe aceptar cero porciones");
    }

    private static void probarSetPorcionesRechazaCero() {
        Receta receta = recetaValida(2);
        assertThrows(IllegalArgumentException.class, () -> receta.setPorciones(0),
                "setPorciones no debe aceptar cero");
    }

    private static void probarRecibirCantidadRechazaCero() {
        Receta receta = recetaValida(2);
        assertThrows(IllegalArgumentException.class, () -> receta.recibirCantidad(0),
                "recibirCantidad no debe romper la invariante de cantidad");
    }

    private static void probarDetalleRechazaCantidadNegativa() {
        assertThrows(IllegalArgumentException.class,
                () -> new Detalle_Receta("A", "Arroz", "kg", -1, 1.25),
                "Un ingrediente no puede tener cantidad negativa");
    }

    private static void probarDetalleRechazaPrecioNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Detalle_Receta("A", "Arroz", "kg", 1, -1.25),
                "Un ingrediente no puede tener precio negativo");
    }

    private static void probarSetPrecioRechazaPrecioNegativo() {
        Detalle_Receta detalle = new Detalle_Receta("A", "Arroz", "kg", 1, 1.25);
        assertThrows(IllegalArgumentException.class, () -> detalle.setPrecio(-0.01),
                "setPrecio debe informar un precio inválido en vez de ignorarlo");
    }

    private static void probarValoresNoFinitosSonRechazados() {
        assertThrows(IllegalArgumentException.class,
                () -> new Detalle_Receta("A", "Arroz", "kg", 1, Double.NaN),
                "NaN no es un precio válido");
        assertThrows(IllegalArgumentException.class,
                () -> new Receta("R", "Inválida", Double.POSITIVE_INFINITY,
                        "unidad", 1, 2),
                "Un precio infinito no es válido");
    }

    private static void probarCostoPorPorcionConservaDecimales() {
        Receta receta = recetaValida(4);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream salidaOriginal = System.out;
        try {
            System.setOut(new PrintStream(bytes, true, StandardCharsets.UTF_8));
            receta.calcularMostrarCostoPorPorcion();
        } finally {
            System.setOut(salidaOriginal);
        }

        String salida = bytes.toString(StandardCharsets.UTF_8);
        assertTrue(salida.contains("Costo por porción: B/. 1.13"),
                "El costo 4.50 / 4 debe mostrarse como 1.13, no truncarse");
    }

    private static void probarRecetaComponeIngredientes() {
        Receta receta = recetaValida(2);
        Detalle_Receta[] ingredientes = ingredientesValidos();
        receta.asignarIngredientes(ingredientes);

        assertTrue(receta.tieneIngredientesCompletos(),
                "La receta debe conocer si su composición está completa");
        assertDoubleEquals(10.00, receta.calcularCostoTotal(),
                "El cálculo heredable debe usar los ingredientes de la receta");
    }

    private static void probarComposicionProtegeElArregloInterno() {
        Receta receta = recetaValida(2);
        Detalle_Receta[] ingredientes = ingredientesValidos();
        receta.asignarIngredientes(ingredientes);
        ingredientes[0] = null;

        Detalle_Receta[] copia = receta.obtenerIngredientes();
        copia[1] = null;
        assertTrue(receta.tieneIngredientesCompletos(),
                "Cambiar el arreglo de entrada o salida no debe romper la composición");
    }

    private static void probarCostoSinIngredientesEsRechazado() {
        Receta receta = recetaValida(2);
        assertThrows(IllegalStateException.class, receta::calcularCostoTotal,
                "No se debe calcular una receta incompleta como costo cero");
    }

    private static void probarArregloIncompletoEsRechazado() {
        Receta receta = recetaValida(2);
        assertThrows(IllegalArgumentException.class,
                () -> receta.calcularCostoTotal(null),
                "Un arreglo nulo no representa costo cero");
        assertThrows(IllegalArgumentException.class,
                () -> receta.calcularCostoTotal(new Detalle_Receta[] {null}),
                "Una posición nula exige completar el registro");
    }

    private static void probarAumentoNegativoEsRechazado() {
        assertThrows(IllegalArgumentException.class,
                () -> Detalle_Receta.calcularPreciosAumentados(ingredientesValidos(), -1),
                "Una opción de aumento no debe aceptar porcentajes negativos");
    }

    private static void probarAumentoNoFinitoEsRechazado() {
        assertThrows(IllegalArgumentException.class,
                () -> Detalle_Receta.calcularPreciosAumentados(
                        ingredientesValidos(), Double.NaN),
                "NaN no es un porcentaje válido");
    }

    private static void probarCostoHeredadoCoincideConRecetaBase() {
        Detalle_Receta[] ingredientes = ingredientesValidos();
        Receta base = recetaValida(2);
        RecetaPostre postre = new RecetaPostre(
                "P", "Postre", 4.50, "unidad", 1, 2, true);
        RecetaPlatoPrincipal plato = new RecetaPlatoPrincipal(
                "PP", "Plato", 4.50, "unidad", 1, 2, "Ensalada");
        base.asignarIngredientes(ingredientes);
        postre.asignarIngredientes(ingredientes);
        plato.asignarIngredientes(ingredientes);

        double costoReferencia = base.calcularCostoTotal();
        assertDoubleEquals(costoReferencia, postre.calcularCostoTotal(),
                "RecetaPostre debe reutilizar el cálculo sin cambiar el costo");
        assertDoubleEquals(costoReferencia, plato.calcularCostoTotal(),
                "RecetaPlatoPrincipal debe reutilizar el cálculo sin cambiar el costo");
    }

    private static void probarResponsablesEnRecetasDiferentes() {
        Supervisor supervisor = new Supervisor(
                "Luis", "luis@example.com", "Sistemas");
        Chef chef = new Chef("Ana", "ana@example.com", "Pastelería");
        Receta recetaSupervisada = recetaValida(2);
        Receta recetaDelChef = recetaValida(4);
        recetaSupervisada.setResponsable(supervisor);
        recetaDelChef.setResponsable(chef);

        assertTrue(recetaSupervisada.getResponsable() == supervisor,
                "Una receta debe conservar al Supervisor asignado");
        assertTrue(recetaDelChef.getResponsable() == chef,
                "Otra receta debe conservar al Chef asignado");
        assertTrue("Sistemas".equals(supervisor.getCarrera()),
                "La referencia Supervisor debe exponer su operación propia");
        assertTrue("Pastelería".equals(chef.getEspecialidad()),
                "La referencia Chef debe exponer su operación propia");
    }

    private static Detalle_Receta[] ingredientesValidos() {
        return new Detalle_Receta[] {
            new Detalle_Receta("A", "Arroz", "kg", 2, 1.25),
            new Detalle_Receta("B", "Pollo", "kg", 3, 2.50)
        };
    }

    private static Receta recetaValida(int porciones) {
        return new Receta("R1", "Receta", 4.50, "unidad", 1, porciones);
    }

    private static void assertThrows(Class<? extends Throwable> esperado,
            Accion accion, String mensaje) {
        pruebas++;
        try {
            accion.ejecutar();
        } catch (Throwable error) {
            if (esperado.isInstance(error)) {
                return;
            }
            throw new AssertionError(mensaje + ": se lanzó " + error, error);
        }
        throw new AssertionError(mensaje + ": no se lanzó " + esperado.getSimpleName());
    }

    private static void assertDoubleEquals(double esperado, double actual, String mensaje) {
        pruebas++;
        if (Math.abs(esperado - actual) > 0.000_001) {
            throw new AssertionError(mensaje + ": esperado=" + esperado + ", actual=" + actual);
        }
    }

    private static void assertTrue(boolean condicion, String mensaje) {
        pruebas++;
        if (!condicion) {
            throw new AssertionError(mensaje);
        }
    }

    @FunctionalInterface
    private interface Accion {
        void ejecutar();
    }
}
