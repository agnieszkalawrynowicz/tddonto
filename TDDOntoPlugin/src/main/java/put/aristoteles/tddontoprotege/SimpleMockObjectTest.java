package put.aristoteles.tddontoprotege;
/**
 * @author agalawrynowicz
 */

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

//import org.coode.owlapi.rdf.renderer.RDFRendererBase;
//import org.coode.owlapi.turtle.TurtleOntologyFormat;
//import org.coode.owlapi.turtle.TurtleRenderer;
//import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
//import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
//import org.semanticweb.owlapi.io.StreamDocumentTarget;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.RemoveAxiom;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
//import org.semanticweb.owlapi.reasoner.OWLReasoner;
//import org.semanticweb.sparql.OWLReasonerSPARQLEngine;
//import org.semanticweb.sparql.arq.OWLOntologyDataSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
//import org.semanticweb.HermiT.Reasoner;

//import com.hp.hpl.jena.query.Query;
//import com.hp.hpl.jena.query.QueryFactory;
//import com.hp.hpl.jena.query.ResultSet;

import org.apache.log4j.Logger;



public class SimpleMockObjectTest {

    
    protected final OWLOntologyManager manager;
    protected final OWLDataFactory factory;
    protected OWLOntology testedOntology;

    OWLReasoner reasoner;
    
    public static final String LS = System.getProperty("line.separator");
    public static final String IND = "http://semantic.cs.put.poznan.pl/Individual";
    
    
    public SimpleMockObjectTest(OWLOntologyManager manager, OWLOntology testedOntology, OWLReasoner reasoner) {
        this.manager = manager;
        this.factory=manager.getOWLDataFactory();
        this.testedOntology = testedOntology;
        this.reasoner = reasoner;
    }


	public boolean testSubClassOf(OWLSubClassOfAxiom axiom) {
        boolean result = false;

		try {
			
			OWLClassExpression subClass = axiom.getSubClass();
			OWLClassExpression superClass = axiom.getSuperClass();
			
			OWLIndividual indA = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualA"));
			OWLClassAssertionAxiom axiom1 = factory.getOWLClassAssertionAxiom(subClass, indA);

			AddAxiom addAxiom1 = new AddAxiom(testedOntology, axiom1);
			manager.applyChange(addAxiom1);
			reasoner.flush();

			NodeSet<OWLNamedIndividual> instances = reasoner.getInstances(superClass, false);
			Iterator it = instances.iterator();
			
			while (it.hasNext() && !result){
				
				Node<OWLNamedIndividual> instanceNode = (Node<OWLNamedIndividual>) it.next();
				Iterator it2 = instanceNode.iterator();
				
				while (it2.hasNext() && !result){
					OWLNamedIndividual instance = (OWLNamedIndividual) it2.next();
					
					if (instance.equals(indA)){
						result = true;
					}
				}
			}
			// clean up
			RemoveAxiom removeAxiom1 = new RemoveAxiom(testedOntology, axiom1);
			manager.applyChange(removeAxiom1);
			reasoner.flush();
//			manager.saveOntology(testedOntology);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	
	
	
	public boolean testDisjointWith(OWLDisjointClassesAxiom axiom) {
        boolean result = false;

		try {
			Set<OWLClassExpression> classes = axiom.getClassExpressions();
			
			OWLIndividual indA = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualA"));
			Set<OWLClassAssertionAxiom> axioms = new HashSet<OWLClassAssertionAxiom>();
			for (Iterator it=classes.iterator(); it.hasNext();){		
				OWLClassAssertionAxiom axiom1 = factory.getOWLClassAssertionAxiom((OWLClassExpression)it.next(), indA);
				axioms.add(axiom1);
				AddAxiom addAxiom1 = new AddAxiom(testedOntology, axiom1);
				manager.applyChange(addAxiom1);
			}
			
			reasoner.flush();
			if (reasoner.isConsistent()){
				result = false;
			} else {
				result = true;
			}
			
			// clean up
			for (Iterator it=axioms.iterator(); it.hasNext();){		
				RemoveAxiom removeAxiom1 = new RemoveAxiom(testedOntology, (OWLAxiom)it.next());
				manager.applyChange(removeAxiom1);
//			manager.saveOntology(testedOntology);
			}
			reasoner.flush();
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	
	public boolean testEquivalentClasses(OWLEquivalentClassesAxiom axiom) {
        boolean result = false;

		try {
			OWLClassExpression c = axiom.getClassExpressionsAsList().get(0);
			OWLClassExpression d = axiom.getClassExpressionsAsList().get(1);
			
			OWLIndividual indA = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualA"));

			OWLClassAssertionAxiom axiomC = factory.getOWLClassAssertionAxiom(c, indA);
			AddAxiom addAxiomC = new AddAxiom(testedOntology, axiomC);
			manager.applyChange(addAxiomC);
			reasoner.flush();
				
			NodeSet<OWLNamedIndividual> instancesD = reasoner.getInstances(d, false);
			Iterator itD = instancesD.iterator();
			
			while (itD.hasNext() && !result){
				
				Node<OWLNamedIndividual> instanceNode = (Node<OWLNamedIndividual>) itD.next();
				Iterator it2 = instanceNode.iterator();
				
				while (it2.hasNext() && !result){
					OWLNamedIndividual instance = (OWLNamedIndividual) it2.next();
					
					if (instance.equals(indA)){
						result = true;
					}
				}
			}
			// clean up
			RemoveAxiom removeAxiomC = new RemoveAxiom(testedOntology, axiomC);
			manager.applyChange(removeAxiomC);
			reasoner.flush();
//			manager.saveOntology(testedOntology);
			
			if (result==false){
				return false;
			} else {
				
				result = false;
				
				OWLClassAssertionAxiom axiomD = factory.getOWLClassAssertionAxiom(d, indA);
				AddAxiom addAxiomD = new AddAxiom(testedOntology, axiomD);
				manager.applyChange(addAxiomD);
				reasoner.flush();
				
				NodeSet<OWLNamedIndividual> instancesC = reasoner.getInstances(c, false);
				Iterator itC = instancesC.iterator();
			
				while (itC.hasNext() && !result){
					Node<OWLNamedIndividual> instanceNode = (Node<OWLNamedIndividual>) itC.next();
					Iterator it2 = instanceNode.iterator();
				
					while (it2.hasNext() && !result){
						OWLNamedIndividual instance = (OWLNamedIndividual) it2.next();
					
						if (instance.equals(indA)){
							result = true;
						}
					}
				}
				// clean up
				RemoveAxiom removeAxiomD = new RemoveAxiom(testedOntology, axiomD);
				manager.applyChange(removeAxiomD);
				reasoner.flush();
//			manager.saveOntology(testedOntology);
		}
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	
	public boolean testSimpleExistentialQuantification(OWLSubClassOfAxiom axiom) {
        boolean result = false;

		try {
			OWLClassExpression c = axiom.getSubClass();
			OWLObjectSomeValuesFrom rhs = (OWLObjectSomeValuesFrom) axiom.getSuperClass();
			OWLClassExpression d = rhs.getFiller();
			OWLObjectProperty r = rhs.getProperty().getNamedProperty();
			
			OWLIndividual indA = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualA"));
			OWLIndividual indB = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualB"));

			OWLClassAssertionAxiom axiomC = factory.getOWLClassAssertionAxiom(c, indA);
			AddAxiom addAxiomC = new AddAxiom(testedOntology, axiomC);
			manager.applyChange(addAxiomC);
			
			OWLClassAssertionAxiom axiomD = factory.getOWLClassAssertionAxiom(d, indB);
			AddAxiom addAxiomD = new AddAxiom(testedOntology, axiomD);
			manager.applyChange(addAxiomD);
			
			OWLObjectPropertyAssertionAxiom axiomR = factory.getOWLObjectPropertyAssertionAxiom(r, indA, indB);
			AddAxiom addAxiomR = new AddAxiom(testedOntology, axiomR);
			manager.applyChange(addAxiomR);
			
			reasoner.flush();
			
			OWLObjectHasValue ohv = factory.getOWLObjectHasValue(r, indB);
			OWLObjectIntersectionOf oio = factory.getOWLObjectIntersectionOf(c, ohv);
			
			NodeSet<OWLNamedIndividual> instancesOio = reasoner.getInstances(oio, false);
			Iterator itOio = instancesOio.iterator();
			
			while (itOio.hasNext() && !result){
				
				Node<OWLNamedIndividual> instanceNode = (Node<OWLNamedIndividual>) itOio.next();
				Iterator it2 = instanceNode.iterator();
				
				while (it2.hasNext() && !result){
					OWLNamedIndividual instance = (OWLNamedIndividual) it2.next();
					
					if (instance.equals(indA)){
						result = true;
					}
				}
			}

			// clean up
			RemoveAxiom removeAxiomC = new RemoveAxiom(testedOntology, axiomC);
			manager.applyChange(removeAxiomC);

			RemoveAxiom removeAxiomD = new RemoveAxiom(testedOntology, axiomD);
			manager.applyChange(removeAxiomD);

			RemoveAxiom removeAxiomR = new RemoveAxiom(testedOntology, axiomR);
			manager.applyChange(removeAxiomR);
			
			reasoner.flush();
//			manager.saveOntology(testedOntology);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	

	public boolean testSimpleExistentialQuantificationTBox(OWLSubClassOfAxiom axiom) {
        boolean result = false;

		try {
			OWLClassExpression c = axiom.getSubClass();
			OWLObjectSomeValuesFrom rhs = (OWLObjectSomeValuesFrom) axiom.getSuperClass();
			
			OWLIndividual indA = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualA"));

			OWLClassAssertionAxiom axiomC = factory.getOWLClassAssertionAxiom(c, indA);
			AddAxiom addAxiomC = new AddAxiom(testedOntology, axiomC);
			manager.applyChange(addAxiomC);
			
			reasoner.flush();
			
			NodeSet<OWLNamedIndividual> instancesOio = reasoner.getInstances(rhs, false);
			Iterator itOio = instancesOio.iterator();
			
			while (itOio.hasNext() && !result){
				
				Node<OWLNamedIndividual> instanceNode = (Node<OWLNamedIndividual>) itOio.next();
				Iterator it2 = instanceNode.iterator();
				
				while (it2.hasNext() && !result){
					OWLNamedIndividual instance = (OWLNamedIndividual) it2.next();
					
					if (instance.equals(indA)){
						result = true;
					}
				}
			}

			// clean up
			RemoveAxiom removeAxiomC = new RemoveAxiom(testedOntology, axiomC);
			manager.applyChange(removeAxiomC);

			reasoner.flush();
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public boolean testSimpleUniversalQuantificationTBox(OWLSubClassOfAxiom axiom) {
        boolean result = false;

		try {
			OWLClassExpression c = axiom.getSubClass();
			OWLObjectAllValuesFrom rhs = (OWLObjectAllValuesFrom) axiom.getSuperClass();
			
			OWLIndividual indA = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualA"));

			OWLClassAssertionAxiom axiomC = factory.getOWLClassAssertionAxiom(c, indA);
			AddAxiom addAxiomC = new AddAxiom(testedOntology, axiomC);
			manager.applyChange(addAxiomC);
			
			reasoner.flush();
			
			NodeSet<OWLNamedIndividual> instancesOio = reasoner.getInstances(rhs, false);
			Iterator itOio = instancesOio.iterator();
			
			while (itOio.hasNext() && !result){
				
				Node<OWLNamedIndividual> instanceNode = (Node<OWLNamedIndividual>) itOio.next();
				Iterator it2 = instanceNode.iterator();
				
				while (it2.hasNext() && !result){
					OWLNamedIndividual instance = (OWLNamedIndividual) it2.next();
					
					if (instance.equals(indA)){
						result = true;
					}
				}
			}

			// clean up
			RemoveAxiom removeAxiomC = new RemoveAxiom(testedOntology, axiomC);
			manager.applyChange(removeAxiomC);

			reasoner.flush();
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	
//does not work
/*
	public boolean testSimpleExistentialQuantification3(OWLSubClassOfAxiom axiom) {
        boolean result = false;

		try {
			OWLClassExpression c = axiom.getSubClass();
			OWLObjectSomeValuesFrom rhs = (OWLObjectSomeValuesFrom) axiom.getSuperClass();
			OWLClassExpression d = rhs.getFiller();
			OWLObjectProperty r = rhs.getProperty().getNamedProperty();
			
			OWLIndividual indA = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualA"));
			OWLIndividual indB = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualB"));

			OWLClassAssertionAxiom axiomC = factory.getOWLClassAssertionAxiom(c, indA);
			AddAxiom addAxiomC = new AddAxiom(testedOntology, axiomC);
			System.out.println("axxx:"+axiomC.toString());
			manager.applyChange(addAxiomC);
			
			OWLClassAssertionAxiom axiomD = factory.getOWLClassAssertionAxiom(d, indB);
			AddAxiom addAxiomD = new AddAxiom(testedOntology, axiomD);
			System.out.println("axxx:"+axiomD.toString());
			manager.applyChange(addAxiomD);
			
//			OWLObjectPropertyAssertionAxiom axiomR = factory.getOWLObjectPropertyAssertionAxiom(r, indA, indB);
//			AddAxiom addAxiomR = new AddAxiom(testedOntology, axiomR);
//			System.out.println("axxx:"+axiomR.toString());
//			manager.applyChange(addAxiomR);
			
			reasoner.flush();
			
			result = indA.hasObjectPropertyValue(r, indB, testedOntology);

			// clean up
			RemoveAxiom removeAxiomC = new RemoveAxiom(testedOntology, axiomC);
			manager.applyChange(removeAxiomC);

			RemoveAxiom removeAxiomD = new RemoveAxiom(testedOntology, axiomD);
			manager.applyChange(removeAxiomD);

//			RemoveAxiom removeAxiomR = new RemoveAxiom(testedOntology, axiomR);
//			manager.applyChange(removeAxiomR);
			
			reasoner.flush();
//			manager.saveOntology(testedOntology);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}
*/
	
	public boolean testObjectPropertyDomain(OWLSubClassOfAxiom axiom) {
        boolean result = false;

		try {
			OWLClassExpression c = axiom.getSuperClass();
			OWLObjectSomeValuesFrom lhs = (OWLObjectSomeValuesFrom) axiom.getSubClass();
			OWLClassExpression d = lhs.getFiller();
			OWLObjectProperty r = lhs.getProperty().getNamedProperty();
			
			OWLIndividual indA = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualA"));
			OWLIndividual indB = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualB"));

			OWLObjectPropertyAssertionAxiom axiomR = factory.getOWLObjectPropertyAssertionAxiom(r, indA, indB);
			AddAxiom addAxiomR = new AddAxiom(testedOntology, axiomR);
			manager.applyChange(addAxiomR);
			
			reasoner.flush();

			NodeSet<OWLNamedIndividual> instancesC = reasoner.getInstances(c, false);
			Iterator itC = instancesC.iterator();
			
			while (itC.hasNext() && !result){
				
				Node<OWLNamedIndividual> instanceNode = (Node<OWLNamedIndividual>) itC.next();
				Iterator it2 = instanceNode.iterator();
				
				while (it2.hasNext() && !result){
					OWLNamedIndividual instance = (OWLNamedIndividual) it2.next();
					
					if (instance.equals(indA)){
						result = true;
					}
				}
			}			

			RemoveAxiom removeAxiomR = new RemoveAxiom(testedOntology, axiomR);
			manager.applyChange(removeAxiomR);
			
			reasoner.flush();
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	

	public boolean testObjectPropertyRange(OWLSubClassOfAxiom axiom) {
        boolean result = false;

		try {
			OWLClassExpression d = axiom.getSuperClass();
			OWLObjectSomeValuesFrom lhs = (OWLObjectSomeValuesFrom) axiom.getSubClass();
			OWLObjectProperty rminus = lhs.getProperty().getNamedProperty().getInverseProperty().getNamedProperty();
			
			OWLIndividual indA = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualA"));
			OWLIndividual indB = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualB"));

			OWLObjectPropertyAssertionAxiom axiomR = factory.getOWLObjectPropertyAssertionAxiom(rminus.getInverseProperty().getNamedProperty(), indB, indA);
			AddAxiom addAxiomR = new AddAxiom(testedOntology, axiomR);
			manager.applyChange(addAxiomR);
			
			reasoner.flush();

			NodeSet<OWLNamedIndividual> instancesD = reasoner.getInstances(d, false);
			Iterator itD = instancesD.iterator();
			
			while (itD.hasNext() && !result){
				
				Node<OWLNamedIndividual> instanceNode = (Node<OWLNamedIndividual>) itD.next();
				Iterator it2 = instanceNode.iterator();
				
				while (it2.hasNext() && !result){
					OWLNamedIndividual instance = (OWLNamedIndividual) it2.next();
					
					if (instance.equals(indA)){
						result = true;
					}
				}
			}			

			RemoveAxiom removeAxiomR = new RemoveAxiom(testedOntology, axiomR);
			manager.applyChange(removeAxiomR);
			
			reasoner.flush();
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/*
	public boolean testObjectPropertySubsumption(OWLSubObjectPropertyOfAxiom axiom) {
        boolean result = false;

		try {
			
			OWLObjectProperty r = axiom.getSubProperty().getNamedProperty();
			OWLObjectProperty s = axiom.getSuperProperty().getNamedProperty();
			
			OWLIndividual indA = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualA"));
			OWLIndividual indB = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualB"));
			

			OWLObjectPropertyAssertionAxiom axiomR = factory.getOWLObjectPropertyAssertionAxiom(r, indA, indB);
			AddAxiom addAxiomR = new AddAxiom(testedOntology, axiomR);
	//		System.out.println("axxx:"+axiomR.toString());
			manager.applyChange(addAxiomR);

			reasoner.flush();

			//System.out.print("axx:" +axiom.toString());
			Map<OWLNamedIndividual, Set<OWLNamedIndividual>> map = reasoner.getObjectPropertyInstances(s);
			
			if (map.containsKey(indA)) {
				if (map.get(indA).contains(indB)) {
					result = true;
				}
				else {
					result = false;
				}
				
			} else {
				result =  false;
			}

			// clean up
			RemoveAxiom removeAxiomR = new RemoveAxiom(testedOntology, axiomR);
			manager.applyChange(removeAxiomR);
			reasoner.flush();
//			manager.saveOntology(testedOntology);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
		
	public boolean testObjectPropertyEquivalence(OWLEquivalentObjectPropertiesAxiom axiom) {
        boolean result = false;

        
		try {
			
			Iterator itP = axiom.getProperties().iterator();
			OWLObjectProperty r = (OWLObjectProperty) itP.next();
			OWLObjectProperty s = (OWLObjectProperty) itP.next();
			
			OWLIndividual indA = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualA"));
			OWLIndividual indB = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualB"));
			

			OWLObjectPropertyAssertionAxiom axiomR = factory.getOWLObjectPropertyAssertionAxiom(r, indA, indB);
			AddAxiom addAxiomR = new AddAxiom(testedOntology, axiomR);
			System.out.println("axxxR:"+axiomR.toString());
			manager.applyChange(addAxiomR);

			reasoner.flush();

			//System.out.print("axx:" +axiom.toString());
			Map<OWLNamedIndividual, Set<OWLNamedIndividual>> map = reasoner.getObjectPropertyInstances(s);
			
			if (map.containsKey(indA)) {
				if (map.get(indA).contains(indB)) {
					result = true;
				}
				else {
					result = false;
				}
				
			} else {
				result =  false;
			}

			// clean up
			RemoveAxiom removeAxiomR = new RemoveAxiom(testedOntology, axiomR);
			manager.applyChange(removeAxiomR);
			reasoner.flush();
//			manager.saveOntology(testedOntology);
			
			result = false;

			OWLObjectPropertyAssertionAxiom axiomS = factory.getOWLObjectPropertyAssertionAxiom(s, indA, indB);
			AddAxiom addAxiomS = new AddAxiom(testedOntology, axiomS);
			System.out.println("axxxS:"+axiomS.toString());
			manager.applyChange(addAxiomS);

			reasoner.flush();

			//System.out.print("axx:" +axiom.toString());
			map = reasoner.getObjectPropertyInstances(r);
			
			if (map.containsKey(indA)) {
				if (map.get(indA).contains(indB)) {
					result = true;
				}
				else {
					result = false;
				}
				
			} else {
				result =  false;
			}

			// clean up
			RemoveAxiom removeAxiomS = new RemoveAxiom(testedOntology, axiomS);
			manager.applyChange(removeAxiomS);
			reasoner.flush();
//			manager.saveOntology(testedOntology);

		} catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	
	public boolean testObjectPropertyInverse(OWLSubObjectPropertyOfAxiom axiom) {
        boolean result = false;

		try {
			
			OWLObjectProperty r = axiom.getSubProperty().getNamedProperty();
			OWLObjectProperty s = axiom.getSuperProperty().getNamedProperty();
			
			OWLIndividual indA = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualA"));
			OWLIndividual indB = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualB"));
			

			OWLObjectPropertyAssertionAxiom axiomR = factory.getOWLObjectPropertyAssertionAxiom(r, indA, indB);
			AddAxiom addAxiomR = new AddAxiom(testedOntology, axiomR);
			System.out.println("axxx:"+axiomR.toString());
			manager.applyChange(addAxiomR);

			reasoner.flush();

			//System.out.print("axx:" +axiom.toString());
			Map<OWLNamedIndividual, Set<OWLNamedIndividual>> map = reasoner.getObjectPropertyInstances(s);
			
			if (map.containsKey(indB)) {
				if (map.get(indB).contains(indA)) {
					result = true;
				}
				else {
					result = false;
				}
				
			} else {
				result =  false;
			}

			// clean up
			RemoveAxiom removeAxiomR = new RemoveAxiom(testedOntology, axiomR);
			manager.applyChange(removeAxiomR);
			reasoner.flush();
//			manager.saveOntology(testedOntology);
	
			OWLObjectPropertyAssertionAxiom axiomS = factory.getOWLObjectPropertyAssertionAxiom(s, indA, indB);
			AddAxiom addAxiomS = new AddAxiom(testedOntology, axiomS);
			System.out.println("axxx:"+axiomS.toString());
			manager.applyChange(addAxiomS);

			reasoner.flush();

			//System.out.print("axx:" +axiom.toString());
			map = reasoner.getObjectPropertyInstances(r);
			
			if (map.containsKey(indB)) {
				if (map.get(indB).contains(indA)) {
					result = true;
				}
				else {
					result = false;
				}
				
			} else {
				result =  false;
			}

			// clean up
			RemoveAxiom removeAxiomS = new RemoveAxiom(testedOntology, axiomS);
			manager.applyChange(removeAxiomS);
			reasoner.flush();
//			manager.saveOntology(testedOntology);
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}

*/	
	public boolean testObjectPropertyFunctional(OWLObjectProperty r) {
        boolean result = false;

		try {
			
			OWLIndividual indA = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualA"));
			OWLIndividual indB = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualB"));
			OWLIndividual indC = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualC"));
			

			OWLObjectPropertyAssertionAxiom axiomRab = factory.getOWLObjectPropertyAssertionAxiom(r, indA, indB);
			AddAxiom addAxiomRab = new AddAxiom(testedOntology, axiomRab);
			manager.applyChange(addAxiomRab);

			OWLObjectPropertyAssertionAxiom axiomRac = factory.getOWLObjectPropertyAssertionAxiom(r, indA, indC);
			AddAxiom addAxiomRac = new AddAxiom(testedOntology, axiomRac);
			manager.applyChange(addAxiomRac);

			OWLDifferentIndividualsAxiom axiomDi = factory.getOWLDifferentIndividualsAxiom(indB, indC);
			AddAxiom addAxiomDi = new AddAxiom(testedOntology, axiomDi);
			manager.applyChange(addAxiomDi);
			
			reasoner.flush();
			
			result = !reasoner.isConsistent();


			// clean up
			RemoveAxiom removeAxiomRab = new RemoveAxiom(testedOntology, axiomRab);
			manager.applyChange(removeAxiomRab);
			reasoner.flush();
			
			RemoveAxiom removeAxiomRac = new RemoveAxiom(testedOntology, axiomRac);
			manager.applyChange(removeAxiomRac);
			reasoner.flush();

			RemoveAxiom removeAxiomDi = new RemoveAxiom(testedOntology, axiomDi);
			manager.applyChange(removeAxiomDi);
			reasoner.flush();

//			manager.saveOntology(testedOntology);
	
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	
	public boolean testObjectPropertyInverseFunctional(OWLObjectProperty r) {
        boolean result = false;

		try {
			
			OWLIndividual indA = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualA"));
			OWLIndividual indB = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualB"));
			OWLIndividual indC = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualC"));
			

			OWLObjectPropertyAssertionAxiom axiomRba = factory.getOWLObjectPropertyAssertionAxiom(r, indB, indA);
			AddAxiom addAxiomRba = new AddAxiom(testedOntology, axiomRba);
			manager.applyChange(addAxiomRba);

			OWLObjectPropertyAssertionAxiom axiomRca = factory.getOWLObjectPropertyAssertionAxiom(r, indC, indA);
			AddAxiom addAxiomRca = new AddAxiom(testedOntology, axiomRca);
			manager.applyChange(addAxiomRca);

			OWLDifferentIndividualsAxiom axiomDi = factory.getOWLDifferentIndividualsAxiom(indB, indC);
			AddAxiom addAxiomDi = new AddAxiom(testedOntology, axiomDi);
			manager.applyChange(addAxiomDi);
			
			reasoner.flush();
			
			result = !reasoner.isConsistent();


			// clean up
			RemoveAxiom removeAxiomRba = new RemoveAxiom(testedOntology, axiomRba);
			manager.applyChange(removeAxiomRba);
			reasoner.flush();
			
			RemoveAxiom removeAxiomRca = new RemoveAxiom(testedOntology, axiomRca);
			manager.applyChange(removeAxiomRca);
			reasoner.flush();

			RemoveAxiom removeAxiomDi = new RemoveAxiom(testedOntology, axiomDi);
			manager.applyChange(removeAxiomDi);
			reasoner.flush();

//			manager.saveOntology(testedOntology);
	
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public boolean testObjectPropertyTransitive(OWLObjectProperty r) {
        boolean result = false;

		try {
			
			OWLNamedIndividual indA = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualA"));
			OWLNamedIndividual indB = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualB"));
			OWLNamedIndividual indC = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualC"));
			

			OWLObjectPropertyAssertionAxiom axiomRab = factory.getOWLObjectPropertyAssertionAxiom(r, indA, indB);
			AddAxiom addAxiomRab = new AddAxiom(testedOntology, axiomRab);
			manager.applyChange(addAxiomRab);

			OWLObjectPropertyAssertionAxiom axiomRbc = factory.getOWLObjectPropertyAssertionAxiom(r, indB, indC);
			AddAxiom addAxiomRbc = new AddAxiom(testedOntology, axiomRbc);
			manager.applyChange(addAxiomRbc);

			reasoner.flush();
			
			if (reasoner.isConsistent()){
		//		Map<OWLNamedIndividual, Set<OWLNamedIndividual>> map = reasoner.getObjectPropertyInstances(r);
                        
				if (reasoner.getObjectPropertyValues(indA, r).containsEntity(indC)) {
					result = true;
				}
				else {
					result = false;
				}
			}
			else {
				result = false;
			}
			// clean up
			RemoveAxiom removeAxiomRab = new RemoveAxiom(testedOntology, axiomRab);
			manager.applyChange(removeAxiomRab);
			reasoner.flush();
			
			RemoveAxiom removeAxiomRbc = new RemoveAxiom(testedOntology, axiomRbc);
			manager.applyChange(removeAxiomRbc);
			reasoner.flush();


//			manager.saveOntology(testedOntology);
	
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	


	
	public boolean testObjectPropertySymmetric(OWLObjectProperty r) {
        boolean result = false;

		try {
			
			OWLNamedIndividual indA = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualA"));
			OWLNamedIndividual indB = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualB"));
			
			OWLObjectPropertyAssertionAxiom axiomRab = factory.getOWLObjectPropertyAssertionAxiom(r, indA, indB);
			AddAxiom addAxiomRab = new AddAxiom(testedOntology, axiomRab);
			manager.applyChange(addAxiomRab);

			reasoner.flush();
			
//			Map<OWLNamedIndividual, Set<OWLNamedIndividual>> map = reasoner.getObjectPropertyInstances(r);
		    
			if (reasoner.getObjectPropertyValues(indB, r).containsEntity(indA)) {
	
					result = true;
				}
				else {
					result = false;
			}
				

		    // clean up
			RemoveAxiom removeAxiomRab = new RemoveAxiom(testedOntology, axiomRab);
			manager.applyChange(removeAxiomRab);
			reasoner.flush();

//			manager.saveOntology(testedOntology);
	
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public boolean testObjectPropertyAssymetric(OWLObjectProperty r) {
        boolean result = false;

		try {
			
			OWLIndividual indA = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualA"));
			OWLIndividual indB = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualB"));

			OWLObjectPropertyAssertionAxiom axiomRab = factory.getOWLObjectPropertyAssertionAxiom(r, indA, indB);
			AddAxiom addAxiomRab = new AddAxiom(testedOntology, axiomRab);
			manager.applyChange(addAxiomRab);

			OWLObjectPropertyAssertionAxiom axiomRba = factory.getOWLObjectPropertyAssertionAxiom(r, indB, indA);
			AddAxiom addAxiomRba = new AddAxiom(testedOntology, axiomRba);
			manager.applyChange(addAxiomRba);
			
			reasoner.flush();
			
			result = reasoner.isConsistent();

			// clean up
			RemoveAxiom removeAxiomRab = new RemoveAxiom(testedOntology, axiomRab);
			manager.applyChange(removeAxiomRab);
			reasoner.flush();

			RemoveAxiom removeAxiomRba = new RemoveAxiom(testedOntology, axiomRba);
			manager.applyChange(removeAxiomRba);
			reasoner.flush();

//			manager.saveOntology(testedOntology);
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	
	public static void main(String[] args) throws Exception {
		File file=new File("/home/daenerys/workspace/owl-bgp-master/test/org/semanticweb/sparql/owlbgp/evaluation/ontologies/pizza.owl");

		OWLOntologyManager localManager = OWLManager.createOWLOntologyManager();
		
		OWLOntology pizza = localManager.loadOntologyFromOntologyDocument(file);
//		OWLOntologyDataSet pizzaDs = new OWLOntologyDataSet(file);
/*
		OWLReasoner res = new OWLReasoner(pizza);


//Create a set of test axioms
		OWLClass iceCream = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#IceCream"));
		OWLClass pizzaTopping = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#PizzaTopping"));
		OWLAxiom axiom1 = localManager.getOWLDataFactory().getOWLSubClassOfAxiom(iceCream, pizzaTopping);

		
		OWLClass quattroFormaggi = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#QuattroFormaggi"));
		OWLClass siciliana = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#Siciliana"));
		OWLSubClassOfAxiom axiom2 = localManager.getOWLDataFactory().getOWLSubClassOfAxiom(quattroFormaggi, siciliana);
		
		OWLClass realItalianPizza = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#RealItalianPizza"));
		OWLClass domainConcept = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#DomainConcept"));
		OWLDisjointClassesAxiom axiom4 = localManager.getOWLDataFactory().getOWLDisjointClassesAxiom(realItalianPizza, iceCream);

		OWLSubClassOfAxiom axiom3 = localManager.getOWLDataFactory().getOWLSubClassOfAxiom(realItalianPizza, domainConcept);
		
		OWLClass chickenTopping = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#ChickenTopping"));
		OWLDisjointClassesAxiom axiom5 = localManager.getOWLDataFactory().getOWLDisjointClassesAxiom(chickenTopping , siciliana);
		
		
		OWLClass vegetarianPizzaEquivalent1 = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#VegetarianPizzaEquivalent1"));
		OWLClass vegetarianPizzaEquivalent2 = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#VegetarianPizzaEquivalent2"));
		OWLEquivalentClassesAxiom axiom6 = localManager.getOWLDataFactory().getOWLEquivalentClassesAxiom(vegetarianPizzaEquivalent1, vegetarianPizzaEquivalent2);
		
		OWLClass valuePartition = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#ValuePartition"));
		OWLClass fourCheesesTopping = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#FourCheesesTopping"));
		OWLObjectProperty hasIngredient = localManager.getOWLDataFactory().getOWLObjectProperty(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#hasIngredient"));

		OWLObjectSomeValuesFrom osvf1 = localManager.getOWLDataFactory().getOWLObjectSomeValuesFrom(hasIngredient, fourCheesesTopping);
		OWLSubClassOfAxiom axiom7 = localManager.getOWLDataFactory().getOWLSubClassOfAxiom(valuePartition, osvf1);

		OWLClass anchoviesTopping = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#AnchoviesTopping"));
		OWLClass cajunSpiceTopping = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#CajunSpiceTopping"));
		OWLObjectProperty isIngredientOf = localManager.getOWLDataFactory().getOWLObjectProperty(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#isIngredientOf"));

		OWLObjectSomeValuesFrom osvf2 = localManager.getOWLDataFactory().getOWLObjectSomeValuesFrom(isIngredientOf, cajunSpiceTopping);
		OWLSubClassOfAxiom axiom8 = localManager.getOWLDataFactory().getOWLSubClassOfAxiom(anchoviesTopping, osvf2);

		OWLClass vegetarianPizza = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#VegetarianPizza"));
		OWLClass pizzaBase = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#PizzaBase"));
		OWLObjectProperty hasBase = localManager.getOWLDataFactory().getOWLObjectProperty(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#hasBase"));

		OWLObjectSomeValuesFrom osvf3 = localManager.getOWLDataFactory().getOWLObjectSomeValuesFrom(hasBase, pizzaBase);
		OWLSubClassOfAxiom axiom9 = localManager.getOWLDataFactory().getOWLSubClassOfAxiom(vegetarianPizza , osvf3);

        OWLClass pizzac = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#Pizza"));
		OWLObjectSomeValuesFrom osvf4 = localManager.getOWLDataFactory().getOWLObjectSomeValuesFrom(hasBase.getInverseProperty(), localManager.getOWLDataFactory().getOWLThing());
		OWLSubClassOfAxiom axiom10 = localManager.getOWLDataFactory().getOWLSubClassOfAxiom(osvf4, pizzaBase);
		
		OWLSubClassOfAxiom axiom11 = localManager.getOWLDataFactory().getOWLSubClassOfAxiom(osvf4, iceCream);

		OWLSubObjectPropertyOfAxiom axiom12 = localManager.getOWLDataFactory().getOWLSubObjectPropertyOfAxiom(hasBase, hasIngredient);	
		OWLSubObjectPropertyOfAxiom axiom13 = localManager.getOWLDataFactory().getOWLSubObjectPropertyOfAxiom(hasBase, isIngredientOf);	
		
		OWLEquivalentObjectPropertiesAxiom axiom14 = localManager.getOWLDataFactory().getOWLEquivalentObjectPropertiesAxiom(hasBase, hasIngredient);
//		OWLObjectProperty hasIngredientEquiv = localManager.getOWLDataFactory().getOWLObjectProperty(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#hasIngredientEquiv"));
//		OWLEquivalentObjectPropertiesAxiom axiom15 = localManager.getOWLDataFactory().getOWLEquivalentObjectPropertiesAxiom(hasIngredient, hasIngredientEquiv);

		OWLSubObjectPropertyOfAxiom axiom16 = localManager.getOWLDataFactory().getOWLSubObjectPropertyOfAxiom(hasBase, isIngredientOf);	
		OWLSubObjectPropertyOfAxiom axiom17 = localManager.getOWLDataFactory().getOWLSubObjectPropertyOfAxiom(hasIngredient, isIngredientOf);	

		OWLObjectProperty transitiveProp = localManager.getOWLDataFactory().getOWLObjectProperty(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#transitiveProp"));
		OWLObjectProperty symmetricProp = localManager.getOWLDataFactory().getOWLObjectProperty(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#symmetricProp"));
		OWLObjectProperty asymmetricProp = localManager.getOWLDataFactory().getOWLObjectProperty(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#asymmetricProp"));

//		int i = 0;
//		for (OWLAxiom ax : pizza.getAxioms()){
//			if (ax.isOfType(AxiomType.SUBCLASS_OF) && i==0){
				System.out.println("axx: " + axiom17.toString());
				
				boolean b;
				SimpleMockObjectTest t = new SimpleMockObjectTest(localManager, pizza, res);
			//	b = t.testObjectPropertyAssymetric(hasBase);
				
				System.out.println("\n Result:"+b);
*/
	}

}

