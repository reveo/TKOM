package view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ErrorWindow extends JPanel {
	private static final long serialVersionUID = 4123990150279254206L;

	private JTextArea textArea;
	private JScrollPane scrollPane;
	
	public ErrorWindow() {

	}

	public ErrorWindow(MainWindow mainWindow) {
		textArea = new JTextArea(5, 80);
		textArea.setEditable(false);
		scrollPane = new JScrollPane(textArea);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane);
	}

	public void setErrorText(String text) {
		textArea.append(text);
	}
	
	public void clearErrorText() {
		textArea.setText("");
	}
}
