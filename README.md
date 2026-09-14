# VaporGrid

Tema final **VaporGrid** (retrowave / synthwave).

## Concepto

Noche violeta con neón: la estética de las grillas de sol sintético de los 80s.
Fondo violeta profundo, cian eléctrico como color de texto/detalle, fucsia rosa
como acento caliente. Y la firma del tema: una **grilla diagonal fucsia** pintada
proceduralmente (TexturePaint, sin assets) sobre paneles y desktop, como el
"camino" retrowave clásico.

## Paleta actual

| Uso | Color |
|-----|-------|
| Fondo base | `#150830` |
| Superficie clara | `#190b38` |
| Texto principal | `#e8ddff` |
| Cian neón (texto/detalle) | `#00fff9` |
| Fucsia rosa (acento, tab, grilla) | `#ff3c98` |
| Oro sunset (warnings) | `#f9c80e` |

La grilla diagonal: `#ff3c98` al ~16% de alpha, tile de 48px, líneas cada 12px.

## Compatibilidad

**Requiere el mismo parche que los temas oscuros (ver Phosphor).**

JDownloader tiene un hardcode en `org.appwork.swing.exttable.columns.ExtProgressColumn`
(`getDefaultForeground()`) que pinta **blanco fijo** el texto de las barras de progreso
de las **filas hijas** cuando el fondo es oscuro. Ninguna key del tema lo puede cambiar;
hay que parchear `JDownloader.jar` para que devuelva `null` y herede el color del tema.

Referencia: `github.com/deviceargent/Phosphor` → `Install-Phosphor.ps1` (idempotente).

## Instalación

1. `FlatVaporGrid.jar` → `libs\laf\`
2. `cfg/VaporGrid.json` → `cfg\laf\`
3. `customlookandfeelclass` → `com.github.deviceargent.vaporgrid.VaporGrid`

## Compilar desde fuente

```bash
javac -cp flatlaf.jar -d bin src/com/github/deviceargent/vaporgrid/*.java
cp src/com/github/deviceargent/vaporgrid/*.properties bin/com/github/deviceargent/vaporgrid/
jar cf FlatVaporGrid.jar -C bin .
```
