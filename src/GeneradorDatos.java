
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Generador funcional de registros simulados.
 * No modifica colecciones compartidas.
 */
public class GeneradorDatos {

    private static final List<String> RUTAS =
            List.of("Ruta A", "Ruta B", "Ruta C", "Ruta D");

    private static final List<String> ESTACIONES =
            List.of("Central", "Norte", "Sur",
                    "Universidad", "Terminal");

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

        // Se programan 25 viajes por jornada.
        int diaAdicional = viaje / 25;
        int viajeDelDia = viaje % 25;

        // Franjas de manana, mediodia y tarde.
        int horaBase;
        int posicion;

        if (viajeDelDia < 8) {
            horaBase = 7;
            posicion = viajeDelDia;
        } else if (viajeDelDia < 16) {
            horaBase = 12;
            posicion = viajeDelDia - 8;
        } else {
            horaBase = 17;
            posicion = viajeDelDia - 16;
        }

        // Cada viaje inicia con al menos 15 minutos
        // de diferencia respecto al anterior.
        long minutos = posicion * 15L
                + (entrada ? 0 : 12);

        LocalDateTime fecha = LocalDate.of(2026, 10, 26)
                .plusDays(diaAdicional)
                .atTime(horaBase, 0)
                .plusMinutes(minutos)
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
