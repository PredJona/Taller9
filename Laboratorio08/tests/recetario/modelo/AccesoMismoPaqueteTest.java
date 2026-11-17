package recetario.modelo;

public final class AccesoMismoPaqueteTest {
    private AccesoMismoPaqueteTest() {
    }

    public static void main(String[] args) {
        Receta receta = new Receta("A", "Accesos", 1, "unidad", 1, 3);

        if (receta.porciones != 3) {
            throw new AssertionError("protected debe ser visible en el mismo paquete");
        }
        if (!"General".equals(receta.categoria)) {
            throw new AssertionError("El acceso de paquete debe ser visible en su paquete");
        }
        if (!"Accesos".equals(receta.getNombre())) {
            throw new AssertionError("El método public debe ser visible");
        }

        // receta.nombreReceta no compila: el atributo es private en Receta.
        System.out.println("AccesoMismoPaqueteTest: 3 pruebas superadas");
    }
}
