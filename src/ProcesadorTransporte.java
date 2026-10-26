
import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Modulo de procesamiento funcional TecnoMovil Data.
 * Las operaciones no modifican los registros originales.
 */
public class ProcesadorTransporte {

    private ProcesadorTransporte() {
    }

    // Funcion de orden superior: recibe un predicado.
    public static List<RegistroTransporte> filtrar(
            List<RegistroTransporte> registros,
            Predicate<RegistroTransporte> condicion) {

        return registros.stream()
                .filter(condicion)
                .toList();
    }

    // Funcion de orden superior: recibe una funcion clasificadora.
    private static <K> Map<K, Long> contarPor(
            List<RegistroTransporte> registros,
            Function<RegistroTransporte, K> clasificador) {

        return registros.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.groupingBy(
                                clasificador,
                                Collectors.counting()
                        ),
                        Map::copyOf
                ));
    }

    /**
     * FUNCIONALIDAD 1.
     * Cuenta ingresos por estacion.
     */
    public static Map<String, Long> calcularAfluencia(
            List<RegistroTransporte> registros) {

        List<RegistroTransporte> entradas = filtrar(
                registros,
                r -> r.accion().equals("entrada")
        );

        return contarPor(
                entradas,
                RegistroTransporte::estacion
        );
    }

    /**
     * FUNCIONALIDAD 2.
     * Cuenta movimientos por hora.
     */
    public static Map<Integer, Long> calcularFlujoPorHora(
            List<RegistroTransporte> registros) {

        return contarPor(
                registros,
                r -> r.timestamp().getHour()
        );
    }

    /**
     * Identifica todas las horas que comparten el flujo maximo.
     */
    public static List<Integer> identificarHorasPico(
            List<RegistroTransporte> registros) {

        Map<Integer, Long> flujo =
                calcularFlujoPorHora(registros);

        long maximo = flujo.values().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0);

        if (flujo.isEmpty()) {
            return List.of();
        }

        return flujo.entrySet().stream()
                .filter(e -> e.getValue() == maximo)
                .map(Map.Entry::getKey)
                .sorted()
                .toList();
    }

    /**
     * FUNCIONALIDAD 3.
     * Cuenta entradas por ruta.
     */
    public static Map<String, Long> calcularRutasMasUtilizadas(
            List<RegistroTransporte> registros) {

        return contarPor(
                filtrar(registros,
                        r -> r.accion().equals("entrada")),
                RegistroTransporte::ruta
        );
    }

    /**
     * FUNCIONALIDAD 4.
     * Lista estaciones visitadas por cada usuario,
     * ordenadas cronologicamente.
     */
    public static Map<String, List<String>> patronesPorUsuario(
            List<RegistroTransporte> registros) {

        return registros.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.groupingBy(
                                RegistroTransporte::idUsuario,
                                Collectors.collectingAndThen(
                                        Collectors.toList(),
                                        lista -> lista.stream()
                                                .sorted(Comparator.comparing(
                                                        RegistroTransporte::timestamp
                                                ))
                                                .map(RegistroTransporte::estacion)
                                                .toList()
                                )
                        ),
                        Map::copyOf
                ));
    }

    /**
     * Obtiene duraciones entre entradas y salidas
     * consecutivas de cada usuario en una misma ruta.
     */
    private static Stream<Long> duracionesUsuario(
            List<RegistroTransporte> registros) {

        List<RegistroTransporte> ordenados =
                registros.stream()
                        .sorted(Comparator.comparing(
                                RegistroTransporte::timestamp
                        ))
                        .toList();

        return IntStream.range(0, ordenados.size() - 1)
                .mapToObj(i -> {
                    RegistroTransporte inicio = ordenados.get(i);
                    RegistroTransporte fin = ordenados.get(i + 1);

                    if (!inicio.accion().equals("entrada")
                            || !fin.accion().equals("salida")
                            || !inicio.ruta().equals(fin.ruta())
                            || inicio.estacion().equals(fin.estacion())) {
                        return 0L;
                    }

                    return Duration.between(
                            inicio.timestamp(),
                            fin.timestamp()
                    ).toMinutes();
                })
                .filter(minutos -> minutos > 0);
    }

    /**
     * FUNCIONALIDAD 5.
     * Calcula tiempo promedio entre estaciones, en minutos.
     */
    public static double calcularTiempoPromedio(
            List<RegistroTransporte> registros) {

        return registros.stream()
                .collect(Collectors.groupingBy(
                        RegistroTransporte::idUsuario
                ))
                .values()
                .stream()
                .flatMap(ProcesadorTransporte::duracionesUsuario)
                .mapToLong(Long::longValue)
                .average()
                .orElse(0.0);
    }

    /**
     * FUNCIONALIDAD 6.
     * Marca las rutas que superan un umbral simulado
     * de ingresos diarios.
     *
     * Es un indicador de demanda, no una medicion
     * de ocupacion simultanea de los buses.
     */
    public static Map<String, String> detectarSobrecarga(
            List<RegistroTransporte> registros,
            long umbral) {

        if (umbral < 0) {
            throw new IllegalArgumentException(
                    "El umbral no puede ser negativo"
            );
        }

        return calcularRutasMasUtilizadas(registros)
                .entrySet()
                .stream()
                .collect(Collectors.toUnmodifiableMap(
                        Map.Entry::getKey,
                        e -> e.getValue() > umbral
                                ? "CRITICA"
                                : "NORMAL"
                ));
    }
}
