# 🏞️ Sistema de Gestión de Reservas en Parques Nacionales

## 📝 Descripción

Este programa, desarrollado en **Java** con la librería **Swing**, implementa un sistema de escritorio para la gestión de reservas en parques nacionales. La aplicación permite administrar parques, alojamientos (campings y cabañas), visitantes y las reservas asociadas. Utiliza una arquitectura **Modelo-Vista-Controlador (MVC)** y persistencia de datos a través de archivos CSV.

✅ **Funcionalidades Principales:**
* **Gestión de Parques y Alojamientos:** Permite agregar nuevos alojamientos a parques existentes y eliminar parques (con validaciones).
* **Gestión de Visitantes:** Registro de nuevos visitantes en el sistema.
* **Ciclo Completo de Reservas:** Funcionalidades para crear, editar (fechas), cancelar y eliminar reservas de forma permanente.
* **Persistencia de Datos:** El sistema carga y guarda la información de visitantes y reservas en archivos `.csv`, manteniendo los datos entre sesiones.
* **Interfaz Gráfica Intuitiva:** Toda la interacción se realiza a través de una ventana principal que centraliza las operaciones y muestra la información en una tabla.
* **Funcionalidad de Búsqueda y Filtro:** Permite buscar reservas por código y filtrar las reservas activas por parque nacional.

---

## ⚙️ Instalación y Ejecución

### Requisitos

* Tener instalado el **Kit de Desarrollo de Java (JDK)**, versión 11 o superior.
* Un IDE de Java como **Eclipse** o **IntelliJ IDEA**.

### Instrucciones de Ejecución

1.  **Clonar o Descargar el Repositorio:**
    Obtén todos los archivos del proyecto y guárdalos en una carpeta en tu computador.

2.  **Abrir el Proyecto en tu IDE:**
    * Abre tu IDE (ej. Eclipse).
    * Selecciona `File > Import...`.
    * Elige `General > Existing Projects into Workspace` y selecciona la carpeta del proyecto.

3.  **Ejecutar la Aplicación:**
    * Localiza el archivo `Main.java` en el paquete `sistemaGestion.parques`.
    * Haz clic derecho sobre el archivo y selecciona `Run As > Java Application`.
    * La ventana principal del sistema se abrirá.

---

## 📁 Estructura del Proyecto (Arquitectura MVC)

El proyecto está organizado en paquetes que reflejan el patrón Modelo-Vista-Controlador:

* **`sistemaGestion.vista` (Vista):** Contiene la clase `VentanaPrincipal.java`, responsable de toda la interfaz gráfica.
* **`sistemaGestion.logica` (Controlador/Modelo):** Contiene la clase `SistemaReservas.java`, que actúa como el cerebro de la aplicación, manejando la lógica de negocio.
* **`sistemaGestion.modelos` (Modelo):** Contiene las clases de entidad que representan los datos: `ParqueNacional`, `Alojamiento`, `Camping`, `Cabaña`, `Reserva` y `Visitante`.
* **`sistemaGestion.datos` (Modelo):** Contiene la clase `GestionArchivos.java`, encargada de la persistencia (leer y escribir archivos CSV).
* **`sistemaGestion.exceptions` (Modelo):** Contiene las excepciones personalizadas para el manejo de errores de negocio.
* **`sistemaGestion.parques`:** Contiene la clase `Main.java`, que actúa como el punto de entrada para lanzar la aplicación.

---

## 🏗️ Clases Principales y Estructuras de Datos

* **`SistemaReservas`:** Clase central que orquesta todas las operaciones, contiene las colecciones principales de datos y la lógica de negocio.
* **`VentanaPrincipal`:** Clase que hereda de `JFrame` y construye toda la interfaz de usuario, manejando los eventos de los botones.
* **`GestionArchivos`:** Clase dedicada a la serialización y deserialización de los objetos desde y hacia archivos CSV.
* **Clases del Modelo:** Clases como `ParqueNacional`, `Reserva`, etc., que encapsulan los datos y sus comportamientos. Se utiliza herencia a través de la clase abstracta `Alojamiento`.
* **Colecciones de Java:** Se utiliza `ArrayList` para las listas de objetos (parques, reservas) y `HashMap` para un acceso eficiente a los visitantes por su RUT.

---

## 🕹️ Funcionamiento Principal

1.  **Carga Inicial:** Al ejecutar `Main.java`, se crea una instancia de `SistemaReservas`. En su constructor, se llama a `GestionArchivos` para leer los archivos `reservas.csv` y `visitantes.csv` y cargar los datos en memoria (`ArrayList` y `HashMap`).
2.  **Inicio de la GUI:** Se crea una instancia de `VentanaPrincipal`, pasándole el objeto `SistemaReservas`. La ventana se hace visible.
3.  **Interacción del Usuario:** El usuario interactúa con los botones de la `VentanaPrincipal`. Los `ActionListeners` (el Controlador) capturan estos eventos y llaman a los métodos correspondientes en el objeto `SistemaReservas` (el Modelo).
4.  **Actualización de la Vista:** Después de cada operación (crear, editar, eliminar), se llama al método `actualizarTabla()` en la `VentanaPrincipal` para refrescar los datos mostrados al usuario.
5.  **Guardado de Datos:** El sistema está preparado para invocar a `guardarDatosAlSalir()` para persistir los cambios en los archivos CSV al cerrar la aplicación.

---

## 🖥️ Interfaz de Usuario

La aplicación cuenta con una ventana principal que centraliza todas las funciones. Una tabla muestra en tiempo real el estado de todas las reservas, y los botones permiten ejecutar las distintas operaciones.

*Figura 1: Ventana principal de la aplicación, mostrando la tabla de reservas y los botones de gestión.*
https://imgur.com/a/1tizqMZ

** **

---

## 👥 Créditos

* Gabriel Sebastián López Quilodrán(Todo el codigo)
* Felipe Ignacio Castro Rojas(documentacion y Readme)
Agradecimientos: A herramientas como ChatGPT y GitHub Copilot por la asistencia en la depuración de código y la generación de ideas, ademas de la ortografia y lenguaje formal. 