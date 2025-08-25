package sistemaGestion.modelos;


public class Visitante {
    private String rut;
    private String nombre;
    private String email;
    
    public Visitante (String rut , String nombre , String email){
    	this.rut = rut ; 
    	this.nombre = nombre ; 
    	this.email = email ; 
    }
    
    //getters
    
    public String getRut() {
        return rut;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }    
    
    
    
}
