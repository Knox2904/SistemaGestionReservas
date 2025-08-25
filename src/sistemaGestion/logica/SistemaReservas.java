package sistemaGestion.logica;

import sistemaGestion.modelos.*;


import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class SistemaReservas {
	
	private static final double TarifaUnicaPorNoche = 5000; //money
	
	//crear listas para gestion
	private ArrayList<Visitante> listaVisitantes = new ArrayList<>();
	private ArrayList<Camping> listaCampings = new ArrayList<>();
	private ArrayList<Cabania> listaCabanias = new ArrayList<>();
	private ArrayList<Reserva> listaReservas = new ArrayList<>();
	private int codigoDeReserva = 1 ;
	

	public SistemaReservas() {
	    
	    listaCampings.add(new Camping("CAMP01", "Camping El Roble", 40));
	    listaCampings.add(new Camping("CAMP02", "Camping La Laguna", 60));
	    listaCabanias.add(new Cabania("CAB01", 4));
	    listaCabanias.add(new Cabania("CAB02", 6));
	}
	
	
	//------------------logica programa--------------------
	
	
	public void registrarVisitante(String rut , String nombre , String email) {
		
		Visitante nuevoVisitante = new Visitante(rut, nombre, email);
		this.listaVisitantes.add(nuevoVisitante) ; 
		System.out.println("Visitante " + nombre + " registrado exitosamente") ; 
	}
	
	public void crearReserva(String rutVisitante, String idAlojamiento, String tipo, LocalDate llegada, LocalDate salida)  {
	    // --- Bloque de validaciones de fechas ---
	    LocalDate hoy = LocalDate.now();
	    if (llegada.isBefore(hoy)) {
	        System.out.println("Error: La fecha de llegada no puede ser una fecha pasada.");
	        return;
	    }
	    if (salida.isBefore(llegada) || salida.isEqual(llegada)) {
	        System.out.println("Error: La fecha de salida debe ser posterior a la fecha de llegada.");
	        return;
	    }
	    // --- Fin del Bloque de validaciones ---

	    //VERIFICAMOS QUE EL ALOJAMIENTO EXISTA
	    boolean alojamientoEncontrado = false;
	    if (tipo.equalsIgnoreCase("Camping")) {
	        for (Camping c : listaCampings) {
	            if (c.getIdCamping().equalsIgnoreCase(idAlojamiento)) {
	                alojamientoEncontrado = true;
	                break;
	            }
	        }
	    } else if (tipo.equalsIgnoreCase("Cabaña")) {
	        for (Cabania cab : listaCabanias) {
	            if (cab.getIdCabaña().equalsIgnoreCase(idAlojamiento)) {
	                alojamientoEncontrado = true;
	                break;
	            }
	        }
	    }

	    if (!alojamientoEncontrado) {
	        System.out.println("Error: El ID del alojamiento no fue encontrado.");
	        return;
	    }

	    //CALCULAMOS LAS NOCHES
	    long numeroNoches = ChronoUnit.DAYS.between(llegada, salida);
	    if (numeroNoches <= 0) {
	        numeroNoches = 1; // Minimo 1 noche de cobro
	    }

	    //CALCULAMOS EL COSTO TOTAL USANDO LA TARIFA UNICA
	    double montoFinal = numeroNoches * TarifaUnicaPorNoche; // Se usa la constante

	    //INFORMAMOS AL USUARIO Y CREAMOS LA RESERVA
	    System.out.println("\n--- Resumen del Costo de la Reserva ---");
	    System.out.println("Tarifa única por noche: $" + TarifaUnicaPorNoche);
	    System.out.println("Número de noches: " + numeroNoches);
	    System.out.println("---------------------------------------");
	    System.out.println("TOTAL A PAGAR: $" + montoFinal);

	    Reserva nuevaReserva = new Reserva(codigoDeReserva, rutVisitante, idAlojamiento, tipo, llegada, salida, "Activa", montoFinal);
	    this.listaReservas.add(nuevaReserva);
	    this.codigoDeReserva++;
	    System.out.println("\nReserva creada con éxito con el codigo " + nuevaReserva.getCodigoReserva());
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
			System.out.println("Tipo: " + r.getTipoAlojamiento()) ; 
			System.out.println("Estado: " + r.getEstado());
			System.out.println("MontoTotal: $" + r.getMontoTotal()) ; 
		}
	}
	
	//buscador de visitantes 
	public Visitante buscarVisitante(String rut) {
		
		for(Visitante v : this.listaVisitantes) {
			if (v.getRut().equalsIgnoreCase(rut)) return v  ; 
			
		}
		return null ; 
		
	}
	
	
	
	public void mostrarOpcionesDeAlojamiento() {
		System.out.println("\n--- Opciones de alojamiento disponible ---");
		System.out.println("Campings:") ; 
		for(Camping c : this.listaCampings) {
			System.out.println("   ID: " + c.getIdCamping() + ", Nombre: " + c.getNombre()) ; 
		}
		System.out.println("Cabañas:");
		for(Cabania cab : this.listaCabanias) {
			System.out.println("   ID: " + cab.getIdCabaña() + ", Capacidad: " + cab.getCapacidad() + " personas") ;
		}
	}
	
	public Reserva buscarReservaPorCodigo(int codigo) {
	    for (Reserva r : this.listaReservas) {
	        if (r.getCodigoReserva() == codigo) {
	            return r; // esta
	        }
	    }
	    return null; // no esta
	}
	
	
	public void cancelarReserva(int codigo) {
	    
	    Reserva reservaParaCancelar = buscarReservaPorCodigo(codigo);

	    if (reservaParaCancelar == null) {
	        System.out.println("Error: No se encontro ninguna reserva con el codigo " + codigo + ".");
	    } 
	    else if (reservaParaCancelar.getEstado().equals("Cancelada")) {
	        System.out.println("Informacion: Esta reserva ya se encontraba cancelada.");
	    } 
	    else {
	        
	        reservaParaCancelar.setEstado("Cancelada");
	        System.out.println("Exito: La reserva con codigo " + codigo + " ha sido cancelada.");
	    }
	}
	
	
	
}
