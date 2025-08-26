package sistemaGestion.modelos;

import java.util.*;


public class ParqueNacional {
	
	private String idParque ; 
	private String nombre ; 
	private ArrayList<Camping> listaCampings ; 
	private ArrayList<Cabania> listaCabanias ; 
	
	public ParqueNacional(String idParque , String nombre) {
		this.idParque = idParque ; 
		this.nombre = nombre ; 
		this.listaCabanias = new ArrayList<>() ; 
		this.listaCampings = new ArrayList<>();
		
		
	}
	
	
	public void agregarCamping(Camping camping) {
		listaCampings.add(camping) ; 
		
	}
	
	
	public void agregarCabania(Cabania cabania) {
		listaCabanias.add(cabania) ; 
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
	
	public ArrayList<Cabania> getListaCabanias () {
		return listaCabanias ; 
	}	
	
	
	
	

}
