package recetario.modelo;

/**
 * Reproduce el contexto de la Parte 3 de la guía, antes de mover
 * RecetaPostre a recetario.especialidades en la Parte 4.
 */
public final class RecetaPostreMismoPaqueteTest extends Receta {
    private RecetaPostreMismoPaqueteTest() {
        super("P", "Postre de prueba", 1, "unidad", 1, 4);
    }

    private void comprobarAccesosPermitidos() {
        if (porciones != 4) {
            throw new AssertionError("protected debe ser visible en la subclase del mismo paquete");
        }
        if (!"General".equals(categoria)) {
            throw new AssertionError("categoria debe ser visible dentro de recetario.modelo");
        }
        if (!"Postre de prueba".equals(getNombre())) {
            throw new AssertionError("getNombre público debe ser visible");
        }

        // System.out.println(nombreReceta); // No compila: private en Receta.
    }

    public static void main(String[] args) {
        new RecetaPostreMismoPaqueteTest().comprobarAccesosPermitidos();
        System.out.println("RecetaPostreMismoPaqueteTest: 3 pruebas superadas");
    }
}
