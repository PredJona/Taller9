package envios.modelo;

import envios.interfaces.ConsumoCombustible;

/** Transporte por automóvil con registro propio de combustible. */
public class Automovil extends MedioTransporte implements ConsumoCombustible {
    private double litrosUtilizados;

    /**
     * Reutiliza mediante super la validación y el contador de la clase base.
     * @param codigo identificador distinto dentro de la flota
     * @param nombreResponsable nombre de quien realiza las entregas
     * @param capacidadMaximaKg capacidad finita y positiva en kilogramos
     * @throws IllegalArgumentException si los datos comunes son inválidos
     */
    public Automovil(String codigo, String nombreResponsable, double capacidadMaximaKg) {
        super(codigo, nombreResponsable, capacidadMaximaKg);
    }

    /** Aporta el comportamiento de automóvil al recorrido polimórfico. */
    @Override
    public void realizarEntrega() {
        System.out.println(getCodigo() + " - " + getNombreResponsable()
                + " traslada el paquete en automóvil hasta el destino.");
    }

    /** {@inheritDoc} */
    @Override
    public void registrarConsumo(double litros) {
        double nuevoTotal = litrosUtilizados + litros;
        if (!Double.isFinite(litros) || litros < 0 || !Double.isFinite(nuevoTotal)) {
            throw new IllegalArgumentException("Los litros y su total deben ser finitos y no negativos.");
        }
        litrosUtilizados = nuevoTotal;
    }

    /** {@inheritDoc} */
    @Override
    public void mostrarConsumo() {
        System.out.println(getCodigo() + " - Consumo de automóvil: " + litrosUtilizados + " litros.");
    }
}
