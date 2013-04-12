package view;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class CPlusPlusWindow extends JPanel {
	private static final long serialVersionUID = -5389192471717222128L;

	public MainWindow mainWindow;

	public CPlusPlusWindow() {

	}

	public CPlusPlusWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		setPreferredSize(new Dimension(mainWindow.getSize().width / 2 - 10,
				mainWindow.getSize().height / 4 * 3));
		JTextArea textArea = new JTextArea();
		textArea.setPreferredSize(this.getPreferredSize());
		add(textArea);
	
	}
}