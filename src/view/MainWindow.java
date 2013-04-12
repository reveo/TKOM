package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 3172688540921699213L;
	
	private CPlusPlusWindow cPlusPlusWindow;
	private PythonWindow pythonWindow;
	private ErrorWindow errorWindow;
	
	public MainWindow()
	{	
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(1000,600);
		setResizable(false);
		setTitle("Python -> C++ Translator");
		
		setLayout(new BorderLayout());
		cPlusPlusWindow = new CPlusPlusWindow(this);
		pythonWindow = new PythonWindow(this);
		errorWindow = new ErrorWindow(this);
		
		getContentPane().add(cPlusPlusWindow,BorderLayout.WEST);
		getContentPane().add(pythonWindow,BorderLayout.EAST);
		getContentPane().add(errorWindow,BorderLayout.SOUTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	
}
