package recetario.app;

import recetario.modelo.Chef;
import recetario.modelo.Configuracion;
import recetario.modelo.Persona;
import recetario.modelo.Supervisor;

/** Ejecuta de forma independiente la demostración de miembros static y final. */
public final class TallerStaticFinalMain {

    /** Impide crear instancias de una clase que sólo contiene el punto de entrada. */
    private TallerStaticFinalMain() {
    }

    /** Crea los objetos del taller y comprueba sus resultados observables. */
    public static void main(String[] args) {
        Chef chefPrincipal = new Chef(
                "Ana", "ana@ejemplo.com", "Repostería");
        new Chef("Carlos", "carlos@ejemplo.com", "Cocina internacional");
        new Supervisor("Luis", "luis@ejemplo.com", "Gestión hotelera");

        int totalInicial = Persona.getTotalPersonas();
        if (totalInicial != 3) {
            throw new IllegalStateException(
                    "Se esperaban 3 personas y se registraron " + totalInicial);
        }

        System.out.println("Total de personas tras tres registros: " + totalInicial);
        System.out.printf("Honorarios del chef por 4 horas: %s%.2f%n",
                Persona.MONEDA, chefPrincipal.calcularHonorarios(4));
        System.out.println("Tipo de registro del chef: "
                + chefPrincipal.obtenerTipoRegistro());
        System.out.println("Máximo de recetas: " + Configuracion.MAX_RECETAS);
        System.out.println("Tipo según Persona: " + Persona.describirTipo());
        System.out.println("Tipo según Chef: " + Chef.describirTipo());

        new Chef("Elena", "elena@ejemplo.com", 10.00, "Cocina general", 0);
        int totalFinal = Persona.getTotalPersonas();
        if (totalFinal != 4) {
            throw new IllegalStateException(
                    "Se esperaban 4 personas y se registraron " + totalFinal);
        }
        System.out.println("Total de personas tras el cuarto registro: " + totalFinal);
    }
}
