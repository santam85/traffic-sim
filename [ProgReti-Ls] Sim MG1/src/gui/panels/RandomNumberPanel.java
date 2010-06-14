package gui.panels;

import gui.GraphUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import simulator.SimulationRunners;
import simulator.distribution.Distribution;
import simulator.distribution.UniformDistribution;
import simulator.random.Provider;
import simulator.random.RandomProvider;

public class RandomNumberPanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private javax.swing.ButtonGroup buttonGroup2;
	private javax.swing.JRadioButton confRbtn;
	private javax.swing.JLabel confidenceLbl;
	private javax.swing.JTextField confidenceTxt;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JComboBox rndCbx;
	private javax.swing.JRadioButton runsRbtn;
	private javax.swing.JButton scatterPlotBtn;
	private javax.swing.JButton testConfidence;
	
	private ExecutorService executor;
	
	public RandomNumberPanel(ExecutorService e) {
		init();
		
		testConfidence.addActionListener(this);
		scatterPlotBtn.addActionListener(this);
		runsRbtn.addActionListener(this);
		confRbtn.addActionListener(this);
		
		executor = e;
	}
	
	private void init() {
	       testConfidence = new javax.swing.JButton();
	       rndCbx = new javax.swing.JComboBox();
	       runsRbtn = new javax.swing.JRadioButton();
	       confRbtn = new javax.swing.JRadioButton();
	       confidenceTxt = new javax.swing.JTextField();
	       confidenceLbl = new javax.swing.JLabel();
	       scatterPlotBtn = new javax.swing.JButton();
	       jLabel10 = new javax.swing.JLabel();
	       buttonGroup2 = new javax.swing.ButtonGroup();
		
	       testConfidence.setText("Test confidence interval");
	       jLabel10.setText("Random provider");
	       rndCbx.setModel(new DefaultComboBoxModel(Provider.values()));
	       buttonGroup2.add(runsRbtn);
	       runsRbtn.setText("Variable runs");
	       buttonGroup2.add(confRbtn);
	       confRbtn.setText("Variable confidence level");
	       confidenceLbl.setText("Confidence level:");
	       scatterPlotBtn.setText("Distribution Charts");
	       confidenceTxt.setText("0.95");
	       runsRbtn.setSelected(true);
	       
	       org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(this);
	       this.setLayout(jPanel1Layout);
	       jPanel1Layout.setHorizontalGroup(
	           jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
	           .add(jPanel1Layout.createSequentialGroup()
	               .addContainerGap()
	               .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
	                   .add(jPanel1Layout.createSequentialGroup()
	                       .add(testConfidence)
	                       .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 17, Short.MAX_VALUE)
	                       .add(scatterPlotBtn))
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
	               .addContainerGap())
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
	               .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
	                   .add(testConfidence)
	                   .add(scatterPlotBtn))
	               .addContainerGap())
	       );
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		 if (e.getSource() == this.testConfidence) {
				handleTestConfidence();
		 }
		 else if (e.getSource() == this.scatterPlotBtn) {
			 handleChartsPlot();
		 }		
		 else if (e.getSource() == this.runsRbtn || e.getSource() == this.confRbtn) {
			 handleConfidenceRadioButtonSelection();
		 }
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
	
	private void handleChartsPlot() {
		executor.submit(new Runnable() {
			public void run() {
				RandomProvider rnd = new RandomProvider(Provider.Java);
				final Distribution d = new UniformDistribution(rnd);

				int N = 1000;
				int K = 1;

				double[][] v = new double[2][N];

				for(int i = 0 ; i< N; i++){
					v[0][i] = d.nextValue();
					for(int j = 1 ; j< K; j++){
						d.nextValue();
					}
					v[1][i] = d.nextValue();
				}

				final LinkedList<double[][]> sct = new LinkedList<double[][]>();
				sct.add(v);

				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						GraphUtils.displayScatterPlot("Ramdom scatter",""+d.getProviderName(), "Random N", "Random N+1", new String[]{""+d.getProviderName()}, sct);
					}
				});
				
				//Media e varianza
				int [] nexp={5,10,20,50,100,500,1000,5000,10000,100000,1000000,1000000};
				double[][] values=new double[2][nexp.length];
				for (int j=0;j<nexp.length;j++){
					double s=0;

					for (int i=0;i<nexp[j];i++){
						s+=d.nextValue();
					}
					values[0][j]=nexp[j];
					values[1][j]=s/nexp[j];
				}

				final LinkedList<double[][]> l = new LinkedList<double[][]>();
				l.add(values);
				
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						GraphUtils.displayCategoryLineChart("Random numbers", d.getProviderName(), "Runs", "Mean", new String[]{"Average"}, l);
					}
				});
			}
		});
	}
	
	private void handleConfidenceRadioButtonSelection() {
		if (runsRbtn.isSelected()) {
			this.confidenceLbl.setText("Confidence level:");
			confidenceTxt.setText("0.95");
		}
		else if (confRbtn.isSelected()) {
			this.confidenceLbl.setText("#runs");
			confidenceTxt.setText("10");
		}
		
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
}
