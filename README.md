# SunsetTape · alpha

Rama de desarrollo activo del tema **SunsetTape** para JDownloader 2.

## Concepto

Una etiqueta VHS de atardecer: papel crema, tinta azul manuscrita y bandas de
cinta en morado, magenta, naranja y amarillo alrededor de la titlebar.

## Paleta

| Uso | Color |
|-----|-------|
| Fondo crema | `#F3EFE3` |
| Morado | `#4E286C` |
| Magenta / rosa oscuro | `#C92F61` |
| Naranja | `#E56934` |
| Amarillo | `#F4B32A` |
| Tinta azul del título | `#1D4E89` |

## Compatibilidad

Es un tema claro basado en `FlatLightLaf`; no requiere parche de
`JDownloader.jar`.

## Instalación

1. Copiar `FlatSunsetTape.jar` a `libs\laf\`.
2. Copiar `cfg/SunsetTape.json` a `cfg\laf\`.
3. Configurar `customlookandfeelclass` como
   `com.github.deviceargent.sunsettape.SunsetTape`.
4. Reiniciar JDownloader 2.

## Compilar desde fuente

```bash
javac -cp flatlaf.jar -d bin src/com/github/deviceargent/sunsettape/*.java
cp src/com/github/deviceargent/sunsettape/SunsetTape.properties bin/com/github/deviceargent/sunsettape/
jar cf FlatSunsetTape.jar -C bin .
```
