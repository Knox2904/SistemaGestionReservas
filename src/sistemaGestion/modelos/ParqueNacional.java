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
	
	
	
	

}
