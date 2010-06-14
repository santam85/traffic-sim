package gui;

import gui.panels.MG1Panel;
import gui.panels.MG1PrioPanel;
import gui.panels.MG1SJNPanel;
import gui.panels.RandomNumberPanel;
import gui.panels.VariableTrafficPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import simulator.ISimulationProgressListener;
import simulator.SimulationProgress;

/**
 *
 * @author Andrea Zagnoli, Marco Santarelli, Michael Gattavecchia. 
 */
public class SimulatorFrame extends javax.swing.JFrame implements ActionListener {

	private static final long serialVersionUID = -8210143930250467129L;

	public SimulatorFrame() {
		initComponents();

		SimulationProgress.getInstance().addIndexingProgressListener((ISimulationProgressListener)simPbr);

		console = ConsoleFrame.getInstance();
		console.setLocation(new java.awt.Point(this.getLocation().x + this.getWidth() + 10,this.getLocation().y));
		consoleTBtn.addActionListener(this);

	}

	private void initComponents() {

		jTabbedPane1 = new javax.swing.JTabbedPane();
		simPbr = new SimulationProgressBar();
		jLabel11 = new javax.swing.JLabel();
		consoleTBtn = new javax.swing.JToggleButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jTabbedPane1.addTab("RandomNumberPanel", new RandomNumberPanel(executor));
		jTabbedPane1.addTab("VariableTraffic", new VariableTrafficPanel(executor));
		jTabbedPane1.addTab("M/G/1", new MG1Panel(executor));
		jTabbedPane1.addTab("M/G/1//PRIO", new MG1PrioPanel(executor));
		jTabbedPane1.addTab("M/M/1//SJN", new MG1SJNPanel(executor));

		jLabel11.setText("Simulation progess");

		consoleTBtn.setText("c");

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
								.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 46, Short.MAX_VALUE)
								.add(consoleTBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 39, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(28, 28, 28))
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(layout.createSequentialGroup()
						.addContainerGap()
						.add(jTabbedPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 388, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
								.add(layout.createSequentialGroup()
										.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
										.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
												.add(jLabel11)
												.add(simPbr, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
												.add(consoleTBtn))
												.addContainerGap(19, Short.MAX_VALUE))
		);

		pack();
	}

	private javax.swing.JToggleButton consoleTBtn;
	private javax.swing.JLabel jLabel11;
	private javax.swing.JTabbedPane jTabbedPane1;
	private javax.swing.JProgressBar simPbr;


	private ConsoleFrame console;

	private final ExecutorService executor = Executors.newSingleThreadExecutor();

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.consoleTBtn) {
			if (consoleTBtn.isSelected()) {
				console.setVisible(true);
			}
			else {
				console.setVisible(false);
			}
		}
	}

}


	