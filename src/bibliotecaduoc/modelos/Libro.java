package bibliotecaduoc.modelos;

public class Libro {

    // Atributos
    private int indiceLibro;
    private String nombre;
    private String autor;
    private Boolean disponible = true;
    private String rutPrestamista;

    // Constructor
    public Libro(int indiceLibro, String nombre, String autor, Boolean disponible) {
        this.indiceLibro = indiceLibro;
        this.nombre = nombre;
        this.autor = autor;
        this.disponible = disponible;
        this.rutPrestamista = null;
    }

    // Getters
    public int getIndiceLibro() {
        return indiceLibro;
    }
    public String getNombre() {
        return nombre;
    }
    public String getAutor() {
        return autor;
    }
    public boolean isDisponible() {
        return disponible;
    }
    public String getRutPrestamista() {
        return rutPrestamista;
    }

    // Setters
    public void setIndiceLibro(int indiceLibro) {
        this.indiceLibro = indiceLibro;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }
    
    public void prestarA(String rut) {
        this.disponible = false;
        this.rutPrestamista = rut;
    }
    
    public void devolver() {
        this.disponible = true;
        this.rutPrestamista = null;
    }    
}
