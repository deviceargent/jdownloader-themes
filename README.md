# Zune

Tema final **Zune** para JDownloader 2.

## Dirección visual

Interfaz casi negra de reproductor, con texto naranja Orbitron, controles de
ventana agrupados con brillo Zune y superficies carbón. Los fondos permanecen
opacos para conservar un repintado estable en Swing.

## Paleta inicial

| Uso | Color |
|-----|-------|
| Fondo | `#080A0C` |
| Superficie | `#101418` |
| Superficie elevada | `#171C21` |
| Texto | `#F0782B` |
| Texto secundario | `#D96725` |
| Naranja Zune | `#F0782B` |
| Naranja profundo | `#C75418` |

## Compatibilidad

Es un tema oscuro. JDownloader puede requerir el parche de progreso descrito
en la documentación de Phosphor para evitar texto blanco hardcodeado en filas
hijas.

## Instalación

1. Copiar `FlatZune.jar` a `libs\laf\`.
2. Copiar `cfg/Zune.json` a `cfg\laf\`.
3. Configurar `customlookandfeelclass` como
   `com.github.deviceargent.zune.Zune`.
