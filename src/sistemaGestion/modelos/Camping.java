package sistemaGestion.modelos;
//todo
//zona


public class Camping extends Alojamiento{
    
	
    private String nombre;
    private int totalSitios;

    public Camping(String idCamping , String nombre , int totalSitios) {
    	
        super(idCamping) ; 
        this.nombre = nombre;
        this.totalSitios = totalSitios;


    }
    
    // getter

    public String getNombre() {
        return nombre;
    }

    public int getTotalSitios() {
        return totalSitios;
    }
    

    
    //setter

    public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setTotalSitios(int totalSitios) {
		this.totalSitios = totalSitios;
	}

	@Override
    public String toString() {
        return "Camping ID: " + id + " | Nombre: " + nombre + " | Sitios: " + totalSitios;
    }
    
    @Override
    public String getDescripcion() {
        return "Camping: " + this.nombre + " con " + this.totalSitios + " sitios.";
    }
    
}
