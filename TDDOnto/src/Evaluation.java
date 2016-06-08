import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyIRIMapper;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.util.AutoIRIMapper;
import org.semanticweb.owlapi.util.SimpleIRIMapper;
import org.semanticweb.sparql.arq.OWLOntologyDataSet;

import com.sun.tools.javac.util.List;

import java.util.logging.Logger;


public class Evaluation {
	SimpleTestGenerator gen;
	SimpleMockObjectTest t1; 
	SimpleSPARQLOWLTest t2; 
//	SimpleOWLAPITest t3;

	File file;
	
	OWLOntologyManager manager;
	protected OWLOntology ontology;
    OWLOntologyDataSet ontologyDs;
    Reasoner reasoner;
    
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    FileWriter track;
    
    class Log {
    	long startTime; 
    	long endTime;
    	long duration;
    	String test;
    	String testMode;
    	String onto;
    	boolean finished;
    	String exception;
    }
    
	
	public Evaluation(String filename) {
//		file=new File("/home/daenerys/workspace/owl-bgp-master/test/org/semanticweb/sparql/owlbgp/evaluation/ontologies/pizza.owl");
		try {
			file=new File(filename);
			File dir = new File("/Users/lawrynka/Desktop/results-owl2-ontologies-kopia");
			
			manager = OWLManager.createOWLOntologyManager();
			
			
			OWLOntologyIRIMapper autoIRIMapper = new AutoIRIMapper(dir, true);
			ontology = manager.loadOntologyFromOntologyDocument(file);
			
			ontologyDs = new OWLOntologyDataSet(file);
	        reasoner = new Reasoner(ontology);

			gen = new SimpleTestGenerator(manager, ontology);
			t1 = new SimpleMockObjectTest(manager,ontology, reasoner);
			t2 = new SimpleSPARQLOWLTest(manager, ontology, ontologyDs);
	//		t3 = new SimpleOWLAPITest(manager,ontology, reasoner);
			
			//track = new FileWriter("/home/daenerys/track.txt");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void evaluate(int numberOfExitingOntologyURIs, int numberOfIterations) {
		try {
		
    	long startTime; 
    	long endTime;
    	long duration;
    	long classificationDuration;
    	String test;
    	String testMode;
    	String onto = file.getName();
    	boolean finished;
    	String exception;
    	FileWriter track;
    	
		boolean res;
		
	    startTime  = System.nanoTime();
		reasoner.precomputeInferences(InferenceType.CLASS_ASSERTIONS,InferenceType.CLASS_HIERARCHY,InferenceType.DIFFERENT_INDIVIDUALS,InferenceType.DISJOINT_CLASSES,InferenceType.OBJECT_PROPERTY_ASSERTIONS,InferenceType.OBJECT_PROPERTY_HIERARCHY,InferenceType.SAME_INDIVIDUAL);
	    endTime = System.nanoTime();
	    classificationDuration = (endTime - startTime);
		
		for (int i=0; i<numberOfIterations; i++){
		    track = new FileWriter("/Users/lawrynka/Desktop/tonesResults2/"+onto+"-"+i+".txt");
			
			
			System.out.println("======== testSubClassOf =========");

			OWLSubClassOfAxiom axiom1 = gen.generateSubClassOfAxiom(manager, ontology, numberOfExitingOntologyURIs);
		    System.out.println("\n Axiom:"+axiom1.toString());
		    //LOGGER.entering(sourceClass, sourceMethod);
		    
		    startTime  = System.nanoTime();
		    res = t1.testSubClassOf(axiom1);
		    endTime = System.nanoTime();
		    duration = (endTime - startTime);
		    test = "testSubClassOf";
		    testMode = "mock";
		    
		    track.write(onto+";"+classificationDuration+";"+axiom1.toString()+";"+ startTime +";"+ endTime +";"+ duration +";"+ test +";"+ testMode +";"+ "\n");
//		    track.close();
		    
		    System.out.println("\n Result1:"+res);
		    
		    startTime  = System.nanoTime();
		    res = t2.testSubClassOf(axiom1);
		    endTime = System.nanoTime();
		    duration = (endTime - startTime);
		    test = "testSubClassOf";
		    testMode = "sparql";
	//	    track = new FileWriter("/home/daenerys/track"+onto+"-"+i+".txt");
		    track.write(onto+";"+classificationDuration+";"+axiom1.toString() +";"+ startTime +";"+ endTime +";"+ duration +";"+ test +";"+ testMode +";"+ "\n");
	//	    track.close();
		    
			System.out.println("\n Result2:"+res);
			 
			System.out.println("======== testDisjointWith =========");

			OWLDisjointClassesAxiom axiom2 = gen.generateDisjointClassesAxiom(manager, ontology, numberOfExitingOntologyURIs);
			System.out.println("\n Axiom:"+axiom2.toString());
		    startTime  = System.nanoTime();

			res = t1.testDisjointWith(axiom2);
		    endTime = System.nanoTime();
		    duration = (endTime - startTime);
		    test = "testDisjointWith";
		    testMode = "mock";
	//	    track = new FileWriter("/home/daenerys/track"+onto+"-"+i+".txt");
		    track.write(onto+";"+classificationDuration+";"+axiom2.toString() +";"+ startTime +";"+ endTime +";"+ duration +";"+ test +";"+ testMode +";"+ "\n");
		//    track.close();

			System.out.println("\n Result1:"+res);
		    startTime  = System.nanoTime();

			res = t2.testDisjointWith(axiom2);
		    endTime = System.nanoTime();
		    duration = (endTime - startTime);
		    test = "testDisjointWith";
		    testMode = "sparql";
		 //   track = new FileWriter("/home/daenerys/track"+onto+"-"+i+".txt");
		    track.write(onto+";"+classificationDuration+";"+axiom2.toString() +";"+ startTime +";"+ endTime +";"+ duration +";"+ test +";"+ testMode +";"+ "\n");
		  //  track.close();

			System.out.println("\n Result2:"+res);

			System.out.println("======== testEquivalentClasses =========");

			OWLEquivalentClassesAxiom axiom3 = gen.generateEquivalentClassesAxiom(manager, ontology, numberOfExitingOntologyURIs);
			System.out.println("\n Axiom:"+axiom3.toString());
		    startTime  = System.nanoTime();

			res = t1.testEquivalentClasses(axiom3);
		    endTime = System.nanoTime();
		    duration = (endTime - startTime);
		    test = "testEquivalentClasses";
		    testMode = "mock";
		  //  track = new FileWriter("/home/daenerys/track"+onto+"-"+i+".txt");
		    track.write(onto+";"+classificationDuration+";"+axiom3.toString() +";"+ startTime +";"+ endTime +";"+ duration +";"+ test +";"+ testMode +";"+ "\n");
		  //  track.close();

			System.out.println("\n Result1:"+res);
		    startTime  = System.nanoTime();

			res = t2.testEquivalentClasses(axiom3);
		    endTime = System.nanoTime();
		    duration = (endTime - startTime);
		    test = "testEquivalentClasses";
		    testMode = "sparql";

		   // track = new FileWriter("/home/daenerys/track"+onto+"-"+i+".txt");
		    track.write(onto+";"+classificationDuration+";"+axiom3.toString() +";"+ startTime +";"+ endTime +";"+ duration +";"+ test +";"+ testMode +";"+ "\n");
		   // track.close();

			System.out.println("\n Result2:"+res);
			
			System.out.println("======== testSimpleExistentialQuantification =========");

			OWLSubClassOfAxiom axiom4 = gen.generateSimpleExistentialQuantificationAxiom(manager, ontology, numberOfExitingOntologyURIs);
			System.out.println("\n Axiom:"+axiom4.toString());
		    startTime  = System.nanoTime();

			res = t1.testSimpleExistentialQuantificationTBox(axiom4);
		    endTime = System.nanoTime();
		    duration = (endTime - startTime);
		    test = "testSimpleExistentialQuantification";
		    testMode = "mock";
		  //  track = new FileWriter("/home/daenerys/track"+onto+"-"+i+".txt");
		    track.write(onto+";"+classificationDuration+";"+axiom4.toString() +";"+ startTime +";"+ endTime +";"+ duration +";"+ test +";"+ testMode +";"+ "\n");
		   // track.close();

			System.out.println("\n Result1:"+res);
		    startTime  = System.nanoTime();

			res = t2.testSimpleExistentialQuantification(axiom4);
		    endTime = System.nanoTime();
		    duration = (endTime - startTime);
		    test = "testSimpleExistentialQuantification";
		    testMode = "sparql";
		   // track = new FileWriter("/home/daenerys/track"+onto+"-"+i+".txt");
		    track.write(onto+";"+classificationDuration+";"+axiom4.toString() +";"+ startTime +";"+ endTime +";"+ duration +";"+ test +";"+ testMode +";"+ "\n");
		   // track.close();

			System.out.println("\n Result2:"+res);

			System.out.println("======== testUniversalExistentialQuantification =========");

			OWLSubClassOfAxiom axiom5 = gen.generateSimpleUniversalQuantificationAxiom(manager, ontology, numberOfExitingOntologyURIs);
			System.out.println("\n Axiom:"+axiom5.toString());
		    startTime  = System.nanoTime();
			res = t1.testSimpleUniversalQuantificationTBox(axiom5);
		    endTime = System.nanoTime();
		    duration = (endTime - startTime);
		    test = "testSimpleUniversalQuantification";
		    testMode = "mock";
		   // track = new FileWriter("/home/daenerys/track"+onto+"-"+i+".txt");
		    track.write(onto+";"+classificationDuration+";"+axiom5.toString() +";"+ startTime +";"+ endTime +";"+ duration +";"+ test +";"+ testMode +";"+ "\n");
		   // track.close();

			System.out.println("\n Result1:"+res);
		    startTime  = System.nanoTime();
			res = t2.testSimpleUniversalQuantification(axiom5);
		    endTime = System.nanoTime();
		    duration = (endTime - startTime);
		    test = "testSimpleUniversalQuantification";
		    testMode = "sparql";
		   // track = new FileWriter("/home/daenerys/track"+onto+"-"+i+".txt");
		    track.write(onto+";"+classificationDuration+";"+axiom5.toString() +";"+ startTime +";"+ endTime +";"+ duration +";"+ test +";"+ testMode +";"+ "\n");
		   // track.close();

			
			System.out.println("\n Result2:"+res);
			
			System.out.println("======== testObjectPropertyDomain =========");

			OWLSubClassOfAxiom axiom6 = gen.generateObjectPropertyDomain(manager, ontology, numberOfExitingOntologyURIs);
			System.out.println("\n Axiom:"+axiom6.toString());
		    startTime  = System.nanoTime();
			res = t1.testObjectPropertyDomain(axiom6);
		    endTime = System.nanoTime();
		    duration = (endTime - startTime);
		    test = "testObjectPropertyDomain";
		    testMode = "mock";
		   // track = new FileWriter("/home/daenerys/track"+onto+"-"+i+".txt");
		    track.write(onto+";"+classificationDuration+";"+axiom6.toString() +";"+ startTime +";"+ endTime +";"+ duration +";"+ test +";"+ testMode +";"+ "\n");
		   // track.close();

			System.out.println("\n Result1:"+res);
		    startTime  = System.nanoTime();
			res = t2.testObjectPropertyDomain(axiom6);
		    endTime = System.nanoTime();
		    duration = (endTime - startTime);
		    test = "testObjectPropertyDomain";
		    testMode = "sparql";
		   // track = new FileWriter("/home/daenerys/track"+onto+"-"+i+".txt");
		    track.write(onto+";"+classificationDuration+";"+axiom6.toString() +";"+ startTime +";"+ endTime +";"+ duration +";"+ test +";"+ testMode +";"+ "\n");
		   // track.close();

			System.out.println("\n Result2:"+res);

			System.out.println("======== testObjectPropertyRange =========");

			OWLSubClassOfAxiom axiom7 = gen.generateObjectPropertyRange(manager, ontology, numberOfExitingOntologyURIs);
			System.out.println("\n Axiom:"+axiom7.toString());
		    startTime  = System.nanoTime();
			res = t1.testObjectPropertyRange(axiom7);
		    endTime = System.nanoTime();
		    duration = (endTime - startTime);
		    test = "testObjectPropertyRange";
		    testMode = "mock";
		   // track = new FileWriter("/home/daenerys/track"+onto+"-"+i+".txt");
		    track.write(onto+";"+classificationDuration+";"+axiom7.toString() +";"+ startTime +";"+ endTime +";"+ duration +";"+ test +";"+ testMode +";"+ "\n");
		   // track.close();

			System.out.println("\n Result1:"+res);
		    startTime  = System.nanoTime();
			res = t2.testObjectPropertyRange(axiom7);
		    endTime = System.nanoTime();
		    duration = (endTime - startTime);
		    test = "testObjectPropertyRange";
		    testMode = "sparql";
		   // track = new FileWriter("/home/daenerys/track"+onto+"-"+i+".txt");
		    track.write(onto+";"+classificationDuration+";"+axiom7.toString() +";"+ startTime +";"+ endTime +";"+ duration +";"+ test +";"+ testMode +";"+ "\n");
			System.out.println("\n Result2:"+res);

		    track.close();
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String filename="/Users/lawrynka/Documents/workspace/owl-bgp-master-2/test/org/semanticweb/sparql/owlbgp/evaluation/ontologies/pizza.owl";
		//String filename = "/home/daenerys/workspace/owl-bgp-master/test/org/semanticweb/sparql/owlbgp/evaluation/ontologies/pizza.owl";
		Evaluation eval = new Evaluation(filename);
		eval.evaluate(2,  3);
		
		HashSet problem = new HashSet();
		
	    //File path = new File("/home/daenerys/Original_ROMULUS");
		File path = new File("/Users/lawrynka/Desktop/owl2ontologies");

	    File [] files = path.listFiles();
	    Arrays.sort(files);
	    
	    for (int i = 0; i < files.length; i++){
	        if (files[i].isFile() 
	        		&& !files[i].toString().contains("catalog-v001.xml")){ 
	            System.out.println(files[i]);
	    		eval = new Evaluation(files[i].toString());
	    		
	    		try {
	    			eval.evaluate(2,  3);
	    		}
	    		catch (Exception e){
	    			problem.add(files[i].toString());
	    			//e.printStackTrace();
	    		}
	        }
	    }
	    
	}
}
