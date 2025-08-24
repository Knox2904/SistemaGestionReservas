package sistemaGestion.modelos;
import java.util.*;
import java.time.*;

public class Reserva {
    private int codigoReserva;
    private String rutVisitante;
    private String idAlojamiento;
    private String tipoAlojamiento;
    private LocalDate fechaLlegada; 
    private LocalDate fechaSalida;  
    private String estado;

    
    public Reserva(int codigoReserva, String rutVisitante, String idAlojamiento, String tipoAlojamiento, LocalDate fechaLlegada, LocalDate fechaSalida, String estado) {
        this.codigoReserva = codigoReserva;
        this.rutVisitante = rutVisitante;
        this.idAlojamiento = idAlojamiento;
        this.tipoAlojamiento = tipoAlojamiento;
        this.fechaLlegada = fechaLlegada;
        this.fechaSalida = fechaSalida;
        this.estado = estado;
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
    
    // setter
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
