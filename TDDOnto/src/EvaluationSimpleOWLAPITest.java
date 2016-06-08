import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;

import org.coode.owlapi.functionalparser.OWLFunctionalSyntaxParser;
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
//import org.semanticweb.sparql.arq.OWLOntologyDataSet;
import org.semanticweb.owlapi.util.AutoIRIMapper;
import org.semanticweb.owlapi.util.SimpleIRIMapper;
import org.semanticweb.sparql.arq.OWLOntologyDataSet;

//import main.java.put.aristoteles.tddontoprotege.SimpleMockObjectTest;




import java.util.logging.Logger;


public class EvaluationSimpleOWLAPITest {
	SimpleTestGenerator gen;
//	SimpleMockObjectTest t1; 
	SimpleSPARQLOWLTest t2; 
	SimpleOWLAPITest t3;
	File file;
	
	OWLOntologyManager manager;
	protected OWLOntology ontology;
    OWLOntologyDataSet ontologyDs;
    Reasoner reasoner;
	OWLFunctionalSyntaxParser parser;
    
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    FileWriter track;
    
    class Log {
    	long startTime; // = System.nanoTime();
    	long endTime;// = System.nanoTime();
    	long duration;// = (endTime - startTime);
    	String test;
    	String testMode;
    	String onto;
    	boolean finished;
    	String exception;
    }
    
	
	public EvaluationSimpleOWLAPITest(String filename) {
//		file=new File("/home/daenerys/workspace/owl-bgp-master/test/org/semanticweb/sparql/owlbgp/evaluation/ontologies/pizza.owl");
		try {
			file=new File(filename);

			File dir = new File("/Users/lawrynka/Desktop/owl2ontologies-kopia");
			
			manager = OWLManager.createOWLOntologyManager();
			
			OWLOntologyIRIMapper autoIRIMapper = new AutoIRIMapper(dir, true);
	        // We can now use this mapper in the usual way, i.e.
			manager.addIRIMapper(
				    new SimpleIRIMapper( IRI.create( "http://www.meteck.org/files/ontologies/goldModule.owl" ), 
				                    IRI.create( "file:/Users/lawrynka/Desktop/owl2ontologies/goldModule.owl" ) ) );
			manager.addIRIMapper(
				    new SimpleIRIMapper( IRI.create( "http://www.informatik.uni-bremen.de/~joana/ontology/modSpace/RCC-Ontology.owl" ), 
				                    IRI.create( "file:/Users/lawrynka/Desktop/owl2ontologies/RCC-Ontology.owl" ) ) );
							
				
			manager.addIRIMapper(autoIRIMapper);
			ontology = manager.loadOntologyFromOntologyDocument(file);

					ontologyDs = new OWLOntologyDataSet(file);
	        reasoner = new Reasoner(ontology);

	        
			gen = new SimpleTestGenerator(manager, ontology);
		//	t1 = new SimpleMockObjectTest(manager,ontology, reasoner);
			t2 = new SimpleSPARQLOWLTest(manager, ontology, ontologyDs);
			t3 = new SimpleOWLAPITest(manager,ontology, reasoner);		
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
	//		OWLSubClassOfAxiom axiom1s = manager.getOWLDataFactory().getOWLSubClassOfAxiom();
		//	String ss = "";
//	        parser = new OWLFunctionalSyntaxParser(new InputStream(ss.getBytes()));
	        
			System.out.println("\n Axiom:"+axiom1.toString());
		    //LOGGER.entering(sourceClass, sourceMethod);
		    
		    startTime  = System.nanoTime();
		    res = t3.testSubClassOf(axiom1);
		    endTime = System.nanoTime();
		    duration = (endTime - startTime);
		    test = "testSubClassOf";
		    testMode = "owlapi";
		    
		    track.write(onto+";"+classificationDuration+";"+axiom1.toString()+";"+ startTime +";"+ endTime +";"+ duration +";"+ test +";"+ testMode +";"+ "\n");
//		    track.close();
		    
		    System.out.println("\n Result1:"+res);
		    
			 
			System.out.println("======== testDisjointWith =========");

			OWLDisjointClassesAxiom axiom2 = gen.generateDisjointClassesAxiom(manager, ontology, numberOfExitingOntologyURIs);
			System.out.println("\n Axiom:"+axiom2.toString());
		    startTime  = System.nanoTime();
			res = t3.testDisjointWith(axiom2);
		    endTime = System.nanoTime();
		    duration = (endTime - startTime);
		    test = "testDisjointWith";
		    testMode = "owlapi";
	//	    track = new FileWriter("/home/daenerys/track"+onto+"-"+i+".txt");
		    track.write(onto+";"+classificationDuration+";"+axiom2.toString() +";"+ startTime +";"+ endTime +";"+ duration +";"+ test +";"+ testMode +";"+ "\n");
		//    track.close();

			System.out.println("\n Result1:"+res);

			System.out.println("======== testEquivalentClasses =========");

			OWLEquivalentClassesAxiom axiom3 = gen.generateEquivalentClassesAxiom(manager, ontology, numberOfExitingOntologyURIs);
			System.out.println("\n Axiom:"+axiom3.toString());
		    startTime  = System.nanoTime();
			res = t3.testEquivalentClasses(axiom3);
		    endTime = System.nanoTime();
		    duration = (endTime - startTime);
		    test = "testEquivalentClasses";
		    testMode = "owlapi";
		  //  track = new FileWriter("/home/daenerys/track"+onto+"-"+i+".txt");
		    track.write(onto+";"+classificationDuration+";"+axiom3.toString() +";"+ startTime +";"+ endTime +";"+ duration +";"+ test +";"+ testMode +";"+ "\n");
		  //  track.close();

			System.out.println("\n Result1:"+res);
			
			System.out.println("======== testSimpleExistentialQuantification =========");

			OWLSubClassOfAxiom axiom4 = gen.generateSimpleExistentialQuantificationAxiom(manager, ontology, numberOfExitingOntologyURIs);
			System.out.println("\n Axiom:"+axiom4.toString());
		    startTime  = System.nanoTime();

			res = t3.testSimpleExistentialQuantificationTBox(axiom4);
		    endTime = System.nanoTime();
		    duration = (endTime - startTime);
		    test = "testSimpleExistentialQuantification";
		    testMode = "owlapi";
		  //  track = new FileWriter("/home/daenerys/track"+onto+"-"+i+".txt");
		    track.write(onto+";"+classificationDuration+";"+axiom4.toString() +";"+ startTime +";"+ endTime +";"+ duration +";"+ test +";"+ testMode +";"+ "\n");
		   // track.close();

			System.out.println("\n Result1:"+res);

			System.out.println("======== testUniversalExistentialQuantification =========");

			OWLSubClassOfAxiom axiom5 = gen.generateSimpleUniversalQuantificationAxiom(manager, ontology, numberOfExitingOntologyURIs);
			System.out.println("\n Axiom:"+axiom5.toString());
		    startTime  = System.nanoTime();
			res = t3.testSimpleUniversalQuantificationTBox(axiom5);
		    endTime = System.nanoTime();
		    duration = (endTime - startTime);
		    test = "testSimpleUniversalQuantification";
		    testMode = "owlapi";
		   // track = new FileWriter("/home/daenerys/track"+onto+"-"+i+".txt");
		    track.write(onto+";"+classificationDuration+";"+axiom5.toString() +";"+ startTime +";"+ endTime +";"+ duration +";"+ test +";"+ testMode +";"+ "\n");
		   // track.close();

			System.out.println("\n Result1:"+res);
			
			System.out.println("======== testObjectPropertyDomain =========");

			OWLSubClassOfAxiom axiom6 = gen.generateObjectPropertyDomain(manager, ontology, numberOfExitingOntologyURIs);
			System.out.println("\n Axiom:"+axiom6.toString());
		    startTime  = System.nanoTime();
			res = t3.testObjectPropertyDomain(axiom6);
		    endTime = System.nanoTime();
		    duration = (endTime - startTime);
		    test = "testObjectPropertyDomain";
		    testMode = "owlapi";
		   // track = new FileWriter("/home/daenerys/track"+onto+"-"+i+".txt");
		    track.write(onto+";"+classificationDuration+";"+axiom6.toString() +";"+ startTime +";"+ endTime +";"+ duration +";"+ test +";"+ testMode +";"+ "\n");
		   // track.close();


			System.out.println("======== testObjectPropertyRange =========");

			OWLSubClassOfAxiom axiom7 = gen.generateObjectPropertyRange(manager, ontology, numberOfExitingOntologyURIs);
			System.out.println("\n Axiom:"+axiom7.toString());
		    startTime  = System.nanoTime();
			res = t3.testObjectPropertyRange(axiom7);
		    endTime = System.nanoTime();
		    duration = (endTime - startTime);
		    test = "testObjectPropertyRange";
		    testMode = "owlapi";
		   // track = new FileWriter("/home/daenerys/track"+onto+"-"+i+".txt");
		    track.write(onto+";"+classificationDuration+";"+axiom7.toString() +";"+ startTime +";"+ endTime +";"+ duration +";"+ test +";"+ testMode +";"+ "\n");
		   // track.close();

			System.out.println("\n Result1:"+res);

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
		File path = new File("/Users/lawrynka/Desktop/owl2_ontologies-kopia");

	    File [] files = path.listFiles();
	    Arrays.sort(files);
	    
	    for (int i = 0; i < files.length; i++){
	        if (files[i].isFile() 
	        		&& !files[i].toString().contains("catalog-v001.xml")){ 
	            System.out.println(files[i]);
	    		eval = new Evaluation(files[i].toString());
	    		
	    		try {
	    			eval.evaluate(2,  3);
	    	    //	if (files[i].renameTo(new File("/Users/lawrynka/Desktop/tonesDone/" + files[i].getName()))){
	    	     //  		System.out.println("File is moved successful!");
	    	     //  	 }else{
	    	      // 		System.out.println("File is failed to move!");
	    	       //	   }
	    		}
	    		catch (Exception e){
	    			problem.add(files[i].toString());
	    			//e.printStackTrace();
	    		}
	        }
	    }
		System.out.println("files.length:"+files.length);
	    
	    for (Object key : problem){
			System.out.println("problem,"+key.toString());
	    }
		
	}
}
