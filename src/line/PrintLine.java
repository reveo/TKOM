package line;

import java.util.Vector;

import model.ErrorHandler;

import view.CPlusPlusWindow;

public class PrintLine extends Token implements ComplexLine {

	boolean isBracketNeeded = false;
	String outputString;

	public PrintLine(String text, int indent) {
		this.tokens = new Vector<String>();
		isOk = true;
		this.indent = indent;
		StringBuffer stringBuffer = new StringBuffer(text.toLowerCase());

		getOutputString(stringBuffer);

		this.tokens = getAllVariables(new StringBuffer(outputString));
		setOutput();
	}

	public boolean isOk() {
		return isOk;
	}

	public void getOutputString(StringBuffer stringBuffer) {
		String text = stringBuffer.toString().trim();
		stringBuffer = new StringBuffer(text);
		if (!stringBuffer.toString().startsWith("print(")) {
			error();
		}
		stringBuffer.delete(0, 6);
		if (stringBuffer.toString().endsWith(");")) {
			stringBuffer.delete(stringBuffer.length() - 2,
					stringBuffer.length());
		} else if (stringBuffer.toString().endsWith(")")) {
			stringBuffer.delete(stringBuffer.length() - 1,
					stringBuffer.length());
		} else
			error();
		outputString = stringBuffer.toString();
	}

	public void setOutput() {
		String s = new String(outputString.replaceAll("and", "&&"));
		String s2 = new String(s.replaceAll("or", "||"));
		outputString = s2;
	}

	public Vector<String> getAllVariables(StringBuffer stringBuffer) {
		Vector<String> variables = new Vector<String>();
		if (stringBuffer.length() == 0)
			isOk = false;
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < stringBuffer.length(); ++i) {
			char c = stringBuffer.charAt(i);
			if (c == '\n' || c == '\"')
				continue;
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
					|| c == ')' || c == '*' || c == '+' || c == '-' || c == '/') {
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
				if (i == stringBuffer.length() - 1) {
					variables.add(builder.toString());
				}
			}
		}
		return variables;
	}

	public void writeLine(CPlusPlusWindow cPlusPlusWindow) {
		StringBuilder builder = new StringBuilder();
		builder.append("cout<<");
		if (isBracketNeeded) {
			builder.append("\"");
		} else {
			builder.append("(");
		}

		builder.append(outputString);

		if (isBracketNeeded) {
			builder.append("\"");
		} else {
			builder.append(")");
		}
		builder.append("<<endl;");
		cPlusPlusWindow.setText(builder.toString(), indent);
	}

	public void error() {
		ErrorHandler.getInstance().setError("Error in PrintLine");
		isOk = false;
	}

	public Vector<String> getIterateVariables() {
		return this.tokens;
	}

	public void setIsBracketNeeded(boolean b) {
		isBracketNeeded = b;
	}
}
