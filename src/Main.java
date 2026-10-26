
import java.util.List;
import java.util.Map;

/**
 * Programa principal de TecnoMovil Data.
 * Ejecuta las seis funcionalidades del caso de estudio.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("           TECNOMOVIL DATA");
        System.out.println("    Sistema de Transporte Urbano");
        System.out.println("======================================");

        // Generacion de registros simulados.
        List<RegistroTransporte> registros =
                GeneradorDatos.generar(10000);

        System.out.println("\nRegistros generados: "
                + registros.size());

        // FUNCIONALIDAD 1
        System.out.println("\n1. AFLUENCIA POR ESTACION");
        System.out.println("-----------------------------");

        Map<String, Long> afluencia =
                ProcesadorTransporte.calcularAfluencia(registros);

        afluencia.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.println(
                        e.getKey() + ": " + e.getValue() + " ingresos"
                ));

        // FUNCIONALIDAD 2
        System.out.println("\n2. IDENTIFICACION DE HORAS PICO");
        System.out.println("--------------------------------");

        Map<Integer, Long> flujo =
                ProcesadorTransporte.calcularFlujoPorHora(registros);

        flujo.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.printf(
                        "Hora %02d:00 - %d movimientos%n",
                        e.getKey(), e.getValue()
                ));

        System.out.println("Horas pico: "
                + ProcesadorTransporte.identificarHorasPico(registros));

        // FUNCIONALIDAD 3
        System.out.println("\n3. RUTAS MAS UTILIZADAS");
        System.out.println("---------------------------");

        Map<String, Long> rutas =
                ProcesadorTransporte.calcularRutasMasUtilizadas(registros);

        rutas.entrySet().stream()
                .sorted(
                        Map.Entry.<String, Long>comparingByValue()
                                .reversed()
                                .thenComparing(Map.Entry.comparingByKey())
                )
                .forEach(e -> System.out.println(
                        e.getKey() + ": " + e.getValue() + " ingresos"
                ));

        // FUNCIONALIDAD 4
        System.out.println("\n4. PATRONES DE VIAJE POR USUARIO");
        System.out.println("----------------------------------");

        Map<String, List<String>> patrones =
                ProcesadorTransporte.patronesPorUsuario(registros);

        System.out.println("Usuarios analizados: "
                + patrones.size());

        // Se muestran 5 usuarios para evitar saturar la consola.
        patrones.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .limit(5)
                .forEach(e -> System.out.println(
                        e.getKey() + ": " + e.getValue()
                ));

        // FUNCIONALIDAD 5
        System.out.println("\n5. TIEMPO PROMEDIO ENTRE ESTACIONES");
        System.out.println("-------------------------------------");

        double promedio =
                ProcesadorTransporte.calcularTiempoPromedio(registros);

        System.out.printf(
                "Tiempo promedio: %.2f minutos%n",
                promedio
        );

        // FUNCIONALIDAD 6
        System.out.println("\n6. DETECCION DE SOBRECARGA EN RUTAS");
        System.out.println("-------------------------------------");

        long umbral = 1200;

        System.out.println("Umbral simulado: "
                + umbral + " ingresos por ruta");

        Map<String, String> estados =
                ProcesadorTransporte.detectarSobrecarga(
                        registros, umbral
                );

        estados.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.println(
                        e.getKey() + ": " + e.getValue()
                ));

        System.out.println("\n======================================");
        System.out.println("     PROCESAMIENTO FINALIZADO");
        System.out.println("======================================");
    }
}
