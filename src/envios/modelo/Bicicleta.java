package envios.modelo;

/** Medio de entrega que aprovecha los datos comunes y se desplaza mediante pedaleo. */
public class Bicicleta extends MedioTransporte {
    /**
     * Inicializa mediante super los datos y el registro compartido del transporte.
     * @param codigo identificador distinto dentro de la flota
     * @param nombreResponsable nombre de quien realiza las entregas
     * @param capacidadMaximaKg capacidad finita y positiva en kilogramos
     * @throws IllegalArgumentException si los datos comunes son inválidos
     */
    public Bicicleta(String codigo, String nombreResponsable, double capacidadMaximaKg) {
        super(codigo, nombreResponsable, capacidadMaximaKg);
    }

    /** Aporta la entrega mediante pedaleo al recorrido polimórfico. */
    @Override
    public void realizarEntrega() {
        System.out.println(getCodigo() + " - " + getNombreResponsable()
                + " pedalea en bicicleta para entregar el paquete.");
    }
}
