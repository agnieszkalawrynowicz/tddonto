package put.aristoteles.tddontoprotege;

/**
 * @author agalawrynowicz
 */

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.protege.editor.owl.model.OWLModelManager;
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
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
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

import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
//import org.semanticweb.sparql.arq.OWLOntologyDataSet;
import org.apache.log4j.Logger;

/**
 * @author agalawrynowicz
 *
 */

public class SimpleOWLAPITest {

    protected final OWLModelManager modelManager;
    protected final OWLOntologyManager manager;
    protected final OWLDataFactory factory;
    protected OWLOntology testedOntology;
  //  protected OWLOntologyDataSet testedDataSet;
   // protected OWLSubClassOfAxiom axiom;

    OWLReasoner reasoner;
   // Reasoner reasoner;
    
    
    public SimpleOWLAPITest(OWLModelManager modelManager, OWLOntologyManager manager, OWLOntology testedOntology, OWLReasoner reasoner) {
        this.modelManager = modelManager;
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
			
			NodeSet<OWLClass> al = reasoner.getSubClasses(superClass, false);
			
			//System.out.println("rr:"+reasoner.getReasonerName());
			//System.out.println("klas_sub:"+subClass);
			//System.out.println("klas_sup:"+superClass);
			
			//for (Iterator<Node<OWLClass>> it = al.iterator(); it.hasNext();){
			//	System.out.println("klassa:"+it.next());
			//}
			
			if (reasoner.getSubClasses(superClass, false).containsEntity(subClass.asOWLClass())) {
				result = true;
			} else{
				result = false;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	
	
	
	public boolean testDisjointWith(OWLDisjointClassesAxiom axiom) {
        boolean result = false;

		try {
			Set<OWLClassExpression> classes = axiom.getClassExpressions();
			OWLClassExpression c = axiom.getClassExpressionsAsList().get(0);
			OWLClassExpression d = axiom.getClassExpressionsAsList().get(1);
	
			if (reasoner.getDisjointClasses(d).containsEntity(c.asOWLClass())) {
				result = true;
			} else{
				result = false;
			}	
	
			
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

			if (reasoner.getEquivalentClasses(d).contains(c.asOWLClass())) {
				result = true;
			} else{
				result = false;
			}	
			
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
			
			if (reasoner.getSubClasses(rhs, false).containsEntity(c.asOWLClass())) {
				result = true;
			} else{
				result = false;
			}
			
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
			
			if (reasoner.getSubClasses(rhs, false).containsEntity(c.asOWLClass())) {
				result = true;
			} else{
				result = false;
			}

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

			if (r.getDomains(testedOntology).contains(c.asOWLClass())){
				result = true;
			} else {
				result = false;
			}
			
			
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
			//OWLClassExpression d = lhs.getFiller();
			OWLObjectProperty rminus = lhs.getProperty().getNamedProperty().getInverseProperty().getNamedProperty();
			
			if (rminus.getDomains(testedOntology).contains(d.asOWLClass())){
				result = true;
			} else {
				result = false;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	
	public boolean testObjectPropertySubsumption(OWLSubObjectPropertyOfAxiom axiom) {
        boolean result = false;

		try {
			
			OWLObjectProperty r = axiom.getSubProperty().getNamedProperty();
			OWLObjectProperty s = axiom.getSuperProperty().getNamedProperty();
			
			if (s.getSubProperties(testedOntology).contains(r)) {
					result = true;
			}
			else {
				result = false;
			}
			
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
			
			if (s.getEquivalentProperties(testedOntology).contains(r)) {
				result = true;
		    }
		    else {
			    result = false;
		    }

		} catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}

/*	
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
	public boolean testObjectPropertyFunctional(OWLFunctionalObjectPropertyAxiom axiom) {
        boolean result = false;

		try {
			if (testedOntology.getAxioms(AxiomType.FUNCTIONAL_OBJECT_PROPERTY).contains(axiom)) {
				result = true;
			}
		
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public boolean testObjectPropertyInverseFunctional(OWLObjectProperty r) {
        boolean result = false;

		try {
			result = r.isInverseFunctional(testedOntology);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}


	public boolean testObjectPropertyTransitive(OWLObjectProperty r) {
        boolean result = false;

		try {
			result = r.isTransitive(testedOntology);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public boolean testObjectPropertySymmetric(OWLObjectProperty r) {
        boolean result = false;

		try {
			result = r.isSymmetric(testedOntology);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public boolean testObjectPropertyAsymmetric(OWLObjectProperty r) {
        boolean result = false;

		try {
			result = r.isAsymmetric(testedOntology);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
/*	
	public boolean testObjectPropertyFunctional(OWLFunctionalObjectPropertyAxiom axiom) {
        boolean result = false;

		try {
			
			OWLIndividual indA = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualA"));
			OWLIndividual indB = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualB"));
			OWLIndividual indC = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualC"));
			

			OWLObjectPropertyAssertionAxiom axiomRab = factory.getOWLObjectPropertyAssertionAxiom(r, indA, indB);
			AddAxiom addAxiomRab = new AddAxiom(testedOntology, axiomRab);
			System.out.println("axxx:"+axiomRab.toString());
			manager.applyChange(addAxiomRab);

			OWLObjectPropertyAssertionAxiom axiomRac = factory.getOWLObjectPropertyAssertionAxiom(r, indA, indC);
			AddAxiom addAxiomRac = new AddAxiom(testedOntology, axiomRac);
			System.out.println("axxx:"+axiomRac.toString());
			manager.applyChange(addAxiomRac);

			OWLDifferentIndividualsAxiom axiomDi = factory.getOWLDifferentIndividualsAxiom(indB, indC);
			AddAxiom addAxiomDi = new AddAxiom(testedOntology, axiomDi);
			System.out.println("axxx:"+axiomDi.toString());
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
	
	
/*	
	public boolean testObjectPropertyFunctional(OWLObjectProperty r) {
        boolean result = false;

		try {
			
			OWLIndividual indA = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualA"));
			OWLIndividual indB = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualB"));
			OWLIndividual indC = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualC"));
			

			OWLObjectPropertyAssertionAxiom axiomRab = factory.getOWLObjectPropertyAssertionAxiom(r, indA, indB);
			AddAxiom addAxiomRab = new AddAxiom(testedOntology, axiomRab);
			System.out.println("axxx:"+axiomRab.toString());
			manager.applyChange(addAxiomRab);

			OWLObjectPropertyAssertionAxiom axiomRac = factory.getOWLObjectPropertyAssertionAxiom(r, indA, indC);
			AddAxiom addAxiomRac = new AddAxiom(testedOntology, axiomRac);
			System.out.println("axxx:"+axiomRac.toString());
			manager.applyChange(addAxiomRac);

			OWLDifferentIndividualsAxiom axiomDi = factory.getOWLDifferentIndividualsAxiom(indB, indC);
			AddAxiom addAxiomDi = new AddAxiom(testedOntology, axiomDi);
			System.out.println("axxx:"+axiomDi.toString());
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
			System.out.println("axxx:"+axiomRba.toString());
			manager.applyChange(addAxiomRba);

			OWLObjectPropertyAssertionAxiom axiomRca = factory.getOWLObjectPropertyAssertionAxiom(r, indC, indA);
			AddAxiom addAxiomRca = new AddAxiom(testedOntology, axiomRca);
			System.out.println("axxx:"+axiomRca.toString());
			manager.applyChange(addAxiomRca);

			OWLDifferentIndividualsAxiom axiomDi = factory.getOWLDifferentIndividualsAxiom(indB, indC);
			AddAxiom addAxiomDi = new AddAxiom(testedOntology, axiomDi);
			System.out.println("axxx:"+axiomDi.toString());
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
			
			OWLIndividual indA = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualA"));
			OWLIndividual indB = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualB"));
			OWLIndividual indC = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualC"));
			

			OWLObjectPropertyAssertionAxiom axiomRab = factory.getOWLObjectPropertyAssertionAxiom(r, indA, indB);
			AddAxiom addAxiomRab = new AddAxiom(testedOntology, axiomRab);
			System.out.println("axxx:"+axiomRab.toString());
			manager.applyChange(addAxiomRab);

			OWLObjectPropertyAssertionAxiom axiomRbc = factory.getOWLObjectPropertyAssertionAxiom(r, indB, indC);
			AddAxiom addAxiomRbc = new AddAxiom(testedOntology, axiomRbc);
			System.out.println("axxx:"+axiomRbc.toString());
			manager.applyChange(addAxiomRbc);

			reasoner.flush();
			
			if (reasoner.isConsistent()){
				Map<OWLNamedIndividual, Set<OWLNamedIndividual>> map = reasoner.getObjectPropertyInstances(r);
			
				if (map.get(indA).contains(indC)) {
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
			
			OWLIndividual indA = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualA"));
			OWLIndividual indB = factory.getOWLNamedIndividual(IRI.create("http://semantic.cs.put.poznan.pl/IndividualB"));
			
			OWLObjectPropertyAssertionAxiom axiomRab = factory.getOWLObjectPropertyAssertionAxiom(r, indA, indB);
			AddAxiom addAxiomRab = new AddAxiom(testedOntology, axiomRab);
			System.out.println("axxx:"+axiomRab.toString());
			manager.applyChange(addAxiomRab);

			reasoner.flush();
			
			Map<OWLNamedIndividual, Set<OWLNamedIndividual>> map = reasoner.getObjectPropertyInstances(r);
			
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
			System.out.println("axxx:"+axiomRab.toString());
			manager.applyChange(addAxiomRab);

			OWLObjectPropertyAssertionAxiom axiomRba = factory.getOWLObjectPropertyAssertionAxiom(r, indB, indA);
			AddAxiom addAxiomRba = new AddAxiom(testedOntology, axiomRba);
			System.out.println("axxx:"+axiomRba.toString());
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

	*/
	public static void main(String[] args) throws Exception {
		File file=new File("/Users/lawrynka/Documents/workspace/owl-bgp-master-2/test/org/semanticweb/sparql/owlbgp/evaluation/ontologies/pizza.owl");
		
		OWLOntologyManager localManager = OWLManager.createOWLOntologyManager();
		
		OWLOntology pizza = localManager.loadOntologyFromOntologyDocument(file);
//		OWLOntologyDataSet pizzaDs = new OWLOntologyDataSet(file);
		System.out.println("Loaded ontology: " + pizza);
   //     OWLReasoner reasoner =  getOWLModelManager().getReasoner();

//		OWLOntologyManager localManager = OWLManager.createOWLOntologyManager();
		
//Create a set of test axioms
		OWLClass iceCream = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#IceCream"));
		OWLClass pizzaTopping = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#PizzaTopping"));
		OWLSubClassOfAxiom axiom1 = localManager.getOWLDataFactory().getOWLSubClassOfAxiom(iceCream, pizzaTopping);

		
		OWLClass quattroFormaggi = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#QuattroFormaggi"));
		OWLClass siciliana = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#Siciliana"));
		OWLSubClassOfAxiom axiom2 = localManager.getOWLDataFactory().getOWLSubClassOfAxiom(quattroFormaggi, siciliana);
		
		OWLClass realItalianPizza = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#RealItalianPizza"));
		OWLClass domainConcept = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#DomainConcept"));
		OWLDisjointClassesAxiom axiom4 = localManager.getOWLDataFactory().getOWLDisjointClassesAxiom(realItalianPizza, iceCream);

		OWLSubClassOfAxiom axiom3 = localManager.getOWLDataFactory().getOWLSubClassOfAxiom(realItalianPizza, domainConcept);
		
		OWLClass chickenTopping = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#ChickenTopping"));
		OWLDisjointClassesAxiom axiom5 = localManager.getOWLDataFactory().getOWLDisjointClassesAxiom(chickenTopping , pizzaTopping);

		OWLClass vegetarianPizzaEquivalent1 = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#VegetarianPizzaEquivalent1"));
		OWLClass vegetarianPizzaEquivalent2 = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#VegetarianPizzaEquivalent2"));
		OWLEquivalentClassesAxiom axiom6 = localManager.getOWLDataFactory().getOWLEquivalentClassesAxiom(vegetarianPizzaEquivalent1, vegetarianPizzaEquivalent2);
		OWLEquivalentClassesAxiom axiom7 = localManager.getOWLDataFactory().getOWLEquivalentClassesAxiom(siciliana, vegetarianPizzaEquivalent2);

		OWLClass valuePartition = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#ValuePartition"));
		OWLClass fourCheesesTopping = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#FourCheesesTopping"));
		OWLObjectProperty hasIngredient = localManager.getOWLDataFactory().getOWLObjectProperty(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#hasIngredient"));

		OWLObjectSomeValuesFrom osvf1 = localManager.getOWLDataFactory().getOWLObjectSomeValuesFrom(hasIngredient, fourCheesesTopping);
		OWLSubClassOfAxiom axiom8 = localManager.getOWLDataFactory().getOWLSubClassOfAxiom(valuePartition, osvf1);

		OWLClass anchoviesTopping = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#AnchoviesTopping"));
		OWLClass cajunSpiceTopping = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#CajunSpiceTopping"));
		OWLObjectProperty isIngredientOf = localManager.getOWLDataFactory().getOWLObjectProperty(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#isIngredientOf"));

		OWLObjectSomeValuesFrom osvf2 = localManager.getOWLDataFactory().getOWLObjectSomeValuesFrom(isIngredientOf, cajunSpiceTopping);
		OWLSubClassOfAxiom axiom9 = localManager.getOWLDataFactory().getOWLSubClassOfAxiom(anchoviesTopping, osvf2);

		OWLClass vegetarianPizza = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#VegetarianPizza"));
		OWLClass pizzaBase = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#PizzaBase"));
		OWLObjectProperty hasBase = localManager.getOWLDataFactory().getOWLObjectProperty(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#hasBase"));

		OWLObjectSomeValuesFrom osvf3 = localManager.getOWLDataFactory().getOWLObjectSomeValuesFrom(hasBase, pizzaBase);
		OWLSubClassOfAxiom axiom10 = localManager.getOWLDataFactory().getOWLSubClassOfAxiom(vegetarianPizza , osvf3);

		OWLClass vegetarianTopping = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#VegetarianTopping"));
		OWLObjectProperty hasTopping = localManager.getOWLDataFactory().getOWLObjectProperty(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#hasTopping"));
		OWLObjectAllValuesFrom osvf4 = localManager.getOWLDataFactory().getOWLObjectAllValuesFrom(hasTopping, vegetarianTopping);
		OWLSubClassOfAxiom axiom11 = localManager.getOWLDataFactory().getOWLSubClassOfAxiom(vegetarianPizzaEquivalent1, osvf4);
		
        OWLClass pizzac = localManager.getOWLDataFactory().getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#Pizza"));
		OWLObjectSomeValuesFrom osvf5 = localManager.getOWLDataFactory().getOWLObjectSomeValuesFrom(hasBase.getInverseProperty(), localManager.getOWLDataFactory().getOWLThing());
		OWLObjectSomeValuesFrom osvf6 = localManager.getOWLDataFactory().getOWLObjectSomeValuesFrom(hasBase.getInverseProperty(), localManager.getOWLDataFactory().getOWLThing());
		OWLObjectSomeValuesFrom osvf7 = localManager.getOWLDataFactory().getOWLObjectSomeValuesFrom(hasBase, localManager.getOWLDataFactory().getOWLThing());

		OWLSubClassOfAxiom axiom12 = localManager.getOWLDataFactory().getOWLSubClassOfAxiom(osvf5, pizzaBase);
		OWLSubClassOfAxiom axiom13 = localManager.getOWLDataFactory().getOWLSubClassOfAxiom(osvf6, pizzac);
		OWLSubClassOfAxiom axiom14 = localManager.getOWLDataFactory().getOWLSubClassOfAxiom(osvf7, pizzaBase);

		OWLSubObjectPropertyOfAxiom axiom15 = localManager.getOWLDataFactory().getOWLSubObjectPropertyOfAxiom(hasBase, hasIngredient);	
		OWLSubObjectPropertyOfAxiom axiom16 = localManager.getOWLDataFactory().getOWLSubObjectPropertyOfAxiom(hasBase, isIngredientOf);	
		
/*		
		boolean res;
		SimpleOWLAPITest t = new SimpleOWLAPITest(localManager, pizza, reasoner);
		//true
		System.out.println("toTest: " + axiom4.toString());
		res = t.testDisjointWith(axiom4);
		
		System.out.println("\n Result axiom 4:"+res+"entailed "+reasoner.isEntailed(axiom4));

		//false
		System.out.println("toTest: " + axiom1.toString());
		res = t.testSubClassOf(axiom1);
		System.out.println("\n Result axiom 1:"+res);

		//false
		System.out.println("toTest: " + axiom2.toString());
		res = t.testSubClassOf(axiom2);
		System.out.println("\n Result axiom 2:"+res);

		//true
		System.out.println("toTest: " + axiom3.toString());
		res = t.testSubClassOf(axiom3);
		System.out.println("\n Result axiom 3:"+res);
		
		//false
		System.out.println("toTest: " + axiom5.toString());
		res = t.testDisjointWith(axiom5);
		System.out.println("\n Result axiom 5 :"+res);

		//true
		System.out.println("toTest: " + axiom6.toString());
		res = t.testEquivalentClasses(axiom6);
		System.out.println("\n Result axiom 6:"+res);

		//false
		System.out.println("toTest: " + axiom7.toString());
		res = t.testEquivalentClasses(axiom7);
		System.out.println("\n Result axiom 7:"+res);

		//false
		System.out.println("toTest: " + axiom8.toString());
		res = t.testSimpleExistentialQuantificationTBox(axiom8);
		System.out.println("\n Result axiom 8:"+res);
		
		//false
		System.out.println("toTest: " + axiom9.toString());
		res = t.testSimpleExistentialQuantificationTBox(axiom9);
		System.out.println("\n Result aximo 9:"+res);
		
		//true
		System.out.println("toTest: " + axiom10.toString());
		res = t.testSimpleExistentialQuantificationTBox(axiom10);
		System.out.println("\n Result axiom 10:"+res);
		
		//true
		System.out.println("toTest: " + axiom11.toString());
		res = t.testSimpleUniversalQuantificationTBox(axiom11);
		System.out.println("\n Result axiom 11:"+res);
		
		//false
		System.out.println("toTest: " + axiom12.toString());
		res = t.testObjectPropertyDomain(axiom12);
		System.out.println("\n Result axiom 12:"+res);

		//true
		System.out.println("toTest: " + axiom13.toString());
		res = t.testObjectPropertyDomain(axiom13);
		System.out.println("\n Result axiom 13:"+res);

		//true
		System.out.println("toTest: " + axiom14.toString());
		res = t.testObjectPropertyRange(axiom14);
		System.out.println("\n Result axiom 14:"+res+"entailed "+reasoner.isEntailed(axiom14));		
		
//		System.out.println("toTest: " + axiom15.toString());
//		res = t.testObjectPropertySubsumption(axiom15);
//		System.out.println("\n Result:"+res);		

//		System.out.println("toTest: " + axiom16.toString());
//		res = t.testObjectPropertySubsumption(axiom16);
//		System.out.println("\n Result:"+res);		

		
//		System.out.println("toTest: " + hasBase.toString()+" functional?");
//		res = t.testObjectPropertyFunctional(hasBase);
//		System.out.println("\n Result:"+res);		

		
//		System.out.println("toTest: " + "inverse?"+hasIngredient.toString()+" " +isIngredientOf.toString());
//		res = t.testInverseOf(hasIngredient, isIngredientOf);
//		System.out.println("\n Result:"+res);		
*/
	}

}



