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

public class MG1PrioPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private javax.swing.JTextField arrivals;
	private javax.swing.JComboBox classes;
	private javax.swing.JLabel jLabel12;
	private javax.swing.JLabel jLabel14;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JTextField mu;
	private javax.swing.JTextField runs;
	private javax.swing.JButton simulate;

	private ExecutorService executor;

	public MG1PrioPanel(ExecutorService e) {
		init();
		
		simulate.addActionListener(this);
        classes.setModel(new DefaultComboBoxModel(new String[]{"2","3a","3b","3c"}));
		
		executor = e;
	}

	private void init() {
		classes = new javax.swing.JComboBox();
		jLabel8 = new javax.swing.JLabel();
		jLabel9 = new javax.swing.JLabel();
		mu = new javax.swing.JTextField();
		simulate = new javax.swing.JButton();
		runs = new javax.swing.JTextField();
		jLabel12 = new javax.swing.JLabel();
		arrivals = new javax.swing.JTextField();
		jLabel14 = new javax.swing.JLabel();

		jLabel8.setText("Number of classes");
		jLabel9.setText("Mu:");
		mu.setText("2");
		simulate.setText("Simulate");
		runs.setText("100");
		jLabel12.setText("# runs:");
		arrivals.setText("1000");
		jLabel14.setText("# arrivals:");

		org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(this);
		this.setLayout(jPanel4Layout);
		jPanel4Layout.setHorizontalGroup(
				jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(jPanel4Layout.createSequentialGroup()
						.addContainerGap()
						.add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(simulate)
								.add(jPanel4Layout.createSequentialGroup()
										.add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
												.add(jLabel9)
												.add(jLabel8)
												.add(jLabel12)
												.add(jLabel14))
												.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
												.add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
														.add(arrivals, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
														.add(runs, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
														.add(mu, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
														.add(classes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 171, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
														.addContainerGap(62, Short.MAX_VALUE))
		);
		jPanel4Layout.setVerticalGroup(
				jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(jPanel4Layout.createSequentialGroup()
						.add(97, 97, 97)
						.add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
								.add(jLabel8)
								.add(classes, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
								.add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
										.add(jLabel9)
										.add(mu, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
										.add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
												.add(jLabel12)
												.add(runs, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
												.add(18, 18, 18)
												.add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
														.add(jLabel14)
														.add(arrivals, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
														.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 52, Short.MAX_VALUE)
														.add(simulate)
														.addContainerGap())
		);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.simulate) {
			handlePrioMG1Simulation();
		}
	}

	private void handlePrioMG1Simulation() {
		if (!checkMG1PrioSimulationParameters()) {
			showDialogMessage("Parameters missing or wrong");
			return ; // pianto unA grana
		}

		final double mu = Double.parseDouble(this.mu.getText());
		final int N = Integer.parseInt(this.runs.getText());
		final int arrivals = Integer.parseInt(this.arrivals.getText());

		executor.submit(new Runnable() {
			public void run() {
				final LinkedList<double[][]> res = SimulationRunners.simulateMG1PrioWithVariableRhos(mu,N,classes.getSelectedItem().toString(),arrivals);
				final String[] keys = new String[res.size()];
				for(int i=0;i<keys.length;i++){
					keys[i] = "C"+i;
				}
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						GraphUtils.displayDevRendererGraph("Eta ("+classes.getSelectedItem().toString()+")","x","Time",keys,res);
					}
				});

			}
		});
	}
	
	private boolean checkMG1PrioSimulationParameters() {
		if (runs.getText().equals("") || mu.getText().equals("") || 
				this.arrivals.getText().equals(""))
			return false;

		try {
			double d;
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
		
		return true;
	}
	
	private void showDialogMessage(String msg) {
		javax.swing.JOptionPane.showMessageDialog(this,msg,"Paramters error",javax.swing.JOptionPane.ERROR_MESSAGE);
	}

}
