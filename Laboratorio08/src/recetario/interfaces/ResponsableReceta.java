package recetario.interfaces;

/** Contrato para asignar y consultar responsabilidades en la preparación de recetas. */
public interface ResponsableReceta {
    /**
     * Sustituye la tarea anterior por la indicada.
     * @param tarea descripción de la tarea asignada
     */
    void asignarTarea(String tarea);

    /** Muestra el nombre del responsable y su tarea actual. */
    void mostrarResponsabilidad();
}
