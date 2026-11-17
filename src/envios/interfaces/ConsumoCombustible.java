package envios.interfaces;

/** Contrato específico para los transportes que utilizan combustible. */
public interface ConsumoCombustible {
    /**
     * Acumula los litros utilizados sin reemplazar el consumo anterior.
     * @param litros cantidad finita y no negativa
     * @throws IllegalArgumentException si la cantidad o el total resultante no son válidos
     */
    void registrarConsumo(double litros);

    /** Muestra el consumo acumulado e identifica al transporte. */
    void mostrarConsumo();
}
