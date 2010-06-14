package gui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;

import simulator.SimulationRunners;
import simulator.distribution.DeterministicDistribution;
import simulator.distribution.Distribution;
import simulator.distribution.DistributionType;
import simulator.distribution.ExponentialDistribution;
import simulator.distribution.ParetoDistribution;
import simulator.distribution.SPPDistribution;
import simulator.distribution.UniformDistribution;
import simulator.misc.Utils;

public class VariableTrafficPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1092730854494855497L;

	private javax.swing.JComboBox distCbx;
	private javax.swing.JButton generateReport;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JTextField lambda;
	private javax.swing.JPanel placeholderPanel;
	private javax.swing.JTextField runs;
	private javax.swing.JPanel additionalParametersPanel;

	private ExecutorService executor;

	public VariableTrafficPanel(ExecutorService e) {
		init();

		distCbx.addActionListener(this);
		generateReport.addActionListener(this);
		DistributionType[] dts = new DistributionType[DistributionType.values().length - 1];
		dts[0] = DistributionType.Deterministic;
		dts[1] = DistributionType.Exponential;
		dts[2] = DistributionType.SPP;
		dts[3] = DistributionType.Pareto;
		distCbx.setModel(new DefaultComboBoxModel(dts));

		executor = e;
	}

	private void init() {
		jLabel1 = new javax.swing.JLabel();
		placeholderPanel = new javax.swing.JPanel();
		distCbx = new javax.swing.JComboBox();
		jLabel2 = new javax.swing.JLabel();
		lambda = new javax.swing.JTextField();
		jLabel3 = new javax.swing.JLabel();
		runs = new javax.swing.JTextField();
		generateReport = new javax.swing.JButton();
		additionalParametersPanel = new javax.swing.JPanel();

		jLabel1.setText("Distribution type");

		org.jdesktop.layout.GroupLayout placeholderPanelLayout = new org.jdesktop.layout.GroupLayout(
				placeholderPanel);
		placeholderPanel.setLayout(placeholderPanelLayout);
		placeholderPanelLayout.setHorizontalGroup(placeholderPanelLayout
				.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(0, 288, Short.MAX_VALUE));
		placeholderPanelLayout.setVerticalGroup(placeholderPanelLayout
				.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(0, 97, Short.MAX_VALUE));

		jLabel2.setText("Lambda:");
		lambda.setText("5");
		jLabel3.setText("# runs:");
		runs.setText("100");
		generateReport.setText("Generate report");

		org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(
				this);
		this.setLayout(jPanel2Layout);
		jPanel2Layout
				.setHorizontalGroup(jPanel2Layout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(
								jPanel2Layout
										.createSequentialGroup()
										.addContainerGap()
										.add(
												jPanel2Layout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.LEADING)
														.add(generateReport)
														.add(
																jPanel2Layout
																		.createParallelGroup(
																				org.jdesktop.layout.GroupLayout.TRAILING,
																				false)
																		.add(
																				org.jdesktop.layout.GroupLayout.LEADING,
																				placeholderPanel,
																				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.add(
																				org.jdesktop.layout.GroupLayout.LEADING,
																				jPanel2Layout
																						.createSequentialGroup()
																						.add(
																								jPanel2Layout
																										.createParallelGroup(
																												org.jdesktop.layout.GroupLayout.LEADING)
																										.add(
																												jLabel2)
																										.add(
																												jLabel1)
																										.add(
																												jLabel3))
																						.addPreferredGap(
																								org.jdesktop.layout.LayoutStyle.RELATED)
																						.add(
																								jPanel2Layout
																										.createParallelGroup(
																												org.jdesktop.layout.GroupLayout.LEADING)
																										.add(
																												runs,
																												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																												54,
																												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
																										.add(
																												lambda,
																												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																												54,
																												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
																										.add(
																												distCbx,
																												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																												171,
																												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))
										.addContainerGap(72, Short.MAX_VALUE)));
		jPanel2Layout
				.setVerticalGroup(jPanel2Layout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(
								jPanel2Layout
										.createSequentialGroup()
										.addContainerGap()
										.add(
												jPanel2Layout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.BASELINE)
														.add(jLabel1)
														.add(
																distCbx,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.UNRELATED)
										.add(
												jPanel2Layout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.BASELINE)
														.add(jLabel2)
														.add(
																lambda,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(
												jPanel2Layout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.BASELINE)
														.add(jLabel3)
														.add(
																runs,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.UNRELATED)
										.add(
												placeholderPanel,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(generateReport).addContainerGap(
												78, Short.MAX_VALUE)));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == distCbx) {
			handleAdditionalParametersPanel();
		} else if (e.getSource() == this.generateReport) {
			handleTrafficGeneration();
		}
	}

	private void handleAdditionalParametersPanel() {
		DistributionType type = (DistributionType) distCbx.getSelectedItem();
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

	private void handleTrafficGeneration() {
		DistributionType type = (DistributionType) distCbx.getSelectedItem();
		Distribution dist = null;
		if (!checkTrafficParameters()) {
			showDialogMessage("Parameters missing or wrong");
			return; // pianto unA grana
		}

		final double lambda = Double.parseDouble(this.lambda.getText());
		switch (type) {
		case Deterministic: {
			dist = new DeterministicDistribution(Double.parseDouble(this.lambda
					.getText()));
			break;
		}
		case SPP: {
			SPPPanel p = (SPPPanel) additionalParametersPanel;
			if (!p.checkParameters()) {
				showDialogMessage("Additional parameters missing or wrong");
				return; // pianto una grana
			}

			double p0 = p.getP0();
			double p1 = p.getP1();
			int k = p.getK();
			double lambda0 = Utils.computeSPPLambda0(lambda, p0, p1, k);
			double lambda1 = lambda0 * k;
			dist = new SPPDistribution((double) lambda0, (double) lambda1,
					(double) p0, (double) p1);
			break;
		}
		case Pareto: {
			ParetoPanel p = (ParetoPanel) additionalParametersPanel;
			if (!p.checkParameters()) {
				showDialogMessage("Additional parameters missing or wrong");
				return; // pianto una grana
			}

			double alfa = p.getAlfa();
			double beta = Utils.computeParetoBeta(lambda, alfa);
			dist = new ParetoDistribution(alfa, beta);
			break;
		}
		case Uniform: {
			dist = new UniformDistribution();
			break;
		}
		case Exponential: {
			dist = new ExponentialDistribution(lambda);
			break;
		}
		}

		final Distribution d = dist;
		final int runs = Integer.parseInt(this.runs.getText());
		executor.submit(new Runnable() {
			public void run() {
				SimulationRunners.generateTraffic(lambda, d, runs);
			}
		});
	}

	private boolean checkTrafficParameters() {
		if (lambda.getText().equals("") || runs.getText().equals(""))
			return false;
		try {
			Double.parseDouble(lambda.getText());
			Integer.parseInt(runs.getText());
			return true;
		} catch (NumberFormatException e) {
		}
		return false;
	}

	private void showDialogMessage(String msg) {
		javax.swing.JOptionPane.showMessageDialog(this, msg, "Paramters error",
				javax.swing.JOptionPane.ERROR_MESSAGE);
	}
}
