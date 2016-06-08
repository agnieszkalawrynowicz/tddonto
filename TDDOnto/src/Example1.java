import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.sparql.arq.OWLOntologyDataSet;

import main.java.put.aristoteles.tddontoprotege.SimpleMockObjectTest;


public class Example1 {

	public Example1() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		File file=new File("/Users/lawrynka/Documents/workspace/owl-bgp-master-2/test/org/semanticweb/sparql/owlbgp/evaluation/ontologies/pizza.owl");
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology pizza;
		
		try {
			pizza = manager.loadOntologyFromOntologyDocument(file);
		    OWLOntologyDataSet pizzaDs;
			pizzaDs = new OWLOntologyDataSet(file);

			SimpleTestGenerator gen = new SimpleTestGenerator(manager, pizza);
			SimpleMockObjectTest t = new SimpleMockObjectTest(manager, pizza, pizzaDs);
		
			boolean res;
			for (int i=0; i<30; i++){
			
			//OWLSubClassOfAxiom axiom1 = gen.generateSubClassOfAxiom(manager, pizza, 2);
			//System.out.println("\n Axiom:"+axiom1.toString());
			
			//boolean res = t.testSubClassOf(axiom1);
			//System.out.println("\n Result:"+res);
			 
//			OWLDisjointClassesAxiom axiom2 = gen.generateDisjointClassesAxiom(manager, pizza, 2);
			
//			System.out.println("\n Axiom:"+axiom2.toString());
//			res = t.testDisjointWith(axiom2);
//			System.out.println("\n Result:"+res);

//			OWLEquivalentClassesAxiom axiom3 = gen.generateEquivalentClassesAxiom(manager, pizza, 2);
			
//			System.out.println("\n Axiom:"+axiom3.toString());
//			res = t.testEquivalentClasses(axiom3);
//			System.out.println("\n Result:"+res);
			
			OWLSubClassOfAxiom axiom4 = gen.generateSimpleExistentialQuantificationAxiom(manager, pizza, 2);
			System.out.println("\n Axiom:"+axiom4.toString());
			res = t.testSimpleExistentialQuantificationTBox(axiom4);
			System.out.println("\n Result:"+res);
		
		}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
