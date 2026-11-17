package recetario.modelo;

/** Representa a un responsable del recetario y calcula sus honorarios. */
public abstract class Persona {
    /** Moneda utilizada en los importes académicos. */
    public static final String MONEDA = "B/.";
    private static int totalPersonas = 0;
    private final int numeroRegistro;
    private String nombre;
    private String correo;
    private double tarifaHora;

    /** Inicializa datos comunes y asigna un identificador único secuencial. */
    public Persona(String nombre, String correo, double tarifaHora) {
        this.nombre = nombre;
        this.correo = correo;
        this.tarifaHora = Math.max(0, tarifaHora);
        totalPersonas++;
        numeroRegistro = totalPersonas;
    }

    /** Encadena con el constructor completo y usa la tarifa predeterminada de 10.00. */
    public Persona(String nombre, String correo) {
        this(nombre, correo, 10.00);
    }

    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public double getTarifaHora() { return tarifaHora; }
    public int getNumeroRegistro() { return numeroRegistro; }
    public static int getTotalPersonas() { return totalPersonas; }
    public static String describirTipo() { return "Persona"; }

    /** Calcula horas por tarifa, rechazando horas negativas. */
    public double calcularHonorarios(int horas) {
        if (horas < 0) {
            throw new IllegalArgumentException("Las horas no pueden ser negativas.");
        }
        return horas * tarifaHora;
    }

    /** Aplica un descuento al importe calculado polimórficamente. */
    public double calcularHonorarios(int horas, double descuentoPorcentaje) {
        if (descuentoPorcentaje < 0 || descuentoPorcentaje > 100) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 100.");
        }
        double importe = calcularHonorarios(horas);
        return importe * (1 - descuentoPorcentaje / 100.0);
    }

    /** Identificación heredable que no puede ser sobrescrita. */
    public final String obtenerIdentificacion() {
        return numeroRegistro + " - " + nombre;
    }

    /** Muestra la información común de la persona. */
    public void mostrarDatos() {
        System.out.printf("Nombre: %s, correo: %s, tarifa: %s%.2f%n",
                nombre, correo, MONEDA, tarifaHora);
    }

    /** Cada rol define la función que desempeña en el hotel. */
    public abstract void realizarFuncion();

    /**
     * Identifica el registro común sin permitir que las subclases lo cambien.
     * @return tipo de registro compartido por todo el personal
     */
    public final String obtenerTipoRegistro() {
        return "Personal del recetario";
    }

    // PRUEBAS INVÁLIDAS (comentadas para que el proyecto compile):
    // public int calcularHonorarios(int horas) { return 0; } // No se sobrecarga por retorno.
    // numeroRegistro = 99; // Un atributo final no puede reasignarse.
    // Persona.MONEDA = "USD"; // Una constante final no puede modificarse.
}
