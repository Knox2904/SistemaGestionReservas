package sistemaGestion.modelos;

import java.time.*;


public class Reserva {
    private int codigoReserva;
    private Visitante visitante ; 
    private String idAlojamiento;
    private String tipoAlojamiento;
    private LocalDate fechaLlegada; 
    private LocalDate fechaSalida;  
    private String estado;
    private double montoTotal ; 

    
    public Reserva(int codigoReserva, Visitante visitante, String idAlojamiento, String tipoAlojamiento, LocalDate fechaLlegada, LocalDate fechaSalida, String estado, double montoTotal) {
        this.codigoReserva = codigoReserva;
        this.visitante = visitante; 
        this.idAlojamiento = idAlojamiento;
        this.tipoAlojamiento = tipoAlojamiento;
        this.fechaLlegada = fechaLlegada;
        this.fechaSalida = fechaSalida;
        this.estado = estado;
        this.montoTotal = montoTotal;
    }

    // getter
    public int getCodigoReserva() {
        return codigoReserva;
    }

    public Visitante getVisitante() {
    	return visitante ; 
    }
    
    public String getIdAlojamiento() {
        return idAlojamiento;
    }

    public String getTipoAlojamiento() {
        return tipoAlojamiento;
    }

    public LocalDate getFechaLlegada() {
        return fechaLlegada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public String getEstado() {
        return estado;
    }
    
    public double getMontoTotal() {
        return montoTotal;
    }
    
    // setter
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    @Override
    public String toString() {
    	return " Reserva codigo: " + codigoReserva + 
    			"| Visitante: " + visitante.getNombre() + 
    			"| Alojamiento: " + idAlojamiento ; 
    }
    	
}
