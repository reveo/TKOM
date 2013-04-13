package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import controller.Controller;


public class MainWindow extends JFrame {
	private static final long serialVersionUID = 3172688540921699213L;

	private Controller model;
	
	private CPlusPlusWindow cPlusPlusWindow;
	private PythonWindow pythonWindow;
	private ErrorWindow errorWindow;

	public MainWindow(Controller model) {
		// 600
		this.model = model;
		setSize(1000, 400);
		setResizable(false);
		setTitle("Python -> C++ Translator");

		setLayout(new BorderLayout());
		cPlusPlusWindow = new CPlusPlusWindow(this);
		pythonWindow = new PythonWindow(this);
		errorWindow = new ErrorWindow(this);

		getContentPane().add(cPlusPlusWindow, BorderLayout.WEST);
		getContentPane().add(pythonWindow, BorderLayout.EAST);
		getContentPane().add(errorWindow, BorderLayout.SOUTH);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		pythonWindow.requestFocus();
	}

	public void processText(String text) {
		model.processText(text);
	}
}
