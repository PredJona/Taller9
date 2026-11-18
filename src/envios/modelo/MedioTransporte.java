package envios.modelo;

public abstract class MedioTransporte {
    // Quien crea la flota debe asignar un código único a cada transporte.
    private final String codigo;
    private String nombreResponsable;
    private double capacidadMaximaKg;
    private static int totalTransportes;

    protected MedioTransporte(String codigo, String nombreResponsable, double capacidadMaximaKg) {
        validarTexto(codigo, "El código");
        validarTexto(nombreResponsable, "El nombre del responsable");
        if (!Double.isFinite(capacidadMaximaKg) || capacidadMaximaKg <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser finita y mayor que cero.");
        }
        this.codigo = codigo;
        this.nombreResponsable = nombreResponsable;
        this.capacidadMaximaKg = capacidadMaximaKg;
        // Contamos el transporte después de validar sus datos.
        totalTransportes++;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public double getCapacidadMaximaKg() {
        return capacidadMaximaKg;
    }

    public static int getTotalTransportes() {
        return totalTransportes;
    }

    /** Permite registrar la entrega aunque todavía se desconozca el peso. */
    public void registrarEntrega(String destino) {
        validarTexto(destino, "El destino");
        mostrarRegistro(destino, "peso no indicado");
    }

    /** Cuando se conoce el peso, se comprueba que el vehículo pueda llevar la carga. */
    public void registrarEntrega(String destino, double pesoKg) {
        validarTexto(destino, "El destino");
        if (!Double.isFinite(pesoKg) || pesoKg <= 0 || pesoKg > capacidadMaximaKg) {
            throw new IllegalArgumentException(
                    "El peso debe ser finito, mayor que cero y no superar " + capacidadMaximaKg + " kg.");
        }
        mostrarRegistro(destino, "peso: " + pesoKg + " kg");
    }

    private void mostrarRegistro(String destino, String detallePeso) {
        System.out.println(codigo + " - Entrega registrada hacia " + destino + ", " + detallePeso + ".");
    }

    private static void validarTexto(String texto, String campo) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException(campo + " no puede estar vacío.");
        }
    }

    public abstract void realizarEntrega();
}
