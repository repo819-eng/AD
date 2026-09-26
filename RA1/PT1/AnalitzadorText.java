import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;public class AnalitzadorText {

    public static void main(String[] args) {String nomFitxer = "text.txt";int numCaracters = 0;
        int numLinies = 0;
        int numParaules = 0;boolean dinsParaula = false;int[] frequencia = new int[65536];boolean fitxerTeContingut = false;
        char ultimCaracter = '\0';
try (FileReader fr = new FileReader(nomFitxer)) {
 int c;while ((c = fr.read()) != -1) {char caracter = (char) c;
                fitxerTeContingut = true;
                ultimCaracter = caracter;if (caracter != '\n' && caracter != '\r') {
                    numCaracters++; }

                if (caracter == '\n') {numLinies++;
                }boolean esSeparador = (caracter == ' ' || caracter == '\t'|| caracter == '\n' || caracter == '\r');if (!esSeparador) {
                    if (!dinsParaula) {
                        numParaules++;dinsParaula = true;
                    }
                } else {dinsParaula = false;
                }if (!esSeparador) {
                    frequencia[caracter]++;
                }}if (fitxerTeContingut && ultimCaracter != '\n') {
                numLinies++; }int maxFreq = 0;
            char caracterMesRepetit = '\0';
            for (int i = 0; i < frequencia.length; i++) {
                if (frequencia[i] > maxFreq) {
                    maxFreq = frequencia[i];
                    caracterMesRepetit = (char) i;}
            }System.out.println("Nombre de caràcters: " + numCaracters);
            System.out.println("Nombre de línies: " + numLinies); System.out.println("Nombre de paraules: " + numParaules);if (maxFreq > 0) {
                System.out.println("Caràcter més repetit: " + caracterMesRepetit
                        + " (apareix " +
maxFreq + " vegades)");
            } else {
                System.out.println("No hi ha cap caràcter per comptar.");}

        } catch (FileNotFoundException e) {System.out.println("El fitxer no existeix.");} catch (IOException e) {
            System.out.println("S'ha produït un error de lectura: " + e.getMessage()); } catch (SecurityException e) {
            System.out.println("No tens permisos per accedir al fitxer.");
        }}
}
