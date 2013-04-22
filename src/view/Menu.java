package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import model.FileReader;

public class Menu extends JPanel implements ActionListener {
	private static final long serialVersionUID = 2401343171737574518L;
	
	MainWindow mainWindow; 
	JButton saveToFile;
	JButton readFromFile;
	JButton exit;
	JFileChooser fileChooser;
	
	public Menu(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		setPreferredSize(new Dimension(200,300));
		
		saveToFile = new JButton("Save to File");
		saveToFile.setPreferredSize(new Dimension(100,20));
		
		readFromFile = new JButton("Read from File");		
		readFromFile.setPreferredSize(new Dimension(100,20));
		readFromFile.addActionListener(this);
		
		exit = new JButton("Exit");
		exit.setPreferredSize(new Dimension(100,20));
		
		add(saveToFile);
		add(readFromFile);
		add(exit);
		
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == readFromFile) {
			fileChooser = new JFileChooser();
			int returnVal = fileChooser.showOpenDialog(this);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				FileReader fileReader = new FileReader(file);
			}
		}
		
	}
}
