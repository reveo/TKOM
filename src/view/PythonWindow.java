package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class PythonWindow extends JPanel {
	private static final long serialVersionUID = 4621872062044006076L;
	
	
	MainWindow mainWindow;
	JTextArea pythonTextArea;
	int numberOfTabsInLine = 0;

	public PythonWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;

		pythonTextArea = new JTextArea();
		pythonTextArea.setTabSize(2);
		pythonTextArea.setPreferredSize(new Dimension(
				mainWindow.getSize().width / 2 - 10,
				mainWindow.getSize().height / 4 * 3));
		add(pythonTextArea);

		addKeyBindings();
		setPreferredSize(new Dimension(mainWindow.getSize().width / 2 - 10,
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
