# ScopeSighter (Personal Fork)

Este repositorio es una versión modificada de **ScopeSighter**, una app Android para calcular ajustes de mira en tiro deportivo, creada originalmente por **Benjamin Reddington** ([repo original](https://github.com/benreddington/ScopeSighter)).

## Sobre este fork

ScopeSighter fue publicada originalmente en Google Play y luego dada de baja por temas administrativos de la tienda. El autor compartió el proyecto de forma pública y, tras consultarlo directamente, autorizó expresamente su modificación y liberó el código bajo licencia MIT.

Este fork nació como un proyecto personal para adaptar la app a mi propio uso en la práctica de tiro deportivo, decompilando y reconstruyendo el proyecto original.

## Modificaciones realizadas

- **Fondos de blanco configurables**: la pantalla de ingreso de disparos ahora permite elegir entre distintas imágenes de fondo (blancos de tiro reales) que coinciden con el círculo de impactos, seleccionables desde el Administrador de Miras y Blancos.
- **Selector de idioma manual**: la app permite alternar entre inglés (idioma original) y español, independientemente del idioma configurado en el sistema operativo.
- **Correcciones de compilación**: se resolvieron varios bugs derivados del proceso de decompilación del APK original (recursos `R.java` obsoletos, errores de manifest, un bug de lógica en `updateSavables()`), permitiendo que el proyecto compile de forma limpia en herramientas modernas de Android Studio.
- **Limpieza de dependencias**: se eliminaron referencias obsoletas a librerías de soporte de Android (`android.support.*`, `android.arch.*`) que quedaron incluidas incorrectamente durante la decompilación.

## Licencia

Este proyecto mantiene la licencia **MIT** original del autor. Ver [LICENSE](./LICENSE).

## Reconocimiento y agradecimiento

Todo el mérito del diseño original, la lógica de cálculo de ajustes, y la concepción de la app es de **Benjamin Reddington**. Este fork existe gracias a su generosidad al liberar el proyecto como código abierto.

Si te resulta útil esta app, considerá apoyar al autor original:
[Donar a Ben Reddington](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=6FDKJT9CS7J6L)

## Estado del repositorio

Este repositorio se mantiene **privado** de forma intencional, como proyecto personal. Si Benjamin Reddington decide retomar el desarrollo del proyecto original, tiene prioridad absoluta y este trabajo queda a su entera disposición.
