package envios.modelo;

/** Base abstracta que centraliza los datos y el registro común de entregas. */
public abstract class MedioTransporte {
    // final impide cambiar el código; los códigos distintos se asignan al crear la flota.
    private final String codigo;
    private String nombreResponsable;
    private double capacidadMaximaKg;
    private static int totalTransportes;

    /**
     * Inicializa el estado común desde los constructores de las subclases.
     * @param codigo identificador no vacío, distinto para cada transporte de la flota
     * @param nombreResponsable nombre de quien realiza las entregas
     * @param capacidadMaximaKg capacidad finita y mayor que cero
     * @throws IllegalArgumentException si los datos no son válidos
     */
    protected MedioTransporte(String codigo, String nombreResponsable, double capacidadMaximaKg) {
        validarTexto(codigo, "El código");
        validarTexto(nombreResponsable, "El nombre del responsable");
        if (!Double.isFinite(capacidadMaximaKg) || capacidadMaximaKg <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser finita y mayor que cero.");
        }
        this.codigo = codigo;
        this.nombreResponsable = nombreResponsable;
        this.capacidadMaximaKg = capacidadMaximaKg;
        // Se cuenta una vez desde super, después de validar todos los datos de construcción.
        totalTransportes++;
    }

    /** @return código inmutable del transporte */
    public String getCodigo() {
        return codigo;
    }

    /** @return nombre del responsable de las entregas */
    public String getNombreResponsable() {
        return nombreResponsable;
    }

    /** @return capacidad máxima de carga en kilogramos */
    public double getCapacidadMaximaKg() {
        return capacidadMaximaKg;
    }

    /** @return cantidad compartida de transportes construidos correctamente */
    public static int getTotalTransportes() {
        return totalTransportes;
    }

    /**
     * Registra una entrega cuyo peso no fue proporcionado, sin asumir un valor.
     * @param destino lugar de entrega no vacío
     * @throws IllegalArgumentException si el destino es nulo o está vacío
     */
    public void registrarEntrega(String destino) {
        validarTexto(destino, "El destino");
        mostrarRegistro(destino, "peso no indicado");
    }

    /**
     * Sobrecarga que permite comprobar la carga antes de registrar la entrega.
     * @param destino lugar de entrega no vacío
     * @param pesoKg peso finito, positivo y no superior a la capacidad
     * @throws IllegalArgumentException si el destino o el peso son inválidos
     */
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

    /** Permite que cada transporte defina su forma particular de realizar una entrega. */
    public abstract void realizarEntrega();
}
