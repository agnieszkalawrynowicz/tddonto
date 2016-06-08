package put.aristoteles.tddontoprotege;

import java.util.HashSet;
import java.util.Iterator;

//import org.protege.editor.owl.model.OWLModelManager;
//import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
//import org.semanticweb.owlapi.model.OWLClass;
//import org.semanticweb.owlapi.model.OWLClassExpression;
//import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
//import org.semanticweb.owlapi.model.OWLOntologyManager;
//import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
//import org.semanticweb.owlapi.reasoner.Node;
//import org.semanticweb.owlapi.reasoner.NodeSet;
//import org.semanticweb.owlapi.reasoner.OWLReasoner;

/**
 * @author agalawrynowicz
 */

public class VocabularyTest {

	//    protected final OWLModelManager modelManager;
	//    protected final OWLOntologyManager manager;
//	    protected final OWLDataFactory factory;
	    protected OWLOntology testedOntology;

//	    OWLReasoner reasoner;
	        
	    
	    public VocabularyTest(OWLOntology testedOntology) {
	  //      this.modelManager = modelManager;
	   // 	this.manager = manager;
	   //     this.factory=manager.getOWLDataFactory();
	        this.testedOntology = testedOntology;
	//        this.reasoner = reasoner;
	    }


		public  java.util.Set<OWLEntity> testVocabulary(OWLAxiom axiom) {
			
		//	java.util.Set<OWLEntity> allEntities;
			java.util.Set<OWLEntity> notReferencedEntities = new HashSet<OWLEntity>();

			try {
			//	allEntities = axiom.getSignature();
				//notReferencedEntities = new HashSet<OWLEntity>();
				
				for (Iterator<OWLEntity> it = axiom.getSignature().iterator(); it.hasNext();)
				{
					OWLEntity entity = it.next();
					if (!testedOntology.containsEntityInSignature(entity, true)){
						notReferencedEntities.add(entity);
					}
				
				}
			} catch(Exception e) {
				e.printStackTrace();
			}

			return notReferencedEntities;
		}

}
