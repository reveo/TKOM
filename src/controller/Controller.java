package controller;

import model.Parser;

public class Controller {
	private Parser parser;

	public Controller() {
		parser = new Parser();
	}

	public void processText(String text, int number) {
		parser.parseText(text, number);
	}
}
