package sistemaGestion.parques; 

import sistemaGestion.logica.SistemaReservas;
import sistemaGestion.vista.VentanaPrincipal;

public class Main {


    public static void main(String[] args) {

        SistemaReservas sistema = new SistemaReservas();

        VentanaPrincipal ventana = new VentanaPrincipal(sistema);

        ventana.setVisible(true);
    }
}