package recetario.modelo;
public class RecetaPlatoPrincipal extends Receta {
    private String acompanamiento;
    public RecetaPlatoPrincipal(String codigo, String nombre, double precio, String unidad, int cantidad, int porciones, String acompanamiento) {
        super(codigo, nombre, precio, unidad, cantidad, porciones, "Plato principal");
        this.acompanamiento=acompanamiento;
    }
    public String getAcompanamiento() { return acompanamiento; }
    public void setAcompanamiento(String valor) { acompanamiento=valor; }
    public void mostrarAcompanamiento() { System.out.println("Acompañamiento: " + acompanamiento); }
}
