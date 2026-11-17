package recetario.modelo;
public class Detalle_Receta {
    private String codigoProducto;
    private String descripcion;
    private String unidadMedida;
    private int cantidad;
    private double precio;

    // Constructor con parámetros
    public Detalle_Receta(String codigoProducto, String descripcion, String unidadMedida, int cantidad, double precio) {
        validarCantidad(cantidad);
        validarPrecio(precio);
        this.codigoProducto = codigoProducto;
        this.descripcion = descripcion;
        this.unidadMedida = unidadMedida;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public void mostrarProductos() {
        System.out.println("----------------------------------------");
        System.out.println("Código Producto: " + codigoProducto);
        System.out.println("Descripción: " + descripcion);
        System.out.println("Unidad de Medida: " + unidadMedida);
        System.out.println("Cantidad: " + cantidad);
        System.out.printf("Precio Unitario: B/.%.2f%n", precio);
        System.out.println("----------------------------------------");
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double nuevoPrecio) {
        validarPrecio(nuevoPrecio);
        precio = nuevoPrecio;
    }

    public double calcularCostoProducto() {
        return cantidad * precio;
    }

    public static double[] calcularPreciosAumentados(Detalle_Receta[] ingredientes, double porcentaje) {
        if (ingredientes == null || ingredientes.length == 0) {
            throw new IllegalArgumentException("Debe registrar al menos un ingrediente");
        }
        if (!Double.isFinite(porcentaje) || porcentaje < 0) {
            throw new IllegalArgumentException("El porcentaje debe ser finito y no negativo");
        }
        double[] nuevosPrecios = new double[ingredientes.length];

        for (int i = 0; i < ingredientes.length; i++) {
            if (ingredientes[i] == null) {
                throw new IllegalArgumentException("Todos los ingredientes deben estar registrados");
            }
            double precioActual = ingredientes[i].getPrecio();
            double nuevoPrecio = precioActual * (1 + porcentaje / 100.0);
            validarPrecio(nuevoPrecio);
            nuevosPrecios[i] = nuevoPrecio;
        }

        return nuevosPrecios;
    }

    private static void validarCantidad(int cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad del ingrediente no puede ser negativa");
        }
    }

    private static void validarPrecio(double precio) {
        if (!Double.isFinite(precio) || precio < 0) {
            throw new IllegalArgumentException("El precio del ingrediente debe ser finito y no negativo");
        }
    }
}
