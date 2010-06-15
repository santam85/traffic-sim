package launchers;

import javax.swing.JFrame;

public class SimulatorGUI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		gui.SimulatorFrame frame = new gui.SimulatorFrame();
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
