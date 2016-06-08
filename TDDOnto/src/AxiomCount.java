import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyIRIMapper;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.AutoIRIMapper;
import org.semanticweb.owlapi.util.SimpleIRIMapper;

public class AxiomCount {

	public AxiomCount() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  File dir = new File("/Users/lawrynka/Desktop/results-owl2-ontologies/");
		  PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter("/Users/lawrynka/Desktop/axiomcounts_owl2_ver2.txt"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		  
		  File[] directoryListing = dir.listFiles();
		  if (directoryListing != null) {
		    for (File child : directoryListing) {
//				File file=new File("/Users/lawrynka/Documents/workspace/owl-bgp-master-2/test/org/semanticweb/sparql/owlbgp/evaluation/ontologies/pizza.owl");
				OWLOntologyManager localManager = OWLManager.createOWLOntologyManager();
				
				OWLOntology onto;
				try {
					onto = localManager.loadOntologyFromOntologyDocument(child);
					onto.getAxiomCount();
					System.out.println("onto:"+child.getName()+" "+onto.getAxiomCount());
					pw.write(child.getName()+";"+onto.getAxiomCount()+"\n");
					
				} catch (OWLOntologyCreationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		    }
		  }
		pw.close();

	}

}
