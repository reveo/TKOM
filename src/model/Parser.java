package model;

import java.util.Iterator;
import java.util.Vector;

import line.AbstractLine;
import line.AssignmentLine;
import line.CommentLine;
import line.ComplexLine;
import line.CreateVariableLine;
import line.ElifLine;
import line.ElseLine;
import line.ForLine;
import line.IfLine;
import line.WhileLine;
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

		String first = getNextToken(text);
		if (first.equals("for")) {

			System.out.println("STOS + " + localStacks.size());
			ComplexLine line = new ForLine(text, indent);

			String iterateVariable = line.getIterateVariables().elementAt(0);
			if (iterateVariable == null)
				return null;
			if ((!checkIterateVariable(iterateVariable)) && line.isOk()) {
				localStacks.add(new LocStack());
				localStacks.lastElement().setIterateVariable(
						line.getIterateVariables().elementAt(0));
				++expectedIndent;
				return line;
			} else
				return null;
		}

		else if (first.equalsIgnoreCase("while")) {

			ComplexLine line = new WhileLine(text, indent);
			if (line.isOk()) {
				Vector<String> variables = line.getIterateVariables();
				Iterator<String> it = variables.iterator();
				while (it.hasNext()) {
					String tmp = it.next();
					if (tmp.equals(""))
						continue;
					if (!((leftOperandExistsActual(tmp)) || leftOperandExistsGlobal(tmp)))
						return null;
				}
				++expectedIndent;
				localStacks.add(new LocStack());
				it = variables.iterator();
				while (it.hasNext()) {
					localStacks.lastElement().addVariable(it.next());
				}

				return line;
			} else
				return null;
		}

		else if (first.equalsIgnoreCase("if")) {
			ComplexLine line = new IfLine(text, indent);
			if (line.isOk()) {
				Vector<String> variables = line.getIterateVariables();
				Iterator<String> it = variables.iterator();
				while (it.hasNext()) {
					String tmp = it.next();
					if (tmp.equals(""))
						continue;
					if (!((leftOperandExistsActual(tmp)) || leftOperandExistsGlobal(tmp)))
						return null;
				}
				++expectedIndent;
				localStacks.add(new LocStack());
				it = variables.iterator();
				while (it.hasNext()) {
					localStacks.lastElement().addVariable(it.next());
				}
				return line;
			} else
				return null;
		}

		else if (first.equalsIgnoreCase("elif")) {
			ComplexLine line = new ElifLine(text, indent);
			if (line.isOk()) {
				Vector<String> variables = line.getIterateVariables();
				Iterator<String> it = variables.iterator();
				while (it.hasNext()) {
					String tmp = it.next();
					if (tmp.equals(""))
						continue;
					if (!((leftOperandExistsActual(tmp)) || leftOperandExistsGlobal(tmp)))
						return null;
				}
				++expectedIndent;
				localStacks.add(new LocStack());
				it = variables.iterator();
				while (it.hasNext()) {
					localStacks.lastElement().addVariable(it.next());
				}
				return line;
			} else
				return null;
		}

		else if (first.equalsIgnoreCase("else")) {
			AbstractLine line = new ElseLine(text, indent);
			if (line.isOk()) {
				return line;
			} else
				return null;
		}

		// KONIEC INSTRUKCJI ZŁOŻONYCH

		else if (checkIfAssignment(text)) {
			String operand = getLeftOperand(text);
			if (checkIterateVariable(operand)) {
				return null;
			}
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
		String lowerText = text.toLowerCase();
		if (lowerText.startsWith("for"))
			return "for";
		if (lowerText.startsWith("while"))
			return "while";
		if (lowerText.startsWith("if"))
			return "if";
		if (lowerText.startsWith("elif"))
			return "elif";
		if (lowerText.startsWith("else"))
			return "else";
		return "";
	}

	public int handleIndent(int newIndent) {
		if (newIndent > expectedIndent) {
			newIndent = expectedIndent;
		}

		int indentDiff = newIndent - expectedIndent;

		if (indentDiff < 0) {
			for (int i = 0; i < Math.abs(indentDiff); ++i) {
				localStacks.remove(localStacks.size() - 1);
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

	public boolean checkIterateVariable(String name) {
		if (localStacks.size() == 0)
			return false;
		for (int i = 0; i < localStacks.size(); ++i) {
			String tmp = localStacks.elementAt(i).getIterateVariable();
			if (tmp == null)
				return false;
			if (localStacks.elementAt(i).getIterateVariable().equals(name))
				return true;
		}
		return false;
	}
}
