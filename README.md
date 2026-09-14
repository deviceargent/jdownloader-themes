# jdownloader-themes

Colección de temas [FlatLaf](https://www.formdev.com/flatlaf/) para **JDownloader 2**.

Cada tema vive en su propia rama. Las ramas sin sufijo son las entregas estables;
las ramas `-alpha` y `-beta` conservan iteraciones históricas o experimentales.

## Temas

| Tema | Estado | Preview | Rama | Descarga |
|------|--------|---------|------|----------|
| **Pastel98** | Estable | ![Pastel98](https://github.com/deviceargent/jdownloader-themes/raw/Pastel98/screenshots/Pastel98.png) | [`Pastel98`](https://github.com/deviceargent/jdownloader-themes/tree/Pastel98) | [FlatPastel98.jar](https://github.com/deviceargent/jdownloader-themes/raw/Pastel98/FlatPastel98.jar) |
| **Zune** | Estable | próximamente | [`Zune`](https://github.com/deviceargent/jdownloader-themes/tree/Zune) | [FlatZune.jar](https://github.com/deviceargent/jdownloader-themes/raw/Zune/FlatZune.jar) |
| **SunsetTape** | Estable | próximamente | [`SunsetTape`](https://github.com/deviceargent/jdownloader-themes/tree/SunsetTape) | [FlatSunsetTape.jar](https://github.com/deviceargent/jdownloader-themes/raw/SunsetTape/FlatSunsetTape.jar) |
| **PixelFizz** | Estable | próximamente | [`PixelFizz`](https://github.com/deviceargent/jdownloader-themes/tree/PixelFizz) | [FlatPixelFizz.jar](https://github.com/deviceargent/jdownloader-themes/raw/PixelFizz/FlatPixelFizz.jar) |
| **VaporGrid** | Estable | próximamente | [`VaporGrid`](https://github.com/deviceargent/jdownloader-themes/tree/VaporGrid) | [FlatVaporGrid.jar](https://github.com/deviceargent/jdownloader-themes/raw/VaporGrid/FlatVaporGrid.jar) |

## Archivado

| Tema | Estado | Rama |
|------|--------|------|
| **XP Royale** | Archivado; no recomendado | [`XpRoyale-alpha`](https://github.com/deviceargent/jdownloader-themes/tree/XpRoyale-alpha) |

## Previews

### Pastel98

![Pastel98](https://github.com/deviceargent/jdownloader-themes/raw/Pastel98/screenshots/Pastel98.png)

![Pastel98](https://github.com/deviceargent/jdownloader-themes/raw/Pastel98/screenshots/Pastel98-2.png)

> GitHub no ofrece pestañas Markdown reales para contenido arbitrario. Para una
> portada compacta conviene usar esta tabla como selector y `<details>` para
> instrucciones largas; los navegadores no recargan la página al abrirlos.

## Checklist para temas nuevos

Al arrancar un tema, decidir **si requiere parche de `JDownloader.jar`**:

- JDownloader hardcodea el color del texto en la columna de progreso por luminancia
  del fondo (`ExtProgressColumn.getDefaultForeground()`): **fondo oscuro → texto
  blanco ilegible; fondo claro → texto negro legible**
- Tema **oscuro** → necesita parche del jar (ver `Install-Phosphor.ps1` en
  [Phosphor](https://github.com/deviceargent/Phosphor)) o aceptar el blanco
- Tema **claro** → sin parche, funciona de fábrica

> ⚠️ La máquina de desarrollo tiene el jar **siempre parcheado**: lo que vemos acá
  no es exactamente lo que ve un usuario limpio. Antes de publicar un tema,
  validar contra un jar sin parchear.

## Instalación rápida

1. Bajá el jar del tema y copialo a `<carpeta de JDownloader 2>\libs\laf\`
2. Copiá el `.json` del tema (si viene) a `cfg\laf\`
3. Reiniciá JDownloader y elegí el tema en *Configuración → Interfaz*,
   o apuntá `customlookandfeelclass` a la clase indicada en el README de cada tema

Cada rama tiene su propio README con instrucciones completas, paleta y detalles.
