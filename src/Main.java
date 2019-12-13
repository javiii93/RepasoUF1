import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Main {

	public static void main(String[] args) {
		
		// escrituraArchivoBinario();
		//ArrayList<Persona> arrPers = lecturaFicheroBinario();
		//escrituraXml(arrPers);
		File f=new File("personas.xml");
		@SuppressWarnings("unused")
		LecturaXML lecturaXml=new LecturaXML(f);
		
	}

	public static void escrituraArchivoBinario() {
		ArrayList<Persona> arrPers = new ArrayList<Persona>();
		Persona p1 = new Persona("MariPili", 33);
		Persona p2 = new Persona("MariJuana", 38);
		arrPers.add(p1);
		arrPers.add(p2);
		ObjectOutputStream salida = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("fichPersona.dat");
			salida = new ObjectOutputStream(fos);
			for (int i = 0; i < arrPers.size(); i++) {
				salida.writeObject(arrPers.get(i));
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (fos != null)
					fos.close();
				if (salida != null)
					salida.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

public static void escrituraXml(ArrayList<Persona> arrPers) {
	 try {
	      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

	      //Elemento raíz
	      Document doc = docBuilder.newDocument();
	      Element rootElement = doc.createElement("Personas");
	      doc.appendChild(rootElement);
	      for (int i=0;i<arrPers.size();i++) {
	    	   //Primer elemento
		      Element persona = doc.createElement("Persona");
		      //Se agrega un atributo al nodo elemento y su valor
		      Attr attr = doc.createAttribute("id");
		      attr.setValue(String.valueOf(i+1));
		      persona.setAttributeNode(attr);
		      Element nombrePersona=doc.createElement("Nombre");
		      nombrePersona.appendChild(doc.createTextNode(arrPers.get(i).getNombre()));
		      persona.appendChild(nombrePersona);
		      
		      Element edadPersona=doc.createElement("Edad");
		      edadPersona.appendChild(doc.createTextNode(String.valueOf(arrPers.get(i).getEdad())));
		      persona.appendChild(edadPersona);
		      
		      rootElement.appendChild(persona);
	      }
	   

	       //Se escribe el contenido del XML en un archivo
	      TransformerFactory transformerFactory = TransformerFactory.newInstance();
	      Transformer transformer = transformerFactory.newTransformer();
	      DOMSource source = new DOMSource(doc);
	      StreamResult result = new StreamResult(new File("personas.xml"));
	      transformer.transform(source, result);
	      System.out.println("Se ha creado el xml correctamente");
	    } catch (ParserConfigurationException pce) {
	      pce.printStackTrace();
	    } catch (TransformerException tfe) {
	      tfe.printStackTrace();
	    }
}

	public static ArrayList<Persona> lecturaFicheroBinario() {
		ArrayList<Persona> arrPers = new ArrayList<Persona>();
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream("fichPersona.dat"));
			// Se lee el primer objeto
			Object aux = ois.readObject();
			// Mientras haya objetos
			while (aux != null) {
				if (aux instanceof Persona)
					System.out.println(aux);
				arrPers.add((Persona) aux);
				// Se escribe en pantalla el objeto
				try {
					aux = ois.readObject();
				} catch (Exception ex) {
					System.out.println("Ya no quedan objetos que leer");
					aux = null;
				}
			}
			ois.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arrPers;
	}
	/*public static void creacionXml(ArrayList<Persona> arrPers) {

	try {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		builder = factory.newDocumentBuilder();
		DOMImplementation implementation = builder.getDOMImplementation();
		Document document = implementation.createDocument(null, "personaxml", null);
		document.setXmlVersion("1.0");
		// Main Node
		Element raiz = document.getDocumentElement();
		// Por cada key creamos un item que contendrá la key y el value
		for (int i = 0; i < arrPers.size(); i++) {
			// Item Node
			Element PersonaNode = document.createElement("Persona " + String.valueOf((i + 1)));
			// Nombre Node
			Element nombreNode = document.createElement("nombre");
			Text nodeNombreValue = document.createTextNode(arrPers.get(i).getNombre());
			nombreNode.appendChild(nodeNombreValue);
			// Edad Node
			Element edadNode = document.createElement("Edad");
			Text nodeEdadValue = document.createTextNode(String.valueOf(arrPers.get(i).getEdad()));
			edadNode.appendChild(nodeEdadValue);
			// append keyNode and valueNode to itemNode
			PersonaNode.appendChild(PersonaNode);
			PersonaNode.appendChild(edadNode);
			// append itemNode to raiz
			raiz.appendChild(PersonaNode); // pegamos el elemento a la raiz "Documento"
		}
		// Generate XML
		Source source = new DOMSource(document);
		// Indicamos donde lo queremos almacenar
		Result result = new StreamResult(new java.io.File("personas.xml")); // nombre del archivo
		Transformer transformer;
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, result);
			System.out.println("se ha creado el XML con exito.");

		} catch (TransformerFactoryConfigurationError | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} catch (ParserConfigurationException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

}*/
}
