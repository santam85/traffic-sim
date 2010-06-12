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
import simulator.distribution.DeterministicDistribution;
import simulator.distribution.Distribution;
import simulator.distribution.ExponentialDistribution;
import simulator.distribution.ParetoDistribution;
import simulator.misc.Utils;

public class MG1Panel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -2685600028397169298L;

	private javax.swing.JTextField arrivals;
	private javax.swing.ButtonGroup buttonGroup1;
	private javax.swing.JCheckBox displayKCbx;
	private javax.swing.JComboBox distCbx;
	private javax.swing.JRadioButton distRbt;
	private javax.swing.JRadioButton estimateRbtn;
	private javax.swing.JLabel jLabel13;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JTextField mu;
	private javax.swing.JTextField rho;
	private javax.swing.JRadioButton rhoRbt;
	private javax.swing.JTextField runs;
	private javax.swing.JButton simulate;

	private ExecutorService executor;

	public MG1Panel(ExecutorService e) {
		init();

		toggleMG1Parameters(false,true);
		simulate.addActionListener(this);
		distRbt.addActionListener(this);
		rhoRbt.addActionListener(this);
		estimateRbtn.addActionListener(this);
		displayKCbx.addActionListener(this);
		distRbt.setSelected(true);

		executor = e;
	}

	private void init() {
		simulate = new javax.swing.JButton();
		distCbx = new javax.swing.JComboBox();
		jLabel4 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		rho = new javax.swing.JTextField();
		jLabel6 = new javax.swing.JLabel();
		mu = new javax.swing.JTextField();
		runs = new javax.swing.JTextField();
		jLabel7 = new javax.swing.JLabel();
		distRbt = new javax.swing.JRadioButton();
		rhoRbt = new javax.swing.JRadioButton();
		estimateRbtn = new javax.swing.JRadioButton();
		displayKCbx = new javax.swing.JCheckBox();
		jLabel13 = new javax.swing.JLabel();
		arrivals = new javax.swing.JTextField();
		buttonGroup1 = new javax.swing.ButtonGroup();

		simulate.setText("Simulate");
		distCbx.setModel(new DefaultComboBoxModel(new String[]{"Deterministic","Exponential","Pareto 2.5","Pareto 1.2"}));
		jLabel4.setText("Distribution type");
		jLabel5.setText("Rho:");
		rho.setText("0.8");
		jLabel6.setText("Mu:");
		mu.setText("2");
		runs.setText("100");
		jLabel7.setText("# runs:");
		buttonGroup1.add(distRbt);
		distRbt.setText("Variable distribution");
		buttonGroup1.add(rhoRbt);
		rhoRbt.setText("Variable rho");
		buttonGroup1.add(estimateRbtn);
		estimateRbtn.setText("Estimate state probability");
		displayKCbx.setText("Display k(t) graph");
		jLabel13.setText("# arrivals:");
		arrivals.setText("1000");

		org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(this);
		this.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(
				jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(jPanel3Layout.createSequentialGroup()
						.addContainerGap()
						.add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(displayKCbx)
								.add(estimateRbtn)
								.add(simulate)
								.add(jPanel3Layout.createSequentialGroup()
										.add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
												.add(jLabel5)
												.add(jLabel4)
												.add(jLabel6)
												.add(jLabel7))
												.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
												.add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
														.add(mu, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
														.add(rho, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
														.add(jPanel3Layout.createSequentialGroup()
																.add(runs, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 43, Short.MAX_VALUE)
																.add(jLabel13)
																.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
																.add(arrivals, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
																.add(distCbx, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 171, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
																.add(distRbt)
																.add(rhoRbt))
																.addContainerGap())
		);
		jPanel3Layout.setVerticalGroup(
				jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel3Layout.createSequentialGroup()
						.addContainerGap()
						.add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
								.add(jLabel4)
								.add(distCbx, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
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
														.add(runs, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
														.add(arrivals, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
														.add(jLabel13))
														.addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
														.add(distRbt)
														.addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
														.add(rhoRbt)
														.addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
														.add(estimateRbtn)
														.addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
														.add(displayKCbx)
														.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 14, Short.MAX_VALUE)
														.add(simulate)
														.addContainerGap())
		);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.simulate) {
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
	}

	private void handleMG1SimulationWithVariableDistribution() {
		if (!checkMG1SimulationParameters()) {
			showDialogMessage("Parameters missing or wrong");
			return ; // pianto unA grana
		}

		final double rho = Double.parseDouble(this.rho.getText());
		final double mu = Double.parseDouble(this.mu.getText());
		final int N = Integer.parseInt(this.runs.getText());
		final int arrivals = Integer.parseInt(this.arrivals.getText());
		executor.submit(new Runnable() {
			public void run() {

				Distribution[] dists = new Distribution[]{new DeterministicDistribution(mu),
						new ExponentialDistribution(mu),
						new ParetoDistribution(2.5,Utils.computeParetoBeta(mu,2.5)),
						new ParetoDistribution(1.2,Utils.computeParetoBeta(mu,1.2))
				};

				final double[][] res = SimulationRunners.compareMG1Simulations(dists,rho,mu,N,arrivals);
				final String[] keys = new String[dists.length];
				final LinkedList<double[]> y = new LinkedList<double[]>();
				final LinkedList<double[]> c = new LinkedList<double[]>();
				y.add(res[0]);
				c.add(res[2]);
				for(int i=0;i<keys.length;i++){
					keys[i]=dists[i].toString();
				}

				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						GraphUtils.displayStatisticalLineChart("MG1Sim","Variable distribution","Distribution","Time",new String[]{"Simulation Eta"},keys,y,c);
					}
				});
			}
		});
	}

	private void handleMG1SimulationWithVariableRho() {
		if (!checkMG1SimulationParameters()) {
			showDialogMessage("Parameters missing or wrong");
			return ; // pianto unA grana
		}

		String s = this.distCbx.getSelectedItem() + "";
		Distribution dist = null;

		final int runs = Integer.parseInt(this.runs.getText());
		final int arrivals = Integer.parseInt(this.arrivals.getText());
		final double mu = Double.parseDouble(this.mu.getText()); 

		if (s.equals("Deterministic")) {
			dist = new DeterministicDistribution(mu);
		}
		else if (s.equals("Exponential")) {
			dist = new ExponentialDistribution(mu);
		}
		else if (s.equals("Pareto 2.5")) {
			dist = new ParetoDistribution(2.5,Utils.computeParetoBeta(mu,2.5));
		}
		else if (s.equals("Pareto 1.2")) {
			dist = new ParetoDistribution(1.2,Utils.computeParetoBeta(mu,1.2));
		}

		final Distribution d = dist;
		executor.submit(new Runnable() {
			public void run() {
				final double[] rhos = new double[]{0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9};
				final double[][] res = SimulationRunners.simulateMG1WithVariableRho(d,rhos,mu,runs,arrivals);
				final String[] keys = new String[]{"0.1","0.2","0.3","0.4","0.5","0.6","0.7","0.8","0.9"};
				final LinkedList<double[]> y = new LinkedList<double[]>();
				final LinkedList<double[]> c = new LinkedList<double[]>();
				y.add(res[0]);
				c.add(res[2]);

				double[] t1= new double[rhos.length];
				double[] t2= new double[rhos.length];
				//Generazione delle funz. teoriche
				for(int i=0; i<rhos.length;i++){
					if(d.getClass()==DeterministicDistribution.class)
						t1[i]=(1.0/mu)*(rhos[i]/(2*(1-rhos[i])));
					else
						t1[i]=(1.0/mu)*(rhos[i]/((1-rhos[i])));
					t2[i]=0;
				}
				y.add(t1);
				c.add(t2);

				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						GraphUtils.displayStatisticalLineChart("MG1Sim","Variable rho ["+d.toString()+"]","Rho","Time",new String[]{"Simulation Eta","Theoretical Eta"},keys,y,c);
					}
				});
			}
		});

	}

	private void handleMG1SimulationEstimatingProbability() {
		if (!checkMG1SimulationParameters()) {
			showDialogMessage("Parameters missing or wrong");
			return ; // pianto unA grana
		}

		String s = this.distCbx.getSelectedItem() + "";
		Distribution dist = null;

		final int runs = Integer.parseInt(this.runs.getText());
		final int arrivals = Integer.parseInt(this.arrivals.getText());
		final double mu = Double.parseDouble(this.mu.getText());
		final double rho = Double.parseDouble(this.rho.getText());

		if (s.equals("Deterministic")) {
			dist = new DeterministicDistribution(mu);
		}
		else if (s.equals("Exponential")) {
			dist = new ExponentialDistribution(mu);
		}
		else if (s.equals("Pareto 2.5")) {
			dist = new ParetoDistribution(2.5,Utils.computeParetoBeta(mu,2.5));
		}
		else if (s.equals("Pareto 1.2")) {
			dist = new ParetoDistribution(1.2,Utils.computeParetoBeta(mu,1.2));
		}

		final Distribution d = dist;
		executor.submit(new Runnable() {
			public void run() {
				final double[][] res = SimulationRunners.simulateMG1EvaluatingProbability(d,rho,mu,runs,arrivals);
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

	private boolean checkMG1SimulationParameters() {
		if (!rhoRbt.isSelected())
			if (rho.getText().equals("") || mu.getText().equals("") || runs.getText().equals("") ||
					this.arrivals.getText().equals(""))
				return false;

		try {
			double d = Double.parseDouble(rho.getText());
			if (d <= 0 || d >= 1)
				return false;
			d = Double.parseDouble(this.mu.getText());
			if (d <= 0)
				return false;
			d = Integer.parseInt(runs.getText());
			if (d <= 0)
				return false;
			d = Integer.parseInt(arrivals.getText());
			if (d <= 0)
				return false;
			return true;
		}
		catch (NumberFormatException e) { }

		return false;
	}

	private void showDialogMessage(String msg) {
		javax.swing.JOptionPane.showMessageDialog(this,msg,"Paramters error",javax.swing.JOptionPane.ERROR_MESSAGE);
	}

	private void toggleMG1Parameters(boolean cmb, boolean rho) {
		this.distCbx.setEnabled(cmb);
		this.rho.setEnabled(rho);
	}
}
