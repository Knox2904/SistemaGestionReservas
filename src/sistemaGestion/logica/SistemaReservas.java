package sistemaGestion.logica;

import sistemaGestion.modelos.*;
import sistemaGestion.parques.*;

import java.time.*;
import java.util.*;

public class SistemaReservas {
	//crear listas para gestion
	private ArrayList<Visitante> listaVisitantes = new ArrayList<>();
	private ArrayList<Camping> listaCampings = new ArrayList<>();
	private ArrayList<Cabania> listaCabanias = new ArrayList<>();
	private ArrayList<Reserva> listaReservas = new ArrayList<>();
	private int codigoDeReserva = 1 ;
	

	public SistemaReservas() {
		
	}
	
	
	//------------------logica programa--------------------
	
	
	public void registrarVisitante(String rut , String nombre , String email) {
		
		Visitante nuevoVisitante = new Visitante(rut, nombre, email);
		this.listaVisitantes.add(nuevoVisitante) ; 
		System.out.println("Visitante " + nombre + " registrado exitosamente") ; 
	}
	
	public void crearReserva(String rutVisitante, String idAlojamiento, String tipo, LocalDate llegada, LocalDate salida) {
		Reserva nuevaReserva = new Reserva(codigoDeReserva, rutVisitante, idAlojamiento, tipo, llegada, salida, "Activa") ; 
		this.listaReservas.add(nuevaReserva) ; 
		codigoDeReserva++ ;
		System.out.println("Reserva creada con codigoDeReserva : " + nuevaReserva.getCodigoReserva() + " exitosamente") ; 
		
	}
	
	public void mostrarTodasLasReservas() {
		
		System.out.println("\n--- Listado de Todas las Reservas ---");
		if(listaReservas.isEmpty()) {
			System.out.println("No hay reservas registradas") ; 
			return ;
		}
		
		for(Reserva r : listaReservas) {
			System.out.println("Codigo: " + r.getCodigoReserva()) ; 
			System.out.println("Rut: " + r.getRutVisitante()) ; 
			System.out.println("Alojamiento: " + r.getIdAlojamiento()) ; 
			System.out.println("Estado: " + r.getEstado());
		}
	}
}
