package view;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ErrorWindow extends JPanel {
	private static final long serialVersionUID = 4123990150279254206L;

	private MainWindow mainWindow;

	public ErrorWindow() {

	}

	public ErrorWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		
		setPreferredSize(new Dimension(mainWindow.getSize().width,
				mainWindow.getSize().height / 4 - 10));
		JTextArea textArea = new JTextArea();
		textArea.setPreferredSize(this.getPreferredSize());
		textArea.setEditable(false);
		add(textArea);
	}
}
