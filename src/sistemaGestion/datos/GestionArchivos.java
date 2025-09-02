package sistemaGestion.datos;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import sistemaGestion.modelos.*;


public class GestionArchivos {
	
	public void guardarReservas(String nombreArchivo , List<Reserva> listaReservas) {
		try(PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))){
			
			//para la cabecera 
			writer.println("codigoReserva,rutVisitante,nombreVisitante,idAlojamiento,tipoAlojamiento,fechaLlegada,fechaSalida,estado,montoTotal");
			
			
			for(Reserva reserva : listaReservas) {
				//formateo para el csv 
				
				String linea = reserva.getCodigoReserva() + "," +
                        reserva.getVisitante().getRut() + "," +
                        reserva.getVisitante().getNombre() + "," +
                        reserva.getIdAlojamiento() + "," +
                        reserva.getTipoAlojamiento() + "," +
                        reserva.getFechaLlegada() + "," +
                        reserva.getFechaSalida() + "," +
                        reserva.getEstado() + "," +
                        reserva.getMontoTotal();

				writer.println(linea);
			}
			
			System.out.println("Reservas guardadas exitosamente.") ; 		
		}
		catch (IOException  e ) { // para manejar el error 
			System.err.println("Error al escrivir en el archivo: " + e.getMessage()) ; 
		}
	}
	
	
	//---------------------------------------------------------------------
	
	
	public ArrayList<Reserva> cargarReservas(String nombreArchivo) {
	    ArrayList<Reserva> reservas = new ArrayList<>();
	    
	    try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
	        String linea;
	        reader.readLine(); // saltamos la cabecera

	        while ((linea = reader.readLine()) != null) {
	            String[] datos = linea.split(",");
	            
	            //leemos los datos del visitante del archivo
	            String rutVisitante = datos[1];
	            String nombreVisitante = datos[2];
	            
	            // el email NO ESTA en el CSV 
	            Visitante visitante = new Visitante(rutVisitante, nombreVisitante, ""); 
	            
	            //leemos el resto de los datos de la reserva
	            int codigoReserva = Integer.parseInt(datos[0]);
	            String idAlojamiento = datos[3];
	            String tipoAlojamiento = datos[4];
	            LocalDate fechaLlegada = LocalDate.parse(datos[5]);
	            LocalDate fechaSalida = LocalDate.parse(datos[6]);
	            String estado = datos[7];
	            double montoTotal = Double.parseDouble(datos[8]);

	            //creamos el objeto Reserva, pasando el objeto Visitante
	            reservas.add(new Reserva(codigoReserva, visitante, idAlojamiento, tipoAlojamiento, fechaLlegada, fechaSalida, estado, montoTotal));
	        }
	        
	        System.out.println("Reservas cargadas exitosamente.");

	    } 
	    catch (FileNotFoundException e) {
	        System.out.println("Archivo de reservas no encontrado, se creará uno nuevo al guardar.");
	    } 
	    catch (Exception e) {
	        System.err.println("Error al leer o procesar el archivo de reservas: " + e.getMessage());
	    }
	    
	    return reservas;
	}
	
	
	//---------------------------------------------------------------------
	
	public void guardarVisitantes(String nombreArchivo, Map<String, Visitante> mapaVisitantes) {
	    try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
	        writer.println("rut,nombre,email");
	        for (Visitante visitante : mapaVisitantes.values()) { // for each por si aun no lo entiendes 
	            String linea = visitante.getRut() + "," +
	                           visitante.getNombre() + "," +
	                           visitante.getEmail();
	            writer.println(linea);
	        }
	    } 
	    catch (IOException e) {
	        System.err.println("Error al guardar visitantes: " + e.getMessage());
	    }
	}
	
	//---------------------------------------------------------------------
	
	
	public Map<String, Visitante> cargarVisitantes(String nombreArchivo) {
	    Map<String, Visitante> visitantes = new HashMap<>();
	    try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
	        String linea;
	        reader.readLine();
	        while ((linea = reader.readLine()) != null) {
	            String[] datos = linea.split(",");
	            if (datos.length == 3) {
	                Visitante visitante = new Visitante(datos[0], datos[1], datos[2]);
	                visitantes.put(visitante.getRut(), visitante);
	            }
	        }
	    }
	    catch (FileNotFoundException e) {
	        System.out.println("Archivo de visitantes no encontrado, se creará uno nuevo.");
	    }
	    catch (IOException e) {
	        System.err.println("Error al cargar visitantes: " + e.getMessage());
	    }
	    return visitantes;
	}
}
