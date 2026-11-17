package recetario.modelo;
public class Receta {
    private String codigo;
    private String nombreReceta;
    protected int porciones;
    String categoria;
    private Persona responsable;
    private double precioUnitario;
    private String unidadMedida;
    private int cantidadReceta;
    private int cantidadPorciones;
    private Detalle_Receta[] ingredientes = new Detalle_Receta[0];

    public Receta(String codigo, String nombreReceta, double precioUnitario, String unidadMedida, int cantidadReceta, int cantidadPorciones) {
        this(codigo, nombreReceta, precioUnitario, unidadMedida,
                cantidadReceta, cantidadPorciones, "General");
    }

    protected Receta(String codigo, String nombreReceta, double precioUnitario,
            String unidadMedida, int cantidadReceta, int cantidadPorciones, String categoria) {
        validarCantidadPositiva(cantidadReceta);
        validarPorciones(cantidadPorciones);
        validarPrecio(precioUnitario);
        this.codigo = codigo;
        this.nombreReceta = nombreReceta;
        this.precioUnitario = precioUnitario;
        this.unidadMedida = unidadMedida;
        this.cantidadReceta = cantidadReceta;
        this.cantidadPorciones = cantidadPorciones;
        this.porciones = cantidadPorciones;
        this.categoria = categoria;
    }

    public String getNombre() { return nombreReceta; }
    public void setNombre(String nombre) { this.nombreReceta = nombre; }
    public int getPorciones() { return porciones; }
    public void setPorciones(int valor) {
        validarPorciones(valor);
        porciones = valor;
        cantidadPorciones = valor;
    }
    public String getCategoria() { return categoria; }
    public final void setCategoria(String categoria) { this.categoria = categoria; }
    public Persona getResponsable() { return responsable; }
    public void setResponsable(Persona responsable) { this.responsable = responsable; }

    public void asignarIngredientes(Detalle_Receta[] nuevosIngredientes) {
        validarIngredientesCompletos(nuevosIngredientes);
        ingredientes = nuevosIngredientes.clone();
    }

    public Detalle_Receta[] obtenerIngredientes() {
        return ingredientes.clone();
    }

    public boolean tieneIngredientesCompletos() {
        if (ingredientes.length == 0) {
            return false;
        }
        for (int i = 0; i < ingredientes.length; i++) {
            if (ingredientes[i] == null) {
                return false;
            }
        }
        return true;
    }
    public void enviarDatos() {
        System.out.println("Código: " + this.codigo);
        System.out.println("Receta: " + this.nombreReceta);
        System.out.printf("Precio Unitario: B/.%.2f%n", this.precioUnitario);
        System.out.println("Unidad de Medida: " + this.unidadMedida);
        System.out.println("Cantidad de Receta: " + this.cantidadReceta);
        System.out.println("Cantidad de Porciones: " + this.cantidadPorciones);
        System.out.println("Categoría: " + categoria);
        if (responsable != null) {
            System.out.println("Responsable: " + responsable.getNombre());
            // El tipo concreto aporta su especialidad o carrera mediante polimorfismo.
            responsable.mostrarDatos();
        }
    }

    public void recibirCantidad(int cantidad) {
        validarCantidadPositiva(cantidad);
        this.cantidadReceta = cantidad;
    }

    // Calcula el costo total recorriendo el arreglo de ingredientes.
    public double calcularCostoTotal(Detalle_Receta[] ingredientes) {
        validarIngredientesCompletos(ingredientes);
        double total = 0;

        for (int i = 0; i < ingredientes.length; i++) {
            total += ingredientes[i].calcularCostoProducto();
        }

        return total;
    }

    public double calcularCostoTotal() {
        if (!tieneIngredientesCompletos()) {
            throw new IllegalStateException("La receta no tiene todos sus ingredientes");
        }
        return calcularCostoTotal(ingredientes);
    }

    public double calcularTotal() {
        return this.precioUnitario * this.cantidadReceta;
    }

    public void mostrarCostoTotalFormateado() {
        double total = calcularTotal();
        System.out.println("Receta: " + this.nombreReceta);
        System.out.println("Costo de Receta: B/." + total);
    }
    public void calcularMostrarCostoPorPorcion() {
        double costoTotal = calcularTotal();
        double costoPorPorcion = costoTotal / cantidadPorciones;
        System.out.printf("Costo de Receta: B/. %.2f%n", costoTotal);
        System.out.println("Cantidad de porciones: " + cantidadPorciones);
        System.out.printf("Costo por porción: B/. %.2f%n", costoPorPorcion);
    }

    public void mostrarPorciones() {
        for (int i = 1; i <= cantidadPorciones; i++) {
            System.out.println("Porción número: " + i);
        }
    }

    private static void validarCantidadPositiva(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad de la receta debe ser positiva");
        }
    }

    private static void validarPorciones(int valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Las porciones deben ser positivas");
        }
    }

    private static void validarPrecio(double precio) {
        if (!Double.isFinite(precio) || precio < 0) {
            throw new IllegalArgumentException("El precio unitario debe ser finito y no negativo");
        }
    }

    private static void validarIngredientesCompletos(Detalle_Receta[] detalles) {
        if (detalles == null || detalles.length == 0) {
            throw new IllegalArgumentException("Debe registrar al menos un ingrediente");
        }
        for (int i = 0; i < detalles.length; i++) {
            if (detalles[i] == null) {
                throw new IllegalArgumentException("Todos los ingredientes deben estar registrados");
            }
        }
    }
}
