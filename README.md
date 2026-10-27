
# TecnoMovil Data

## EA2 - Programación Orientada a Objetos II

**Institución:** Institución Universitaria Digital de Antioquia  
**Proyecto:** TecnoMovil Data  
**Lenguaje:** Java

## Descripción

TecnoMovil Data es un sistema académico desarrollado en Java para generar y procesar datos simulados del transporte urbano.

El proyecto aplica conceptos de programación funcional, expresiones lambda, Streams y procesamiento de colecciones.

## Objetivo

Analizar información simulada del transporte urbano mediante operaciones de procesamiento de datos que permitan identificar patrones de movilidad y condiciones de funcionamiento del sistema.

## Funcionalidades

1. Análisis de afluencia por estación.
2. Identificación de horas pico.
3. Identificación de rutas más utilizadas.
4. Análisis de patrones de viaje por usuario.
5. Cálculo del tiempo promedio entre estaciones.
6. Detección de sobrecarga simulada en rutas.

## Estructura del proyecto

```text
TecnoMovilData/
├── src/
│   ├── Main.java
│   ├── GeneradorDatos.java
│   ├── RegistroTransporte.java
│   └── ProcesadorTransporte.java
├── README.md
└── .gitignore
```

## Tecnologías y conceptos

- Java.
- Programación orientada a objetos.
- Programación funcional.
- Streams y expresiones lambda.
- Procesamiento de colecciones.
- Operaciones de análisis de datos.

## Compilación

Desde la carpeta principal del proyecto, ejecutar:

```powershell
cd src
javac GeneradorDatos.java RegistroTransporte.java ProcesadorTransporte.java Main.java
```

## Ejecución

Desde la carpeta `src`, ejecutar:

```powershell
java Main
```

## Pruebas realizadas

**Prueba 1: 10.000 registros**

El sistema ejecutó las seis funcionalidades y finalizó correctamente.

**Prueba 2: 100.000 registros**

El sistema generó y procesó 100.000 registros simulados y completó las seis funcionalidades sin presentar errores durante la ejecución.

## Resultados de la prueba de 100.000 registros

- Registros generados: 100.000.
- Horas pico identificadas: 07:00 y 17:00.
- Usuarios analizados en patrones de viaje: 1.000.
- Tiempo promedio entre estaciones: 12 minutos.
- Rutas clasificadas con sobrecarga simulada: A, B, C y D.

## Conclusiones

El proyecto permitió implementar funcionalidades de procesamiento de datos simulados de transporte urbano mediante Java.

Las pruebas con 10.000 y 100.000 registros finalizaron correctamente, demostrando la ejecución de las funcionalidades implementadas con diferentes volúmenes de información.

Los resultados corresponden a datos simulados con fines académicos.
