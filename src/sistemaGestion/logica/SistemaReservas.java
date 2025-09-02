package sistemaGestion.logica;

import sistemaGestion.modelos.*;

import sistemaGestion.datos.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;


public class SistemaReservas {
	
	private static final double TarifaUnicaPorNoche = 5000; //money
	
	//crear listas para gestion
	private List <ParqueNacional> listaParques = new ArrayList<>() ; 
	private Map <String , Visitante> mapaVisitantes = new HashMap<>() ; 
	private List<Reserva> listaReservas = new ArrayList<>();
	private int codigoDeReserva = 1 ;
	
	private GestionArchivos gestorArchivos;
    private final String ARCHIVO_RESERVAS = "reservas.csv";
    private final String ARCHIVO_VISITANTES = "visitantes.csv";
	
	
	

	public SistemaReservas() {
		this.gestorArchivos = new GestionArchivos();
		
		this.listaReservas = gestorArchivos.cargarReservas(ARCHIVO_RESERVAS);
	    this.mapaVisitantes = gestorArchivos.cargarVisitantes(ARCHIVO_VISITANTES);
		
	    if (this.listaReservas.isEmpty()) {
	        this.codigoDeReserva = 1;
	    } 
	    else {
	        
	        this.codigoDeReserva = this.listaReservas.stream().mapToInt(Reserva::getCodigoReserva).max().orElse(0) + 1;
	    }
	    
	    inicializarParques();
	     
	    
	}
	
	//----------------------------------------------------
	
	
	private void inicializarParques() {
	    this.listaParques = new ArrayList<>();
	    ParqueNacional conguillio = new ParqueNacional("PN01", "Parque Nacional Conguillío");
	    ParqueNacional torresDelPaine = new ParqueNacional("PN02", "Parque Nacional Torres del Paine");
	    
	    conguillio.agregarCamping(new Camping("CAMP01", "Camping El Roble", 40));
	    torresDelPaine.agregarCamping(new Camping("CAMP02", "Camping La Laguna", 60));
	    torresDelPaine.agregarCabañas(new Cabaña("CAB01", 4));
	    
	    this.listaParques.add(torresDelPaine);
	    this.listaParques.add(conguillio);
	}
	
	
	//------------------logica programa--------------------
	
	
	public void registrarVisitante(String rut , String nombre , String email) {
		
		Visitante nuevoVisitante = new Visitante(rut, nombre, email);
		this.mapaVisitantes.put(rut, nuevoVisitante) ; 
		System.out.println("Visitante " + nombre + " registrado exitosamente") ; 
	}
	
	//----------------------------------------------------------------
	
	public void crearReserva(String rutVisitante, String idAlojamiento, String tipo, LocalDate llegada, LocalDate salida)  {
	    
		//busqueda del visitante 
	    Visitante visitante = this.buscarVisitante(rutVisitante);
	    if (visitante == null) {
	        System.out.println("Error: El visitante con RUT " + rutVisitante + " no está registrado.");
	        return; // detiene la ejecucion si no se encuentra
	    }
		
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
	    
	    if (tipo.equalsIgnoreCase("Cabaña")) {     
	        for (ParqueNacional parque : listaParques) {
	            for (Cabaña cab : parque.getListaCabañas()) {
	                if (cab.getIdCabaña().equalsIgnoreCase(idAlojamiento)) {
	                    alojamientoEncontrado = true;
	                    break; // Si la encontramos, salimos del bucle de cabañas
	                }
	            }
	            if (alojamientoEncontrado) {
	                break;
	            }
	        }
	    } else if (tipo.equalsIgnoreCase("Camping")) {
	        for (ParqueNacional parque : listaParques) {
	            for (Camping c : parque.getListaCampings()) {
	                if (c.getIdCamping().equalsIgnoreCase(idAlojamiento)) {
	                    alojamientoEncontrado = true;
	                    break; // Salimos del bucle de campings
	                }
	            }
	            if (alojamientoEncontrado) {
	                break; // Salimos del bucle de parques
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

	    Reserva nuevaReserva = new Reserva(codigoDeReserva, visitante, idAlojamiento, tipo, llegada, salida, "Activa", montoFinal);
	    
	    this.listaReservas.add(nuevaReserva);
	    this.codigoDeReserva++;
	    System.out.println("\nReserva creada con éxito para " + visitante.getNombre() + " con el código " + nuevaReserva.getCodigoReserva());
	}
	
	//-----------------------------------------------------------------------------
	
	public void mostrarTodasLasReservas() {
		
		System.out.println("\n--- Listado de Todas las Reservas ---");
		if(listaReservas.isEmpty()) {
			System.out.println("No hay reservas registradas") ; 
			return ;
		}
		
		for(Reserva r : listaReservas) {
			Visitante v = r.getVisitante() ; 
			
			System.out.println("Codigo: " + r.getCodigoReserva()) ; 
			System.out.println("Visitante: " + v.getNombre() + " (RUT: " + v.getRut() + ")");
			System.out.println("Alojamiento: " + r.getIdAlojamiento()) ; 
			System.out.println("Tipo: " + r.getTipoAlojamiento()) ; 
			System.out.println("Estado: " + r.getEstado());
			System.out.println("MontoTotal: $" + r.getMontoTotal()) ; 
		}
	}
	
	//-----------------------------------------------------------------------------
	
	public Visitante buscarVisitante(String rut) {
		return this.mapaVisitantes.get(rut);
	}
	
	//-----------------------------------------------------------------------------
	
	public void mostrarOpcionesDeAlojamiento() {
	    System.out.println("\n--- Opciones de alojamiento disponible ---");
	    // Recorre cada parque y muestra sus alojamientos
	    for (ParqueNacional parque : this.listaParques) {
	        System.out.println("\n>> Parque: " + parque.getNombre());
	        System.out.println("  Campings:");
	        for (Camping c : parque.getListaCampings()) {
	            System.out.println("    ID: " + c.getIdCamping() + ", Nombre: " + c.getNombre());
	        }
	        System.out.println("  Cabañas:");
	        for (Cabaña cab : parque.getListaCabañas()) {
	            System.out.println("    ID: " + cab.getIdCabaña() + ", Capacidad: " + cab.getCapacidad() + " personas");
	        }
	    }
	}

	
	//-----------------------------------------------------------------------------
	
	public Reserva buscarReservaPorCodigo(int codigo) {
	    for (Reserva r : this.listaReservas) {
	        if (r.getCodigoReserva() == codigo) {
	            return r; // esta
	        }
	    }
	    return null; // no esta
	}
	
	
	//-----------------------------------------------------------------------------
	
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
	
	public void cancelarReserva(Reserva reserva) {
	    if (reserva == null) {
	        System.out.println("Error: La reserva proporcionada es nula.");
	        return;
	    }

	    if (reserva.getEstado().equals("Cancelada")) {
	        System.out.println("Información: Esta reserva ya se encontraba cancelada.");
	    } else {
	        reserva.setEstado("Cancelada");
	        System.out.println("Éxito: La reserva con código " + reserva.getCodigoReserva() + " ha sido cancelada.");
	    }
	}
	
	
	//-----------------------------------------------------------------------------
	
	public List<ParqueNacional> getListaParques(){
		return listaParques ; 
	}
	
	//-----------------------------------------------------------------------------
	
	public void guardarDatosAlSalir() {
	    System.out.println("Guardando datos antes de cerrar...");
	    gestorArchivos.guardarReservas(ARCHIVO_RESERVAS, this.listaReservas);
	    gestorArchivos.guardarVisitantes(ARCHIVO_VISITANTES, this.mapaVisitantes);
	    System.out.println("Datos guardados.");
	}
	
	//-------------------------------------------------------------------------------
	
	public boolean eliminarReserva(int codigo) {
		Reserva reservaAEliminar = buscarReservaPorCodigo(codigo) ; 
		
	    if (reservaAEliminar == null) {
	        System.out.println("Error: No se encontró ninguna reserva con el código " + codigo + ".");
	        return false;
	    }

	    this.listaReservas.remove(reservaAEliminar);
	    System.out.println("Exito: La reserva con codigo " + codigo + " ha sido eliminada permanentemente.");
	    return true;

	}
	
	
	//---------------------------------------------------------------------------------
	
	public boolean editarFechas(int codigo, LocalDate nuevaLlegada, LocalDate nuevaSalida) {
		Reserva reservaAEditar = buscarReservaPorCodigo(codigo) ; 
		
		if(reservaAEditar == null ) {
	        System.out.println("Error: No se encontró ninguna reserva con el código " + codigo + ".");
	        return false;
	    }
		
		//--- validacion --- 
		if(nuevaSalida.isBefore(nuevaLlegada) || nuevaSalida.isEqual(nuevaLlegada)) {
			System.out.println("Error : La nueva salida deve ser posterior a la nueva fecha de llegada.") ; 
			
		}
		
	    reservaAEditar.setFechaLlegada(nuevaLlegada);
	    reservaAEditar.setFechaSalida(nuevaSalida);
		
	    
	    //--- recalculo y re ajustes ---
	    long numeroNoches = ChronoUnit.DAYS.between(nuevaLlegada, nuevaSalida);
	    double nuevoMonto = numeroNoches * TarifaUnicaPorNoche; 
	    reservaAEditar.setMontoTotal(nuevoMonto);

	    System.out.println("Exito: La reserva " + codigo + " ha sido actualizada.");
	    System.out.println("Nuevo total por " + numeroNoches + " noches: $" + nuevoMonto);
	    return true;
		
	}
	
	//------------------------------------------------------------------------------
	/**
	 * Filtra la lista principal de reservas y devuelve solo aquellas
	 * que pertenecen a un parque nacional específico y están activas.
	 * @param nombreParque El nombre del parque para el cual filtrar las reservas.
	 * @return Una nueva lista con las reservas que cumplen el criterio.
	 */
	public List<Reserva> filtrarReservasActivasPorParque(String nombreParque) {
	    List<Reserva> reservasFiltradas = new ArrayList<>();
	    ParqueNacional parqueSeleccionado = null;

	    //buscamos el objeto ParqueNacional que coincida con el nombre.
	    for (ParqueNacional parque : this.listaParques) {
	        if (parque.getNombre().equalsIgnoreCase(nombreParque)) {
	            parqueSeleccionado = parque;
	            break;
	        }
	    }

	    // si no se encuentra el parque, devolvemos una lista vacia.
	    if (parqueSeleccionado == null) {
	        return reservasFiltradas;
	    }

	    //recorremos todas las reservas.
	    for (Reserva reserva : this.listaReservas) {
	        //verificamos dos condiciones: que este activa y que pertenezca al parque.
	        if (reserva.getEstado().equalsIgnoreCase("Activa")) {
	        	
	            // Verificamos si el ID del alojamiento de la reserva existe en las listas del parque.
	            boolean perteneceAlParque = parqueSeleccionado.getListaCampings().stream().anyMatch(c -> c.getIdCamping().equals(reserva.getIdAlojamiento()))
	            		||parqueSeleccionado.getListaCabañas().stream().anyMatch(cab -> cab.getIdCabaña().equals(reserva.getIdAlojamiento()));
	            
	            if (perteneceAlParque) {
	                reservasFiltradas.add(reserva);
	            }
	        }
	    }

	    return reservasFiltradas;
	}
	//------------------------------------------------------------------------------
	
	
	
	
	
	
	
	

}
