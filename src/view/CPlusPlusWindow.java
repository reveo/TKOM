package view;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import line.AbstractLine;

public class CPlusPlusWindow extends JPanel {
	private static final long serialVersionUID = -5389192471717222128L;
	//private static final int tabSize = 2;
	private JScrollPane scrollPane;
	public MainWindow mainWindow;
	public JTextArea textArea;
	public JLabel title;
	public CPlusPlusWindow() {

	}

	public CPlusPlusWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		setPreferredSize(new Dimension(550,
				mainWindow.getSize().height / 4 * 3));
		title = new JLabel("Output window");
		add(title);
		textArea = new JTextArea(17,40);
		textArea.setEditable(false);
		scrollPane = new JScrollPane(textArea);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane);

	}

	public void writeLine(final AbstractLine line) {
		line.writeLine(this);
	}

	public void writeBracket(int indent) {
		setText("}", indent);
	}

	public void setText(String text, int indent) {
		System.out.println("INDENT TO " + indent);
		for (int i = 0; i < indent; ++i) {
			textArea.append("    ");
		}
		textArea.append(text + "\n");
	}
}