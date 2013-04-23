package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import model.FromFileReader;
import model.ToFileWriter;

public class Menu extends JPanel implements ActionListener {
	private static final long serialVersionUID = 2401343171737574518L;

	MainWindow mainWindow;
	JButton newButton;
	JButton saveToFile;
	JButton readFromFile;
	JButton exit;
	JFileChooser fileChooser;

	public Menu(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		setPreferredSize(new Dimension(200, 400));
		fileChooser = new JFileChooser();
		
		newButton = new JButton("New");
		newButton.setPreferredSize(new Dimension(100, 20));
		newButton.addActionListener(this);

		saveToFile = new JButton("Save to File");
		saveToFile.setPreferredSize(new Dimension(100, 20));
		saveToFile.addActionListener(this);

		readFromFile = new JButton("Read from File");
		readFromFile.setPreferredSize(new Dimension(100, 20));
		readFromFile.addActionListener(this);

		exit = new JButton("Exit");
		exit.setPreferredSize(new Dimension(100, 20));
		exit.addActionListener(this);

		add(newButton);
		add(saveToFile);
		add(readFromFile);
		add(exit);

	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == newButton) {
			mainWindow.resetAllStacks();
			mainWindow.clearText();
		} else if (e.getSource() == readFromFile) {
			
			int returnVal = fileChooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				new FromFileReader(file, mainWindow);
			}
		} else if (e.getSource() == saveToFile) {
			int returnVal = fileChooser.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				new ToFileWriter(file, mainWindow);
			}
		} else if (e.getSource() == exit) {
			System.exit(0);
		}

	}
}
