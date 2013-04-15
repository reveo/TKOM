package line;

import java.util.Vector;

import model.ErrorHandler;

import view.CPlusPlusWindow;

public class ForLine extends Token implements ComplexLine {

	public ForLine(String text, int indent) {
		tokens = new Vector<String>();
		StringBuffer stringBuffer = new StringBuffer(text);

		getVariableToken(stringBuffer);
		getRangeToken(stringBuffer);
		this.indent = indent;
		isOk = true;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < tokens.size(); ++i) {
			builder.append(tokens.elementAt(i) + "\t");
		}
		return builder.toString();
	}

	private void getVariableToken(StringBuffer text) {
		StringBuilder variableToken = new StringBuilder();
		for (int i = 0; i < text.length(); ++i) {
			char c = text.charAt(i);
			if (c == ' ') {
				text = text.delete(0, i + 1);
				break;
			}
		}

		for (int i = 0; i < text.length(); ++i) {
			char c = text.charAt(i);
			if (c == ' ') {
				text = text.delete(0, i + 1);
				tokens.add(variableToken.toString());
				return;
			}
			variableToken.append(c);
		}
	}

	private void getRangeToken(StringBuffer text) {
		if (text.toString().toLowerCase().startsWith("in range(")) {
			System.out.println("JEST OK");
			text.delete(0, 9);
			if (!text.toString().endsWith("):")) {
				error();
				return;
			}
			text.delete(text.length() - 2, text.length()); // POZBYWAMY SIE
															// NAWIASU I
															// DWUKROPKA
			int index = text.toString().indexOf(',');
			if (index == -1) {
				try {
					Integer.parseInt(text.toString());
					tokens.add(text.toString());
				} catch (NumberFormatException e) {
					error();
				}
			} else {
				String textOne = text.toString().substring(0, index);

				String textTwo = text.toString().substring(index + 1,
						text.length());
				try {
					int numberOne = Integer.parseInt(textOne.toString());
					int numberTwo = Integer.parseInt(textTwo.toString());
					if (numberOne >= numberTwo)
						error();
					else {
						tokens.add(textOne);
						tokens.add(textTwo);
					}
				} catch (NumberFormatException e) {
					error();
				}

			}
		} else
			error();
		return;
	}

	public String getNextToken(String text) {
		StringBuilder token = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c == ' ') {
				System.out.println("TU JESTESMY");
				text = text.substring(token.length() + 1);
				return token.toString();
			}
			token.append(c);
		}

		return token.toString();
	}

	public boolean isOk() {
		return isOk;
	}

	public void error() {
		System.out.println("BŁĘDNE DANE");
		ErrorHandler.getInstance().setError("BŁĘDNE DANE");
		isOk = false;
	}

	public void writeLine(CPlusPlusWindow cPlusPlusWindow) {
		StringBuilder builder = new StringBuilder();
		if (tokens.size() == 2) {
			builder.append("for(int " + tokens.elementAt(0) + "=0;"
					+ tokens.elementAt(0) + "<" + tokens.elementAt(1) + ";++"
					+ tokens.elementAt(0) + ") {");
			cPlusPlusWindow.setText(builder.toString(), indent);
		} else if (tokens.size() == 3) {
			builder.append("for(int " + tokens.elementAt(0) + " = "
					+ tokens.elementAt(1) + ";" + tokens.elementAt(0) + "<"
					+ tokens.elementAt(2) + ";++" + tokens.elementAt(0) + ") {");
			cPlusPlusWindow.setText(builder.toString(), indent);

		}
	}

	public String getIterateVariable() {
		return tokens.elementAt(0);
	}
}