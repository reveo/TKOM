package view;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import line.AbstractLine;
import line.ForLine;

public class CPlusPlusWindow extends JPanel {
	private static final long serialVersionUID = -5389192471717222128L;

	public MainWindow mainWindow;
	public JTextArea textArea;

	public CPlusPlusWindow() {

	}

	public CPlusPlusWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		setPreferredSize(new Dimension(mainWindow.getSize().width / 2 - 10,
				mainWindow.getSize().height / 4 * 3));
		textArea = new JTextArea();
		textArea.setPreferredSize(this.getPreferredSize());
		textArea.setEditable(false);
		add(textArea);

	}

	public void writeLine(final AbstractLine line) {
		StringBuilder builder = new StringBuilder();
		Vector<String> tokens = line.getTokens();
		if (line instanceof ForLine) {
			builder.append("for(int " + tokens.elementAt(0) + "=0;"
					+ tokens.elementAt(0) + "<" + tokens.elementAt(1) + ";++"
					+ tokens.elementAt(0) + ") {");		
		}
		
		setText(builder.toString());
	}

	public void writeBracket() {
		setText("\n }");
	}

	public void setText(String text) {
		textArea.append(text + "\n");
	}
}