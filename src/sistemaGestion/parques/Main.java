package sistemaGestion.parques;
import java.util.*;
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
                break;
            case 2:
                //crearReserva();
                System.out.println("Opción 'Crear Reserva' aún no implementada.");
                break;
            case 3:
                //cancelarReserva();
                System.out.println("Opción 'Cancelar Reserva' aún no implementada.");
                break;
            case 4:
                sistema.mostrarTodasLasReservas();
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
}
