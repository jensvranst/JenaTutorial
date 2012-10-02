import javax.annotation.Resource;

import org.omg.CORBA.portable.InputStream;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

public class HelloOwlWorld {

	static String inputFileName  = "/Users/jens/Downloads/pizza.owl";
	/**
	 * @param args
	 */
	public static void firstSteps() {
		Model model = ModelFactory.createDefaultModel();

		// use the FileManager to find the input file
		java.io.InputStream in = FileManager.get().open("/Users/jens/Downloads/pizza.owl");

		if (in == null) {
			throw new IllegalArgumentException("File: " + inputFileName	+ " not found");
		}else {
			System.out.print("owl file loaded.");			
		}
		
		// read the RDF/XML file
		model.read(in, null);

		// write it to standard out
		model.write(System.out);
		
		com.hp.hpl.jena.rdf.model.Resource vcard = model.getResource("cheesyPizza");
		
		System.out.print(vcard);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		firstSteps();
	}

}
