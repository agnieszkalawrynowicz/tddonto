package put.aristoteles.tddontoprotege;

/**
 * @author agalawrynowicz
 *
 */

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.PrintWriter;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.table.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JComponent;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.HermiT.Reasoner.ReasonerFactory;
import org.apache.log4j.Logger;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.io.OWLParserFactory;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLParser;
import org.protege.editor.core.ui.action.ProtegeAction;
import org.protege.editor.core.ui.view.DisposableAction;
import org.protege.editor.core.ui.view.ViewAction;
import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.model.OWLModelManager;
import org.protege.editor.owl.model.inference.OWLReasonerManager;
import org.protege.editor.owl.model.inference.ReasonerStatus;
import org.protege.editor.owl.ui.selector.OWLClassSelectorPanel;
import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;
import org.protege.editor.core.ui.util.ComponentFactory;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxEditorParser;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;
import javax.swing.BorderFactory;
import java.awt.Color;
import org.protege.editor.owl.ui.clsdescriptioneditor.ExpressionEditor;
import org.protege.editor.owl.ui.clsdescriptioneditor.OWLExpressionChecker;
import org.protege.editor.owl.ui.clsdescriptioneditor.ExpressionEditor;
import org.protege.editor.owl.ui.clsdescriptioneditor.OWLExpressionChecker;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLUnaryPropertyAxiom;




public class TDDOntoRunner extends AbstractOWLViewComponent {

    private static final Logger logger = Logger.getLogger(TDDOntoRunner.class.getName());
 
    private JButton addTestButton;

    private JButton executeButton;
    private JButton addButton;
    private JButton deleteButton;
    private JCheckBox selectAllCheckBox;
    
    private ExpressionEditor<OWLClassAxiom> owlDescriptionEditor;
    private JTable table;
    private DefaultTableModel model;
    private ManchesterOWLSyntaxEditorParser parser;
    
    private OWLOntology ontology; 
    
    private SimpleMockObjectTest mockObjectTest; 
    private SimpleOWLAPITest owlAPITest; 
    private VocabularyTest vocabularyTest;
    
    private OWLReasoner reasoner;
    private OWLReasonerFactory reasonerFactory;
    OWLOntologyManager localManager;
    	
    private JComponent createTestsPanel() {
    	 
        JComponent testsPanel = new JPanel(new BorderLayout(10, 10));
        testsPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(
                Color.LIGHT_GRAY), "Tests"), BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        
        final OWLExpressionChecker<OWLClassAxiom> checker = getOWLModelManager().getOWLExpressionCheckerFactory().getClassAxiomChecker();
     
    	Object[] columnNames = {"Test", "Status", "Selection"};
        Object[][] data = {
  //          {new ExpressionEditor<OWLClassAxiom>(getOWLEditorKit(), checker), "OK",  false},
  //          {new ExpressionEditor<OWLClassAxiom>(getOWLEditorKit(), checker), "OK",  false},
  //          {new ExpressionEditor<OWLClassAxiom>(getOWLEditorKit(), checker), "",  false},
        };
        model = new DefaultTableModel(data, columnNames);
        table = new JTable(model) {

            private static final long serialVersionUID = 1L;

            /*@Override
            public Class getColumnClass(int column) {
            return getValueAt(0, column).getClass();
            }*/
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                    	return String.class;
                    case 1:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
        };
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JTableHeader header = table.getTableHeader();
        testsPanel.add(header);
        testsPanel.add(table);
        
        JPanel buttonHolder = new JPanel(new FlowLayout(FlowLayout.LEFT));
        executeButton = new JButton("Execute tests");
        executeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doExecuteTests();
			}
		});
        buttonHolder.add(executeButton);

        deleteButton = new JButton("Delete selected tests");
        deleteButton.addActionListener(new ActionListener() {
 			public void actionPerformed(ActionEvent e) {
 				doDelete();
 			}
 		});
        buttonHolder.add(deleteButton);

        addButton = new JButton("Add to ontology");
        addButton.addActionListener(new ActionListener() {
 			public void actionPerformed(ActionEvent e) {
 				doAddToOntology();
 			}
 		});
        buttonHolder.add(addButton);

        
        
        selectAllCheckBox = new JCheckBox("Select / Unselect All");
        selectAllCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doSelectAll();
			}
		});

        buttonHolder.add(selectAllCheckBox);

        testsPanel.add(buttonHolder, BorderLayout.SOUTH);
        
        return testsPanel;
    }
    

    private void doAdd() {
            ((DefaultTableModel) table.getModel()).addRow(new Object[]{owlDescriptionEditor.getText(), "", false});
    }


    private void doDelete() {
        ArrayList selectedRows = new ArrayList();
        
        int totalRows = table.getRowCount();
        for (int row=0; row < totalRows; row++) {
        	 boolean selected = (Boolean) table.getValueAt(row, 2);

        	 if (selected){
            	model.removeRow(row);
            }
        }
        model.fireTableDataChanged();
    }    

    private void doAddToOntology() {
        try {
        	ArrayList selectedRows = new ArrayList();
        
        	int j = 0;
        	
        	int totalRows = table.getRowCount();
        	for (int row=0; row < totalRows; row++) {
        	 boolean selected = (Boolean) table.getValueAt(row, 2);

        	 if (selected){
                 String axiomText = (String)table.getValueAt(row, 0);
             	 parser = new ManchesterOWLSyntaxEditorParser(ontology.getOWLOntologyManager().getOWLDataFactory(), axiomText);        
                 parser.setDefaultOntology(ontology);
                 OWLEntityChecker entityChecker = new ShortFormEntityChecker(new BidirectionalShortFormProviderAdapter(
                		                             getOWLModelManager().getOWLOntologyManager(),
                		                                           Collections.singleton(ontology),
                		                                           new SimpleShortFormProvider()  ));

              	 parser.setOWLEntityChecker(entityChecker);
              	
                 OWLAxiom axiom = null;
                  
                 axiom = parser.parseAxiom();

        		 
        		 AddAxiom addAxiom = new AddAxiom(ontology, axiom);
        		 getOWLModelManager().applyChange(addAxiom); 
        		 j++;
        	    } 
             }
        	reasoner.flush();
        	JOptionPane.showMessageDialog(null, String.valueOf(j)+ " axiom(s) added to the ontology.");
        }
        catch (Exception e){
        	e.printStackTrace();
        }
   }    
    
    private void doSelectAll() {

        ArrayList selectedRows = new ArrayList();
        boolean selected = selectAllCheckBox.isSelected();
        
        int totalRows = table.getRowCount();

        for (int row=0; row < totalRows; row++) {
        	if (selectAllCheckBox.isSelected()) {
       		    table.setValueAt(true, row, 2);
        	}
        	else {
       		    table.setValueAt(false, row, 2);
        	}
        }
   }    
    
    private void doInitialiseTests() {
        try {
        	OWLEditorKit editorKit = this.getOWLEditorKit();
        	OWLModelManager modelManager = editorKit.getOWLModelManager();
        	
        } catch (Exception e) {
        	e.printStackTrace();
         }
    }

    private void doExecuteTests() {
      try {
    	  
    	  localManager = getOWLModelManager().getOWLOntologyManager();//new OWLOntologyManager();
 
       	  ArrayList<OWLAxiom> axioms = new ArrayList<OWLAxiom>();    	 

          ontology = getOWLModelManager().getActiveOntology();
     	  OWLDataFactory dataFactory = ontology.getOWLOntologyManager().getOWLDataFactory();
          
          OWLEditorKit editorKit = this.getOWLEditorKit();
          OWLModelManager modelManager = editorKit.getOWLModelManager();
          OWLOntologyManager ontoManager = modelManager.getOWLOntologyManager();
          OWLReasonerManager reasonerManager = getOWLModelManager().getOWLReasonerManager();
            
          OWLReasonerFactory reasonerFactory =  reasonerManager.getCurrentReasonerFactory().getReasonerFactory();
        	
          reasoner = reasonerManager.getCurrentReasoner();
          if (reasonerManager.getReasonerStatus() == ReasonerStatus.INITIALIZED) {
  
             ArrayList selectedRows = new ArrayList();
          
             int totalRows = table.getRowCount();
             table.clearSelection();
             for (int row=0; row < totalRows; row++) {
                 boolean selected = (Boolean) table.getValueAt(row, 2);
                 if (selected){

                     String axiomText = (String)table.getValueAt(row, 0);
             	     parser = new ManchesterOWLSyntaxEditorParser(dataFactory, axiomText);        
                     parser.setDefaultOntology(ontology);
                     OWLEntityChecker entityChecker = new ShortFormEntityChecker(new BidirectionalShortFormProviderAdapter(
                		                                           localManager,
                		                                           Collections.singleton(ontology),
                		                                           new SimpleShortFormProvider()  ));

              	     parser.setOWLEntityChecker(entityChecker);
              	
                     OWLAxiom axiom = null;
                  
                     try {
                	     axiom = parser.parseAxiom();
                         axioms.add(axiom);
                         selectedRows.add(row);                      
                     }
                     catch (Exception e){
                	    e.printStackTrace();
                 	   table.setValueAt("Undefined", row, 1);
                     }

               }
           }
          
          if (!(reasoner.isPrecomputed(InferenceType.CLASS_HIERARCHY))) {
       	   	 reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
          }
          if (!reasoner.isPrecomputed(InferenceType.OBJECT_PROPERTY_HIERARCHY)) {
       	   	 reasoner.precomputeInferences(InferenceType.OBJECT_PROPERTY_HIERARCHY);
          }
          if (!reasoner.isPrecomputed(InferenceType.DATA_PROPERTY_HIERARCHY)) {
       	   	 reasoner.precomputeInferences(InferenceType.DATA_PROPERTY_HIERARCHY);
          }
          if (!reasoner.isPrecomputed(InferenceType.DISJOINT_CLASSES)) {
       	   	 reasoner.precomputeInferences(InferenceType.DISJOINT_CLASSES);
          }
          if (!reasoner.isPrecomputed(InferenceType.CLASS_ASSERTIONS)) {
       	   	 reasoner.precomputeInferences(InferenceType.CLASS_ASSERTIONS);
          }

          if (!reasoner.isPrecomputed(InferenceType.OBJECT_PROPERTY_ASSERTIONS)) {
        	 reasoner.precomputeInferences(InferenceType.OBJECT_PROPERTY_ASSERTIONS);
           }
          if (!reasoner.isPrecomputed(InferenceType.DATA_PROPERTY_ASSERTIONS)) {
       	   	 reasoner.precomputeInferences(InferenceType.DATA_PROPERTY_ASSERTIONS);
          }
          if (!reasoner.isPrecomputed(InferenceType.SAME_INDIVIDUAL)) {
       	   	 reasoner.precomputeInferences(InferenceType.SAME_INDIVIDUAL);
          }
          if (!reasoner.isPrecomputed(InferenceType.DIFFERENT_INDIVIDUALS)) {
          	 reasoner.precomputeInferences(InferenceType.DIFFERENT_INDIVIDUALS);
          }
          
   //       vocabularyTest = new VocabularyTest(getOWLModelManager().getActiveOntology());
          owlAPITest = new SimpleOWLAPITest(getOWLModelManager(), getOWLModelManager().getOWLOntologyManager(), getOWLModelManager().getActiveOntology(), reasoner);
          mockObjectTest = new SimpleMockObjectTest(getOWLModelManager().getOWLOntologyManager(), getOWLModelManager().getActiveOntology(), reasoner);
     	  
           int k = 0;
           for ( OWLAxiom axiom : axioms ) {
        	   
                int row1 = (Integer) selectedRows.get(k);
                if (axiom.getAxiomType() == AxiomType.SUBCLASS_OF) {
                	if (owlAPITest.testSubClassOf((OWLSubClassOfAxiom) axiom)) {
                		table.setValueAt("OK", (Integer) selectedRows.get(k), 1);
                	} else {
                		table.setValueAt("Failed", (Integer) selectedRows.get(k), 1);           	
                	}
                } else if (axiom.getAxiomType() == AxiomType.EQUIVALENT_CLASSES) {
                	if (owlAPITest.testEquivalentClasses((OWLEquivalentClassesAxiom) axiom)) {
                		table.setValueAt("OK", (Integer) selectedRows.get(k), 1);
                	} else {
                		table.setValueAt("Failed", (Integer) selectedRows.get(k), 1);           	
                	}
                } else if (axiom.getAxiomType() == AxiomType.DISJOINT_CLASSES) {
                	if (owlAPITest.testDisjointWith((OWLDisjointClassesAxiom) axiom)) {
                		table.setValueAt("OK", (Integer) selectedRows.get(k), 1);
                	} else {
                		table.setValueAt("Failed", (Integer) selectedRows.get(k), 1);           	
                    }
                } else if (axiom.getAxiomType() == AxiomType.SUB_OBJECT_PROPERTY) {
                	if (owlAPITest.testObjectPropertySubsumption((OWLSubObjectPropertyOfAxiom) axiom)) {
                		table.setValueAt("OK", (Integer) selectedRows.get(k), 1);
                	} else {
                		table.setValueAt("Failed", (Integer) selectedRows.get(k), 1);           	
                    }
                } else if (axiom.getAxiomType() == AxiomType.EQUIVALENT_OBJECT_PROPERTIES) {
                	if (owlAPITest.testObjectPropertyEquivalence((OWLEquivalentObjectPropertiesAxiom) axiom)) {
                		table.setValueAt("OK", (Integer) selectedRows.get(k), 1);
                	} else {
                		table.setValueAt("Failed", (Integer) selectedRows.get(k), 1);           	
                    }
                } else if (axiom.getAxiomType() == AxiomType.FUNCTIONAL_OBJECT_PROPERTY) {
                	//if (owlAPITest.testObjectPropertyFunctional(((OWLUnaryPropertyAxiom<OWLObjectPropertyExpression>) axiom).getProperty().asOWLObjectProperty())) {
                	if (owlAPITest.testObjectPropertyFunctional((OWLFunctionalObjectPropertyAxiom) axiom)) {
                		table.setValueAt("OK", (Integer) selectedRows.get(k), 1);
                	} else {
                		table.setValueAt("Failed", (Integer) selectedRows.get(k), 1);           	
                    }
                } else if (axiom.getAxiomType() == AxiomType.INVERSE_FUNCTIONAL_OBJECT_PROPERTY) {
                	if (owlAPITest.testObjectPropertyInverseFunctional(((OWLUnaryPropertyAxiom<OWLObjectPropertyExpression>) axiom).getProperty().asOWLObjectProperty())) {
                		table.setValueAt("OK", (Integer) selectedRows.get(k), 1);
                	} else {
                		table.setValueAt("Failed", (Integer) selectedRows.get(k), 1);           	
                    }
                } else if (axiom.getAxiomType() == AxiomType.TRANSITIVE_OBJECT_PROPERTY) {
                	if (owlAPITest.testObjectPropertyTransitive(((OWLUnaryPropertyAxiom<OWLObjectPropertyExpression>) axiom).getProperty().asOWLObjectProperty())) {
                		table.setValueAt("OK", (Integer) selectedRows.get(k), 1);
                	} else {
                		table.setValueAt("Failed", (Integer) selectedRows.get(k), 1);           	
                    }
                } else if (axiom.getAxiomType() == AxiomType.SYMMETRIC_OBJECT_PROPERTY) {
                	if (owlAPITest.testObjectPropertySymmetric(((OWLUnaryPropertyAxiom<OWLObjectPropertyExpression>) axiom).getProperty().asOWLObjectProperty())) {
                		table.setValueAt("OK", (Integer) selectedRows.get(k), 1);
                	} else {
                		table.setValueAt("Failed", (Integer) selectedRows.get(k), 1);           	
                    }
                } else if (axiom.getAxiomType() == AxiomType.ASYMMETRIC_OBJECT_PROPERTY) {
                	if (owlAPITest.testObjectPropertyAsymmetric(((OWLUnaryPropertyAxiom<OWLObjectPropertyExpression>) axiom).getProperty().asOWLObjectProperty())) {
                		table.setValueAt("OK", (Integer) selectedRows.get(k), 1);
                	} else {
                		table.setValueAt("Failed", (Integer) selectedRows.get(k), 1);           	
                    }
                }
                k++;
            }

            } else {
            	JOptionPane.showMessageDialog(null, "No reasoner has been initialized so inference cannot proceed. Go to the reasoner menu and select Start reasoner.");
            }
            
           
       } catch (Exception e) {
      	e.printStackTrace();
          //logger.error(marker, "An error occurred whilst adding the class definition: {}", e.getMessage(), e);
       }
  }

    private JComponent createQueryPanel() {
        JPanel editorPanel = new JPanel(new BorderLayout());
        JPanel buttonHolder1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton loadTestsButton;
        JButton saveTestsButton;
        
        final OWLExpressionChecker<OWLClassAxiom> checker = getOWLModelManager().getOWLExpressionCheckerFactory().getClassAxiomChecker();
        owlDescriptionEditor = new ExpressionEditor<OWLClassAxiom>(getOWLEditorKit(), checker);
        owlDescriptionEditor.setPreferredSize(new Dimension(100, 50));

        editorPanel.add(ComponentFactory.createScrollPane(owlDescriptionEditor), BorderLayout.CENTER);
        
        addTestButton = new JButton("Add test");
        addTestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doAdd();
			}
		});
        buttonHolder1.add(addTestButton);
        
        loadTestsButton = new JButton("Load tests");
        loadTestsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doLoadTests();
			}
		});
        buttonHolder1.add(loadTestsButton);
        
        saveTestsButton = new JButton("Save tests");
        saveTestsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doSaveTests();
			}
		});
        buttonHolder1.add(saveTestsButton);

        editorPanel.add(buttonHolder1, BorderLayout.SOUTH);
        editorPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(
                Color.LIGHT_GRAY), "New test"), BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        return editorPanel;
    }

    
    private void doSaveTests() {

    	try {
    		boolean selected ;
   
    		String csv; 
    		JFrame parentFrame = new JFrame();
         
    		JFileChooser fileChooser = new JFileChooser();
    		fileChooser.setDialogTitle("Specify a file to save");   
         
    		int userSelection = fileChooser.showSaveDialog(parentFrame);
         
    		if (userSelection == JFileChooser.APPROVE_OPTION) {
    			File fileToSave = fileChooser.getSelectedFile();
    			csv = fileToSave.getAbsolutePath();
    			System.out.println("Save as file: " + csv);
       
    			PrintWriter pw = new PrintWriter(new File(csv));
    			StringBuilder sb = new StringBuilder();
         
    			int totalRows = table.getRowCount();
    			for (int row=0; row < totalRows; row++) {
    				selected = (Boolean) table.getValueAt(row, 2);
    				if (selected){
    					table.setValueAt(true, row, 2);
    					sb = new StringBuilder();
    					sb.append((String) table.getValueAt(row, 0));
    					sb.append(';');
    					sb.append((String) table.getValueAt(row, 1));
    					sb.append(';');
    					sb.append("");    					
    					sb.append('\n');
       	        
    					pw.write(sb.toString());
    				}
    			}

    			pw.close();       
    		}
        }
    	catch (Exception e){
    		e.printStackTrace();
    	}
    }   

    private void doLoadTests() {

    	try {
    		boolean selected ;
    		BufferedReader br = null;
    		String line = "";
    		String cvsSplitBy = ";";
    		
    		String csv; 
    		JFrame parentFrame = new JFrame();
         
    		JFileChooser fileChooser = new JFileChooser();
    		fileChooser.setDialogTitle("Specify a file to load");   
         
    		int userSelection = fileChooser.showOpenDialog(parentFrame);
         
    		if (userSelection == JFileChooser.APPROVE_OPTION) {
    			//clear table
    			
    			File fileToLoad = fileChooser.getSelectedFile();
    			csv = fileToLoad.getAbsolutePath();
       
    	        int totalRows = model.getRowCount();
    	        //table.clearSelection();
    	        System.out.println("totalRows:"+totalRows);
    	        while (model.getRowCount() > 0) {
    	            model.removeRow(0);
    	        }
    			
    			br = new BufferedReader(new FileReader(csv));
    			while ((line = br.readLine()) != null) {
    				String[] test = line.split(cvsSplitBy);
    				Object[] rowContent = new Object[3];
    				rowContent[0] = test[0];
    				rowContent[1] = "";
    				rowContent[2] = false;
    				model.addRow(rowContent);
    			}
    			table.setModel(model);
    			model.fireTableDataChanged();
    			br.close();       
    		}
        }
    	catch (Exception e){
    		e.printStackTrace();
    	}
    }   
    
    @Override
    protected void initialiseOWLView() throws Exception {
  //      logger.trace("initialise() begin");
    	
        setLayout(new BorderLayout(10, 10));

        JComponent editorPanel = createQueryPanel();
        JComponent testsPanel = createTestsPanel() ;

        JSplitPane splitter = new JSplitPane(JSplitPane.VERTICAL_SPLIT, editorPanel, testsPanel);
        splitter.setDividerLocation(0.3);

        add(splitter, BorderLayout.CENTER);

  //      updateGUI();

        doInitialiseTests();
    }

    @Override
    protected void disposeOWLView() {
    }
    

}
