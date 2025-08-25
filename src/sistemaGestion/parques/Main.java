package sistemaGestion.parques;
import java.util.*;
import java.time.format.*;
import java.time.*;
//clases
import sistemaGestion.modelos.*;
import sistemaGestion.logica.*;

public class Main {
	public static void main(String[] args) {
		
		SistemaReservas sistema = new SistemaReservas() ; 
		//creamos el scanner
		Scanner sc = new Scanner(System.in) ; 
		
		//emepzamos el bucle para leer datos 
		while(true) {
			limpiarConsola();
			
			
            System.out.println("\n--- Sistema de Reservas de Parques Nacionales ---");
            System.out.println("1. Registrar Visitante");
            System.out.println("2. Crear Reserva");
            System.out.println("3. Cancelar Reserva");
            System.out.println("4. Ver todas las Reservas");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");			
			
			int opcion = sc.nextInt() ;
			sc.nextLine() ;  // para el salto de linea \n
			
			
			//hacemos el switch-case
			
			switch(opcion) {
            case 1:
            	System.out.print("Ingrese RUT: ");
                String rut = sc.nextLine();
                System.out.print("Ingrese nombre: ");
                String nombre = sc.nextLine();
                System.out.print("Ingrese email: ");
                String email = sc.nextLine();
                
                sistema.registrarVisitante(rut, nombre, email);
                presionarEnterParaContinuar(sc);
                break;
            case 2:
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
                }            	
                presionarEnterParaContinuar(sc);
                break;
            case 3:
                System.out.println("\n--- Cancelación de Reserva ---");
                System.out.print("Ingrese el código de la reserva a cancelar: ");
                int codigoCancelar = sc.nextInt();
                sc.nextLine(); // Limpiamos el buffer
                sistema.cancelarReserva(codigoCancelar);
                presionarEnterParaContinuar(sc);
                break;
            case 4:
                sistema.mostrarTodasLasReservas();
                presionarEnterParaContinuar(sc);
                break;
            case 5:
                System.out.println("Saliendo del sistema. ¡Hasta pronto!");
                sc.close(); 
                return; // Termina el programa
            default:
                System.out.println("Opción no válida. Por favor, intente de nuevo.");
			}
		}
	}
	
	// Método ayudante para no repetir código
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
	
}
