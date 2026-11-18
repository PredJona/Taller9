package envios.interfaces;

public interface ConsumoCombustible {
    /** Suma los litros al consumo anterior. Si la suma excede el rango de double, se rechaza el registro. */
    void registrarConsumo(double litros);

    void mostrarConsumo();
}
