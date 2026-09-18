# jdownloader-themes

Coleccion de temas [FlatLaf](https://www.formdev.com/flatlaf/) para **JDownloader 2**.

## Instalacion rapida

```powershell
# Clonar el repo
git clone https://github.com/deviceargent/jdownloader-themes.git
cd jdownloader-themes

# Ejecutar instalador (menu interactivo)
.\installer\Install-Theme.ps1

# O instalar directamente
.\installer\Install-Theme.ps1 -Theme Zune -PatchProgress
```

## Temas

| Tema | Estilo | Custom UI | Preview |
|------|--------|-----------|---------|
| **Airlock** | Consola industrial, cian electrico | No | proximamente |
| **Pastel98** | Pastel colorido, Windows 98 | PanelUI, DesktopPaneUI | ![Pastel98](https://github.com/deviceargent/jdownloader-themes/raw/Pastel98/screenshots/Pastel98.png) |
| **SunsetTape** | Retrowave synthwave | RootPaneUI, TitlePane | proximamente |
| **PixelFizz** | Pixel art, aura neon | ButtonUI | proximamente |
| **VaporGrid** | Vaporwave con grid | PanelUI, DesktopPaneUI | proximamente |
| **Zune** | Dispositivo Zune, Orbitron | RootPaneUI, TitlePane | proximamente |

## Estructura del repo

```
jdownloader-themes/
├── installer/
│   ├── Install-Theme.ps1          ← instalador principal
│   ├── themes/                    ← jars + configs + clases compiladas
│   │   ├── Airlock/
│   │   ├── Pastel98/
│   │   ├── SunsetTape/
│   │   ├── PixelFizz/
│   │   ├── VaporGrid/
│   │   └── Zune/
│   └── patches/
│       └── ExtProgressColumn.class
├── src/                           ← fuentes de todos los temas
├── bitacora/
│   └── BITACORA.md                ← hallazgos tecnicos y learnings
└── README.md
```

## Que hace el instalador

1. Detecta la instalacion de JDownloader
2. Muestra menu de temas disponibles
3. Copia el jar del tema a `libs/laf/`
4. Copia el JSON de config a `cfg/laf/`
5. Inyecta clases custom en `JDownloader.jar` (si las necesita)
6. Aplica parche de progress bars (opcional)
7. Configura `customlookandfeelclass`

## Por que hay que inyectar clases?

JDownloader **no carga clases** de `libs/laf/*.jar`. Solo lee los JSON de colores.
Los themes que tienen custom UI (title pane, panel painting, button glow) necesitan
que sus clases esten dentro de `JDownloader.jar`.

Ver [Bitacora](bitacora/BITACORA.md) para el detalle completo.

## Ramas

| Rama | Contenido |
|------|-----------|
| `main` | Hub principal con instalador y fuentes |
| `Airlock` | Tema Airlock (historial de commits) |
| `Zune` | Tema Zune (historial de commits) |
| `Pastel98` | Tema Pastel98 (historial de commits) |
| `SunsetTape` | Tema SunsetTape (historial de commits) |
| `PixelFizz` | Tema PixelFizz (historial de commits) |
| `VaporGrid` | Tema VaporGrid (historial de commits) |
| `*-alpha` | Iteraciones historicas experimentales |

## Desarrollo

Ver [Bitacora](bitacora/BITACORA.md) para:
- Hallazgos tecnicos sobre el mecanismo de LAF de JD
- Dependencias de clases por tema
- Decisiones de diseno
- Log de sesiones
