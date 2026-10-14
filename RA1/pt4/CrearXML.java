import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Parte 1 - Crea el fichero tienda.xml con una lista de videojuegos.
 */
public class CrearXML {

    public static final String FICHERO = "tienda.xml";

    public static void main(String[] args) {
        // id, titulo, genero, plataforma, precio
        String[][] videojuegos = {
            {"1", "The Legend of Zelda", "Aventura", "Switch", "59.99"},
            {"2", "Minecraft", "Sandbox", "PC", "29.99"},
            {"3", "FIFA 26", "Deportes", "PS5", "69.99"},
            {"4", "Elden Ring", "Rol", "PC", "49.99"},
            {"5", "Mario Kart 8", "Carreras", "Switch", "54.99"}
        };

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element tienda = doc.createElement("tienda");
            doc.appendChild(tienda);

            for (String[] v : videojuegos) {
                Element videojuego = doc.createElement("videojuego");
                videojuego.setAttribute("id", v[0]);
                anadirHijo(doc, videojuego, "titulo", v[1]);
                anadirHijo(doc, videojuego, "genero", v[2]);
                anadirHijo(doc, videojuego, "plataforma", v[3]);
                anadirHijo(doc, videojuego, "precio", v[4]);
                tienda.appendChild(videojuego);
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(new DOMSource(doc), new StreamResult(new File(FICHERO)));

            System.out.println("Fichero " + FICHERO + " creado correctamente con " + videojuegos.length + " videojuegos.");
        } catch (Exception e) {
            System.err.println("Error al crear el fichero XML: " + e.getMessage());
        }
    }

    private static void anadirHijo(Document doc, Element padre, String etiqueta, String valor) {
        Element hijo = doc.createElement(etiqueta);
        hijo.setTextContent(valor);
        padre.appendChild(hijo);
    }
}
