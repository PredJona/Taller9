package recetario.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import recetario.especialidades.RecetaPostre;
import recetario.interfaces.ResponsableReceta;
import recetario.modelo.Chef;
import recetario.modelo.Persona;
import recetario.modelo.ConfiguracionRecetario;
import recetario.modelo.Detalle_Receta;
import recetario.modelo.Receta;
import recetario.modelo.RecetaPlatoPrincipal;
import recetario.modelo.Supervisor;

public class Main {
    private static final int CANTIDAD_INGREDIENTES = 3;

    private Receta recetaRegistrada;

    public static void main(String[] args) {
        Main aplicacion = new Main();
        aplicacion.ejecutar(new BufferedReader(new InputStreamReader(System.in)));
    }

    private void ejecutar(BufferedReader entrada) {
        while (true) {
            mostrarMenu();
            try {
                String linea = entrada.readLine();
                if (linea == null) {
                    finalizarPrograma();
                    return;
                }

                int opcion = Integer.parseInt(linea);
                switch (opcion) {
                    case 1 -> registrarReceta(entrada);
                    case 2 -> mostrarReceta();
                    case 3 -> calcularCostoTotal();
                    case 4 -> aumentarCostoIngredientes(entrada);
                    case 5 -> registrarRecetaEspecializada(entrada);
                    case 6 -> demostrarAccesos();
                    case 7 -> demostrarLaboratorio7();
                    case 8 -> {
                        finalizarPrograma();
                        return;
                    }
                    default -> System.out.println("Opción inválida. Seleccione una opción");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un número válido.");
            } catch (IOException e) {
                System.out.println("Error al leer los datos.");
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("---------------------------------");
        System.out.println("        SISTEMA DE RECETAS       ");
        System.out.println("---------------------------------");
        System.out.println("1. Registrar receta");
        System.out.println("2. Mostrar receta");
        System.out.println("3. Calcular costo total");
        System.out.println("4. Aumento del costo de ingredientes");
        System.out.println("5. Registrar receta especializada y responsable");
        System.out.println("6. Demostrar accesos y herencia");
        System.out.println("7. Gestión de personal y honorarios");
        System.out.println("8. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private void registrarReceta(BufferedReader entrada) throws IOException {
        System.out.print("Código: ");
        String codigo = leerLinea(entrada);
        System.out.print("Nombre de la receta: ");
        String nombre = leerLinea(entrada);
        System.out.print("Precio unitario: ");
        double precio = Double.parseDouble(leerLinea(entrada));
        System.out.print("Unidad de medida: ");
        String unidad = leerLinea(entrada);
        int cantidad = leerCantidadPositiva(entrada, "Ingrese cantidad de la receta: ");
        System.out.print("Cantidad de porciones: ");
        int porciones = Integer.parseInt(leerLinea(entrada));

        Receta nuevaReceta = new Receta(
                codigo, nombre, precio, unidad, cantidad, porciones);
        System.out.println("\n--- REGISTRO DE LOS 3 PRODUCTOS DE LA RECETA ---");
        Detalle_Receta[] nuevosIngredientes = leerIngredientes(entrada, "Producto ");
        nuevaReceta.asignarIngredientes(nuevosIngredientes);

        recetaRegistrada = nuevaReceta;
        System.out.println("¡Receta y productos registrados exitosamente!");
    }

    private void mostrarReceta() {
        if (!hayRegistroCompleto()) {
            mostrarRegistroPendiente();
            return;
        }

        recetaRegistrada.enviarDatos();
        System.out.println("\n=== DETALLE DE PRODUCTOS DE LA RECETA ===");
        Detalle_Receta[] ingredientes = recetaRegistrada.obtenerIngredientes();
        for (int i = 0; i < ingredientes.length; i++) {
            ingredientes[i].mostrarProductos();
        }
    }

    private void calcularCostoTotal() {
        if (!hayRegistroCompleto()) {
            mostrarRegistroPendiente();
            return;
        }

        System.out.printf("Costo total de la receta: B/.%.2f%n",
                recetaRegistrada.calcularCostoTotal());
    }

    private void aumentarCostoIngredientes(BufferedReader entrada) throws IOException {
        if (!hayRegistroCompleto()) {
            mostrarRegistroPendiente();
            return;
        }

        System.out.print("Ingrese porcentaje de aumento: ");
        double porcentaje = Double.parseDouble(leerLinea(entrada));
        Detalle_Receta[] ingredientes = recetaRegistrada.obtenerIngredientes();
        double[] nuevosPrecios =
                Detalle_Receta.calcularPreciosAumentados(ingredientes, porcentaje);

        for (int i = 0; i < nuevosPrecios.length; i++) {
            ingredientes[i].setPrecio(nuevosPrecios[i]);
        }

        System.out.println("Precios actualizados:");
        for (int i = 0; i < ingredientes.length; i++) {
            System.out.printf("Ingrediente %d: B/.%.2f%n", i + 1,
                    ingredientes[i].getPrecio());
        }
    }

    private void registrarRecetaEspecializada(BufferedReader entrada) throws IOException {
        System.out.print("Tipo (1 Postre, 2 Plato principal): ");
        int tipo = Integer.parseInt(leerLinea(entrada));
        if (tipo != 1 && tipo != 2) {
            System.out.println("Tipo inválido");
            return;
        }

        System.out.print("Código: ");
        String codigo = leerLinea(entrada);
        System.out.print("Nombre: ");
        String nombre = leerLinea(entrada);
        System.out.print("Precio unitario: ");
        double precio = Double.parseDouble(leerLinea(entrada));
        System.out.print("Unidad de medida: ");
        String unidad = leerLinea(entrada);
        System.out.print("Cantidad: ");
        int cantidad = Integer.parseInt(leerLinea(entrada));
        System.out.print("Porciones: ");
        int porciones = Integer.parseInt(leerLinea(entrada));

        Receta nuevaReceta = crearRecetaEspecializada(
                entrada, tipo, codigo, nombre, precio, unidad, cantidad, porciones);
        asignarResponsable(entrada, nuevaReceta);
        Detalle_Receta[] nuevosIngredientes = leerIngredientes(entrada, "Ingrediente ");
        nuevaReceta.asignarIngredientes(nuevosIngredientes);

        recetaRegistrada = nuevaReceta;
        System.out.println("Receta especializada registrada");
        mostrarResumenEspecializado(nuevaReceta);
    }

    private static Receta crearRecetaEspecializada(BufferedReader entrada, int tipo,
            String codigo, String nombre, double precio, String unidad,
            int cantidad, int porciones) throws IOException {
        if (tipo == 1) {
            System.out.print("¿Requiere refrigeración? (s/n): ");
            boolean refrigeracion = leerLinea(entrada).equalsIgnoreCase("s");
            return new RecetaPostre(codigo, nombre, precio, unidad,
                    cantidad, porciones, refrigeracion);
        }

        System.out.print("Acompañamiento: ");
        return new RecetaPlatoPrincipal(codigo, nombre, precio, unidad,
                cantidad, porciones, leerLinea(entrada));
    }

    private static void asignarResponsable(BufferedReader entrada, Receta receta)
            throws IOException {
        System.out.print("Responsable (1 Supervisor, 2 Chef): ");
        int rol = Integer.parseInt(leerLinea(entrada));
        System.out.print("Nombre responsable: ");
        String nombre = leerLinea(entrada);
        System.out.print("Correo: ");
        String correo = leerLinea(entrada);

        if (rol == 1) {
            System.out.print("Carrera: ");
            receta.setResponsable(new Supervisor(nombre, correo, leerLinea(entrada)));
        } else if (rol == 2) {
            System.out.print("Especialidad: ");
            receta.setResponsable(new Chef(nombre, correo, leerLinea(entrada)));
        } else {
            throw new IllegalArgumentException("Rol inválido");
        }
    }

    private static Detalle_Receta[] leerIngredientes(BufferedReader entrada,
            String etiqueta) throws IOException {
        Detalle_Receta[] ingredientes = new Detalle_Receta[CANTIDAD_INGREDIENTES];
        for (int i = 0; i < ingredientes.length; i++) {
            System.out.println(etiqueta + (i + 1) + ":");
            System.out.print("  Código del producto: ");
            String codigo = leerLinea(entrada);
            System.out.print("  Descripción: ");
            String descripcion = leerLinea(entrada);
            System.out.print("  Unidad de medida: ");
            String unidad = leerLinea(entrada);
            int cantidad = leerCantidadNoNegativa(entrada, "  Cantidad: ");
            double precio = leerPrecioNoNegativo(entrada, "  Precio: ");
            ingredientes[i] = new Detalle_Receta(
                    codigo, descripcion, unidad, cantidad, precio);
        }
        return ingredientes;
    }

    private static int leerCantidadPositiva(BufferedReader entrada, String mensaje)
            throws IOException {
        while (true) {
            System.out.print(mensaje);
            int cantidad = Integer.parseInt(leerLinea(entrada));
            if (cantidad > 0) {
                System.out.println("Cantidad aceptada");
                return cantidad;
            }
            System.out.println("La cantidad debe ser mayor que cero");
        }
    }

    private static int leerCantidadNoNegativa(BufferedReader entrada, String mensaje)
            throws IOException {
        while (true) {
            System.out.print(mensaje);
            int cantidad = Integer.parseInt(leerLinea(entrada));
            if (cantidad >= 0) {
                return cantidad;
            }
            System.out.println("La cantidad no puede ser negativa");
        }
    }

    private static double leerPrecioNoNegativo(BufferedReader entrada, String mensaje)
            throws IOException {
        while (true) {
            System.out.print(mensaje);
            double precio = Double.parseDouble(leerLinea(entrada));
            if (Double.isFinite(precio) && precio >= 0) {
                return precio;
            }
            System.out.println("El precio debe ser finito y no negativo");
        }
    }

    private static String leerLinea(BufferedReader entrada) throws IOException {
        String valor = entrada.readLine();
        if (valor == null) {
            throw new IOException("Fin de entrada");
        }
        return valor;
    }

    private boolean hayRegistroCompleto() {
        return recetaRegistrada != null && recetaRegistrada.tieneIngredientesCompletos();
    }

    private static void mostrarResumenEspecializado(Receta receta) {
        receta.enviarDatos();
        if (receta.getResponsable() instanceof Supervisor supervisor) {
            supervisor.mostrarCarrera();
        } else if (receta.getResponsable() instanceof Chef chef) {
            chef.mostrarEspecialidad();
        }
        if (receta instanceof RecetaPostre postre) {
            postre.mostrarConservacion();
        } else if (receta instanceof RecetaPlatoPrincipal plato) {
            plato.mostrarAcompanamiento();
        }
        System.out.printf("Costo heredado: B/.%.2f%n", receta.calcularCostoTotal());
        // Honorarios independientes del costo de los ingredientes.
        if (receta.getResponsable() != null) {
            Persona responsable = receta.getResponsable();
            responsable.mostrarDatos();
            System.out.printf("Honorarios (4 h): %s%.2f%n", Persona.MONEDA,
                    responsable.calcularHonorarios(4));
            System.out.printf("Honorarios (4 h, 10%% descuento): %s%.2f%n", Persona.MONEDA,
                    responsable.calcularHonorarios(4, 10));
        }
    }



    private static void demostrarLaboratorio7() {

        // Se conserva esta entrada del laboratorio anterior para ampliar la opción 7.
        Chef segundoChef = new Chef("Elena", "elena@ejemplo.com", 10.00, "Cocina general", 0);
        // Persona personaInvalida = new Persona("Ana", "ana@ejemplo.com");
        Chef chef = new Chef("Ana", "ana@ejemplo.com", "Repostería");
        Supervisor supervisor = new Supervisor("Luis", "luis@ejemplo.com",
                12.00, "Gestión hotelera", 15.00);

        System.out.println("\n========== PARTE A: CLASE ABSTRACTA ==========");
        chef.mostrarDatos();
        chef.realizarFuncion();
        supervisor.mostrarDatos();
        supervisor.realizarFuncion();

        System.out.println("\n========== PARTE B: INTERFAZ ==========");
        chef.asignarTarea("Preparar menú del evento");
        supervisor.asignarTarea("Verificar calidad de platos");
        chef.mostrarResponsabilidad();
        supervisor.mostrarResponsabilidad();

        System.out.println("\n========== PARTE C: POLIMORFISMO ==========");
        System.out.println("\n--- Polimorfismo mediante Persona ---");
        Persona[] personal = new Persona[3];
        personal[0] = chef;
        personal[1] = segundoChef;
        personal[2] = supervisor;
        for (Persona persona : personal) {
            persona.mostrarDatos();
            persona.realizarFuncion();
        }

        System.out.println("\n--- Polimorfismo mediante ResponsableReceta ---");
        ResponsableReceta[] responsables = new ResponsableReceta[2];
        responsables[0] = chef;
        responsables[1] = supervisor;
        for (ResponsableReceta responsable : responsables) {
            responsable.mostrarResponsabilidad();
        }
        demostrarResponsablesDeRecetas(chef, supervisor);
    }

    /** Conserva la demostración de honorarios separada del costo de ingredientes. */
    private static void demostrarResponsablesDeRecetas(Chef chef, Supervisor supervisor) {
        System.out.println("\n--- Responsables de las recetas ---");
        Receta recetaChef = new Receta("L8-1", "Postre de demostración", 5, "unidad", 1, 4);
        Receta recetaSupervisor = new Receta("L8-2", "Plato de demostración", 8, "unidad", 1, 4);
        recetaChef.setResponsable(chef);
        recetaSupervisor.setResponsable(supervisor);
        // Ingredientes ficticios de demostración: el algoritmo original permanece intacto.
        recetaChef.asignarIngredientes(new Detalle_Receta[] {
                new Detalle_Receta("A", "Harina", "kg", 2, 3.00),
                new Detalle_Receta("B", "Azúcar", "kg", 1, 2.00),
                new Detalle_Receta("C", "Leche", "l", 1, 1.50)
        });
        recetaSupervisor.asignarIngredientes(new Detalle_Receta[] {
                new Detalle_Receta("D", "Arroz", "kg", 2, 2.00),
                new Detalle_Receta("E", "Verduras", "kg", 1, 3.00),
                new Detalle_Receta("F", "Aceite", "l", 1, 4.00)
        });
        for (Receta receta : new Receta[] { recetaChef, recetaSupervisor }) {
            // La referencia Persona ejecuta el método sobrescrito del objeto real.
            Persona responsable = receta.getResponsable();
            System.out.println("--- " + receta.getNombre() + " ---");
            receta.enviarDatos();
            System.out.println("Registro: " + responsable.obtenerIdentificacion());
            System.out.printf("Honorarios 4 horas: %s%.2f%n", Persona.MONEDA,
                    responsable.calcularHonorarios(4));
            System.out.printf("Honorarios 4 horas, 10%% descuento: %s%.2f%n", Persona.MONEDA,
                    responsable.calcularHonorarios(4, 10));
            System.out.printf("Costo de ingredientes (separado): %s%.2f%n", Persona.MONEDA,
                    receta.calcularCostoTotal());
        }
        System.out.println("Total personas: " + Persona.getTotalPersonas());
        System.out.println("Tipos estáticos: " + Persona.describirTipo() + ", " + Chef.describirTipo());
        // Persona.MONEDA = "USD"; // Error: constante final.
        // class OtraConfiguracion extends ConfiguracionRecetario {} // Error: clase final.
    }

    private static void demostrarAccesos() {
        RecetaPostre ejemplo = new RecetaPostre(
                "D1", "Flan", 2, "unidad", 1, 4, true);
        ejemplo.probarAccesos();
        System.out.println("Desde Main, getNombre(): " + ejemplo.getNombre());
        // ejemplo.nombreReceta: private en Receta; no compila.
        // ejemplo.porciones: protected en otro paquete sin herencia; no compila.
        // ejemplo.categoria: acceso de paquete desde recetario.modelo; no compila.
    }

    private static void mostrarRegistroPendiente() {
        System.out.println("Primero debe registrar la receta y sus ingredientes");
    }

    private static void finalizarPrograma() {
        System.out.println("Gracias por utilizar el Sistema de Recetas");
        System.out.println("Programa finalizado");
    }
}
