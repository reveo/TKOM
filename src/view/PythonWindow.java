package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class PythonWindow extends JPanel {
	private static final long serialVersionUID = 4621872062044006076L;
	
	
	MainWindow mainWindow;
	JTextArea pythonTextArea;
	JScrollPane scrollPane;
	int numberOfTabsInLine = 0;
	JLabel title;
	public PythonWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		
		title = new JLabel("Input window");
		add(title);
		pythonTextArea = new JTextArea(17,42);
		pythonTextArea.setTabSize(2);
		
		scrollPane = new JScrollPane(pythonTextArea);
		add(scrollPane);
		addKeyBindings();
		setPreferredSize(new Dimension(540,
				mainWindow.getSize().height / 4 * 3));
	}

	public void processTab() {
		numberOfTabsInLine++;
		pythonTextArea.append("      ");
	}

	public void processEnter() {
		String s = pythonTextArea.getText();
		pythonTextArea.setText("");
		StringBuilder builder = new StringBuilder();

		boolean flague = false;
		for (char c : s.toCharArray()) {
			if (c == ' ' && !flague)
				continue;
			else {
				flague = true;
				builder.append(c);
			}
		}

		mainWindow.processText(builder.toString(), numberOfTabsInLine);
		numberOfTabsInLine = 0;
	}

	public void addKeyBindings() {
		InputMap inputMap = pythonTextArea.getInputMap();
		ActionMap actionMap = pythonTextArea.getActionMap();
		KeyStroke key;

		key = KeyStroke.getKeyStroke("TAB");
		inputMap.put(key, "tabPressed");
		TabPressedAction tabPressedAction = new TabPressedAction(this);
		actionMap.put("tabPressed", tabPressedAction);

		key = KeyStroke.getKeyStroke("ENTER");
		inputMap.put(key, "enterPressed");
		EnterPressedAction enterPressedAction = new EnterPressedAction(this);
		actionMap.put("enterPressed", enterPressedAction);

	}
}

class TabPressedAction extends AbstractAction {
	private static final long serialVersionUID = 288648273973263604L;
	PythonWindow pythonWindow;

	public TabPressedAction(PythonWindow pythonWindow) {
		this.pythonWindow = pythonWindow;
	}

	public void actionPerformed(ActionEvent e) {
		pythonWindow.processTab();
	}
}

class EnterPressedAction extends AbstractAction {
	private static final long serialVersionUID = 7131754904803654697L;
	PythonWindow pythonWindow;

	public EnterPressedAction(PythonWindow pythonWindow) {
		this.pythonWindow = pythonWindow;
	}

	public void actionPerformed(ActionEvent e) {
		pythonWindow.processEnter();
	}
}
