package sistemaGestion.modelos;

public abstract class Alojamiento {
	
	protected String id ; 
	
	public Alojamiento(String id) {
		this.id = id ; 
		
	}
	
	public String getId() {
		return id ; 
	}
	
	public void setId(String id) {
		this.id = id ; 
	}
	
	public abstract String getDescripcion();

}
