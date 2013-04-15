package model;

import java.util.Vector;

import line.AbstractLine;
import line.AssignmentLine;
import line.CommentLine;
import line.CreateVariableLine;
import line.ForLine;
import view.MainWindow;

public class Parser {

	int previousIndent = 0;
	int actualIndent = 0;
	int expectedIndent = 0;
	MainWindow mainWindow;
	private Vector<LocStack> localStacks;

	public Parser(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		localStacks = new Vector<LocStack>();
	}

	public AbstractLine parseText(String text, int indent) {

		indent = handleIndent(indent);

		if (text.startsWith("#")) {
			return new CommentLine(text, indent);
		}

		if (indent < actualIndent) {

		}

		String first = getNextToken(text);

		if (first.equalsIgnoreCase("for")) {
			++expectedIndent;
			localStacks.add(new LocStack());
			System.out.println("STOS + " + localStacks.size());
			return new ForLine(text, indent);
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
			String operand = getLeftOperand(text);
			if (leftOperandExistsActual(operand)) {
				System.out.println("MAMY JUŻ TĘ ZMIENNA");
				return new AssignmentLine(text, indent);
			} else {
				System.out.println("JESZCZE JEJ NIE MAMY");
				if (localStacks.size() != 0)
					localStacks.lastElement().addVariable(operand);
				else
					GlobalStack.getInstance().addVariable(operand);
				return new CreateVariableLine(text, indent);
			}

		}
		return null;
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

	public int handleIndent(int newIndent) {
		if (newIndent > expectedIndent) {
			System.out.println("INDENT JEST ZA DUZY");
			newIndent = expectedIndent;
		}
		System.out.println("NEW INDENT " + newIndent);
		System.out.println("EXPECTED INDENT TO " + expectedIndent);
		int indentDiff = newIndent - expectedIndent;

		// if (indentDiff == 0)
		// return;

		if (indentDiff < 0) {
			for (int i = 0; i < Math.abs(indentDiff); ++i) {
				System.out.println("ILOSC STOSOW TO " + localStacks.size());
				System.out.println("INDENT DIFF TO " + indentDiff);
				localStacks.remove(localStacks.size() - 1);//
				--expectedIndent;
				mainWindow.setBracket(localStacks.size());
			}
		}
		return newIndent;
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

	public boolean leftOperandExistsGlobal(String operand) {
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

	public boolean leftOperandExistsActual(String operand) {
		if (localStacks.size() == 0) {
			if (GlobalStack.getInstance().checkIfVariableExists(operand))
				return true;
			else
				return false;
		}
		if (localStacks.lastElement().checkIfVariableExists(operand)) {
			return true;
		} else
			return false;
	}

}
