# jdownloader-themes

Colección de temas [FlatLaf](https://www.formdev.com/flatlaf/) para **JDownloader 2**.

Cada tema vive en su propia rama. Las ramas sin sufijo son las entregas estables;
las ramas `-alpha` conservan iteraciones históricas o experimentales.

## Temas estables

| Tema | Preview | Rama | Descarga |
|------|---------|------|----------|
| **Pastel98** | ![Pastel98](https://github.com/deviceargent/jdownloader-themes/raw/Pastel98/screenshots/Pastel98.png) | [`Pastel98`](https://github.com/deviceargent/jdownloader-themes/tree/Pastel98) | [FlatPastel98.jar](https://github.com/deviceargent/jdownloader-themes/raw/Pastel98/FlatPastel98.jar) |
| **Zune** | próximamente | [`Zune`](https://github.com/deviceargent/jdownloader-themes/tree/Zune) | [FlatZune.jar](https://github.com/deviceargent/jdownloader-themes/raw/Zune/FlatZune.jar) |
| **SunsetTape** | próximamente | [`SunsetTape`](https://github.com/deviceargent/jdownloader-themes/tree/SunsetTape) | [FlatSunsetTape.jar](https://github.com/deviceargent/jdownloader-themes/raw/SunsetTape/FlatSunsetTape.jar) |
| **PixelFizz** | próximamente | [`PixelFizz`](https://github.com/deviceargent/jdownloader-themes/tree/PixelFizz) | [FlatPixelFizz.jar](https://github.com/deviceargent/jdownloader-themes/raw/PixelFizz/FlatPixelFizz.jar) |
| **VaporGrid** | próximamente | [`VaporGrid`](https://github.com/deviceargent/jdownloader-themes/tree/VaporGrid) | [FlatVaporGrid.jar](https://github.com/deviceargent/jdownloader-themes/raw/VaporGrid/FlatVaporGrid.jar) |
| **Airlock** | próximamente | [`Airlock`](https://github.com/deviceargent/jdownloader-themes/tree/Airlock) | [FlatAirlock.jar](https://github.com/deviceargent/jdownloader-themes/raw/Airlock/FlatAirlock.jar) |

## Detalles por tema

<details>
<summary><b>Pastel98</b> — Paleta pastel colorida estilo Windows 98</summary>

- **Estilo:** Light, colores pastel vibrantes
- **Fuente:** Sistema
- **Rama:** [`Pastel98`](https://github.com/deviceargent/jdownloader-themes/tree/Pastel98)
- **Preview:**
  ![Pastel98](https://github.com/deviceargent/jdownloader-themes/raw/Pastel98/screenshots/Pastel98.png)
  ![Pastel98-2](https://github.com/deviceargent/jdownloader-themes/raw/Pastel98/screenshots/Pastel98-2.png)
- **Instalación:** Copiar `FlatPastel98.jar` a `libs\laf\`

</details>

<details>
<summary><b>Zune</b> — Interfaz oscura estilo dispositivo Zune</summary>

- **Estilo:** Dark, acentos naranjas, fuente Orbitron
- **Fuente:** Orbitron
- **Rama:** [`Zune`](https://github.com/deviceargent/jdownloader-themes/tree/Zune)
- **Instalación:**
  1. Copiar `FlatZune.jar` a `libs\laf\`
  2. Copiar `cfg/Zune.json` a `cfg\laf\`
  3. Configurar `customlookandfeelclass` como `com.github.deviceargent.zune.Zune`

</details>

<details>
<summary><b>SunsetTape</b> — Estética retrowave synthwave</summary>

- **Estilo:** Dark, gradientes neón, estilo 80s
- **Rama:** [`SunsetTape`](https://github.com/deviceargent/jdownloader-themes/tree/SunsetTape)
- **Instalación:** Copiar `FlatSunsetTape.jar` a `libs\laf\`

</details>

<details>
<summary><b>PixelFizz</b> — Inspirado en pixel art</summary>

- **Estilo:** Dark, acentos pixelados
- **Rama:** [`PixelFizz`](https://github.com/deviceargent/jdownloader-themes/tree/PixelFizz)
- **Instalación:** Copiar `FlatPixelFizz.jar` a `libs\laf\`

</details>

<details>
<summary><b>VaporGrid</b> — Estética vaporwave con grid</summary>

- **Estilo:** Dark, grid vaporwave, colores neón
- **Rama:** [`VaporGrid`](https://github.com/deviceargent/jdownloader-themes/tree/VaporGrid)
- **Instalación:** Copiar `FlatVaporGrid.jar` a `libs\laf\`

</details>

<details>
<summary><b>Airlock</b> — Consola industrial de cabina espacial</summary>

- **Estilo:** Dark, superficies de acero oscuro, cian eléctrico
- **Fuente:** Orbitron
- **Rama:** [`Airlock`](https://github.com/deviceargent/jdownloader-themes/tree/Airlock)
- **Instalación:**
  1. Copiar `FlatAirlock.jar` a `libs\laf\`
  2. Copiar `cfg/Airlock.json` a `cfg\laf\`
  3. Configurar `customlookandfeelclass` como `com.github.deviceargent.airlock.Airlock`

</details>

## Instalación general

1. Descargá el `.jar` del tema que prefieras
2. Copialo a `<carpeta de JDownloader 2>\libs\laf\`
3. Si el tema incluye `.json`, copialo a `cfg\laf\`
4. Reiniciá JDownloader
5. Elegí el tema en *Configuración → Interfaz*, o configurá `customlookandfeelclass`

## Archivado

| Tema | Rama | Notas |
|------|------|-------|
| **XP Royale** | [`XpRoyale-alpha`](https://github.com/deviceargent/jdownloader-themes/tree/XpRoyale-alpha) | No recomendado |

## Notas para desarrollo

- Cada tema oscuro requiere parche de `JDownloader.jar` para que las barras de progreso hereden color del tema (ver [Phosphor](https://github.com/deviceargent/Phosphor))
- La máquina de desarrollo tiene el jar siempre parcheado; validar contra jar limpio antes de publicar
