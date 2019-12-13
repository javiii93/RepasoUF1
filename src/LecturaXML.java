import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LecturaXML {

	public LecturaXML(File f) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(f);
			// estos métodos podemos usarlos combinados para normalizar el archivo XML
			// doc.getDocumentElement().normalize();
			// almacenamos los nodos para luego mostrar la
			// cantidad de ellos con el método getLength()
			NodeList nList = doc.getElementsByTagName("Persona");
			System.out.println("Número de personas: " + nList.getLength());
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					System.out.println("\nPersona id: " + eElement.getAttribute("id"));
					System.out.println("Nombre: " + eElement.getElementsByTagName("Nombre").item(0).getTextContent());
					System.out.println("Edad: " + eElement.getElementsByTagName("Edad").item(0).getTextContent());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
