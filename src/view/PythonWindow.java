package view;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PythonWindow extends JPanel {
	private static final long serialVersionUID = 4621872062044006076L;

	MainWindow mainWindow;

	public PythonWindow() {

	}

	public PythonWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		setPreferredSize(new Dimension(mainWindow.getSize().width / 2 - 10,
				mainWindow.getSize().height / 4 * 3 ));
		JTextArea textArea = new JTextArea();
		textArea.setPreferredSize(this.getPreferredSize());
		add(textArea);
	}
}
