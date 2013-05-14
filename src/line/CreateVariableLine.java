package line;

import java.util.Vector;

import model.ErrorHandler;

import view.CPlusPlusWindow;

public class CreateVariableLine extends Token implements AbstractLine {

	public CreateVariableLine(String text, int indent) {
		tokens = new Vector<String>();
		StringBuffer stringBuffer = new StringBuffer(text);
		isOk = true;
		getVariableName(stringBuffer);
		getValue(stringBuffer);
		this.indent = indent;

	}

	public void getVariableName(StringBuffer text) {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < text.length(); ++i) {
			char c = text.charAt(i);
			if (c == '=') {
				text = text.delete(0, i + 1);
				String newVar = builder.toString().trim();
				if (newVar.equals("if") || newVar.equals("in")
						|| newVar == "for" || newVar == "else"
						|| newVar == "elif" || newVar == "print"
						|| newVar == "while")
					error();
				tokens.add(newVar);
			}
			builder.append(c);
		}

	}

	public void getValue(StringBuffer text) {
		if (text.toString().endsWith(";"))
			text.delete(text.length() - 1, text.length());
		StringBuilder builder = new StringBuilder(text);
		String value = builder.toString().trim().replaceAll("\'", "\"");
		tokens.add(value);
	}

	public boolean isOk() {
		return isOk;
	}

	public void writeLine(CPlusPlusWindow cPlusPlusWindow) {
		StringBuilder builder = new StringBuilder();
		builder.append("Variable " + tokens.firstElement() + "("
				+ tokens.lastElement() + ");");
		cPlusPlusWindow.setText(builder.toString(), indent);
	}

	public void error() {
		ErrorHandler.getInstance().setError("Error in CreateVariableLine");
		isOk = false;
	}
}
