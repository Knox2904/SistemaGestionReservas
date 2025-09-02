package sistemaGestion.parques;
import java.util.*;
import java.time.format.*;
import java.time.*;
//clases
import sistemaGestion.modelos.*;
import sistemaGestion.logica.*;
import sistemaGestion.exceptions.*;

public class Main {
	public static void main(String[] args) {
		
		SistemaReservas sistema = new SistemaReservas() ; 
		//creamos el scanner
		Scanner sc = new Scanner(System.in) ; 
		
		//guardamos siempre los datos antes de salir SIEMPRE
		//aunque lo cerremos por accidente o se corte la luz que ya a pasasdo antes 
		Runtime.getRuntime().addShutdownHook(new Thread(() -> sistema.guardarDatosAlSalir()));
		
		//emepzamos el bucle para leer datos 
		while(true) {
			limpiarConsola();
			
			System.out.println("\n--- Sistema de Parques Nacionales ---");
			System.out.println("--- Administración de Alojamientos ---");
			System.out.println("1. Agregar Alojamiento a un Parque");
			System.out.println("2. Eliminar un Parque Nacional"); //
			System.out.println("3. Mostrar Alojamientos por Parque");
			System.out.println("--- Gestión de Reservas ---");
			System.out.println("4. Registrar Visitante");
			System.out.println("5. Crear Reserva");
			System.out.println("6. Editar Fechas de Reserva");   
		    System.out.println("7. Eliminar Reserva");           		
			System.out.println("8. Cancelar Reserva");
			System.out.println("9. Ver Todas las Reservas");
			System.out.println("10. Filtrar Reservas por Parque");
			System.out.println("11. Salir");
			System.out.print("Seleccione una opción: ");			
			
			int opcion = sc.nextInt() ;
			sc.nextLine() ;  // para el salto de linea \n
			
			
			//hacemos el switch-case
			
			switch(opcion) {
			case 1 : 
				ParqueNacional parqueSeleccionado = seleccionarParque(sc, sistema);
			    
			    if (parqueSeleccionado != null) {
			        System.out.print("¿Que desea agregar? ('Camping' o 'Cabaña'): ");
			        String tipo = sc.nextLine();

			        if (tipo.equalsIgnoreCase("Camping")) {
			            System.out.print("Ingrese ID para el camping: ");
			            String id = sc.nextLine();
			            System.out.print("Ingrese nombre del camping: ");
			            String nombre = sc.nextLine();
			            System.out.print("Ingrese total de sitios: ");
			            int sitios = sc.nextInt();
			            sc.nextLine();
			            
			            
			            parqueSeleccionado.agregarCamping(new Camping(id, nombre, sitios));
			            System.out.println("Camping agregado a " + parqueSeleccionado.getNombre());
			        } 
			        
			        else if (tipo.equalsIgnoreCase("Cabaña")) {
			            System.out.print("Ingrese ID para la cabaña: ");
			            String id = sc.nextLine();
			            System.out.print("Ingrese capacidad de personas: ");
			            int capacidad = sc.nextInt();
			            sc.nextLine();

			            parqueSeleccionado.agregarCabañas(new Cabaña(id, capacidad));
			            System.out.println("Cabaña agregada a " + parqueSeleccionado.getNombre());
			        } 
			        
			        else {
			            System.out.println("Tipo de alojamiento no valido.");
			        }
			    }
			    presionarEnterParaContinuar(sc);
			    break ; 
			    
			
			case 2 : 
				 System.out.println("\n--- Eliminación de Parque Nacional ---");
				 System.out.print("Ingrese el nombre del parque a eliminar: ");
				 String nombreParqueEliminar = sc.nextLine();
				    
				 System.out.print("ADVERTENCIA: Esta acción es permanente. ¿Está seguro? (S/N): ");
				 String confirmacionParque = sc.nextLine();

				 if (confirmacionParque.equalsIgnoreCase("S")) {
				     try {
				         sistema.eliminarParqueNacional(nombreParqueEliminar);
				     } 
				     catch (ReglaDeNegocioException | EntidadNoEncontradaException e) {
				         System.err.println("Error al eliminar el parque: " + e.getMessage());
				     }
				     
				    } 
				  else {
				        System.out.println("Operación cancelada.");
				    }
				 
				    presionarEnterParaContinuar(sc);
				    break;
			    
			    
			case 3 : 
			    sistema.mostrarOpcionesDeAlojamiento();
			    presionarEnterParaContinuar(sc);
			    break;	
			    
			
            case 4:
            	System.out.print("Ingrese RUT: ");
                String rut = sc.nextLine();
                System.out.print("Ingrese nombre: ");
                String nombre = sc.nextLine();
                System.out.print("Ingrese email: ");
                String email = sc.nextLine();
                
                sistema.registrarVisitante(rut, nombre, email);
                presionarEnterParaContinuar(sc);
                break;
                
                
            case 5:
            	try {
            		System.out.println("\n--- Creación de Nueva Reserva ---");
            		System.out.print("Ingrese RUT del visitante que hará la reserva: ");
            		String rutReserva = sc.nextLine();

            		//Buscamos si el visitante existe
            		Visitante visitanteEncontrado = sistema.buscarVisitante(rutReserva);

            		//Verificamos el resultado de la búsqueda
            		if (visitanteEncontrado == null) {
            			System.out.println("Error: No se encontró un visitante con ese RUT. Por favor, registrelo primero (Opción 1).");
            		} 
            		else {
            			//Si existe, continuamos pidiendo los datos de la reserva
            			System.out.println("Visitante encontrado: " + visitanteEncontrado.getNombre());
                    
            			// Mostramos las opciones
            			sistema.mostrarOpcionesDeAlojamiento();
                    
            			System.out.print("\nIngrese el ID del Camping o Cabaña a reservar: ");
            			String idAlojamiento = sc.nextLine();

            			System.out.print("Confirme el tipo ('Camping' o 'Cabaña'): ");
            			String tipoAlojamiento = sc.nextLine();

            			// Pedimos las fechas
            			LocalDate fechaLlegada = pedirFecha(sc, "Ingrese fecha de llegada (dd-MM-yyyy): ");
            			LocalDate fechaSalida = pedirFecha(sc, "Ingrese fecha de salida (dd-MM-yyyy): ");
                    
            			//Con todos los datos,llamamos al sistema
            			sistema.crearReserva(rutReserva, idAlojamiento, tipoAlojamiento, fechaLlegada, fechaSalida);
            			System.out.println("Reserva creada con exito");
            		}
            	}
            	catch (ReglaDeNegocioException e) {
                    System.err.println("Error al crear la reserva: " + e.getMessage());
                }
                
                presionarEnterParaContinuar(sc);
                break;
                
                
            case 6: 
                System.out.println("\n--- Edicion de Reserva ---");
                System.out.print("Ingrese el codigo de la reserva a editar: ");
                int codigoEditar = sc.nextInt();
                sc.nextLine(); // Limpiar buffer

                try {
                    // Buscamos la reserva.
                    Reserva resActual = sistema.buscarReservaPorCodigo(codigoEditar);
                    
                    //mostramos los datos y pedimos los nuevos.
                    System.out.println("Datos actuales: " + resActual.toString());
                    LocalDate nuevaLlegada = pedirFecha(sc, "Ingrese la nueva fecha de llegada (dd-MM-yyyy): ");
                    LocalDate nuevaSalida = pedirFecha(sc, "Ingrese la nueva fecha de salida (dd-MM-yyyy): ");
                    
                    sistema.editarFechasReserva(codigoEditar, nuevaLlegada, nuevaSalida);

                } 
                catch (EntidadNoEncontradaException e) {
                    System.err.println("Error de búsqueda: " + e.getMessage());
                } 
                catch (ReglaDeNegocioException e) {
                    System.err.println("Error en la validación: " + e.getMessage());
                }
                presionarEnterParaContinuar(sc);
                break;
                
            	           	
            case 7: 
                System.out.println("\n--- Eliminacion de Reserva ---");
                System.out.print("Ingrese el codigo de la reserva a eliminar: ");
                int codigoEliminar = sc.nextInt();
                sc.nextLine();

                try {
                    // Verificamos que la reserva exista
                    Reserva reservaAEliminar = sistema.buscarReservaPorCodigo(codigoEliminar);
                    System.out.println("Se eliminara la siguiente reserva: " + reservaAEliminar.toString());
                    
                    System.out.print("¿Esta seguro que desea eliminarla permanentemente? (S/N): ");
                    String confirmacion = sc.nextLine();
                    
                    if (confirmacion.equalsIgnoreCase("S")) {
                        
                        boolean eliminado = sistema.eliminarReserva(codigoEliminar);
                        if (!eliminado) {
                            
                            System.out.println("Hubo un problema al intentar eliminar la reserva.");
                        }
                    } 
                    else {
                        System.out.println("Operacion de Eliminacion cancelada.");
                    }
                } 
                catch (EntidadNoEncontradaException e) {
                    System.err.println("Error: " + e.getMessage());
                }
                
                presionarEnterParaContinuar(sc);
                break;
                
                
            case 8:
                System.out.println("\n--- Cancelacion de Reserva ---");
                System.out.print("Ingrese el codigo de la reserva a cancelar: ");
                int codigoCancelar = sc.nextInt();
                sc.nextLine();

                try {
                    sistema.cancelarReserva(codigoCancelar);
                } 
                catch (EntidadNoEncontradaException e) {
                    System.err.println("Error: " + e.getMessage());
                }

                presionarEnterParaContinuar(sc);
                break;
                
                
            case 9:
                sistema.mostrarTodasLasReservas();
                presionarEnterParaContinuar(sc);
                break;
                
                
            case 10: 
            	System.out.println("\n--- Filtrar Reservas Activas por Parque ---");
                System.out.print("Ingrese el nombre del parque a consultar: ");
                String nombreParque = sc.nextLine();
                
                List<Reserva> reservasEncontradas = sistema.filtrarReservasActivasPorParque(nombreParque);
                
                if (reservasEncontradas.isEmpty()) {
                    System.out.println("No se encontraron reservas activas para el parque '" + nombreParque + "'.");
                } 
                else {
                    System.out.println("Se encontraron " + reservasEncontradas.size() + " reservas activas para '" + nombreParque + "':");
                    
                    for (Reserva r : reservasEncontradas) {
                        System.out.println("- " + r.toString());
                    }
                }
                presionarEnterParaContinuar(sc);
                break;

                
            case 11:
            	limpiarConsola() ; 
                System.out.println("Saliendo del sistema. ¡Hasta pronto!");
                sc.close(); 
                System.exit(0);
                return; // Termina el programa
                
                
            default:
                System.out.println("Opcion no valida. Por favor, intente de nuevo.");
			}
		}
	}
	
	// Metodos ayudantes para no repetir codigo
	private static LocalDate pedirFecha(Scanner sc, String mensaje) {
	    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    LocalDate fecha = null;
	    while (fecha == null) {
	        System.out.print(mensaje);
	        String textoFecha = sc.nextLine();
	        try {
	            fecha = LocalDate.parse(textoFecha, formato);
	        } 
	        catch (DateTimeParseException e) {
	            System.out.println("Error: Formato de fecha incorrecto. Inténtelo de nuevo.");
	        }
	    }
	    return fecha;
	}
	
	private static void limpiarConsola() {
	    for (int i = 0; i < 50; i++) {
	        System.out.println(); // jajaja spam
	    }
	}
	
	private static void presionarEnterParaContinuar(Scanner sc) {
	    System.out.print("\nPresione Enter para continuar...");
	    sc.nextLine(); // gracias profe araya por la idea 
	}
	
	private static ParqueNacional seleccionarParque(Scanner sc, SistemaReservas sistema) {
	    System.out.println("\n--- Seleccione un Parque ---");
	    ArrayList<ParqueNacional> parques = (ArrayList<ParqueNacional>) sistema.getListaParques();
	    
	    if (parques.isEmpty()) { System.out.println("NO HAY PARQUES REGISTRADOS") ; return null; }

	    for (int i = 0; i < parques.size(); i++) {
	        System.out.println((i + 1) + ". " + parques.get(i).getNombre());
	    }

	    System.out.print("Elija el numero del parque: ");
	    int opcion = sc.nextInt();
	    sc.nextLine(); // Limpiar buffer

	    if (opcion > 0 && opcion <= parques.size()) {
	        return parques.get(opcion - 1);
	    } else {
	        System.out.println("Opcion no valida.");
	        return null;
	    }
	}
	
	
	
}
