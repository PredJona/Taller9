package recetario.tests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import recetario.app.TallerStaticFinalMain;

public final class TallerStaticFinalTest {
    private static int pruebas;

    private TallerStaticFinalTest() {
    }

    public static void main(String[] args) {
        ByteArrayOutputStream bytesSalida = new ByteArrayOutputStream();
        PrintStream salidaOriginal = System.out;
        try {
            System.setOut(new PrintStream(bytesSalida, true, StandardCharsets.UTF_8));
            TallerStaticFinalMain.main(new String[0]);
        } finally {
            System.setOut(salidaOriginal);
        }

        String salida = bytesSalida.toString(StandardCharsets.UTF_8);
        assertContiene(salida, "Total de personas tras tres registros: 3",
                "La demostración debe validar tres objetos iniciales");
        assertContiene(salida, "Honorarios del chef por 4 horas: B/.80.00",
                "Debe mostrar honorarios del Chef con Persona.MONEDA");
        assertContiene(salida, "Tipo de registro del chef: Personal del recetario",
                "Chef debe invocar el método final heredado");
        assertContiene(salida, "Máximo de recetas: 100",
                "Debe mostrar Configuracion.MAX_RECETAS");
        assertContiene(salida, "Tipo según Persona: Persona",
                "Debe invocar Persona.describirTipo()");
        assertContiene(salida, "Tipo según Chef: Chef",
                "Debe invocar Chef.describirTipo()");
        assertContiene(salida, "Total de personas tras el cuarto registro: 4",
                "El cuarto objeto debe incrementar una sola vez el contador");

        System.out.println("TallerStaticFinalTest: " + pruebas + " pruebas superadas");
    }

    private static void assertContiene(String texto, String esperado, String mensaje) {
        pruebas++;
        if (!texto.contains(esperado)) {
            throw new AssertionError(mensaje + ": no se encontró <" + esperado + ">\n" + texto);
        }
    }
}
