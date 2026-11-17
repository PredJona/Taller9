package recetario.especialidades;

import recetario.modelo.Receta;

public final class AccesoSubclaseTest extends Receta {
    private AccesoSubclaseTest(int porciones) {
        super("S", "Subclase", 1, "unidad", 1, porciones);
    }

    private int sumarPorciones(AccesoSubclaseTest otra) {
        return this.porciones + otra.porciones;
    }

    public static void main(String[] args) {
        AccesoSubclaseTest primera = new AccesoSubclaseTest(2);
        AccesoSubclaseTest segunda = new AccesoSubclaseTest(3);

        if (primera.sumarPorciones(segunda) != 5) {
            throw new AssertionError("protected debe ser visible mediante referencias de la subclase");
        }
        if (!"Subclase".equals(primera.getNombre())) {
            throw new AssertionError("public debe ser visible desde otro paquete");
        }

        // primera.categoria no compila: el acceso de paquete no se hereda fuera del paquete.
        // Receta base = primera; base.porciones no compila desde otro paquete.
        System.out.println("AccesoSubclaseTest: 2 pruebas superadas");
    }
}
