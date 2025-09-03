package sistemaGestion.vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import sistemaGestion.exceptions.EntidadNoEncontradaException;
import sistemaGestion.exceptions.ReglaDeNegocioException;
import sistemaGestion.logica.SistemaReservas;
import sistemaGestion.modelos.Cabaña;
import sistemaGestion.modelos.Camping;
import sistemaGestion.modelos.ParqueNacional;
import sistemaGestion.modelos.Reserva;
import sistemaGestion.modelos.Visitante;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.awt.event.ActionEvent; 



public class VentanaPrincipal extends JFrame {
    private SistemaReservas sistema;
    private JPanel contentPane;
    private JTable tableReservas;


    public VentanaPrincipal(SistemaReservas sistema) {
        this.sistema = sistema;

        // --- Configuración General de la Ventana ---
        setTitle("Sistema de Gestión de Parques Nacionales");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 850, 600);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // --- Tabla de Reservas ---
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 21, 814, 300);
        contentPane.add(scrollPane);

        tableReservas = new JTable();
        scrollPane.setViewportView(tableReservas);

        // --- Panel de Administración de Alojamientos ---
        JPanel panelAdmin = new JPanel();
        panelAdmin.setBorder(new TitledBorder(null, "Administración de Alojamientos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
        panelAdmin.setBounds(10, 332, 814, 70);
        contentPane.add(panelAdmin);
        panelAdmin.setLayout(null);

        JButton btnAgregarAlojamiento = new JButton("Agregar Alojamiento");
        btnAgregarAlojamiento.setBounds(10, 25, 170, 25);
        panelAdmin.add(btnAgregarAlojamiento);

        JButton btnEliminarParque = new JButton("Eliminar Parque");
        btnEliminarParque.setBounds(190, 25, 170, 25);
        panelAdmin.add(btnEliminarParque);

        JButton btnMostrarAlojamientos = new JButton("Mostrar Alojamientos");
        btnMostrarAlojamientos.setBounds(370, 25, 170, 25);
        panelAdmin.add(btnMostrarAlojamientos);

        // --- Panel de Gestión de Reservas ---
        JPanel panelReservas = new JPanel();
        panelReservas.setBorder(new TitledBorder(null, "Gestión de Reservas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelReservas.setBounds(10, 413, 814, 100);
        contentPane.add(panelReservas);
        panelReservas.setLayout(null);

        JButton btnRegistrarVisitante = new JButton("Registrar Visitante");
        btnRegistrarVisitante.setBounds(10, 25, 155, 25);
        panelReservas.add(btnRegistrarVisitante);

        JButton btnCrearReserva = new JButton("Crear Reserva");
        btnCrearReserva.setBounds(175, 25, 155, 25);
        panelReservas.add(btnCrearReserva);

        JButton btnEditarReserva = new JButton("Editar Reserva");
        btnEditarReserva.setBounds(340, 25, 155, 25);
        panelReservas.add(btnEditarReserva);
        
        JButton btnEliminarReserva = new JButton("Eliminar Reserva");
        btnEliminarReserva.setBounds(505, 25, 155, 25);
        panelReservas.add(btnEliminarReserva);

        JButton btnCancelarReserva = new JButton("Cancelar Reserva");
        btnCancelarReserva.setBounds(10, 60, 155, 25);
        panelReservas.add(btnCancelarReserva);
        
        JButton btnFiltrarReservas = new JButton("Filtrar Reservas");
        btnFiltrarReservas.setBounds(175, 60, 155, 25);
        panelReservas.add(btnFiltrarReservas);

        JButton btnActualizarTabla = new JButton("Ver Todas / Actualizar");
        btnActualizarTabla.setBounds(649, 25, 155, 60);
        panelReservas.add(btnActualizarTabla);


        // --- Botón de Salir ---
        JButton btnSalir = new JButton("Salir");
        btnSalir.setBounds(739, 524, 85, 25);
        contentPane.add(btnSalir);

        // Carga inicial de datos en la tabla
        actualizarTabla();

        // --- Action Listeners ---

        btnSalir.addActionListener(e -> System.exit(0));
        btnActualizarTabla.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarTabla();
                JOptionPane.showMessageDialog(VentanaPrincipal.this, "La tabla de reservas ha sido actualizada.", "Actualización", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnAgregarAlojamiento.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<ParqueNacional> parques = sistema.getListaParques();
                if (parques.isEmpty()) {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this, "No hay parques nacionales registrados.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String[] nombresParques = new String[parques.size()];
                for (int i = 0; i < parques.size(); i++) {
                    nombresParques[i] = parques.get(i).getNombre();
                }
                
                String parqueElegidoNombre = (String) JOptionPane.showInputDialog(
                    VentanaPrincipal.this,
                    "Seleccione un parque:",
                    "Agregar Alojamiento",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    nombresParques,
                    nombresParques[0]
                );

                if (parqueElegidoNombre == null) {
                    return; 
                }

                ParqueNacional parqueSeleccionado = null;
                for (ParqueNacional p : parques) {
                    if (p.getNombre().equals(parqueElegidoNombre)) {
                        parqueSeleccionado = p;
                        break;
                    }
                }

                String[] tipos = {"Camping", "Cabaña"};
                int tipoElegido = JOptionPane.showOptionDialog(
                    VentanaPrincipal.this,
                    "¿Qué tipo de alojamiento desea agregar al parque '" + parqueSeleccionado.getNombre() + "'?",
                    "Tipo de Alojamiento",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    tipos,
                    tipos[0]
                );

                if (tipoElegido == 0) { 
                    try {
                        String id = JOptionPane.showInputDialog(VentanaPrincipal.this, "Ingrese el ID para el nuevo camping:");
                        String nombre = JOptionPane.showInputDialog(VentanaPrincipal.this, "Ingrese el nombre del camping:");
                        int sitios = Integer.parseInt(JOptionPane.showInputDialog(VentanaPrincipal.this, "Ingrese el total de sitios:"));

 
                        parqueSeleccionado.agregarCamping(new Camping(id, nombre, sitios));
                        JOptionPane.showMessageDialog(VentanaPrincipal.this, "Camping agregado exitosamente");

                    }
                    catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(VentanaPrincipal.this, "Error: El numero de sitios debe ser un valor numerico.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                    }
                } 
                else if (tipoElegido == 1) {
                    try {
                        String id = JOptionPane.showInputDialog(VentanaPrincipal.this, "Ingrese el ID para la nueva cabaña:");
                        int capacidad = Integer.parseInt(JOptionPane.showInputDialog(VentanaPrincipal.this, "Ingrese la capacidad de personas:"));
                        
                        parqueSeleccionado.agregarCabañas(new Cabaña(id, capacidad));
                        JOptionPane.showMessageDialog(VentanaPrincipal.this, "Cabaña agregada exitosamente");

                    } 
                    catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(VentanaPrincipal.this, "Error: La capacidad debe ser un valor numerico.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        
        btnEliminarParque.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<ParqueNacional> parques = sistema.getListaParques();
                if (parques.isEmpty()) {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this, "No hay parques nacionales para eliminar.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                String[] nombresParques = new String[parques.size()];
                for (int i = 0; i < parques.size(); i++) {
                    nombresParques[i] = parques.get(i).getNombre();
                }

                String parqueElegidoNombre = (String) JOptionPane.showInputDialog(
                    VentanaPrincipal.this,
                    "Seleccione el parque nacional que desea eliminar:",
                    "Eliminar Parque Nacional",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    nombresParques,
                    nombresParques[0]
                );

                if (parqueElegidoNombre == null) {
                    return;                 }

                int confirmacion = JOptionPane.showConfirmDialog(
                    VentanaPrincipal.this,
                    "ADVERTENCIA: ¿Está seguro de que desea eliminar el parque '" + parqueElegidoNombre + "'?\n" +
                    "Esta acción es permanente y solo funcionara si el parque no tiene reservas activas.",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );

                
                if (confirmacion == JOptionPane.YES_OPTION) {
                    try {
                        sistema.eliminarParqueNacional(parqueElegidoNombre);
                        JOptionPane.showMessageDialog(VentanaPrincipal.this, "Parque eliminado exitosamente.");
                        
                    } 
                    catch (ReglaDeNegocioException | EntidadNoEncontradaException ex) {
                        JOptionPane.showMessageDialog(VentanaPrincipal.this, "Error: " + ex.getMessage(), "Error al Eliminar", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        
        btnMostrarAlojamientos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                String reporteAlojamientos = sistema.obtenerTextoAlojamientos();

                JTextArea textArea = new JTextArea(20, 50); 
                textArea.setText(reporteAlojamientos);
                textArea.setEditable(false); 
                textArea.setCaretPosition(0); 


                JScrollPane scrollPane = new JScrollPane(textArea);

                JOptionPane.showMessageDialog(
                    VentanaPrincipal.this,
                    scrollPane,
                    "Listado de Alojamientos",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        

        
        btnRegistrarVisitante.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String rut = JOptionPane.showInputDialog(
                    VentanaPrincipal.this,
                    "Ingrese el RUT del visitante:",
                    "Registrar Visitante",
                    JOptionPane.QUESTION_MESSAGE
                );

                if (rut == null || rut.trim().isEmpty()) {
                    return;
                }

                String nombre = JOptionPane.showInputDialog(
                    VentanaPrincipal.this,
                    "Ingrese el Nombre del visitante:",
                    "Registrar Visitante",
                    JOptionPane.QUESTION_MESSAGE
                );

                if (nombre == null || nombre.trim().isEmpty()) {
                    return;
                }

                String email = JOptionPane.showInputDialog(
                    VentanaPrincipal.this,
                    "Ingrese el Email del visitante:",
                    "Registrar Visitante",
                    JOptionPane.QUESTION_MESSAGE
                );

                if (email == null || email.trim().isEmpty()) {
                    return; 
                }

                try {
                    
                    sistema.registrarVisitante(rut, nombre, email);
                    
                    JOptionPane.showMessageDialog(
                        VentanaPrincipal.this,
                        "¡Visitante '" + nombre + "' registrado exitosamente!",
                        "Registro Exitoso",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                } 
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                        VentanaPrincipal.this,
                        "Ocurrió un error al registrar el visitante: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
        
        
        btnCrearReserva.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Map<String, Visitante> visitantesMap = sistema.getMapaVisitantes();
                if (visitantesMap.isEmpty()) {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this, "No hay visitantes registrados.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String[] opcionesVisitantes = visitantesMap.values().stream()
                        .map(v -> v.getNombre() + " (" + v.getRut() + ")")
                        .toArray(String[]::new);

                String visitanteSeleccionadoStr = (String) JOptionPane.showInputDialog(
                    VentanaPrincipal.this, "Seleccione el visitante:", "Crear Reserva - Paso 1",
                    JOptionPane.QUESTION_MESSAGE, null, opcionesVisitantes, opcionesVisitantes[0]
                );

                if (visitanteSeleccionadoStr == null) return;
                String rutVisitante = visitanteSeleccionadoStr.substring(visitanteSeleccionadoStr.indexOf("(") + 1, visitanteSeleccionadoStr.indexOf(")"));

                String reporteAlojamientos = sistema.obtenerTextoAlojamientos();
                JTextArea textArea = new JTextArea(reporteAlojamientos, 20, 50);
                textArea.setEditable(false);
                textArea.setCaretPosition(0);
                JScrollPane scrollPane = new JScrollPane(textArea);
                JOptionPane.showMessageDialog(VentanaPrincipal.this, scrollPane, "Alojamientos Disponibles", JOptionPane.INFORMATION_MESSAGE);
                
                String idAlojamiento = JOptionPane.showInputDialog(VentanaPrincipal.this, "Ingrese el ID del Camping o Cabaña:", "Crear Reserva - Paso 2", JOptionPane.QUESTION_MESSAGE);
                if (idAlojamiento == null || idAlojamiento.trim().isEmpty()) return;

                LocalDate fechaLlegada = pedirFechaConDialogo("Ingrese la fecha de llegada (dd-MM-yyyy):", "Crear Reserva - Paso 3");
                if (fechaLlegada == null) return; 

                LocalDate fechaSalida = pedirFechaConDialogo("Ingrese la fecha de salida (dd-MM-yyyy):", "Crear Reserva - Paso 4");
                if (fechaSalida == null) return; 
                
                try {
                    
                    sistema.crearReserva(rutVisitante, idAlojamiento, fechaLlegada, fechaSalida);
                    JOptionPane.showMessageDialog(VentanaPrincipal.this, "¡Reserva creada exitosamente!");
                    actualizarTabla(); 
                } 
                catch (ReglaDeNegocioException | EntidadNoEncontradaException ex) {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this, "Error: " + ex.getMessage(), "Error al Crear Reserva", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        
        btnEditarReserva.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tableReservas.getSelectedRow();

                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this, "Por favor, seleccione una reserva de la tabla para editar.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int codigoReserva = (int) tableReservas.getValueAt(filaSeleccionada, 0);


                LocalDate nuevaLlegada = pedirFechaConDialogo("Ingrese la NUEVA fecha de llegada (dd-MM-yyyy):", "Editar Reserva - Nueva Llegada");
                if (nuevaLlegada == null) {
                    return; 
                }

                LocalDate nuevaSalida = pedirFechaConDialogo("Ingrese la NUEVA fecha de salida (dd-MM-yyyy):", "Editar Reserva - Nueva Salida");
                if (nuevaSalida == null) {
                    return; 
                }

                try {

                    boolean exito = sistema.editarFechasReserva(codigoReserva, nuevaLlegada, nuevaSalida);

                    if (exito) {

                        JOptionPane.showMessageDialog(VentanaPrincipal.this, "¡Reserva actualizada exitosamente!");
                        actualizarTabla();
                    }
                    
                }
                catch (ReglaDeNegocioException | EntidadNoEncontradaException ex) {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this, "Error al editar la reserva: " + ex.getMessage(), "Error de Edición", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        
        
        btnEliminarReserva.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tableReservas.getSelectedRow();

                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(
                        VentanaPrincipal.this, 
                        "Por favor, seleccione una reserva de la tabla para eliminar.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE
                    );
                    return; 
                }

                int codigoReserva = (int) tableReservas.getValueAt(filaSeleccionada, 0);

                int confirmacion = JOptionPane.showConfirmDialog(
                    VentanaPrincipal.this, 
                    "¿Está seguro de que desea eliminar permanentemente la reserva con codigo " + codigoReserva + "?", 
                    "Confirmar Eliminación", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );

                if (confirmacion == JOptionPane.YES_OPTION) {
                    try {
                        sistema.eliminarReserva(codigoReserva);
                        

                        JOptionPane.showMessageDialog(VentanaPrincipal.this, "Reserva eliminada exitosamente.");
                        
                        actualizarTabla();

                    } 
                    catch (EntidadNoEncontradaException ex) {
                        JOptionPane.showMessageDialog(
                            VentanaPrincipal.this, 
                            "Error: " + ex.getMessage(), 
                            "Error al Eliminar", 
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        });
        
        
        btnCancelarReserva.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tableReservas.getSelectedRow();

                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this, "Por favor, seleccione una reserva de la tabla para cancelar.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int codigoReserva = (int) tableReservas.getValueAt(filaSeleccionada, 0);
                String estadoActual = (String) tableReservas.getValueAt(filaSeleccionada, 5);

                if (estadoActual.equalsIgnoreCase("Cancelada")) {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this, "Esta reserva ya se encuentra cancelada.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                
                int confirmacion = JOptionPane.showConfirmDialog(
                    VentanaPrincipal.this, 
                    "¿Está seguro de que desea cambiar el estado de la reserva " + codigoReserva + " a 'Cancelada'?", 
                    "Confirmar Cancelación", 
                    JOptionPane.YES_NO_OPTION
                );

                if (confirmacion == JOptionPane.YES_OPTION) {
                    try {
                        sistema.cancelarReserva(codigoReserva);
                        
                        JOptionPane.showMessageDialog(VentanaPrincipal.this, "Reserva cancelada exitosamente.");
                        
                        
                        actualizarTabla();

                    } 
                    catch (EntidadNoEncontradaException ex) {
                        JOptionPane.showMessageDialog(
                            VentanaPrincipal.this, 
                            "Error: " + ex.getMessage(), 
                            "Error al Cancelar", 
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        });
        
        btnFiltrarReservas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // --- Paso 1: Seleccionar el Parque para filtrar ---
                List<ParqueNacional> parques = sistema.getListaParques();
                if (parques.isEmpty()) {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this, "No hay parques para usar como filtro.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                String[] nombresParques = parques.stream()
                                              .map(ParqueNacional::getNombre)
                                              .toArray(String[]::new);

                String parqueElegido = (String) JOptionPane.showInputDialog(
                    VentanaPrincipal.this,
                    "Seleccione el parque para filtrar las reservas activas:",
                    "Filtrar Reservas",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    nombresParques,
                    nombresParques[0]
                );

                if (parqueElegido == null) {
                    return;
                }

                List<Reserva> reservasFiltradas = sistema.filtrarReservasActivasPorParque(parqueElegido);

                if (reservasFiltradas.isEmpty()) {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this, "No se encontraron reservas activas para el parque '" + parqueElegido + "'.");
                } else {
                    JTable tablaResultados = new JTable();
                    String[] columnas = {"Código", "Visitante", "Alojamiento", "F. Llegada", "F. Salida", "Estado", "Monto"};
                    DefaultTableModel modeloResultados = new DefaultTableModel(columnas, 0);

                    for (Reserva r : reservasFiltradas) {
                        Object[] fila = {
                            r.getCodigoReserva(),
                            r.getVisitante().getNombre(),
                            r.getIdAlojamiento(),
                            r.getFechaLlegada(),
                            r.getFechaSalida(),
                            r.getEstado(),
                            r.getMontoTotal()
                        };
                        modeloResultados.addRow(fila);
                    }
                    tablaResultados.setModel(modeloResultados);

                    JScrollPane scrollPaneResultados = new JScrollPane(tablaResultados);
                    JOptionPane.showMessageDialog(
                        VentanaPrincipal.this, 
                        scrollPaneResultados, 
                        "Resultados del Filtro: " + parqueElegido, 
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });

        
        
    } // fin fin 
    
    

    private void actualizarTabla() {
        String[] columnas = {
            "Código", "Visitante", "Alojamiento", "F. Llegada", "F. Salida", "Estado", "Monto"
        };


        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        List<Reserva> reservas = sistema.getListaReservas();

        if (reservas != null) {
            for (Reserva r : reservas) {
                Object[] fila = {
                    r.getCodigoReserva(),
                    r.getVisitante().getNombre(),
                    r.getIdAlojamiento(),
                    r.getFechaLlegada(),
                    r.getFechaSalida(),
                    r.getEstado(),
                    r.getMontoTotal()
                };
                modelo.addRow(fila);
            }
        }

        tableReservas.setModel(modelo);
    }
    
    /**
     * Muestra un diálogo para pedir una fecha y la valida, repitiendo si el formato es incorrecto.
     * @param mensaje El texto a mostrar en el diálogo.
     * @param titulo El título de la ventana de diálogo.
     * @return La LocalDate ingresada por el usuario, o null si cancela.
     */
    private LocalDate pedirFechaConDialogo(String mensaje, String titulo) {
        LocalDate fecha = null;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        while (fecha == null) {
            String fechaStr = JOptionPane.showInputDialog(VentanaPrincipal.this, mensaje, titulo, JOptionPane.QUESTION_MESSAGE);
            if (fechaStr == null) return null; // El usuario canceló
            try {
                fecha = LocalDate.parse(fechaStr, formato);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(VentanaPrincipal.this, "Formato de fecha incorrecto. Use dd-MM-yyyy.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        }
        return fecha;
    }
    
    
    
}