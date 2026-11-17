package recetario.modelo;

import recetario.interfaces.ResponsableReceta;

/** Responsable especializado que agrega un cargo fijo a sus honorarios. */
public class Chef extends Persona implements ResponsableReceta {
    private String tareaAsignada;
    private String especialidad;
    private double cargoEspecialidad;

    /** Construye un chef reutilizando la inicialización común mediante super. */
    public Chef(String nombre, String correo, double tarifaHora,
            String especialidad, double cargoEspecialidad) {
        super(nombre, correo, tarifaHora);
        this.especialidad = especialidad;
        this.cargoEspecialidad = Math.max(0, cargoEspecialidad);
    }

    /** Constructor abreviado: tarifa 15.00 y cargo 20.00. */
    public Chef(String nombre, String correo, String especialidad) {
        this(nombre, correo, 15.00, especialidad, 20.00);
    }

    public String getEspecialidad() { return especialidad; }
    public double getCargoEspecialidad() { return cargoEspecialidad; }
    public void mostrarEspecialidad() { System.out.println("Especialidad: " + especialidad); }

    /** Sobrescribe el cálculo base y aplica el cargo una sola vez. */
    @Override
    public double calcularHonorarios(int horas) {
        return super.calcularHonorarios(horas) + cargoEspecialidad;
    }

    /** Extiende la presentación heredada sin repetir los atributos comunes. */
    @Override
    public void mostrarDatos() {
        super.mostrarDatos();
        System.out.printf("Especialidad: %s, cargo: %s%.2f%n",
                especialidad, MONEDA, cargoEspecialidad);
    }

    /** Oculta el método estático de Persona; no es sobrescritura. */
    public static String describirTipo() { return "Chef"; }

    // @Override // No se aplica a describirTipo(): los métodos static no se sobrescriben.
    // public String obtenerIdentificacion() { return ""; } // No se sobrescribe un método final.

    /** Presenta la función propia de este rol al invocarse mediante Persona. */
    @Override
    public void realizarFuncion() {
        System.out.println("Prepara recetas del hotel");
    }

    /**
     * Conserva la tarea hasta la siguiente asignación.
     * @param tarea descripción de la responsabilidad
     */
    @Override
    public void asignarTarea(String tarea) {
        tareaAsignada = tarea;
    }

    /** Usa el nombre heredado para identificar a quien realiza la tarea. */
    @Override
    public void mostrarResponsabilidad() {
        System.out.println(getNombre() + ": " + tareaAsignada);
    }
}
