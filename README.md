# PixelFizz · alpha

Rama de desarrollo activo del tema **PixelFizz** (8-bit / colores ácidos / 80s).

> Nada de esta rama está garantizado. Ni siquiera este README. 🧪

## Paleta actual

Noche vino-violeta profundo + chicle rosa + verde calmado, sobre FlatDarkLaf:

| Uso | Color |
|-----|-------|
| Fondo base | `#26101f` |
| Superficie clara | `#1c0b17` |
| Texto principal | `#f2dceb` |
| Acento / chicle | `#ff7ac8` |
| Verde secundario | `#3cc271` |
| Selección | `#ff7ac8` + texto `#24091c` |

Además arrancó el experimento de **aura neón** en botones vía
`PixelFizzButtonUI` (halo leve en foco/hover sobre el borde del botón).

## Compatibilidad

**Requiere el mismo parche que los temas oscuros (ver Phosphor).**

JDownloader tiene un hardcode en `org.appwork.swing.exttable.columns.ExtProgressColumn`
(`getDefaultForeground()`) que pinta **blanco fijo** el texto de las barras de progreso
de las **filas hijas** (los links de audio/subs/thumbnail dentro de un paquete) cuando el
fondo es oscuro. Ninguna key del tema lo puede cambiar; hay que parchear `JDownloader.jar`
para que devuelva `null` y deje heredar el color del tema.

Referencia:
- `github.com/deviceargent/Phosphor` → `Install-Phosphor.ps1` (parche bytecode idempotente).
- Lo mismo se aplica a este tema oscuro (y a todos los oscuros).

## Instalación

1. `FlatPixelFizz.jar` → `libs\laf\`
2. `cfg/PixelFizz.json` → `cfg\laf\`
3. `customlookandfeelclass` → `com.github.deviceargent.pixelfizz.PixelFizz`

## Compilar desde fuente

```bash
javac -cp flatlaf.jar -d bin src/com/github/deviceargent/pixelfizz/*.java
cp src/com/github/deviceargent/pixelfizz/*.properties bin/com/github/deviceargent/pixelfizz/
jar cf FlatPixelFizz.jar -C bin .
```