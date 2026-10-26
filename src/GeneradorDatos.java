
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Genera registros simulados sin modificar datos compartidos.
 */
public class GeneradorDatos {

    private static final List<String> RUTAS =
            List.of("Ruta A", "Ruta B", "Ruta C", "Ruta D");

    private static final List<String> ESTACIONES = List.of(
            "Central", "Norte", "Sur",
            "Universidad", "Terminal"
    );

    private GeneradorDatos() {
    }

    public static List<RegistroTransporte> generar(int cantidad) {

        if (cantidad < 0) {
            throw new IllegalArgumentException(
                    "La cantidad no puede ser negativa"
            );
        }

        return IntStream.range(0, cantidad)
                .mapToObj(GeneradorDatos::crearRegistro)
                .toList();
    }

    private static RegistroTransporte crearRegistro(int i) {

        int usuario = i % 1000;
        int movimiento = i / 1000;
        int viaje = movimiento / 2;

        boolean entrada = movimiento % 2 == 0;

        int numeroViaje = viaje % 5;
        int diaAdicional = viaje / 5;

        LocalTime horaBase = switch (numeroViaje) {
            case 0 -> LocalTime.of(7, 0);
            case 1 -> LocalTime.of(7, 30);
            case 2 -> LocalTime.of(12, 0);
            case 3 -> LocalTime.of(17, 0);
            default -> LocalTime.of(17, 30);
        };

        LocalDateTime fecha = LocalDate.of(2026, 10, 26)
                .plusDays(diaAdicional)
                .atTime(horaBase)
                .plusMinutes(entrada ? 0 : 12)
                .plusSeconds(usuario % 60);

        return new RegistroTransporte(
                "Usuario" + (usuario + 1),
                RUTAS.get(usuario % RUTAS.size()),
                ESTACIONES.get(
                        (usuario + movimiento) % ESTACIONES.size()
                ),
                entrada ? "entrada" : "salida",
                fecha
        );
    }
}
