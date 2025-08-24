package sistemaGestion.modelos;
//todo
//zona
import java.util.*;

public class Camping {
    private String idCamping;
    private String nombre;
    private int totalSitios;
    private double tarifaNoche;

    public Camping(String idCamping , String nombre , int totalSitios , double tarifaNoche) {
    	
        this.idCamping = idCamping;
        this.nombre = nombre;
        this.totalSitios = totalSitios;
        this.tarifaNoche = tarifaNoche;

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

    public double getTarifaNoche() {
        return tarifaNoche;
    }
    
}
