import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Parte 2 - Lee tienda.xml y muestra los videojuegos por pantalla.
 * Extra: cuenta los videojuegos y muestra solo los de la plataforma "PC".
 */
public class LeerXML {

    public static void main(String[] args) {
        File fichero = new File(CrearXML.FICHERO);
        if (!fichero.exists()) {
            System.err.println("No se ha encontrado " + CrearXML.FICHERO + ". Ejecuta primero CrearXML.");
            return;
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Evita cargar entidades externas (protección contra ataques XXE)
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(fichero);
            doc.getDocumentElement().normalize();

            NodeList videojuegos = doc.getElementsByTagName("videojuego");

            System.out.println("===== VIDEOJUEGOS DE LA TIENDA =====");
            for (int i = 0; i < videojuegos.getLength(); i++) {
                mostrarVideojuego((Element) videojuegos.item(i));
            }

            // Extra 1: contar videojuegos
            System.out.println("Total de videojuegos en la tienda: " + videojuegos.getLength());
            System.out.println();

            // Extra 2: solo los de PC
            System.out.println("===== VIDEOJUEGOS DE PC =====");
            int dePC = 0;
            for (int i = 0; i < videojuegos.getLength(); i++) {
                Element videojuego = (Element) videojuegos.item(i);
                if ("PC".equalsIgnoreCase(texto(videojuego, "plataforma"))) {
                    mostrarVideojuego(videojuego);
                    dePC++;
                }
            }
            if (dePC == 0) {
                System.out.println("No hay videojuegos de PC.");
            }
        } catch (Exception e) {
            System.err.println("Error al leer el fichero XML: " + e.getMessage());
        }
    }

    private static void mostrarVideojuego(Element videojuego) {
        System.out.println("Videojuego #" + videojuego.getAttribute("id"));
        System.out.println("Título: " + texto(videojuego, "titulo"));
        System.out.println("Género: " + texto(videojuego, "genero"));
        System.out.println("Plataforma: " + texto(videojuego, "plataforma"));
        System.out.println("Precio: " + texto(videojuego, "precio") + " €");
        System.out.println();
    }

    // Devuelve el texto de una etiqueta hija, o "-" si no existe
    private static String texto(Element padre, String etiqueta) {
        NodeList lista = padre.getElementsByTagName(etiqueta);
        return lista.getLength() > 0 ? lista.item(0).getTextContent().trim() : "-";
    }
}
