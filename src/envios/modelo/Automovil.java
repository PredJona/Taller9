package envios.modelo;

import envios.interfaces.ConsumoCombustible;

public class Automovil extends MedioTransporte implements ConsumoCombustible {
    private double litrosUtilizados;

    public Automovil(String codigo, String nombreResponsable, double capacidadMaximaKg) {
        super(codigo, nombreResponsable, capacidadMaximaKg);
    }

    @Override
    public void realizarEntrega() {
        System.out.println(getCodigo() + " - " + getNombreResponsable()
                + " traslada el paquete en automóvil hasta el destino.");
    }

    @Override
    public void registrarConsumo(double litros) {
        double nuevoTotal = litrosUtilizados + litros;
        if (!Double.isFinite(litros) || litros < 0 || !Double.isFinite(nuevoTotal)) {
            throw new IllegalArgumentException("Los litros y su total deben ser finitos y no negativos.");
        }
        litrosUtilizados = nuevoTotal;
    }

    @Override
    public void mostrarConsumo() {
        System.out.println(getCodigo() + " - Consumo de automóvil: " + litrosUtilizados + " litros.");
    }
}
