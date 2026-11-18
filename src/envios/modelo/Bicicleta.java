package envios.modelo;

public class Bicicleta extends MedioTransporte {
    public Bicicleta(String codigo, String nombreResponsable, double capacidadMaximaKg) {
        super(codigo, nombreResponsable, capacidadMaximaKg);
    }

    @Override
    public void realizarEntrega() {
        System.out.println(getCodigo() + " - " + getNombreResponsable()
                + " pedalea en bicicleta para entregar el paquete.");
    }
}
