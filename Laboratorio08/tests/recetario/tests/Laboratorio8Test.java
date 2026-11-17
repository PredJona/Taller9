package recetario.tests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import recetario.interfaces.ResponsableReceta;
import recetario.modelo.Chef;
import recetario.modelo.Persona;
import recetario.modelo.Receta;
import recetario.modelo.Supervisor;

/** Pruebas sin dependencias: contratos, reasignación, constructores y honorarios. */
public final class Laboratorio8Test {
    private static int comprobaciones;

    private Laboratorio8Test() {
    }

    public static void main(String[] args) {
        int inicial = Persona.getTotalPersonas();
        Chef chef = new Chef("Ana", "ana@ejemplo.com", "Repostería");
        Supervisor supervisor = new Supervisor("Luis", "luis@ejemplo.com", "Hotelería");
        Chef otroChef = new Chef("Elena", "elena@ejemplo.com", 10, "Cocina", 0);
        Supervisor otroSupervisor = new Supervisor("Pedro", "pedro@ejemplo.com", 20, "Calidad", 5);
        comprobar(Persona.getTotalPersonas() == inicial + 4, "Un incremento por constructor");
        comprobar(otroSupervisor.getNumeroRegistro() == inicial + 4, "Registro secuencial");
        comprobar(chef.obtenerIdentificacion().endsWith(" - Ana"), "Identificación heredada");
        comprobar("Personal del recetario".equals(chef.obtenerTipoRegistro()), "Tipo final heredado");
        comprobar(chef.calcularHonorarios(4) == 80, "Constructor abreviado Chef");
        comprobar(supervisor.calcularHonorarios(4) == 63, "Constructor abreviado Supervisor");
        comprobar(otroChef.calcularHonorarios(4) == 40, "Constructor completo Chef");
        comprobar(otroSupervisor.calcularHonorarios(4) == 85, "Constructor completo Supervisor");
        comprobar(chef.calcularHonorarios(4, 10) == 72, "Descuento sobre cargo incluido");
        comprobar(chef.calcularHonorarios(0) == 20, "Cargo fijo con cero horas");
        comprobar(supervisor.calcularHonorarios(4, 100) == 0, "Descuento total");
        probarRechazos(chef);
        probarRechazos(supervisor);
        probarResponsabilidad(chef, "Ana");
        probarResponsabilidad(supervisor, "Luis");
        probarRecetas(chef, supervisor);
        System.out.println("Laboratorio8Test: " + comprobaciones + " comprobaciones superadas");
    }

    private static void probarRechazos(Persona persona) {
        boolean rechazo = false;
        try {
            persona.calcularHonorarios(-1);
        } catch (IllegalArgumentException esperado) {
            rechazo = true;
        }
        comprobar(rechazo, "Rechaza horas negativas mediante Persona");
        for (double descuento : new double[] {-1, 101}) {
            rechazo = false;
            try {
                persona.calcularHonorarios(4, descuento);
            } catch (IllegalArgumentException esperado) {
                rechazo = true;
            }
            comprobar(rechazo, "Rechaza descuento fuera de rango");
        }
    }

    private static void probarResponsabilidad(ResponsableReceta responsable, String nombre) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream original = System.out;
        try (PrintStream captura = new PrintStream(bytes, true, StandardCharsets.UTF_8)) {
            System.setOut(captura);
            responsable.asignarTarea("Primera tarea");
            responsable.mostrarResponsabilidad();
            comprobar(bytes.toString(StandardCharsets.UTF_8).contains(nombre + ": Primera tarea"),
                    "Almacena la primera tarea");
            bytes.reset();
            responsable.asignarTarea("Nueva tarea");
            responsable.mostrarResponsabilidad();
            String salida = bytes.toString(StandardCharsets.UTF_8);
            comprobar(salida.contains(nombre + ": Nueva tarea"), "Reasigna mediante interfaz");
            comprobar(!salida.contains("Primera tarea"), "La tarea anterior se sustituye");
        } finally {
            System.setOut(original);
        }
    }

    private static void probarRecetas(Chef chef, Supervisor supervisor) {
        Receta recetaChef = new Receta("A", "Postre", 5, "unidad", 1, 4);
        Receta recetaSupervisor = new Receta("B", "Plato", 8, "unidad", 1, 4);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream original = System.out;
        try (PrintStream captura = new PrintStream(bytes, true, StandardCharsets.UTF_8)) {
            System.setOut(captura);
            recetaChef.enviarDatos();
            comprobar(!bytes.toString(StandardCharsets.UTF_8).contains("Responsable:"),
                    "Receta sin responsable no falla ni inventa datos");
            recetaChef.setResponsable(chef);
            recetaSupervisor.setResponsable(supervisor);
            bytes.reset();
            recetaChef.enviarDatos();
            String salida = bytes.toString(StandardCharsets.UTF_8);
            comprobar(salida.contains("Responsable: Ana") && salida.contains("Especialidad: Repostería"),
                    "Receta invoca mostrarDatos del Chef");
            bytes.reset();
            recetaSupervisor.enviarDatos();
            salida = bytes.toString(StandardCharsets.UTF_8);
            comprobar(salida.contains("Responsable: Luis") && salida.contains("Carrera: Hotelería"),
                    "Receta invoca mostrarDatos del Supervisor");
            recetaChef.setResponsable(null);
            comprobar(recetaChef.getResponsable() == null, "Permite retirar el responsable");
        } finally {
            System.setOut(original);
        }
    }

    private static void comprobar(boolean condicion, String mensaje) {
        comprobaciones++;
        if (!condicion) {
            throw new AssertionError(mensaje);
        }
    }
}
