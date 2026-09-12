# jdownloader-themes

Colección de temas [FlatLaf](https://www.formdev.com/flatlaf/) para **JDownloader 2**.

Cada tema vive en su propia rama. Los temas estables incluyen el jar ya compilado
en la rama y builds automáticos por CI. Los temas en desarrollo tienen su rama de
pre-releases (`<tema>-alpha`, `-beta`, ...) donde ocurre la magia antes de llegar a estable.

## Temas

| Tema | Estado | Preview | Rama | Descarga |
|------|--------|---------|------|----------|
| **Pastel98** | Estable | ![Pastel98](https://github.com/deviceargent/jdownloader-themes/raw/Pastel98/screenshots/Pastel98.png) | [`Pastel98`](https://github.com/deviceargent/jdownloader-themes/tree/Pastel98) | [FlatPastel98.jar](https://github.com/deviceargent/jdownloader-themes/raw/Pastel98/FlatPastel98.jar) |
| **PixelFizz** | En desarrollo (8-bit / colores ácidos / 80s) | próximamente | [`PixelFizz`](https://github.com/deviceargent/jdownloader-themes/tree/PixelFizz) · [`PixelFizz-alpha`](https://github.com/deviceargent/jdownloader-themes/tree/PixelFizz-alpha) | — |
| **VaporGrid** | En desarrollo (retrowave / synthwave) | próximamente | [`VaporGrid-alpha`](https://github.com/deviceargent/jdownloader-themes/tree/VaporGrid-alpha) | [FlatVaporGrid.jar](https://github.com/deviceargent/jdownloader-themes/raw/VaporGrid-alpha/FlatVaporGrid.jar) |

## Previews

### Pastel98

![Pastel98](https://github.com/deviceargent/jdownloader-themes/raw/Pastel98/screenshots/Pastel98.png)

![Pastel98](https://github.com/deviceargent/jdownloader-themes/raw/Pastel98/screenshots/Pastel98-2.png)

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