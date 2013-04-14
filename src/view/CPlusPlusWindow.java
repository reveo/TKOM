package view;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import line.AbstractLine;
import line.ForLine;

public class CPlusPlusWindow extends JPanel {
	private static final long serialVersionUID = -5389192471717222128L;
	private static final int tabSize = 2;
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
		line.writeLine(this);
	}

	public void writeBracket() {
		setText("\n }",0);
	}

	public void setText(String text, int indent) {
		System.out.println("INDENT TO " + indent);
		for(int i = 0; i < indent;++i ) {
			textArea.append("    ");
		}
		textArea.append(text + "\n");
	}
}