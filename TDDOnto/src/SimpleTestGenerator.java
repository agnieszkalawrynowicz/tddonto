import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLClass;

import com.hp.hpl.jena.ontology.ObjectProperty;

public class SimpleTestGenerator {

	/*
	 * How many URIs in a tested axiom should be from the tested ontology. 
	 */
	public static final int ZERO_EXISTING_ONTOLOGY_URIS = 0;
    public static final int ONE_EXISTING_ONTOLOGY_URI = 1;
    public static final int ALL_EXISTING_ONTOLOGY_URIS = 2;
    
    ArrayList<OWLClass> classes;
    ArrayList<OWLObjectProperty> objectProperties;
    Random rand;

    
	public SimpleTestGenerator(OWLOntologyManager manager, OWLOntology ontology) {
		classes = new ArrayList<OWLClass>();
		objectProperties = new ArrayList<OWLObjectProperty>();
		
		for (OWLClass cl : ontology.getClassesInSignature()) {
			classes.add(cl);
		}
		
		for (OWLObjectProperty op : ontology.getObjectPropertiesInSignature()){
			objectProperties.add(op);
		}
		
		rand = new Random();
	}
	
	ArrayList<OWLClass> selectRandomClasses(OWLOntologyManager manager, OWLOntology ontology, int k){
		ArrayList<OWLClass> randomClasses = new ArrayList<OWLClass>();
		OWLClass c1;
		OWLClass c2;
		
		if (k==ZERO_EXISTING_ONTOLOGY_URIS){
			//create two new classes
			c1 = manager.getOWLDataFactory().getOWLClass(IRI.create("http://semantic.cs.put.poznan.pl/Class1"));
			c2 = manager.getOWLDataFactory().getOWLClass(IRI.create("http://semantic.cs.put.poznan.pl/Class2"));
		} else if (k==ONE_EXISTING_ONTOLOGY_URI) {
			c1 = manager.getOWLDataFactory().getOWLClass(IRI.create("http://semantic.cs.put.poznan.pl/Class1"));
			c2 = classes.get(rand.nextInt(classes.size()));
		} else {
			c1 = classes.get(rand.nextInt(classes.size()));
			c2 = classes.get(rand.nextInt(classes.size()));
		}
		
		randomClasses.add(c1);
		randomClasses.add(c2);
		
		return randomClasses;
	}
	
	ArrayList<OWLObjectProperty> selectRandomObjectProperties(OWLOntologyManager manager, OWLOntology ontology, int howmany, int k){
		ArrayList<OWLObjectProperty> randomObjectProperties = new ArrayList<OWLObjectProperty>();
		ArrayList<OWLObjectProperty> ops = new ArrayList<OWLObjectProperty>();
		
		if (k==ZERO_EXISTING_ONTOLOGY_URIS){
			//create two new classes
			for (int i = 0; i < howmany; i++){
			    OWLObjectProperty op1 = manager.getOWLDataFactory().getOWLObjectProperty(IRI.create("http://semantic.cs.put.poznan.pl/ObjectProperty"+i));
			    ops.add(op1);
			}
		} else if (k==ONE_EXISTING_ONTOLOGY_URI) {
			OWLObjectProperty op1 = objectProperties.get(rand.nextInt(objectProperties.size()));
			ops.add(op1);
			for (int i = 0; i < howmany-1; i++){
				OWLObjectProperty op2 = manager.getOWLDataFactory().getOWLObjectProperty(IRI.create("http://semantic.cs.put.poznan.pl/ObjectProperty"+i));
			    ops.add(op2);
			}
		} else {
			for (int i = 0; i < howmany; i++){
				OWLObjectProperty op3 = objectProperties.get(rand.nextInt(objectProperties.size()));
			    ops.add(op3);
			}
		}
		
		return ops;
	}
	
	public OWLSubClassOfAxiom generateSubClassOfAxiom(OWLOntologyManager manager, OWLOntology ontology, int uriCoverage){
		ArrayList<OWLClass> classes = selectRandomClasses(manager, ontology, uriCoverage);

		OWLSubClassOfAxiom axiom = manager.getOWLDataFactory().getOWLSubClassOfAxiom(classes.get(0), classes.get(1));
		
		return axiom;
	}
	
	public OWLDisjointClassesAxiom generateDisjointClassesAxiom(OWLOntologyManager manager, OWLOntology ontology, int uriCoverage){
		ArrayList<OWLClass> classes = selectRandomClasses(manager, ontology, uriCoverage);

		OWLDisjointClassesAxiom axiom = manager.getOWLDataFactory().getOWLDisjointClassesAxiom(classes.get(0), classes.get(1));
		
		return axiom;
	}
	
	public OWLEquivalentClassesAxiom generateEquivalentClassesAxiom(OWLOntologyManager manager, OWLOntology ontology, int uriCoverage){
		ArrayList<OWLClass> classes = selectRandomClasses(manager, ontology, uriCoverage);

		OWLEquivalentClassesAxiom axiom = manager.getOWLDataFactory().getOWLEquivalentClassesAxiom(classes.get(0), classes.get(1));
		
		return axiom;
	}

	public OWLSubClassOfAxiom generateSimpleExistentialQuantificationAxiom(OWLOntologyManager manager, OWLOntology ontology, int uriCoverage){
		ArrayList<OWLClass> classes = selectRandomClasses(manager, ontology, uriCoverage);
		ArrayList<OWLObjectProperty> objectProperties = selectRandomObjectProperties(manager, ontology, 1, uriCoverage);

		OWLObjectSomeValuesFrom osvf = manager.getOWLDataFactory().getOWLObjectSomeValuesFrom(objectProperties.get(0), classes.get(1));
		
		OWLSubClassOfAxiom axiom = manager.getOWLDataFactory().getOWLSubClassOfAxiom(classes.get(0), osvf);
		
		return axiom;
	}

	public OWLSubClassOfAxiom generateSimpleUniversalQuantificationAxiom(OWLOntologyManager manager, OWLOntology ontology, int uriCoverage){
		ArrayList<OWLClass> classes = selectRandomClasses(manager, ontology, uriCoverage);
		ArrayList<OWLObjectProperty> objectProperties = selectRandomObjectProperties(manager, ontology, 1, uriCoverage);

		OWLObjectAllValuesFrom osvf = manager.getOWLDataFactory().getOWLObjectAllValuesFrom(objectProperties.get(0), classes.get(1));
		
		OWLSubClassOfAxiom axiom = manager.getOWLDataFactory().getOWLSubClassOfAxiom(classes.get(0), osvf);
		
		return axiom;
	}
	

	public OWLSubClassOfAxiom generateObjectPropertyDomain(OWLOntologyManager manager, OWLOntology ontology, int uriCoverage){
		ArrayList<OWLClass> classes = selectRandomClasses(manager, ontology, uriCoverage);
		ArrayList<OWLObjectProperty> objectProperties = selectRandomObjectProperties(manager, ontology, 1, uriCoverage);

		OWLObjectSomeValuesFrom osvf = manager.getOWLDataFactory().getOWLObjectSomeValuesFrom(objectProperties.get(0).getInverseProperty(), manager.getOWLDataFactory().getOWLThing());
		
		OWLSubClassOfAxiom axiom = manager.getOWLDataFactory().getOWLSubClassOfAxiom(osvf, classes.get(0));
		
		return axiom;
	}

	
	public OWLSubClassOfAxiom generateObjectPropertyRange(OWLOntologyManager manager, OWLOntology ontology, int uriCoverage){
		ArrayList<OWLClass> classes = selectRandomClasses(manager, ontology, uriCoverage);
		ArrayList<OWLObjectProperty> objectProperties = selectRandomObjectProperties(manager, ontology, 1, uriCoverage);

		OWLObjectSomeValuesFrom osvf = manager.getOWLDataFactory().getOWLObjectSomeValuesFrom(objectProperties.get(0), manager.getOWLDataFactory().getOWLThing());
		OWLSubClassOfAxiom axiom = manager.getOWLDataFactory().getOWLSubClassOfAxiom(osvf, classes.get(0));
		
		return axiom;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
