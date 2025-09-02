package sistemaGestion.modelos;

import java.util.*;


public class ParqueNacional {
	
	private String idParque ; 
	private String nombre ; 
	private ArrayList<Camping> listaCampings ; 
	private ArrayList<Cabaña> listaCabañas ; 
	
	public ParqueNacional(String idParque , String nombre) {
		this.idParque = idParque ; 
		this.nombre = nombre ; 
		this.listaCabañas = new ArrayList<>() ; 
		this.listaCampings = new ArrayList<>();
		
		
	}
	
	
	public void agregarCamping(Camping camping) {
		listaCampings.add(camping) ; 
		
	}
	
	public void agregarCamping(String id, String nombre, int sitios) {
	    Camping nuevoCamping = new Camping(id, nombre, sitios);
	    this.listaCampings.add(nuevoCamping);
	}
	
	
	public void agregarCabañas(Cabaña cabaña) {
		listaCabañas.add(cabaña) ; 
	}
	
	
	public String getNombre() {
		return nombre ; 
	}
	
	public String getId() {
		return idParque ; 
	
	}
	
	public ArrayList<Camping> getListaCampings () {
		return listaCampings ; 
	}
	
	public ArrayList<Cabaña> getListaCabañas () {
		return listaCabañas ; 
	}	
	
	
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder() ; 
        
        sb.append("========================================\n");
        sb.append("Parque Nacional: ").append(this.nombre);
        sb.append(" (ID: ").append(this.idParque).append(")\n");
        sb.append("========================================\n");
        
        
        sb.append("--- Campings Disponibles ---\n") ; 
        if(this.listaCampings.isEmpty()) {
        	sb.append("\tNo hay campings disponibles \n") ; 
        }
        
    	else {
    		for (Camping camping : this.listaCampings) {
    			sb.append("\t- ").append(camping.toString()).append("\n");
    		}
    	}

    	sb.append("\n--- Cabañas Disponibles ---\n");
    	if (this.listaCabañas.isEmpty()) {
    		sb.append("\tNo hay cabañas registradas\n");
    	} 
    	else {
    		for (Cabaña cabana : this.listaCabañas) {
    			
    			sb.append("\t- ").append(cabana.toString()).append("\n");
    		}
    	}
    	
    	return sb.toString();
    }
}
