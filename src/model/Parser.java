package model;

import java.util.Iterator;
import java.util.Vector;

import line.AbstractLine;
import line.AssignmentLine;
import line.BasicArithmeticLine;
import line.CommentLine;
import line.ComplexLine;
import line.CreateVariableLine;
import line.ElifLine;
import line.ElseLine;
import line.ForLine;
import line.IfLine;
import line.ListAssignmentLine;
import line.ListCreateVariableLine;
import line.OtherLine;
import line.PrintLine;
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
			if (localStacks.size() == 0)
				setGlobalStackIf(false);
			return new CommentLine(text, indent);

		}

		String first = getNextToken(text);
		if (first.equals("for")) {

			System.out.println("STOS + " + localStacks.size());
			ComplexLine line = new ForLine(text, indent);

			String iterateVariable = line.getIterateVariables().elementAt(0);
			if (iterateVariable == null)
				return null;
			if ((checkIterateVariable(iterateVariable)) && line.isOk()) {
				localStacks.add(new LocStack());
				localStacks.lastElement().setIterateVariable(
						line.getIterateVariables().elementAt(0));
				++expectedIndent;
				if (localStacks.size() == 0)
					setGlobalStackIf(false);
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
					if (!((operandExistsActual(tmp)) || operandExistsGlobal(tmp)))
						return null;
				}
				++expectedIndent;
				localStacks.add(new LocStack());
				it = variables.iterator();
				while (it.hasNext()) {
					localStacks.lastElement().addVariable(it.next());
				}
				if (localStacks.size() == 0)
					setGlobalStackIf(false);
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
					if (!((operandExistsActual(tmp)) || operandExistsGlobal(tmp)))
						return null;
				}
				++expectedIndent;
				setIf(true);
				localStacks.add(new LocStack());
				it = variables.iterator();
				while (it.hasNext()) {
					localStacks.lastElement().addVariable(it.next());
				}
				if (localStacks.size() == 0)
					setGlobalStackIf(true);
				return line;
			} else
				return null;
		}

		else if (first.equalsIgnoreCase("elif")) {
			ComplexLine line = new ElifLine(text, indent);
			if (line.isOk() && checkIf()) {
				Vector<String> variables = line.getIterateVariables();
				Iterator<String> it = variables.iterator();
				while (it.hasNext()) {
					String tmp = it.next();
					if (tmp.equals(""))
						continue;
					if (!((operandExistsActual(tmp)) || operandExistsGlobal(tmp)))
						return null;
				}
				++expectedIndent;
				localStacks.add(new LocStack());
				it = variables.iterator();
				while (it.hasNext()) {
					localStacks.lastElement().addVariable(it.next());
				}
				if (localStacks.size() == 0)
					setGlobalStackIf(true);
				return line;
			} else
				return null;
		}

		else if (first.equalsIgnoreCase("else")) {
			AbstractLine line = new ElseLine(text, indent);
			if (line.isOk() && checkIf()) {
				localStacks.add(new LocStack());
				setIf(false);
				++expectedIndent;
				if (localStacks.size() == 0)
					setGlobalStackIf(false);
				return line;
			} else
				return null;
		}

		else if (first.equalsIgnoreCase("print")) {
			PrintLine line = new PrintLine(text, indent);
			Vector<String> variables = line.getIterateVariables();
			if ((variables.size() == 0) && (line.isOk())) {
				line.setIsBracketNeeded(false);
				if (localStacks.size() == 0)
					setGlobalStackIf(false);
				return (AbstractLine) line;
			}
			Iterator<String> it = variables.iterator();
			while (it.hasNext()) {
				String tmp = it.next();
				if (tmp.equals(""))
					continue;
				if (!((operandExistsActual(tmp)) || operandExistsGlobal(tmp))) {
					line.setIsBracketNeeded(true);
					if (localStacks.size() == 0)
						setGlobalStackIf(false);
					return (AbstractLine) line;
				}
			}
			line.setIsBracketNeeded(false);
			if (localStacks.size() == 0)
				setGlobalStackIf(false);
			return (AbstractLine) line;
		}

		else if (checkIfAssignment(text)) {
			String operand = getLeftOperand(text);

			Vector<String> tmp = isRightOperandList(text);
			if ((tmp != null) && (tmp.size() != 0)) {
				if (!checkLeftOperand(operand)) {
					return null;
				}
				if (operandExistsActual(operand)) {
					System.out.println("MAMY JUŻ TĘ ZMIENNA");
					if (localStacks.size() == 0)
						setGlobalStackIf(false);
					return new ListAssignmentLine(text, indent);
				} else {
					System.out.println("JESZCZE JEJ NIE MAMY");
					if (localStacks.size() != 0)
						localStacks.lastElement().addVariable(operand);
					else
						GlobalStack.getInstance().addVariable(operand);
					if (localStacks.size() == 0)
						setGlobalStackIf(false);
					return new ListCreateVariableLine(text, indent);
				}
			} else {
				Vector<String> rOperands = getRightOperand(text);
				if (!(rOperands.size() == 0)) {
					Iterator<String> it = rOperands.iterator();
					while (it.hasNext()) {
						String s = (String) it.next();
						if (!((operandExistsActual(s)) || operandExistsGlobal(s)))
							return null;
					}
				}

				if (!checkLeftOperand(operand)) {
					return null;
				}
				if (operandExistsActual(operand)) {
					System.out.println("MAMY JUŻ TĘ ZMIENNA");
					if (localStacks.size() == 0)
						setGlobalStackIf(false);
					return new AssignmentLine(text, indent);
				} else {
					System.out.println("JESZCZE JEJ NIE MAMY");
					if (localStacks.size() != 0)
						localStacks.lastElement().addVariable(operand);
					else
						GlobalStack.getInstance().addVariable(operand);
					if (localStacks.size() == 0)
						setGlobalStackIf(false);
					return new CreateVariableLine(text, indent);
				}
			}
		}

		else if (checkIfBasicArithmeticOperation(text)) {
			if (localStacks.size() == 0)
				setGlobalStackIf(false);
			return new BasicArithmeticLine(text, indent);
		}

		else {
			OtherLine line = new OtherLine(text, indent);
			Vector<String> variables = line.getIterateVariables();
			if ((variables.size() == 0) && (line.isOk())) {
				line.setIsBracketNeeded(false);
				if (localStacks.size() == 0)
					setGlobalStackIf(false);
				return (AbstractLine) line;
			}
			Iterator<String> it = variables.iterator();
			while (it.hasNext()) {
				String tmp = it.next();
				if (tmp.equals(""))
					continue;
				if (!((operandExistsActual(tmp)) || operandExistsGlobal(tmp))) {
					line.setIsBracketNeeded(true);
					if (localStacks.size() == 0)
						setGlobalStackIf(false);
					return (AbstractLine) line;
				}
			}
			line.setIsBracketNeeded(false);
			if (localStacks.size() == 0)
				setGlobalStackIf(false);
			return (AbstractLine) line;
		}
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
		if (lowerText.startsWith("print"))
			return "print";
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
		int numberOfEqualSigns = 0;
		for (int i = 0; i < text.length(); ++i) {
			char c = text.charAt(i);
			if (c == '=')
				++numberOfEqualSigns;
		}
		return (numberOfEqualSigns == 1);
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

	public boolean operandExistsGlobal(String operand) {
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

	public boolean operandExistsActual(String operand) {
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
			return true;
		for (int i = 0; i < localStacks.size(); ++i) {
			String tmp = localStacks.elementAt(i).getIterateVariable();
			if (tmp == null)
				return true;
			if (localStacks.elementAt(i).getIterateVariable().equals(name))
				return false;
		}
		return true;
	}

	public boolean checkLeftOperand(String name) {
		char c = name.charAt(0);
		if (Character.isDigit(c))
			return false;
		if (c == '?' || c == '/' || c == '<' || c == '>' || c == ','
				|| c == '.' || c == '\"')
			return false;

		for (int i = 0; i < name.length(); ++i) {
			c = name.charAt(i);
			if (c == '(' || c == ')' || c == '[' || c == ']')
				return false;
		}
		return true;
	}

	public boolean checkIfLogicalCondition(String name) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < name.length(); ++i) {
			char c = name.charAt(i);
			if (c == '(' || c == ')' || c == ' ' || c == '=') {
				if (!(operandExistsActual(builder.toString()) || operandExistsGlobal(builder
						.toString())))
					return false;
			} else {
				builder.append(c);
			}
		}
		return true;
	}

	public boolean checkIfBasicArithmeticOperation(String text) {
		char c;
		for (int i = 0; i < text.length(); ++i) {
			c = text.charAt(i);
			if (Character.isDigit(c))
				continue;
			if (c == '(' || c == ')' || c == '+' || c == '-' || c == '*'
					|| c == '/' || c == '%' || c == ' ')
				continue;
			if ((i == text.length() - 1) && (c == ';'))
				continue;
			return false;
		}
		return true;
	}

	public boolean checkIf() {
		if (localStacks.size() == 1) {
			return GlobalStack.getInstance().wasIf();
		}
		if (localStacks.size() == 0) {
			return GlobalStack.getInstance().wasIf();
		}
		return localStacks.elementAt(localStacks.size() - 1).wasIf();
	}

	public void setIf(boolean b) {
		if (localStacks.size() == 1) {
			GlobalStack.getInstance().setIf(b);
			return;
		}
		if (localStacks.size() == 0) {
			GlobalStack.getInstance().setIf(b);
			return;
		}

		localStacks.elementAt(localStacks.size() - 1).setIf(b);
		return;
	}

	public Vector<String> getRightOperand(String text) {
		Vector<String> variables = new Vector<String>();

		boolean inBracket = false;
		int i = 0;
		for (; i < text.length(); ++i) {
			char c = text.charAt(i);
			if (c == '=')
				break;
		}
		if (i == text.length())
			return null;
		StringBuilder builder = new StringBuilder();
		++i;
		for (; i < text.length(); ++i) {
			char c = text.charAt(i);
			if ((c == '\"') || (c == '\'')) {
				inBracket = !inBracket;
				continue;
			}
			if (inBracket)
				continue;
			if (c == '(' || c == ')' || c == ';' || c == '+' || c == '-'
					|| c == '*' || c == '/' || c == ' ' || Character.isDigit(c)) {
				String x = builder.toString();
				if (!(x.equals("")))
					variables.add(x);
				builder.setLength(0);
			} else
				builder.append(c);
		}

		return variables;
	}

	Vector<String> isRightOperandList(String text) {
		Vector<String> tokens = new Vector<String>();
		int i = 0;
		for (; i < text.length(); ++i) {
			char c = text.charAt(i);
			if (c == '=')
				break;
		}

		String newText = text.substring(i + 1).trim();
		if (!newText.startsWith(("[")))
			return null;
		if (!((newText.endsWith("];") || (newText.endsWith("]")))))
			return null;

		if (newText.endsWith("]"))
			newText = newText.substring(1, newText.length() - 1);
		else
			newText = newText.substring(1, newText.length() - 2);

		StringBuilder builder = new StringBuilder();
		for (int j = 0; j < newText.length(); ++j) {
			char c = newText.charAt(j);
			if (c == ' ')
				continue;
			else if (c == ',') {
				if (builder.length() != 0)
					tokens.add(builder.toString());
				else
					return null;
				builder.setLength(0);
			} else
				builder.append(c);
		}
		tokens.add(builder.toString());
		System.out.println(newText);
		return tokens;
	}

	void setGlobalStackIf(boolean b) {
		GlobalStack.getInstance().setIf(b);
	}

}
