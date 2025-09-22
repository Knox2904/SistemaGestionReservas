package sistemaGestion.logica;

import sistemaGestion.modelos.*;

import sistemaGestion.datos.*;
import sistemaGestion.exceptions.EntidadNoEncontradaException;
import sistemaGestion.exceptions.ReglaDeNegocioException;

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
	
	public void crearReserva(String rutVisitante, String idAlojamiento, LocalDate llegada, LocalDate salida) throws ReglaDeNegocioException, EntidadNoEncontradaException {
	    Visitante visitante = this.buscarVisitante(rutVisitante);
	    if (visitante == null) {
	        throw new EntidadNoEncontradaException("El visitante con RUT " + rutVisitante + " no está registrado.");
	    }
	    
	    // Validaciones de fechas
	    if (llegada.isBefore(LocalDate.now())) {
	        throw new ReglaDeNegocioException("La fecha de llegada no puede ser una fecha pasada.");
	    }
	    if (salida.isBefore(llegada) || salida.isEqual(llegada)) {
	        throw new ReglaDeNegocioException("La fecha de salida debe ser posterior a la de llegada.");
	    }

	    // --- LOGICA MEJORADA ---
	    String tipoAlojamientoEncontrado = null;
	    for (ParqueNacional parque : listaParques) {
	        if (parque.getListaCampings().stream().anyMatch(c -> c.getId().equalsIgnoreCase(idAlojamiento))) {
	            tipoAlojamientoEncontrado = "Camping";
	            break;
	        }
	        if (parque.getListaCabañas().stream().anyMatch(cab -> cab.getId().equalsIgnoreCase(idAlojamiento))) {
	            tipoAlojamientoEncontrado = "Cabaña";
	            break;
	        }
	    }
	    
	    if (tipoAlojamientoEncontrado == null) {
	        throw new EntidadNoEncontradaException("El ID de alojamiento '" + idAlojamiento + "' no fue encontrado.");
	    }
	    // --- FIN DE LA LOGICA MEJORADA ---

	    // Calculamos noches y costo
	    long numeroNoches = ChronoUnit.DAYS.between(llegada, salida);
	    double montoFinal = numeroNoches * TarifaUnicaPorNoche;

	    Reserva nuevaReserva = new Reserva(codigoDeReserva, visitante, idAlojamiento, tipoAlojamientoEncontrado, llegada, salida, "Activa", montoFinal);
	    this.listaReservas.add(nuevaReserva);
	    this.codigoDeReserva++;
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
	
	/**
	 * Genera un String formateado con la lista de todos los parques y sus respectivos alojamientos.
	 * @return Un String con el reporte completo de alojamientos.
	 */
	public String obtenerTextoAlojamientos() {
	    StringBuilder reporte = new StringBuilder();
	    reporte.append("--- Opciones de Alojamiento Disponible ---\n");

	    for (ParqueNacional parque : this.listaParques) {
	        reporte.append("\n>> Parque: ").append(parque.getNombre()).append("\n");
	        
	        reporte.append("  Campings:\n");
	        if (parque.getListaCampings().isEmpty()) {
	            reporte.append("    (No hay campings registrados)\n");
	        } else {
	            for (Camping c : parque.getListaCampings()) {
	                reporte.append("    - ").append(c.toString()).append("\n");
	            }
	        }
	        
	        reporte.append("  Cabañas:\n");
	        if (parque.getListaCabañas().isEmpty()) {
	            reporte.append("    (No hay cabañas registradas)\n");
	        } else {
	            for (Cabaña cab : parque.getListaCabañas()) {
	                reporte.append("    - ").append(cab.toString()).append("\n");
	            }
	        }
	    }
	    return reporte.toString();
	}

	
	//-----------------------------------------------------------------------------
	
	public Reserva buscarReservaPorCodigo(int codigo) throws EntidadNoEncontradaException {
	    for (Reserva r : this.listaReservas) {
	        if (r.getCodigoReserva() == codigo) {
	            return r; // Se encontró
	        }
	    }
	    
	    throw new EntidadNoEncontradaException("No se encontro ninguna reserva con el codigo " + codigo + ".");
	}
	
	
	//-----------------------------------------------------------------------------
	
	public void cancelarReserva(int codigo) throws EntidadNoEncontradaException {

	    Reserva reservaParaCancelar = buscarReservaPorCodigo(codigo);

	    if (reservaParaCancelar.getEstado().equals("Cancelada")) {
	        System.out.println("Información: Esta reserva ya se encontraba cancelada.");
	    } 
	    else {
	        reservaParaCancelar.setEstado("Cancelada");
	        System.out.println("Éxito: La reserva con código " + codigo + " ha sido cancelada.");
	    }
	}
	
	//-----------------------------------------------------------------------------
	
	public void cancelarReserva(Reserva reserva) {
	    if (reserva == null) {
	        System.out.println("Error: La reserva proporcionada es nula.");
	        return;
	    }

	    if (reserva.getEstado().equals("Cancelada")) {
	        System.out.println("Información: Esta reserva ya se encontraba cancelada.");
	    } 
	    else {
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
	
	public boolean eliminarReserva(int codigo) throws EntidadNoEncontradaException {
	    
	    Reserva reservaAEliminar = buscarReservaPorCodigo(codigo);

	    this.listaReservas.remove(reservaAEliminar);
	    System.out.println("Éxito: La reserva con código " + codigo + " ha sido eliminada permanentemente.");
	    return true;
	}
	
	
	//---------------------------------------------------------------------------------
	
	public boolean editarFechasReserva(int codigo, LocalDate nuevaLlegada, LocalDate nuevaSalida) throws EntidadNoEncontradaException, ReglaDeNegocioException {
	    
	    Reserva reservaAEditar = buscarReservaPorCodigo(codigo);
	    
	    // --- Validacion ---
	    if (nuevaSalida.isBefore(nuevaLlegada) || nuevaSalida.isEqual(nuevaLlegada)) {
	        throw new ReglaDeNegocioException("La nueva fecha de salida debe ser posterior a la nueva fecha de llegada.");
	    }

	    reservaAEditar.setFechaLlegada(nuevaLlegada);
	    reservaAEditar.setFechaSalida(nuevaSalida);
	    
	    // --- Recalculo y reajustes ---
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
	            boolean perteneceAlParque = parqueSeleccionado.getListaCampings().stream().anyMatch(c -> c.getId().equals(reserva.getIdAlojamiento()))
	            		||parqueSeleccionado.getListaCabañas().stream().anyMatch(cab -> cab.getId().equals(reserva.getIdAlojamiento()));
	            
	            if (perteneceAlParque) {
	                reservasFiltradas.add(reserva);
	            }
	        }
	    }

	    return reservasFiltradas;
	}
	//------------------------------------------------------------------------------
	
	public void eliminarParqueNacional(String nombreParque) throws ReglaDeNegocioException, EntidadNoEncontradaException {
	    
	    List<Reserva> reservasDelParque = filtrarReservasActivasPorParque(nombreParque);
	    if (!reservasDelParque.isEmpty()) {
	        throw new ReglaDeNegocioException("No se puede eliminar el parque '" + nombreParque + "' porque tiene " + reservasDelParque.size() + " reservas activas.");
	    }

	    // si no tiene reservas, procedemos a eliminarlo
	    ParqueNacional parqueAEliminar = null;
	    for (ParqueNacional parque : this.listaParques) {
	        if (parque.getNombre().equalsIgnoreCase(nombreParque)) {
	            parqueAEliminar = parque;
	            break;
	        }
	    }

	    if (parqueAEliminar == null) {
	        throw new EntidadNoEncontradaException("No se encontro el parque con el nombre '" + nombreParque + "'.");
	    }

	    this.listaParques.remove(parqueAEliminar);
	    System.out.println("Parque '" + nombreParque + "' eliminado exitosamente.");
	}
	
	
	
	
	//------------------------------------------------------------------------------
	
	public List<Reserva> getListaReservas() { 
		return this.listaReservas; 
		}
	
	//------------------------------------------------------------------------------
	
	public Map<String, Visitante> getMapaVisitantes() {
	    return this.mapaVisitantes;
	}
	//------------------------------------------------------------------------------
}
