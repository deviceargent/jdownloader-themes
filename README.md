# Airlock

Tema industrial para JDownloader 2, inspirado en paneles de carga y compuertas
de una nave espacial.

## Dirección visual

Base negra, superficies de acero oscuro, naranja de advertencia y cian técnico.
La primera instancia prioriza una base estable y opaca: la geometría diagonal,
remaches y placas superpuestas quedan como siguiente capa visual.

## Paleta

| Uso | Color |
|-----|-------|
| Fondo | `#080B0E` |
| Superficie | `#10161B` |
| Superficie elevada | `#19232A` |
| Texto | `#D7E0E5` |
| Advertencia | `#F0782B` |
| Cian técnico | `#42D9E8` |

## Instalación

1. Copiar `FlatAirlock.jar` a `libs\laf\`.
2. Copiar `cfg/Airlock.json` a `cfg\laf\`.
3. Configurar `customlookandfeelclass` como
   `com.github.deviceargent.airlock.Airlock`.
4. Reiniciar JDownloader 2.

## Compilar desde fuente

```bash
javac -cp flatlaf.jar -d bin src/com/github/deviceargent/airlock/*.java
cp src/com/github/deviceargent/airlock/Airlock.properties bin/com/github/deviceargent/airlock/
jar cf FlatAirlock.jar -C bin .
```
