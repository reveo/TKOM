package line;

import java.util.Vector;

import model.ErrorHandler;
import view.CPlusPlusWindow;

public class ElifLine extends Token implements ComplexLine {
	String outputString;

	public ElifLine(String text, int indent) {
		tokens = new Vector<String>();
		StringBuffer stringBuffer = new StringBuffer(text.toLowerCase());
		isOk = true;
		this.indent = indent;

		getCondition(stringBuffer);
		setOutput();
	}

	public void getCondition(StringBuffer stringBuffer) {
		if (!stringBuffer.toString().startsWith("elif")) {
			error();
			return;
		}

		stringBuffer.delete(0, 4);
		stringBuffer = new StringBuffer(stringBuffer.toString().trim());
		if (!stringBuffer.toString().startsWith("(")) {
			error();
			return;
		}

		if (!stringBuffer.toString().endsWith("):")) {
			error();
			return;
		}

		stringBuffer.delete(0, 1);
		stringBuffer.delete(stringBuffer.length() - 2, stringBuffer.length());
		tokens = getAllVariables(stringBuffer);
		outputString = stringBuffer.toString();
	}

	public Vector<String> getTokens() {
		return this.tokens;
	}

	private Vector<String> getAllVariables(StringBuffer stringBuffer) {
		Vector<String> variables = new Vector<String>();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < stringBuffer.length(); ++i) {
			char c = stringBuffer.charAt(i);
			if (c == 'a') {
				int tmp = i;
				++tmp;
				if ((tmp < stringBuffer.length())
						&& (stringBuffer.charAt(tmp) == 'n')) {
					++tmp;
					if ((tmp < stringBuffer.length())
							&& (stringBuffer.charAt(tmp) == 'd')) {
						i += 2;
						builder.setLength(0);
						continue;
					}
				}
			} else if (c == 'o') {
				int tmp = i;
				++tmp;
				if ((tmp < stringBuffer.length())
						&& (stringBuffer.charAt(tmp) == 'r')) {
					i += 1;
					builder.setLength(0);
					continue;
				}
			}

			if (c == '&' || c == '|')
				error();
			if (c == '=' || c == '!' || c == '<' || c == '>' || c == '('
					|| c == ')') {
				if (builder.length() != 0)
					variables.add(builder.toString());
				builder.setLength(0);
				continue;
			} else if (c == ' ') {
				variables.add(builder.toString());
				builder.setLength(0);
				continue;
			} else if (Character.isDigit(c)) {
				variables.add(builder.toString());
				builder.setLength(0);
				continue;
			}

			else {
				builder.append(c);
			}
		}
		if(builder.length() != 0 ) variables.add(builder.toString());
		return variables;
	}

	public void setOutput() {
		String s = new String(outputString.replaceAll("and", "&&"));
		String s2 = new String(s.replaceAll("or", "||"));
		outputString = s2;
	}

	public boolean isOk() {
		return isOk;
	}

	public void writeLine(CPlusPlusWindow cPlusPlusWindow) {
		StringBuilder builder = new StringBuilder();
		builder.append("else if(" + outputString + ") { ");
		cPlusPlusWindow.setText(builder.toString(), indent);
	}


	public Vector<String> getIterateVariables() {
		return tokens;
	}
	
	public void error() {
		ErrorHandler.getInstance().setError("Error in ElifLine");
		isOk = false;
	}

}
