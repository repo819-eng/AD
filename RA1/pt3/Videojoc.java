import java.io.Serializable;

// Classe que representa un videojoc.
// Implementa Serializable perquè ObjectOutputStream la pugui guardar al fitxer.
// Si no posem "implements Serializable", en desar ens saltaria una NotSerializableException.
public class Videojoc implements Serializable {

    // Número de versió de la classe. Serveix perquè Java sàpiga si l'objecte
    // guardat al fitxer és compatible amb la classe actual.
    // Si no el posem, Java en calcula un automàticament i si canviem la classe
    // (per exemple afegint un atribut) ja no podríem llegir el fitxer antic.
    private static final long serialVersionUID = 1L;

    // Atributs (els que demana l'enunciat)
    private String titol;
    private String genere;
    private int anyLlancament;
    private String plataforma;
    private double preu;

    // Constructor: crea un videojoc amb totes les dades
    public Videojoc(String titol, String genere, int anyLlancament, String plataforma, double preu) {
        this.titol = titol;
        this.genere = genere;
        this.anyLlancament = anyLlancament;
        this.plataforma = plataforma;
        this.preu = preu;
    }

    // ----- Getters i setters -----

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public int getAnyLlancament() {
        return anyLlancament;
    }

    public void setAnyLlancament(int anyLlancament) {
        this.anyLlancament = anyLlancament;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public double getPreu() {
        return preu;
    }

    public void setPreu(double preu) {
        this.preu = preu;
    }

    // toString(): com es mostra el videojoc quan fem System.out.println(videojoc)
    @Override
    public String toString() {
        return "Títol: " + titol
                + " | Gènere: " + genere
                + " | Any: " + anyLlancament
                + " | Plataforma: " + plataforma
                + " | Preu: " + String.format("%.2f", preu) + " €";
    }
}
