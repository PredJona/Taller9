package recetario.modelo;

/** Configuración no instanciable ni extensible del recetario académico. */
public final class ConfiguracionRecetario {
    public static final String NOMBRE = "Recetario académico";

    private ConfiguracionRecetario() { }

    // class Intento extends ConfiguracionRecetario {} // Error: una clase final no se extiende.
}
