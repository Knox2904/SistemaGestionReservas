package sistemaGestion.modelos;

import java.time.*;


public class Reserva {
    private int codigoReserva;
    private String rutVisitante;
    private String idAlojamiento;
    private String tipoAlojamiento;
    private LocalDate fechaLlegada; 
    private LocalDate fechaSalida;  
    private String estado;
    private double montoTotal ; 

    
    public Reserva(int codigoReserva, String rutVisitante, String idAlojamiento, String tipoAlojamiento, LocalDate fechaLlegada, LocalDate fechaSalida, String estado , double montoTotal) {
        this.codigoReserva = codigoReserva;
        this.rutVisitante = rutVisitante;
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

    public String getRutVisitante() {
        return rutVisitante;
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
    
}
