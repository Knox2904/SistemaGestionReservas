package sistemaGestion.modelos;

public class Cabaña extends Alojamiento{ 
    
	
    private int capacidad;


    // constructor
    public Cabaña(String idCabaña, int capacidad) {
        super(idCabaña) ; 
        this.capacidad = capacidad;
    }

    // getter

    public int getCapacidad() {
        return capacidad;
    }
    
    
    
    //setter
    public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
    
    
    
    @Override
    public String toString() {
    	return " Cabaña ID: " + id + 
    			" | Capacidad: " + capacidad ; 
    }
    

	@Override
    public String getDescripcion() {
        return "Cabaña con capacidad para " + this.capacidad + " personas.";
    }
}
