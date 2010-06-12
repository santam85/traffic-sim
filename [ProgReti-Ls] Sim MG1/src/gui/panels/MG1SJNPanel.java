package gui.panels;

import gui.GraphUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import simulator.SimulationRunners;

public class MG1SJNPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private javax.swing.JTextField arrivals;
	private javax.swing.JLabel jLabel15;
	private javax.swing.JLabel jLabel16;
	private javax.swing.JLabel jLabel17;
	private javax.swing.JLabel jLabel18;
	private javax.swing.JLabel jLabel19;
	private javax.swing.JLabel jLabel20;
	private javax.swing.JTextField mu;
	private javax.swing.JTextField multiplier;
	private javax.swing.JTextField rho;
	private javax.swing.JTextField runs;
	private javax.swing.JButton simulate;
	private javax.swing.JTextField step;

	private ExecutorService executor;

	public MG1SJNPanel(ExecutorService e) {
		init();

		simulate.addActionListener(this);

		executor = e;
	}

	private void init() {
		jLabel15 = new javax.swing.JLabel();
		rho = new javax.swing.JTextField();
		jLabel16 = new javax.swing.JLabel();
		mu = new javax.swing.JTextField();
		jLabel17 = new javax.swing.JLabel();
		runs = new javax.swing.JTextField();
		jLabel18 = new javax.swing.JLabel();
		arrivals = new javax.swing.JTextField();
		simulate = new javax.swing.JButton();
		step = new javax.swing.JTextField();
		jLabel19 = new javax.swing.JLabel();
		jLabel20 = new javax.swing.JLabel();
		multiplier = new javax.swing.JTextField();

		jLabel15.setText("Rho:");
		rho.setText("0.8");
		jLabel16.setText("Mu:");
		mu.setText("2");
		jLabel17.setText("# runs:");
		runs.setText("100");
		jLabel18.setText("# arrivals:");
		arrivals.setText("1000");
		simulate.setText("Simulate");
		step.setText("20");
		jLabel19.setText("[0-teta] steps:");
		jLabel20.setText("teta multiplier:");
		multiplier.setText("5");

		org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(this);
		this.setLayout(jPanel5Layout);
		jPanel5Layout.setHorizontalGroup(
				jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(jPanel5Layout.createSequentialGroup()
						.addContainerGap()
						.add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
										.add(jPanel5Layout.createSequentialGroup()
												.add(simulate)
												.add(262, 262, 262))
												.add(jPanel5Layout.createSequentialGroup()
														.add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
																.add(jLabel15)
																.add(jLabel16)
																.add(jLabel17))
																.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
																.add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
																		.add(mu, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
																		.add(rho, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
																		.add(jPanel5Layout.createSequentialGroup()
																				.add(runs, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
																				.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 106, Short.MAX_VALUE)
																				.add(jLabel18)
																				.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
																				.add(arrivals, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
																				.addContainerGap())
																				.add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel5Layout.createSequentialGroup()
																						.add(jLabel19)
																						.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
																						.add(step, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
																						.addContainerGap()))
																						.add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel5Layout.createSequentialGroup()
																								.add(jLabel20)
																								.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
																								.add(multiplier, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
																								.addContainerGap())))
		);
		jPanel5Layout.setVerticalGroup(
				jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel5Layout.createSequentialGroup()
						.add(80, 80, 80)
						.add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
								.add(jLabel15)
								.add(rho, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
								.add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
										.add(jLabel16)
										.add(mu, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
										.add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
												.add(jLabel17)
												.add(runs, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
												.add(arrivals, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
												.add(jLabel18))
												.addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
												.add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
														.add(step, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
														.add(jLabel19))
														.addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
														.add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
																.add(multiplier, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
																.add(jLabel20))
																.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 26, Short.MAX_VALUE)
																.add(simulate)
																.addContainerGap())
		);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.simulate) {
			handleSJNSimulation();
		}
	}

	private void handleSJNSimulation() {
		if (!checkSJNParameters()) {
			showDialogMessage("Parameters missing or wrong");
			return ; // pianto unA grana
		}

		final double mu = Double.parseDouble(this.mu.getText());
		final double rho = Double.parseDouble(this.rho.getText());
		final int runs = Integer.parseInt(this.runs.getText());
		final int arrivals = Integer.parseInt(this.arrivals.getText());
		final int step = Integer.parseInt(this.step.getText());
		final double multiplier = Double.parseDouble(this.multiplier.getText());

		executor.submit(new Runnable() {
			public void run() { 										
				final LinkedList<double[][]> values = SimulationRunners.simulateMG1SJN(rho,mu,runs,arrivals,step,multiplier);
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						GraphUtils.displayLineChart("M/G/1//SJN", "Mean eta by service time", "Service time", "Wait Time", new String[]{"Mean Eta"},values);
					}
				});

			}
		});
	}



	private boolean checkSJNParameters() {
		if (this.runs.getText().equals("") || this.mu.getText().equals("") || 
				this.rho.getText().equals("") || this.arrivals.getText().equals("") || 
				this.step.getText().equals("") || this.multiplier.getText().equals("")) {
			return false;
		}
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
			d = Integer.parseInt(step.getText());
			if (d <= 0) {
				return false;
			}
			d = Double.parseDouble(multiplier.getText());
			if (d <= 0) {
				return false;
			}
			return true;
		}
		catch (NumberFormatException e) { }

		return false;
	}



	private void showDialogMessage(String msg) {
		javax.swing.JOptionPane.showMessageDialog(this,msg,"Paramters error",javax.swing.JOptionPane.ERROR_MESSAGE);
	}

}
