package sistemaGestion.modelos;

public class Cabaña { 
    private String idCabania; 
    private int capacidad;


    // constructor
    public Cabaña(String idCabania, int capacidad) {
        this.idCabania = idCabania;
        this.capacidad = capacidad;
    }

    // getter
    public String getIdCabaña() {
        return idCabania;
    }

    public int getCapacidad() {
        return capacidad;
    }
    
    @Override
    public String toString() {
    	return " Cabaña ID: " + idCabania + 
    			" | Capacidad: " + capacidad ; 
    }
}
