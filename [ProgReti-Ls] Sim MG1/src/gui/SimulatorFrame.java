package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingUtilities;

import simulator.ISimulationProgressListener;
import simulator.SimulationProgress;
import simulator.SimulationRunners;
import simulator.Utils;
import simulator.distribution.DeterministicDistribution;
import simulator.distribution.Distribution;
import simulator.distribution.DistributionType;
import simulator.distribution.ExponentialDistribution;
import simulator.distribution.ParetoDistribution;
import simulator.distribution.SPPDistribution;
import simulator.distribution.UniformDistribution;
import simulator.random.Provider;

/**
 *
 * @author andreazagnoli
 */
public class SimulatorFrame extends javax.swing.JFrame implements ActionListener {

	private static final long serialVersionUID = -8210143930250467129L;
	
    public SimulatorFrame() {
        initComponents();
        
        distCbx.setModel(new DefaultComboBoxModel(simulator.distribution.DistributionType.values()));
        distCbx.addActionListener(this);
        distCbx_mg1.setModel(new DefaultComboBoxModel(new String[]{"Deterministic","Exponential","Pareto 2.5","Pareto 1.2"}));
        classes_prio.setModel(new DefaultComboBoxModel(new String[]{"2","3a","3b","3c"}));
        this.rndCbx.setModel(new DefaultComboBoxModel(Provider.values()));
        generateReport.addActionListener(this);
        placeholderPanel.setLayout(new BorderLayout());
        simulate_mg1.addActionListener(this);
        distRbt.setSelected(true);
        distRbt.addActionListener(this);
        rhoRbt.addActionListener(this);
        estimateRbtn.addActionListener(this);
        this.runsRbtn.addActionListener(this);
        this.confRbtn.addActionListener(this);
        toggleMG1Parameters(false,true);
        this.simulate_prio.addActionListener(this);
        this.testConfidence.addActionListener(this);
        SimulationProgress.getInstance().addIndexingProgressListener((ISimulationProgressListener)simPbr);
        
        console = ConsoleFrame.getInstance();
        console.setLocation(new java.awt.Point(this.getLocation().x + this.getWidth() + 10,this.getLocation().y));
        consoleTBtn.addActionListener(this);
        
    }

   private void initComponents() {
       buttonGroup1 = new javax.swing.ButtonGroup();
       buttonGroup2 = new javax.swing.ButtonGroup();
       jTabbedPane1 = new javax.swing.JTabbedPane();
       jPanel1 = new javax.swing.JPanel();
       testConfidence = new javax.swing.JButton();
       jLabel10 = new javax.swing.JLabel();
       rndCbx = new javax.swing.JComboBox();
       runsRbtn = new javax.swing.JRadioButton();
       confRbtn = new javax.swing.JRadioButton();
       confidenceTxt = new javax.swing.JTextField();
       confidenceLbl = new javax.swing.JLabel();
       jPanel2 = new javax.swing.JPanel();
       jLabel1 = new javax.swing.JLabel();
       placeholderPanel = new javax.swing.JPanel();
       distCbx = new javax.swing.JComboBox();
       jLabel2 = new javax.swing.JLabel();
       lambda = new javax.swing.JTextField();
       jLabel3 = new javax.swing.JLabel();
       runs = new javax.swing.JTextField();
       generateReport = new javax.swing.JButton();
       jPanel3 = new javax.swing.JPanel();
       simulate_mg1 = new javax.swing.JButton();
       distCbx_mg1 = new javax.swing.JComboBox();
       jLabel4 = new javax.swing.JLabel();
       jLabel5 = new javax.swing.JLabel();
       rho = new javax.swing.JTextField();
       jLabel6 = new javax.swing.JLabel();
       mu = new javax.swing.JTextField();
       runs_mg1 = new javax.swing.JTextField();
       jLabel7 = new javax.swing.JLabel();
       distRbt = new javax.swing.JRadioButton();
       rhoRbt = new javax.swing.JRadioButton();
       estimateRbtn = new javax.swing.JRadioButton();
       jPanel4 = new javax.swing.JPanel();
       classes_prio = new javax.swing.JComboBox();
       jLabel8 = new javax.swing.JLabel();
       jLabel9 = new javax.swing.JLabel();
       mu_prio = new javax.swing.JTextField();
       simulate_prio = new javax.swing.JButton();
       runs_prio = new javax.swing.JTextField();
       jLabel12 = new javax.swing.JLabel();
       simPbr = new SimulationProgressBar();
       jLabel11 = new javax.swing.JLabel();
       consoleTBtn = new javax.swing.JToggleButton();

       setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

       testConfidence.setText("Test confidence interval");

       jLabel10.setText("Random provider");

       rndCbx.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

       buttonGroup2.add(runsRbtn);
       runsRbtn.setText("variable runs");

       buttonGroup2.add(confRbtn);
       confRbtn.setText("variable confidence level");

       confidenceLbl.setText("confidence level:");

       org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
       jPanel1.setLayout(jPanel1Layout);
       jPanel1Layout.setHorizontalGroup(
           jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
           .add(jPanel1Layout.createSequentialGroup()
               .addContainerGap()
               .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                   .add(testConfidence)
                   .add(runsRbtn)
                   .add(confRbtn)
                   .add(jPanel1Layout.createSequentialGroup()
                       .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                           .add(jLabel10)
                           .add(confidenceLbl))
                       .add(18, 18, 18)
                       .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                           .add(confidenceTxt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                           .add(rndCbx, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 171, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
               .addContainerGap(64, Short.MAX_VALUE))
       );
       jPanel1Layout.setVerticalGroup(
           jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
           .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
               .add(79, 79, 79)
               .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                   .add(jLabel10)
                   .add(rndCbx, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
               .add(18, 18, 18)
               .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                   .add(confidenceLbl)
                   .add(confidenceTxt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
               .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
               .add(runsRbtn)
               .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
               .add(confRbtn)
               .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 81, Short.MAX_VALUE)
               .add(testConfidence)
               .addContainerGap())
       );

       jTabbedPane1.addTab("RandomNumber", jPanel1);

       jLabel1.setText("Distribution type");

       org.jdesktop.layout.GroupLayout placeholderPanelLayout = new org.jdesktop.layout.GroupLayout(placeholderPanel);
       placeholderPanel.setLayout(placeholderPanelLayout);
       placeholderPanelLayout.setHorizontalGroup(
           placeholderPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
           .add(0, 288, Short.MAX_VALUE)
       );
       placeholderPanelLayout.setVerticalGroup(
           placeholderPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
           .add(0, 97, Short.MAX_VALUE)
       );

       distCbx.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

       jLabel2.setText("Lambda:");

       jLabel3.setText("# runs:");

       generateReport.setText("Generate report");

       org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
       jPanel2.setLayout(jPanel2Layout);
       jPanel2Layout.setHorizontalGroup(
           jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
           .add(jPanel2Layout.createSequentialGroup()
               .addContainerGap()
               .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                   .add(generateReport)
                   .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                       .add(org.jdesktop.layout.GroupLayout.LEADING, placeholderPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                       .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel2Layout.createSequentialGroup()
                           .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                               .add(jLabel2)
                               .add(jLabel1)
                               .add(jLabel3))
                           .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                           .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                               .add(runs, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                               .add(lambda, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                               .add(distCbx, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 171, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))
               .addContainerGap(72, Short.MAX_VALUE))
       );
       jPanel2Layout.setVerticalGroup(
           jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
           .add(jPanel2Layout.createSequentialGroup()
               .addContainerGap()
               .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                   .add(jLabel1)
                   .add(distCbx, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
               .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
               .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                   .add(jLabel2)
                   .add(lambda, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
               .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
               .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                   .add(jLabel3)
                   .add(runs, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
               .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
               .add(placeholderPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
               .add(generateReport)
               .addContainerGap(78, Short.MAX_VALUE))
       );

       jTabbedPane1.addTab("VariableTraffic", jPanel2);

       simulate_mg1.setText("Simulate");

       distCbx_mg1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

       jLabel4.setText("Distribution type");

       jLabel5.setText("Rho:");

       jLabel6.setText("Mu:");

       jLabel7.setText("# runs:");

       buttonGroup1.add(distRbt);
       distRbt.setText("variable distribution");

       buttonGroup1.add(rhoRbt);
       rhoRbt.setText("variable rho");

       buttonGroup1.add(estimateRbtn);
       estimateRbtn.setText("esimate state's probability");

       org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
       jPanel3.setLayout(jPanel3Layout);
       jPanel3Layout.setHorizontalGroup(
           jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
           .add(jPanel3Layout.createSequentialGroup()
               .addContainerGap()
               .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                   .add(estimateRbtn)
                   .add(simulate_mg1)
                   .add(jPanel3Layout.createSequentialGroup()
                       .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                           .add(jLabel5)
                           .add(jLabel4)
                           .add(jLabel6)
                           .add(jLabel7))
                       .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                       .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                           .add(runs_mg1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                           .add(mu, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                           .add(rho, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                           .add(distCbx_mg1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 171, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                   .add(distRbt)
                   .add(rhoRbt))
               .addContainerGap(72, Short.MAX_VALUE))
       );
       jPanel3Layout.setVerticalGroup(
           jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
           .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel3Layout.createSequentialGroup()
               .addContainerGap()
               .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                   .add(jLabel4)
                   .add(distCbx_mg1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
               .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
               .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                   .add(jLabel5)
                   .add(rho, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
               .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
               .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                   .add(jLabel6)
                   .add(mu, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
               .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
               .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                   .add(jLabel7)
                   .add(runs_mg1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
               .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
               .add(distRbt)
               .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
               .add(rhoRbt)
               .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
               .add(estimateRbtn)
               .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 44, Short.MAX_VALUE)
               .add(simulate_mg1)
               .addContainerGap())
       );

       jTabbedPane1.addTab("M/G/1", jPanel3);

       classes_prio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

       jLabel8.setText("Number of classes");

       jLabel9.setText("Mu:");

       simulate_prio.setText("Simulate");

       jLabel12.setText("# runs:");

       org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
       jPanel4.setLayout(jPanel4Layout);
       jPanel4Layout.setHorizontalGroup(
           jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
           .add(jPanel4Layout.createSequentialGroup()
               .addContainerGap()
               .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                   .add(simulate_prio)
                   .add(jPanel4Layout.createSequentialGroup()
                       .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                           .add(jLabel9)
                           .add(jLabel8)
                           .add(jLabel12))
                       .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                       .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                           .add(runs_prio, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                           .add(mu_prio, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                           .add(classes_prio, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 171, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
               .addContainerGap(62, Short.MAX_VALUE))
       );
       jPanel4Layout.setVerticalGroup(
           jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
           .add(jPanel4Layout.createSequentialGroup()
               .add(97, 97, 97)
               .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                   .add(jLabel8)
                   .add(classes_prio, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
               .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
               .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                   .add(jLabel9)
                   .add(mu_prio, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
               .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
               .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                   .add(jLabel12)
                   .add(runs_prio, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
               .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 98, Short.MAX_VALUE)
               .add(simulate_prio)
               .addContainerGap())
       );

       jTabbedPane1.addTab("M/G/1//PRIO", jPanel4);

       jLabel11.setText("Simulation progess");

       consoleTBtn.setText("C");

       org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
       getContentPane().setLayout(layout);
       layout.setHorizontalGroup(
           layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
           .add(layout.createSequentialGroup()
               .addContainerGap()
               .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
               .addContainerGap())
           .add(layout.createSequentialGroup()
               .add(29, 29, 29)
               .add(jLabel11)
               .add(18, 18, 18)
               .add(simPbr, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 49, Short.MAX_VALUE)
               .add(consoleTBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 36, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .add(28, 28, 28))
       );
       layout.setVerticalGroup(
           layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
           .add(layout.createSequentialGroup()
               .addContainerGap()
               .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 388, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
               .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
               .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                   .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                       .add(jLabel11)
                       .add(simPbr, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                   .add(consoleTBtn))
               .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
       );

       pack();
   }
   
   private javax.swing.ButtonGroup buttonGroup1;
   private javax.swing.ButtonGroup buttonGroup2;
   private javax.swing.JComboBox classes_prio;
   private javax.swing.JRadioButton confRbtn;
   private javax.swing.JLabel confidenceLbl;
   private javax.swing.JComboBox distCbx;
   private javax.swing.JComboBox distCbx_mg1;
   private javax.swing.JRadioButton distRbt;
   private javax.swing.JButton generateReport;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel10;
   private javax.swing.JLabel jLabel11;
   private javax.swing.JLabel jLabel12;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JLabel jLabel3;
   private javax.swing.JLabel jLabel4;
   private javax.swing.JLabel jLabel5;
   private javax.swing.JLabel jLabel6;
   private javax.swing.JLabel jLabel7;
   private javax.swing.JLabel jLabel8;
   private javax.swing.JLabel jLabel9;
   private javax.swing.JPanel jPanel1;
   private javax.swing.JPanel jPanel2;
   private javax.swing.JPanel jPanel3;
   private javax.swing.JPanel jPanel4;
   private javax.swing.JTabbedPane jTabbedPane1;
   private javax.swing.JTextField lambda;
   private javax.swing.JTextField mu;
   private javax.swing.JTextField mu_prio;
   private javax.swing.JPanel placeholderPanel;
   private javax.swing.JTextField rho;
   private javax.swing.JRadioButton rhoRbt;
   private javax.swing.JComboBox rndCbx;
   private javax.swing.JTextField runs;
   private javax.swing.JRadioButton runsRbtn;
   private javax.swing.JRadioButton estimateRbtn;
   private javax.swing.JTextField confidenceTxt;
   private javax.swing.JTextField runs_mg1;
   private javax.swing.JTextField runs_prio;
   private javax.swing.JProgressBar simPbr;
   private javax.swing.JButton simulate_mg1;
   private javax.swing.JButton simulate_prio;
   private javax.swing.JButton testConfidence;
   private javax.swing.JToggleButton consoleTBtn;
    // End of variables declaration//GEN-END:variables

    private javax.swing.JPanel additionalParametersPanel;
    private ConsoleFrame console;
    
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == distCbx) {
			handleAdditionalParametersPanel();
		}
		else if (e.getSource() == this.testConfidence) {
			handleTestConfidence();
		}
		else if (e.getSource() == this.generateReport) {
			handleTrafficGeneration();
		}
		else if (e.getSource() == this.simulate_mg1) {
			if (this.rhoRbt.isSelected())
				handleMG1SimulationWithVariableRho();
			else if (this.distRbt.isSelected())
				handleMG1SimulationWithVariableDistribution();
			else if (this.estimateRbtn.isSelected())
				handleMG1SimulationEstimatingProbability();
		}
		else if (e.getSource() == this.distRbt || e.getSource() == this.rhoRbt || e.getSource() == estimateRbtn) {
			handleMG1RadioButtonSelection();
		}
		else if (e.getSource() == this.runsRbtn || e.getSource() == this.confRbtn) {
			handleConfidenceRadioButtonSelection();
		}
		else if (e.getSource() == this.simulate_prio) {
			handlePrioMG1Simulation();
		}
		else if (e.getSource() == this.consoleTBtn) {
			if (consoleTBtn.isSelected()) {
				console.setVisible(true);
			}
			else {
				console.setVisible(false);
			}
		}
			
	}

	private void handleAdditionalParametersPanel() {
		DistributionType type = (DistributionType)distCbx.getSelectedItem();
		switch (type) {
			case SPP: {
				placeholderPanel.removeAll();
				placeholderPanel.add(additionalParametersPanel = new SPPPanel());
				placeholderPanel.repaint();
				break;
			}
			case Pareto: {
				placeholderPanel.removeAll();
				placeholderPanel.add(additionalParametersPanel = new ParetoPanel());
				placeholderPanel.repaint();
				break;
			}
			default: 
				placeholderPanel.removeAll();
		}
		this.repaint();
	}
	
	private void handleTestConfidence() {
		final Provider provider = (Provider)this.rndCbx.getSelectedItem();
		
		if (!checkConfidenceIntervalParameters()) {
			showDialogMessage("Parameters missing or wrong");
			return ;
		}
		
		if (confRbtn.isSelected()) {
			executor.submit(new Runnable() {
				public void run() {
					final double[][] res1 = SimulationRunners.testConfidenceIntervalWithVariableConfidence(provider,getConfidenceIntervalSimulationRuns());
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							LinkedList<double[][]> tmp = new LinkedList<double[][]>();
							tmp.add(res1);
							GraphUtils.displayLineChart("Confidence interval with variable confidence level","Confidence interval [random provider " + provider.toString() + " ]", "confidence level", "confidence interval size",new String[]{"Confidence interval"},tmp);
						}
					});
				}
			});
		}
		else if (runsRbtn.isSelected()) {
			executor.submit(new Runnable() {
				public void run() {
					final double[][] res2 = SimulationRunners.testConfidenceIntervalWithVariableRuns(provider,getConfidenceIntervalConfidenceLevel());
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							LinkedList<double[][]> tmp = new LinkedList<double[][]>();
							tmp.add(res2);
							GraphUtils.displayLineChart("Confidence interval with variable runs","Confidence interval [random provider " + provider.toString() + " ]", "# of values", "confidence interval size", new String[]{"Confidence interval"},tmp);
						}
					});
				}
			});
		}
	}
	
	private void handleTrafficGeneration() {
		DistributionType type = (DistributionType)distCbx.getSelectedItem();
		Distribution dist = null;
		if (!checkTrafficParameters()) {
			showDialogMessage("Parameters missing or wrong");
			return ; // pianto unA grana
		}
		switch (type) {
			case Deterministic: {
				dist = new DeterministicDistribution((double)getLambda());
				break;
			}
			case SPP: {
				SPPPanel p = (SPPPanel)additionalParametersPanel;
				if (!p.checkParameters()) {
					showDialogMessage("Additional parameters missing or wrong");
					return ; // pianto una grana
				}
					
				double lambda = getLambda();
				double p0 = p.getP0();
				double p1 = p.getP1();
				int k = p.getK();
				double lambda0 = Utils.computeSPPLambda0(lambda,p0,p1,k);
				double lambda1 = lambda0 * k;
				dist = new SPPDistribution((double)lambda0,(double)lambda1,(double)p0,(double)p1);
				break;
			}
			case Pareto: {
				ParetoPanel p = (ParetoPanel)additionalParametersPanel;
				if (!p.checkParameters()) {
					showDialogMessage("Additional parameters missing or wrong");
					return ; // pianto una grana
				}
				
				double lambda = (double)getLambda();
				double alfa = (double)p.getAlfa();
				double beta = ((1/lambda) * (alfa - 1))/alfa;
				dist = new ParetoDistribution(alfa,beta);
				break;
			}
			case Uniform: {
				dist = new UniformDistribution();
				break ;
			}
			case Exponential: {
				dist = new ExponentialDistribution((double)getLambda());
				break ;
			}
		}
		
		final Distribution d = dist;
		executor.submit(new Runnable() {
			public void run() {
				SimulationRunners.generateTraffic((double)getLambda(),d,getTrafficSimulationRuns());
			}
		});
	}
	
	private void handleMG1SimulationWithVariableDistribution() {
		if (!checkMG1SimulationParameters()) {
			showDialogMessage("Parameters missing or wrong");
			return ; // pianto unA grana
		}
		
		executor.submit(new Runnable() {
			public void run() {
				double rho = getRho();
				double mu = getMu();
				int N = getMG1SimulationRuns();
				
				Distribution[] dists = new Distribution[]{new DeterministicDistribution(mu),
						new ExponentialDistribution(mu),
						new ParetoDistribution(2.5,Utils.computeParetoBeta(mu,2.5)),
						new ParetoDistribution(1.2,Utils.computeParetoBeta(mu,1.2))
				};
				
				final double[][] res = SimulationRunners.compareMG1Simulations(dists,rho,mu,N);
				final String[] keys = new String[dists.length];
				for(int i=0;i<keys.length;i++){
					keys[i]=dists[i].toString();
				}
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						GraphUtils.displayStatisticalLineChart("MG1Sim","Variable distribution","Distribution","Time","Eta",keys,res[0],res[2]);
					}
				});
			}
		});
	}
	
	private void handleMG1SimulationWithVariableRho() {
		String s = this.distCbx_mg1.getSelectedItem() + "";
		Distribution dist = null;
		if (s.equals("Deterministic")) {
			dist = new DeterministicDistribution(this.getMu());
		}
		else if (s.equals("Exponential")) {
			dist = new ExponentialDistribution(this.getMu());
		}
		else if (s.equals("Pareto 2.5")) {
			dist = new ParetoDistribution(2.5,Utils.computeParetoBeta(this.getMu(),2.5));
		}
		else if (s.equals("Pareto 1.2")) {
			dist = new ParetoDistribution(1.2,Utils.computeParetoBeta(this.getMu(),1.2));
		}
		
		final Distribution d = dist;
		executor.submit(new Runnable() {
			public void run() {
				final double[] rhos = new double[]{0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9};
				final double[][] res = SimulationRunners.simulateMG1WithVariableRho(d,rhos,getMu(),getMG1SimulationRuns());
				final String[] keys = new String[]{"0.1","0.2","0.3","0.4","0.5","0.6","0.7","0.8","0.9"};
				
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						GraphUtils.displayStatisticalLineChart("MG1Sim","Variable rho","Rho","Time","Eta",keys,res[0],res[2]);
					}
				});
			}
		});
		
	}
	
	private void handleMG1SimulationEstimatingProbability() {
		String s = this.distCbx_mg1.getSelectedItem() + "";
		Distribution dist = null;
		if (s.equals("Deterministic")) {
			dist = new DeterministicDistribution(this.getMu());
		}
		else if (s.equals("Exponential")) {
			dist = new ExponentialDistribution(this.getMu());
		}
		else if (s.equals("Pareto 2.5")) {
			dist = new ParetoDistribution(2.5,Utils.computeParetoBeta(this.getMu(),2.5));
		}
		else if (s.equals("Pareto 1.2")) {
			dist = new ParetoDistribution(1.2,Utils.computeParetoBeta(this.getMu(),1.2));
		}
		
		final Distribution d = dist;
		executor.submit(new Runnable() {
			public void run() {
				final double[][] res = SimulationRunners.simulateMG1EvaluatingProbability(d,getRho(),getMu(),getMG1SimulationRuns());
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						double[][] values = new double[2][res[0].length];
						for (int i = 0; i < values[0].length; i ++) {
							values[0][i] = i;
							values[1][i] = res[0][i];
						}
						LinkedList<double[][]> tmp = new LinkedList<double[][]>();
						tmp.add(values);
						GraphUtils.displayLineChart("State probability","Probability of each state","K","Probability",new String[]{"State probability"},tmp);
					}
				});
			}
		});
	}
	
	private void handlePrioMG1Simulation() {
		if (!checkMG1PrioSimulationParameters()) {
			showDialogMessage("Parameters missing or wrong");
			return ; // pianto unA grana
		}
		
		final double mu = getMuPrio();
		final int N = getNRunsPrio();
		executor.submit(new Runnable() {
			public void run() {
				final double[][][] res = SimulationRunners.simulateMG1PrioWithVariableRhos(mu,N,classes_prio.getSelectedItem().toString());
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						GraphUtils.displayDevRendererGraph("Eta ("+classes_prio.getSelectedItem().toString()+")","eta mean","x","eta",res);
					}
				});
				
			}
		});
	}
	
	private void handleMG1RadioButtonSelection() {
		if (distRbt.isSelected()) {
			toggleMG1Parameters(false,true);
		}
		else if (rhoRbt.isSelected()) {
			toggleMG1Parameters(true,false);
		}
		else if (estimateRbtn.isSelected()) {
			toggleMG1Parameters(true,true);
		}
	}
	
	private void handleConfidenceRadioButtonSelection() {
		if (runsRbtn.isSelected()) {
			this.confidenceLbl.setText("Confidence level:");
		}
		else if (confRbtn.isSelected()) {
			this.confidenceLbl.setText("#runs");
		}
		confidenceTxt.setText("");
	}
	
	private boolean checkConfidenceIntervalParameters() {
		if (runsRbtn.isSelected()) {
			double conf = 0;
			if ((conf = getConfidenceIntervalConfidenceLevel()) == -1)
				return false;
			if (conf <= 0.5 || conf >= 1)
				return false;
		}
		else if (confRbtn.isSelected()) {
			int N = 0;
			if ((N = getConfidenceIntervalSimulationRuns()) == -1)
				return false;
			if (N <= 0)
				return false;
		}
		return true;
	}
	
	private boolean checkTrafficParameters() {
		if (lambda.getText().equals("") || runs.getText().equals(""))
			return false;
		try {
			Double.parseDouble(lambda.getText());
			Integer.parseInt(runs.getText());
			return true;
		}
		catch (NumberFormatException e) { }
		return false;
	}
	
	private boolean checkMG1SimulationParameters() {
		if (!rhoRbt.isSelected())
			if (rho.getText().equals("") || mu.getText().equals("") || runs_mg1.getText().equals(""))
				return false;
		
		try {
			double d = Double.parseDouble(rho.getText());
			if (d <= 0 || d >= 1)
				return false;
			d = Double.parseDouble(this.mu.getText());
			if (d <= 0)
				return false;
			d = Integer.parseInt(runs_mg1.getText());
			if (d <= 0)
				return false;
			return true;
		}
		catch (NumberFormatException e) { }
		
		return false;
	}
	
	private boolean checkMG1PrioSimulationParameters() {
		if (runs_prio.getText().equals("") || mu_prio.getText().equals(""))
			return false;
		if (this.getMuPrio() < 0 || this.getNRunsPrio() <= 1)
			return false;
		return true;
	}
	
	public double getLambda() {
		if (lambda.getText().equals(""))
			return -1;
		try {
			return Double.parseDouble(lambda.getText());
		}
		catch (NumberFormatException e) { }
		return -1;
	}
	
	public int getTrafficSimulationRuns() {
		if (runs.getText().equals(""))
			return -1;
		try {
			return Integer.parseInt(runs.getText());
		}
		catch (NumberFormatException e) { }
		return -1;
	}
	
	public double getRho() {
		if (rho.getText().equals(""))
			return -1;
		try {
			return Double.parseDouble(rho.getText());
		}
		catch (NumberFormatException e) { }
		return -1;
	}
	
	public double getMuPrio() {
		if (mu_prio.getText().equals(""))
			return -1;
		try {
			return Double.parseDouble(mu_prio.getText());
		}
		catch (NumberFormatException e) { }
		return -1;
	}
	
	public int getNRunsPrio() {
		if (runs_prio.getText().equals(""))
			return -1;
		try {
			return Integer.parseInt(runs_prio.getText());
		}
		catch (NumberFormatException e) { }
		return -1;
	}
	
	public double getMu() {
		if (mu.getText().equals(""))
			return -1;
		try {
			return Double.parseDouble(mu.getText());
		}
		catch (NumberFormatException e) { }
		return -1;
	}
	
	public int getMG1SimulationRuns() {
		if (runs_mg1.getText().equals(""))
			return -1;
		try {
			return Integer.parseInt(runs_mg1.getText());
		}
		catch (NumberFormatException e) { }
		return -1;
	}
	
	public int getConfidenceIntervalSimulationRuns() {
		if (confidenceTxt.getText().equals(""))
			return -1;
		try {
			return Integer.parseInt(confidenceTxt.getText());
		}
		catch (NumberFormatException e) { }
		return -1;
	}
	
	public double getConfidenceIntervalConfidenceLevel() {
		if (confidenceTxt.getText().equals(""))
			return -1;
		try {
			return Double.parseDouble(confidenceTxt.getText());
		}
		catch (NumberFormatException e) { }
		return -1;
	}
	
	private void showDialogMessage(String msg) {
		javax.swing.JOptionPane.showMessageDialog(this,msg,"Paramters error",javax.swing.JOptionPane.ERROR_MESSAGE);
	}
	
	private void toggleMG1Parameters(boolean cmb, boolean rho) {
		this.distCbx_mg1.setEnabled(cmb);
		this.rho.setEnabled(rho);
	}
}
