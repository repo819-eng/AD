import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// Programa principal: menú amb el CRUD de videojocs.
// Les dades es guarden a "videojocs.dat" (fitxer binari) amb ObjectOutputStream
// i es tornen a carregar amb ObjectInputStream quan arranca el programa.
public class GestioVideojocsApp {

    // Nom del fitxer on guardem tot
    private static final String FITXER = "videojocs.dat";

    // Scanner per llegir el que escriu l'usuari (un de sol per a tot el programa)
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // 1) Al començar, carreguem el que hi hagi al fitxer (si existeix)
        ArrayList<Videojoc> videojocs = carregarVideojocs();
        System.out.println("S'han carregat " + videojocs.size() + " videojocs del fitxer.");

        int opcio;
        do {
            mostrarMenu();
            opcio = llegirEnter("Tria una opció: ");

            // Cada opció crida un mètode, així el main queda més net
            switch (opcio) {
                case 1:
                    afegirVideojoc(videojocs);
                    break;
                case 2:
                    llistarVideojocs(videojocs);
                    break;
                case 3:
                    cercarPerTitol(videojocs);
                    break;
                case 4:
                    actualitzarVideojoc(videojocs);
                    break;
                case 5:
                    eliminarVideojoc(videojocs);
                    break;
                case 6:
                    // Abans de sortir guardem tot per si de cas
                    desarVideojocs(videojocs);
                    System.out.println("Canvis guardats. Adéu!");
                    break;
                default:
                    System.out.println("Opció incorrecta, torna-ho a provar.");
            }
        } while (opcio != 6);

        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n===== GESTIÓ DE VIDEOJOCS =====");
        System.out.println("1. Afegir videojoc");
        System.out.println("2. Llistar tots els videojocs");
        System.out.println("3. Cercar videojocs per títol");
        System.out.println("4. Actualitzar un videojoc");
        System.out.println("5. Eliminar un videojoc");
        System.out.println("6. Sortir");
    }

    // ===================== CRUD =====================

    // CREATE -> demanem les dades, creem l'objecte, l'afegim a la llista i desem
    private static void afegirVideojoc(ArrayList<Videojoc> videojocs) {
        System.out.println("\n--- Afegir videojoc ---");
        String titol = llegirText("Títol: ");
        String genere = llegirText("Gènere (acció, rol, esport...): ");
        int any = llegirEnter("Any de llançament: ");
        String plataforma = llegirText("Plataforma (PC, PlayStation, Xbox, Switch...): ");
        double preu = llegirDouble("Preu: ");

        videojocs.add(new Videojoc(titol, genere, any, plataforma, preu));
        desarVideojocs(videojocs); // l'enunciat diu que es desi al fitxer després d'afegir
        System.out.println("Videojoc afegit i desat!");
    }

    // READ -> recorrem la llista i mostrem cada videojoc (fa servir el toString())
    private static void llistarVideojocs(ArrayList<Videojoc> videojocs) {
        System.out.println("\n--- Llista de videojocs ---");
        if (videojocs.isEmpty()) {
            System.out.println("No hi ha cap videojoc guardat.");
            return;
        }
        // Mostrem un número davant de cada un, així després l'usuari pot triar-lo
        for (int i = 0; i < videojocs.size(); i++) {
            System.out.println((i + 1) + ". " + videojocs.get(i));
        }
    }

    // READ (cerca) -> busquem els que CONTENEN el text, sense importar majúscules
    private static void cercarPerTitol(ArrayList<Videojoc> videojocs) {
        String text = llegirText("Text a cercar al títol: ").toLowerCase();
        boolean trobat = false;

        for (Videojoc v : videojocs) {
            // contains() fa la coincidència parcial. Passem tot a minúscules
            // perquè "zelda" també trobi "Zelda"
            if (v.getTitol().toLowerCase().contains(text)) {
                System.out.println(v);
                trobat = true;
            }
        }
        if (!trobat) {
            System.out.println("No s'ha trobat cap videojoc amb aquest títol.");
        }
    }

    // UPDATE -> l'usuari tria un videojoc de la llista i li canviem les dades
    private static void actualitzarVideojoc(ArrayList<Videojoc> videojocs) {
        int index = triarVideojoc(videojocs);
        if (index == -1) return; // no hi ha res o ha posat un número dolent

        Videojoc v = videojocs.get(index);
        System.out.println("Deixa en blanc (prem Enter) per mantenir el valor actual.");

        // Per cada camp: si l'usuari escriu alguna cosa, fem servir el setter
        String titol = llegirTextOpcional("Títol [" + v.getTitol() + "]: ");
        if (!titol.isEmpty()) v.setTitol(titol);

        String genere = llegirTextOpcional("Gènere [" + v.getGenere() + "]: ");
        if (!genere.isEmpty()) v.setGenere(genere);

        String any = llegirTextOpcional("Any [" + v.getAnyLlancament() + "]: ");
        if (!any.isEmpty()) {
            try {
                v.setAnyLlancament(Integer.parseInt(any));
            } catch (NumberFormatException e) {
                System.out.println("Any no vàlid, es manté l'anterior.");
            }
        }

        String plataforma = llegirTextOpcional("Plataforma [" + v.getPlataforma() + "]: ");
        if (!plataforma.isEmpty()) v.setPlataforma(plataforma);

        String preu = llegirTextOpcional("Preu [" + v.getPreu() + "]: ");
        if (!preu.isEmpty()) {
            try {
                // replace per si algú escriu 19,99 amb coma
                double nouPreu = Double.parseDouble(preu.replace(",", "."));
                if (nouPreu >= 0) v.setPreu(nouPreu);
                else System.out.println("El preu no pot ser negatiu, es manté l'anterior.");
            } catch (NumberFormatException e) {
                System.out.println("Preu no vàlid, es manté l'anterior.");
            }
        }

        desarVideojocs(videojocs);
        System.out.println("Videojoc actualitzat!");
    }

    // DELETE -> l'usuari tria un videojoc i el traiem de la llista
    private static void eliminarVideojoc(ArrayList<Videojoc> videojocs) {
        int index = triarVideojoc(videojocs);
        if (index == -1) return;

        Videojoc eliminat = videojocs.remove(index); // remove(index) també ens retorna l'objecte
        desarVideojocs(videojocs);
        System.out.println("S'ha eliminat: " + eliminat.getTitol());
    }

    // Mostra la llista i demana un número. Retorna la posició a l'ArrayList o -1 si no és vàlid
    private static int triarVideojoc(ArrayList<Videojoc> videojocs) {
        llistarVideojocs(videojocs);
        if (videojocs.isEmpty()) return -1;

        int num = llegirEnter("Número del videojoc: ");
        if (num < 1 || num > videojocs.size()) {
            System.out.println("Aquest número no existeix.");
            return -1;
        }
        return num - 1; // l'usuari compta des de 1, l'ArrayList des de 0
    }

    // ===================== PERSISTÈNCIA =====================

    // Llegeix tota la llista del fitxer amb ObjectInputStream.
    // Guardem l'ArrayList sencer com UN sol objecte, així no cal fer bucles
    // amb EOFException per saber quan s'acaba el fitxer.
    @SuppressWarnings("unchecked") // el cast d'Object a ArrayList<Videojoc> el compilador no el pot comprovar
    private static ArrayList<Videojoc> carregarVideojocs() {
        File fitxer = new File(FITXER);

        // Primera vegada que s'executa: encara no hi ha fitxer, tornem llista buida
        if (!fitxer.exists()) {
            return new ArrayList<>();
        }

        // try-with-resources: el stream es tanca sol en acabar (no cal fer close())
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fitxer))) {
            return (ArrayList<Videojoc>) ois.readObject(); // readObject torna Object, per això el cast
        } catch (InvalidClassException e) {
            // Passa si hem canviat la classe Videojoc i el serialVersionUID no coincideix
            System.out.println("El fitxer és d'una versió antiga de la classe: " + e.getMessage());
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            // Fitxer corrupte, buit, o que no conté el que esperem
            System.out.println("Error llegint el fitxer: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    // Escriu tota la llista al fitxer amb ObjectOutputStream.
    // Cada vegada sobreescriu el fitxer sencer amb la llista actualitzada.
    private static void desarVideojocs(ArrayList<Videojoc> videojocs) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FITXER))) {
            oos.writeObject(videojocs); // ArrayList ja és Serializable, i Videojoc també
        } catch (IOException e) {
            System.out.println("Error desant el fitxer: " + e.getMessage());
        }
    }

    // ===================== LECTURA DE TECLAT =====================
    // Llegim sempre amb nextLine() i després convertim. Així evitem el típic
    // problema de nextInt() que deixa el salt de línia al buffer, i si l'usuari
    // escriu lletres on va un número no peta el programa.

    private static String llegirText(String missatge) {
        String text;
        do {
            System.out.print(missatge);
            text = sc.nextLine().trim();
            if (text.isEmpty()) System.out.println("No pot estar buit.");
        } while (text.isEmpty());
        return text;
    }

    // Igual que l'anterior però deixa posar-ho buit (per a l'actualitzar)
    private static String llegirTextOpcional(String missatge) {
        System.out.print(missatge);
        return sc.nextLine().trim();
    }

    private static int llegirEnter(String missatge) {
        while (true) {
            System.out.print(missatge);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Has d'escriure un número enter.");
            }
        }
    }

    private static double llegirDouble(String missatge) {
        while (true) {
            System.out.print(missatge);
            try {
                double num = Double.parseDouble(sc.nextLine().trim().replace(",", "."));
                if (num >= 0) return num;
                System.out.println("El preu no pot ser negatiu.");
            } catch (NumberFormatException e) {
                System.out.println("Has d'escriure un número (ex: 59.99).");
            }
        }
    }
}
