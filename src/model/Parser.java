package model;

import java.util.Vector;

import line.AbstractLine;
import line.CommentLine;
import line.ForLine;

public class Parser {

	int previousIndent = 0;
	int actualIndent = 0;
	int expectedIndent = 0;
	private Vector<AbstractLine> lines;
	private Vector<LocStack> localStacks;

	public Parser() {
		lines = new Vector<AbstractLine>();
		localStacks = new Vector<LocStack>();
	}

	public void parseText(String text, int number) {
		if (text.startsWith("#")) {
			System.out.println("KOMENTARZ");
			AbstractLine newLine = new CommentLine(text);
			lines.add(newLine);
			// Create new CommentLine object and return;
		}

		handleIndent(number);

		if (number < actualIndent) {

		}

		String first = getNextToken(text);

		if (first.equalsIgnoreCase("for")) {
			++expectedIndent;
			localStacks.add(new LocStack());
			lines.add(new ForLine(text));
		}

		else if (first.equalsIgnoreCase("while")) {
			++expectedIndent;
			localStacks.add(new LocStack());
		}

		else if (first.equalsIgnoreCase("if")) {
			++expectedIndent;
			localStacks.add(new LocStack());
		}

		else if (first.equalsIgnoreCase("elif")) {
			++expectedIndent;
			localStacks.add(new LocStack());
		}

		else if (first.equalsIgnoreCase("else")) {
			++expectedIndent;
			localStacks.add(new LocStack());
		}

		// KONIEC INSTRUKCJI ZŁOŻONYCH

		else if (checkIfAssignment(text)) {

			System.out.println(GlobalStack.getInstance());
			String operand = getLeftOperand(text);
			System.out.println("OPERAND TO " + operand);
			if (leftOperandExists(operand)) {
				System.out.println("MAMY JUŻ TĘ ZMIENNA");
			} else {
				System.out.println("JESZCZE JEJ NIE MAMY");
				if (localStacks.size() != 0)
					localStacks.lastElement().addVariable(operand);
				else
					GlobalStack.getInstance().addVariable(operand);
				System.out.println(GlobalStack.getInstance());
			}
		}

	}

	public String getNextToken(String text) {
		StringBuilder token = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c == ' ') {
				return token.toString();
			}
			token.append(c);
		}

		return token.toString();
	}

	public void handleIndent(int newIndent) {
		if (newIndent > expectedIndent)
			newIndent = expectedIndent;
		int indentDiff = newIndent - expectedIndent;
		
		if (indentDiff == 0)
			return;

		if (indentDiff < 0) {
			for (int i = 0; i < Math.abs(indentDiff); ++i) {
				localStacks.remove(localStacks.size() - i - 1);
				--expectedIndent;
				// ZAMYKAJ NAWIASY
			}
		}
	}

	public boolean checkIfAssignment(String text) {
		for (int i = 0; i < text.length(); ++i) {
			char c = text.charAt(i);
			if (c == '=')
				return true;
		}
		return false;
	}

	public String getLeftOperand(String text) {
		for (int i = 0; i < text.length(); ++i) {
			char c = text.charAt(i);
			if (c == '=') {
				String operand = text.substring(0, i);
				operand = operand.replaceAll("\\s", "");
				return operand;
			}
		}
		return "";
	}

	public boolean leftOperandExists(String operand) {
		if (localStacks.size() != 0) {
			for (int j = localStacks.size() - 1; j >= 0; --j) {
				if (localStacks.elementAt(j).checkIfVariableExists(operand)) {
					return true;
				}
			}
		}
		if (GlobalStack.getInstance().checkIfVariableExists(operand))
			return true;
		return false;
	}

}
