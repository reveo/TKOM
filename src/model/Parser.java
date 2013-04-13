package model;

import java.util.Vector;

public class Parser {

	int previousIndent = 0;
	int actualIndent = 0;
	Vector<LocalStack> localStacks;

	public Parser() {
		localStacks = new Vector<LocalStack>();
	}

	public void parseText(String text, int number) {
		if (text.startsWith("#")) {
			System.out.println("KOMENTARZ");

			// Create new CommentLine object and return;
		}

		if (number < actualIndent) {

		}
		
		String first = getNextToken(text);

		if (first.equalsIgnoreCase("for")) {
			actualIndent++;
			localStacks.add(new LocalStack());
		}

		else if (first.equalsIgnoreCase("while")) {
			actualIndent++;
			localStacks.add(new LocalStack());
		}

		else if (first.equalsIgnoreCase("if")) {
			actualIndent++;
			localStacks.add(new LocalStack());
		}

		else if (first.equalsIgnoreCase("elif")) {
			actualIndent++;
			localStacks.add(new LocalStack());
		}

		else if (first.equalsIgnoreCase("else")) {
			actualIndent++;
			localStacks.add(new LocalStack());
		}

		// KONIEC INSTRUKCJI ZŁOŻONYCH

	}

	public String getNextToken(String text) {
		StringBuilder token = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c == ' ') {
				text = text.substring(token.length() + 1);
				return token.toString();
			}
			token.append(c);
		}

		return token.toString();
	}
}
