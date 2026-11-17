package recetario.modelo;

import recetario.interfaces.ResponsableReceta;

/** Responsable de supervisión con un cargo fijo por participación. */
public class Supervisor extends Persona implements ResponsableReceta {
    private String tareaAsignada;
    private String carrera;
    private double cargoSupervision;

    /** Construye al supervisor usando el constructor de Persona. */
    public Supervisor(String nombre, String correo, double tarifaHora,
            String carrera, double cargoSupervision) {
        super(nombre, correo, tarifaHora);
        this.carrera = carrera;
        this.cargoSupervision = Math.max(0, cargoSupervision);
    }

    /** Mantiene compatibilidad con el registro existente del laboratorio anterior. */
    public Supervisor(String nombre, String correo, String carrera) {
        this(nombre, correo, 12.00, carrera, 15.00);
    }

    public String getCarrera() { return carrera; }
    public double getCargoSupervision() { return cargoSupervision; }
    public void mostrarCarrera() { System.out.println("Carrera: " + carrera); }

    /** Suma el cargo de supervisión al cálculo del método heredado. */
    @Override
    public double calcularHonorarios(int horas) {
        return super.calcularHonorarios(horas) + cargoSupervision;
    }

    /** Reutiliza la salida de Persona y presenta datos propios del supervisor. */
    @Override
    public void mostrarDatos() {
        super.mostrarDatos();
        System.out.printf("Carrera: %s, cargo: %s%.2f%n",
                carrera, MONEDA, cargoSupervision);
    }

    /** Presenta la función propia de este rol al invocarse mediante Persona. */
    @Override
    public void realizarFuncion() {
        System.out.println("Supervisa la preparación de recetas");
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
