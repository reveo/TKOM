package controller;

public class Controller {
	private Parser parser;

	public Controller() {
		parser = new Parser();
	}

	public void processText(String text) {
		parser.parseText(text);
	}
}
