package controller;

import line.AbstractLine;
import model.Parser;
import view.MainWindow;

public class Controller {
	private Parser parser;
	private MainWindow mainWindow;

	public Controller() {	
	}

	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		parser = new Parser(mainWindow);
	}

	public void processText(String text, int number) {
		AbstractLine nowLine = parser.parseText(text, number);
		if(nowLine.isOk()) mainWindow.setNewLine(nowLine);
	}
}
