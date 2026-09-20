<!--
  AGENTS: Esta bitácora es EXCLUSIVAMENTE para jdownloader-themes.
  Para anotaciones de propósito general, usar:
  https://github.com/deviceargent/bitacora (privado, NUNCA publicar).
  Esa otra bitácora debería contener el aviso inverso apuntando a este repo.
-->

# Bitacora — jdownloader-themes

Registro de hallazgos tecnicos, decisiones de diseno y learnings del desarrollo
de temas FlatLaf para JDownloader 2.

---

## Hallazgos criticos

### JDownloader no carga clases custom de `libs/laf/`

**Fecha:** 2026-09-17
**Severidad:** Critica

JDownloader **no carga archivos `.class`** desde `libs/laf/*.jar`. Solo lee
archivos JSON de `cfg/laf/` para colores y la clase LAF desde
`customlookandfeelclass`.

**Implicaciones:**
- Los `.properties` de FlatLaf SIE se cargan (colores, fonts)
- Las clases custom (RootPaneUI, PanelUI, ButtonUI, etc.) NO se cargan
- Si una `.properties` referencia una clase custom, JD la ignora silenciosamente

**Solucion:** Inyectar clases directamente en `JDownloader.jar`:
```bash
jar uf JDownloader.jar -C bin com/github/deviceargent/<tema>/
```

**Por que funciona:** `libs/laf/` esta en el classpath de FlatLaf para
cargar properties, pero JD usa su propio classloader para instanciar
UI delegates, y ese classloader no incluye los jars de temas.

---

### JD se auto-actualiza y pierde parches

**Fecha:** 2026-09-17
**Severidad:** Alta

JDownloader se auto-actualiza y sobreescribe `JDownloader.jar`. Los parches
aplicados (como `ExtProgressColumn`) se pierden.

**Deteccion:** El jar actualizado (16/9, 5.2MB) era mas chico que el parcheado
(11/9, 5.3MB).

**Solucion:** Reaplicar parches despues de cada actualizacion de JD. El
instalador incluye opcion `-PatchProgress` para esto.

---

### `lookandfeeltheme` no afecta la carga del custom LAF

**Fecha:** 2026-09-17
**Severidad:** Media

El setting `lookandfeeltheme: FLATLAF_DARK` en la config de JD no interfiere
con `customlookandfeelclass`. Ambos pueden coexistir. Remover
`lookandfeeltheme` no cambia el comportamiento.

---

## Arquitectura de temas

### Dependencia de clases por tema

| Tema | Clases custom | Efecto visual | Necesita inyeccion |
|------|--------------|---------------|-------------------|
| Airlock | Ninguna | Colores y fonts via properties | **NO** |
| Pastel98 | PanelUI, DesktopPaneUI | Textura dithered (Bayer matrix), scanlines | SI |
| SunsetTape | RootPaneUI, TitlePane | Title pane VHS con franjas diagonales | SI |
| PixelFizz | ButtonUI | Aura neon concentrica en botones | SI |
| VaporGrid | PanelUI, DesktopPaneUI | Grid retrowave diagonal | SI |
| Zune | RootPaneUI, TitlePane | Title pane con gradiente Aero | SI |

### Estructura de un tema

```
com.github.deviceargent.<tema>/
├── <Tema>.java              ← Clase LAF (extiende FlatDarkLaf o FlatLightLaf)
├── <Tema>.properties        ← Propiedades FlatLaf (colores, fonts, UI delegates)
├── <Tema>RootPaneUI.java    ← (opcional) Custom title pane
├── <Tema>TitlePane.java     ← (opcional) Custom title pane painting
├── <Tema>PanelUI.java       ← (opcional) Custom panel painting
└── <Tema>ButtonUI.java      ← (opcional) Custom button painting
```

### FlatLaf properties vs JD config

**FlatLaf properties** (`.properties` en el jar):
- Se cargan automaticamente por nombre de clase
- Definen colores, fonts, y UI delegates
- Funcionan en cualquier app que use FlatLaf

**JD config** (`.json` en `cfg/laf/`):
- Solo JD-specific keys: `colorfor*`, `configlabel*`, etc.
- Se aplican DESPUES de las properties de FlatLaf
- Pueden overridear keys de FlatLaf

---

## ExtProgressColumn patch

### Problema

JDownloader hardcodea el color del texto y las barras en la columna de progreso
por luminancia del fondo (`ExtProgressColumn.getDefaultForeground()`):
- **Fondo oscuro** → texto/barras blanco
- **Fondo claro** → texto/barras negro

Esto ignora por completo el LookAndFeel activo. Las barras de progreso en
child rows quedan negras en temas claros (Pastel98) o blancas en temas oscuros.

### Mecanismo del parche

El metodo `ExtProgressColumn.getDefaultForeground()` tiene este bytecode:

```
aload_0
invokevirtual getDefaultBackground
invokestatic getContrastBWColor    ← calcula negro/blanco por luminancia
areturn
```

El parche reemplaza esto con:

```
aconst_null     ← devuelve null
areturn
nop x6          ← relleno
```

Al devolver `null`, la columna hereda el color del tema via
`UIDefaults.get("Table.foreground")` o el JSON config
(`colorforprogressbarforeground*`).

### Patron de busqueda (hex)

```
2A B6 ?? ?? B8 ?? ?? B0
```

Donde:
- `2A` = `aload_0`
- `B6` = `invokevirtual`
- `B8` = `invokestatic`
- `B0` = `areturn`

### Implementacion (PowerShell)

```powershell
Add-Type -AssemblyName System.IO.Compression.FileSystem
$zip = [System.IO.Compression.ZipFile]::Open($jarPath, "Update")
# ... buscar patron 2A B6 ?? ?? B8 ?? ?? B0 ...
# ... reemplazar con 01 B0 00 00 00 00 00 00 ...
```

Ver `Install-Phosphor.ps1` en [Phosphor](https://github.com/deviceargent/Phosphor)
para la implementacion completa.

### Por que el parche pre-compilado no funciona

El `ExtProgressColumn.class` extraido de un backup viejo (version anterior de JD)
puede tener layout de bytecode diferente. El patron de busqueda no matchea y
el parche falla silenciosamente.

**Solucion correcta:** Aplicar el parche de bytecode directamente sobre el
`JDownloader.jar` actual, como hace Phosphor.

### Ubicacion

```
org/appwork/swing/exttable/columns/ExtProgressColumn.class
```

### Backup

Siempre guardar backup antes de parchear:
```
JDownloader.jar.bak-progress-YYYYMMDD-HHMMSS
```

### Colores del tema en JSON

El JSON config de cada tema define los colores de progress bars:

```json
{
    "colorforprogressbarforeground1": "#5fe88ab0",
    "colorforprogressbarforeground2": "#7fe88ab0",
    ...
}
```

Despues del parche, estas keys son las que controlan el color de las barras.

---

## Auto-actualizacion de JD

JDownloader descarga y aplica updates automaticamente. Esto:
1. Sobreescribe `JDownloader.jar` (pierde parches y clases inyectadas)
2. Puede cambiar el mecanismo de carga de LAFs
3. Resethea configs a valores por defecto

**Mitigation:** El instalador crea backups antes de cada cambio. Monitorear
`JDownloader.jar.bak-theme-*` para detectar actualizaciones.

---

## Cambio de tema: restaurar jar primero

**Fecha:** 2026-09-19
**Severidad:** Alta

Si un usuario instala tema A y luego tema B sin restaurar, las clases de A
quedan en `JDownloader.jar` junto con las de B. Esto causa conflictos
(ambas clases compiten por el mismo UI delegate).

**Solucion:** Antes de inyectar clases de un tema nuevo, restaurar el jar
desde el backup más limpio disponible:

1. `bak-prepatch-*` → más limpio (sin ningun parche)
2. `bak-theme-*` → más viejo (menos inyecciones acumuladas)
3. `bak-*` → cualquier backup

El instalador ahora ejecuta `Restore-JDJar` antes de `Inject-Classes`.

**Regla:** Un tema = un jar limpio + sus clases + su parche.

---

## `jar uf` falla en Windows con PowerShell

**Fecha:** 2026-09-19
**Severidad:** Media

El comando `jar uf` de JDK a veces falla en PowerShell con errores
inesperados (encoding, paths, exit code).

**Alternativa confiable:** `System.IO.Compression.ZipFile` de .NET:
```powershell
Add-Type -AssemblyName System.IO.Compression.FileSystem
$zip = [System.IO.Compression.ZipFile]::Open($jarPath, "Update")
$entry.Delete()
$newEntry = $zip.CreateEntry($entryName)
# ... escribir bytes ...
$zip.Dispose()
```

Esta API funciona directamente sobre el `.jar` (que es un ZIP) sin
depender de `jar.exe` del JDK.

---

## Decisiones de diseno

### Por que FlatDarkLaf como base

La mayoria de temas usan `FlatDarkLaf` como base porque:
- JD tiene beaucoup de widgets con fondos oscuros
- FlatDarkLaf ya maneja correctamente la legibilidad de texto
- Las properties de FlatLaf son un excellent punto de partida

Excepcion: **Pastel98** usa `FlatLightLaf` (tema claro).

### Por que Orbitron

Orbitron es una fuente tipografica sans-serif geometrica que evoca
interfaces tecnologicas/ciencia ficcion. Se usa en Zune y Airlock.

**Licencia:** SIL Open Font License (OFL) — libre para uso comercial.
**Descarga:** Google Fonts

### Grupo de botones de ventana (Zune)

Los botones de ventana (minimizar, maximizar, cerrar) se agrupan en un
panel con gradiente naranja. Esto requiere `ZuneTitlePane` que extiende
`FlatTitlePane` y pinta el gradiente sobre el `buttonPanel`.

---

## Herramientas del lab

### Utilizadas en el desarrollo

| Herramienta | Uso | Notas |
|-------------|-----|-------|
| **PowerShell** | Instalador, inyeccion de clases, parches de bytecode | API `ZipFile` mas confiable que `jar uf` en Windows |
| **git** | Control de versiones, ramas por tema | Cada tema tiene su rama historica |
| **Java JRE 21 (Temurin)** | Ejecucion de JDownloader para testing | `C:\JD2-Lab\JDownloader\jre\bin\java.exe` |
| **jar.exe** | Manipulacion de JDownloader.jar | Alternativa: `System.IO.Compression.ZipFile` |
| **gh CLI** | Gestion de repo en GitHub | Publicacion, visibilidad, uploads |

### Disponibles en el lab (no utilizadas en temas)

| Herramienta | Funcion | Por que no se uso |
|-------------|---------|-------------------|
| **Swag 1.2.6** | Generador de wrappers para Swing apps | No era necesario para desarrollo de LAF |
| **Swingspector 2.1.3** | Inspector de componentes Swing en runtime | Las herramientas de debug de FlatLaf fueron suficientes |
| **Scenic View** | Inspector de escena JavaFX | JD usa Swing, no JavaFX |

**Leccion:** Para desarrollo de temas FlatLaf, las herramientas esenciales
son: Java JRE, PowerShell, git, y el propio FlatLaf como referencia.
Las herramientas de inspeccion de Swing son utiles para debugging avanzado
pero no criticas para el flujo de trabajo de temas.

---

## Notas para futuras iteraciones

### Temas con geometria diagonal

Docks, airlocks, paneles industriales con placas superpuestas y remaches.
Requiere custom `paintComponent()` en PanelUI o DesktopPaneUI.

**Estado:** Conceptual, no implementado.

### Transparencia en Swing

Los experimentos con transparencia (glass pane, alpha en panels) causaron
artefactos de repaint en Swing. Los fondos opacos son mas estables.

**Leccion:** Evitar alpha en `colorforpanelbackground`. Siempre usar `#ff` prefix.

### GitHub y previews

GitHub no soporta tabs Markdown reales. Usar `<details>` para contenido
expandible. Los screenshots deben subirse a cada rama en `screenshots/`.

### Pintado programatico de iconos standard

Los iconos de JD (play, pause, stop, carpeta, etc.) usan colores hardcodeados.
Se pueden recolorear con el color de acento del tema mediante:

- `IconUI` o `IconEffect` de FlatLaf
- Clases custom que reemplazan `UIManager.getIcon()`
- Filtros de imagen via `BufferedImageOp`

**Estado:** Ya implementado en Phosphor. Pendiente replicar en otros temas.

---

## Repos de referencia

### Phosphor

`https://github.com/deviceargent/Phosphor` — Tema FlatLaf con progress bars
personalizadas. Contiene `Install-Phosphor.ps1` que es la referencia para:

- El parche de `ExtProgressColumn` (bytecode patching)
- La estructura del instalador
- El mecanismo de inyección de clases

Cualquier cambio en el instalador debe verificarse contra Phosphor para
mantener compatibilidad.

---

## Log de sesiones

### 2026-09-17 — Sesion de debugging Zune

**Objetivo:** Entender por que Zune no se veia correctamente.

**Descubrimientos:**
1. El `FlatZune.jar` en la rama estaba desactualizado
2. Habia archivos viejos en la rama (ZuneGlassPane, ZunePanelUI, ZuneViewportUI)
3. JD se auto-actualizo y pidio el parche de ExtProgressColumn
4. `lookandfeeltheme` no era el problema
5. **Descubrimiento final:** JD no carga clases de `libs/laf/*.jar`

**Resultado:** Zune funciona despues de inyectar clases en `JDownloader.jar`.

### 2026-09-17 — Instalador y reorganizacion

**Objetivo:** Crear instalador estandarizado y reorganizar el repo.

**Resultado:**
- Script `Install-Theme.ps1` con menu interactivo
- Estructura `installer/themes/<Tema>/` con jars, configs y clases compiladas
- Bitacora con todos los hallazgos

### 2026-09-17 — Parche ExtProgressColumn para Pastel98

**Objetivo:** Arreglar barras de progreso negras en child rows de Pastel98.

**Problema:** El parche pre-compilado (clase .class extraida de backup viejo)
no funcionaba con la version actual de JD. Las barras seguian negras.

**Descubrimiento:** El parche de Phosphor no reemplaza la clase completa —
modifica el bytecode in-situ buscando un patron especifico:

```
2A B6 ?? ?? B8 ?? ?? B0  (aload_0, invokevirtual, invokestatic, areturn)
```

Lo reemplaza con:

```
01 B0 00 00 00 00 00 00  (aconst_null, areturn, nop x6)
```

**Resultado:** Aplicado correctamente en offset 6262. Barras de progreso
ahora hereden el color del tema (pink/lilac para Pastel98).

**Leccion:** No usar clases pre-parcheadas de backups viejos. Siempre aplicar
el parche de bytecode directamente sobre el jar actual.

### 2026-09-19 — Instalador: cambio seguro de temas

**Objetivo:** Permitir cambiar de tema sin acumular clases anteriores.

**Problema:** El instalador original inyectaba clases sobre el jar actual.
Si el usuario ya tenia un tema instalado, las clases del anterior quedaban.

**Solucion:** Agregar funcion `Restore-JDJar` que restaura desde el backup
más limpio antes de inyectar. Flujo actual:

1. Backup del jar actual
2. Restore desde backup más limpio (prepatch > theme viejo > cualquier bak)
3. Inyectar clases del nuevo tema
4. Aplicar parche si se pide
5. Actualizar settings

**Leccion:** Un tema = un jar limpio. Nunca apilar temas.

### 2026-09-19 — Parche ExtProgressColumn con ZipFile

**Objetivo:** Aplicar parche de progress bars de forma confiable en Windows.

**Problema:** `jar uf` falla en PowerShell con errores inesperados.

**Solucion:** Usar `System.IO.Compression.ZipFile` de .NET para modificar
el bytecode directamente. Más confiable que `jar.exe` del JDK.

**Implementacion:** Ver funcion `Apply-ProgressPatch` en `Install-Theme.ps1`.

### 2026-09-19 — Screenshots y repo publico

**Resultado:**
- 7 screenshots capturados (Airlock, Pastel98, PixelFizz, SunsetTape, VaporGrid, Zune)
- README actualizado con previews en tabla
- Repo publicado como public
- Bitácora actualizada con intel faltante
