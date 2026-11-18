package envios.modelo;

import envios.interfaces.ConsumoCombustible;

public class Motocicleta extends MedioTransporte implements ConsumoCombustible {
    private double litrosUtilizados;

    public Motocicleta(String codigo, String nombreResponsable, double capacidadMaximaKg) {
        super(codigo, nombreResponsable, capacidadMaximaKg);
    }

    @Override
    public void realizarEntrega() {
        System.out.println(getCodigo() + " - " + getNombreResponsable()
                + " recorre las calles en motocicleta para entregar el paquete.");
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
        System.out.println(getCodigo() + " - Consumo de motocicleta: " + litrosUtilizados + " litros.");
    }
}
