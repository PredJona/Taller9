package recetario.especialidades;
import recetario.modelo.Receta;
public class RecetaPostre extends Receta {
    private boolean requiereRefrigeracion;
    public RecetaPostre(String codigo, String nombre, double precio, String unidad, int cantidad, int porciones, boolean refrigeracion) {
        super(codigo, nombre, precio, unidad, cantidad, porciones, "Postre");
        requiereRefrigeracion=refrigeracion;
    }
    public boolean isRequiereRefrigeracion() { return requiereRefrigeracion; }
    public void setRequiereRefrigeracion(boolean valor) { requiereRefrigeracion=valor; }
    public void mostrarConservacion() { System.out.println(requiereRefrigeracion ? "Conservar refrigerado" : "No requiere refrigeración"); }
    public void probarAccesos() {
        System.out.println("getNombre(): " + getNombre());
        System.out.println("this.porciones: " + this.porciones);
        // System.out.println(categoria); // No compila: atributo de paquete diferente.
        // System.out.println(nombreReceta); // No compila: atributo privado.
    }
    public void comparar(RecetaPostre otra, Receta base) {
        System.out.println(this.porciones);
        System.out.println(otra.porciones);
        // System.out.println(base.porciones); // No compila: referencia de tipo Receta.
    }
}
