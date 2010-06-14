package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 
 * @author Andrea Zagnoli, Marco Santarelli, Michael Gattavecchia. 
 */
public class ConsoleFrame extends javax.swing.JFrame implements ActionListener {

	private static final long serialVersionUID = 2798836000453178812L;
	private static ConsoleFrame obj;
	
    /**
     * Creates new form ConsoleFrame
     */
    private ConsoleFrame() {
        initComponents();
        
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.clearBtn.addActionListener(this);
        // this.setResizable(true);
        
        Logger.getLogger("simulation").addAppender(new ConsoleAppender());
    }
    
    public static ConsoleFrame getInstance() {
    	if (obj == null)
    		obj = new ConsoleFrame();
    	return obj;
    }

    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        consoleArea = new javax.swing.JTextArea();
        clearBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Simulator Console");
        setPreferredSize(new java.awt.Dimension(550, 301));

        consoleArea.setBackground(new java.awt.Color(204, 204, 204));
        consoleArea.setColumns(40);
        consoleArea.setEditable(false);
        consoleArea.setRows(5);
        jScrollPane1.setViewportView(consoleArea);

        clearBtn.setIcon(new javax.swing.ImageIcon("img/delete.gif"));
        clearBtn.setToolTipText("clear console");
        clearBtn.setFocusPainted(false);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(clearBtn)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(clearBtn)
                .add(3, 3, 3)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea consoleArea;
    private javax.swing.JButton clearBtn;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == clearBtn) {
			consoleArea.setText("");
		}
	}
    
    public class ConsoleAppender implements org.apache.log4j.Appender {
    	
    	private String name;
    	
		public void addFilter(Filter newFilter) { }

		public void clearFilters() { }

		public void close() { }

		public void doAppend(LoggingEvent event) {
			consoleArea.setText((consoleArea.getText().equals("")?"":consoleArea.getText() + "\n") + event.getMessage());
		}

		public ErrorHandler getErrorHandler() { return null; }

		@Override
		public Filter getFilter() { return null; }

		@Override
		public Layout getLayout() { return null; }

		@Override
		public String getName() {
			return name;
		}

		@Override
		public boolean requiresLayout() {
			return false;
		}

		@Override
		public void setErrorHandler(ErrorHandler errorHandler) { }

		@Override
		public void setLayout(Layout layout) { }

		@Override
		public void setName(String name) {
			this.name = name;
		}
    }
}
