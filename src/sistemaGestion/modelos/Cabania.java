package sistemaGestion.modelos;

public class Cabania { 
    private String idCabania; 
    private int capacidad;


    // constructor
    public Cabania(String idCabania, int capacidad) {
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

}
