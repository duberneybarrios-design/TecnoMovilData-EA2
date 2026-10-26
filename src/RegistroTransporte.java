
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Modelo inmutable de un movimiento de transporte.
 */
public record RegistroTransporte(
        String idUsuario,
        String ruta,
        String estacion,
        String accion,
        LocalDateTime timestamp
) {
    public RegistroTransporte {
        Objects.requireNonNull(idUsuario, "Usuario obligatorio");
        Objects.requireNonNull(ruta, "Ruta obligatoria");
        Objects.requireNonNull(estacion, "Estacion obligatoria");
        Objects.requireNonNull(accion, "Accion obligatoria");
        Objects.requireNonNull(timestamp, "Fecha obligatoria");

        if (!accion.equals("entrada") && !accion.equals("salida")) {
            throw new IllegalArgumentException(
                    "La accion debe ser entrada o salida"
            );
        }
    }
}
