package envios.app;

import envios.interfaces.ConsumoCombustible;
import envios.modelo.Automovil;
import envios.modelo.Bicicleta;
import envios.modelo.MedioTransporte;
import envios.modelo.Motocicleta;

public class Main {
    public static void main(String[] args) {
        Motocicleta motocicleta = new Motocicleta("MOTO-001", "Ana", 25);
        Automovil automovil = new Automovil("AUTO-001", "Luis", 200);
        Bicicleta bicicleta = new Bicicleta("BICI-001", "Elena", 10);

        System.out.println("=== Envíos Express Panamá ===");
        System.out.println("--- Registro de entregas ---");
        motocicleta.registrarEntrega("El Cangrejo");
        automovil.registrarEntrega("Costa del Este", 80);
        bicicleta.registrarEntrega("Casco Antiguo", 5);

        // Cada vehículo realiza la entrega a su manera.
        MedioTransporte[] transportes = {motocicleta, automovil, bicicleta};
        System.out.println("--- Realización de entregas ---");
        for (MedioTransporte transporte : transportes) {
            transporte.realizarEntrega();
        }

        // Solo la moto y el automóvil llevan un registro de combustible.
        ConsumoCombustible[] transportesConCombustible = {motocicleta, automovil};
        motocicleta.registrarConsumo(1.5);
        automovil.registrarConsumo(4);
        System.out.println("--- Combustible utilizado ---");
        for (ConsumoCombustible transporte : transportesConCombustible) {
            transporte.mostrarConsumo();
        }
        System.out.println("Total de transportes: " + MedioTransporte.getTotalTransportes());
    }
}
