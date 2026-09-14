# Zune · alpha

Primera base del tema **Zune** para JDownloader 2.

## Dirección visual

Interfaz oscura de reproductor: grafito casi negro, superficies carbón,
texto blanco cálido y naranja fuerte como único acento. La intención es que el
contenido y los controles parezcan una interfaz de dispositivo, no una ventana
de escritorio genérica.

## Paleta inicial

| Uso | Color |
|-----|-------|
| Fondo | `#1B1B1B` |
| Superficie | `#242424` |
| Superficie elevada | `#303030` |
| Texto | `#F2F0E8` |
| Texto secundario | `#B8B5AC` |
| Naranja Zune | `#F0782B` |
| Naranja profundo | `#C75418` |

## Compatibilidad

Es una alpha oscura. JDownloader puede requerir el parche de progreso descrito
en la documentación del repositorio para evitar texto blanco hardcodeado en
filas hijas.

## Instalación

1. Copiar `FlatZune.jar` a `libs\laf\`.
2. Copiar `cfg/Zune.json` a `cfg\laf\`.
3. Configurar `customlookandfeelclass` como
   `com.github.deviceargent.zune.Zune`.
