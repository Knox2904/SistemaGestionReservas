package sistemaGestion.modelos;

public class Cabania { 
    private String idCabania; 
    private int capacidad;
    private double tarifaNoche;

    // CONSTRUCTOR
    public Cabania(String idCabania, int capacidad, double tarifaNoche) {
        this.idCabania = idCabania;
        this.capacidad = capacidad;
        this.tarifaNoche = tarifaNoche;
    }

    // getter
    public String getIdCabaña() {
        return idCabania;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public double getTarifaNoche() {
        return tarifaNoche;
    }
}
