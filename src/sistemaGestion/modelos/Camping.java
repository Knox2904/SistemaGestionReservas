package sistemaGestion.modelos;
//todo
//zona


public class Camping {
    private String idCamping;
    private String nombre;
    private int totalSitios;

    public Camping(String idCamping , String nombre , int totalSitios) {
    	
        this.idCamping = idCamping;
        this.nombre = nombre;
        this.totalSitios = totalSitios;


    }
    
    // getter
    
    public String getIdCamping() {
        return idCamping;
    }

    public String getNombre() {
        return nombre;
    }

    public int getTotalSitios() {
        return totalSitios;
    }


    
}
