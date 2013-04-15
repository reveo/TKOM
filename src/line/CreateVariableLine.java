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
				System.out.println("ZMIENNA TO " + text);
				tokens.add(builder.toString().trim());
			}
			builder.append(c);
		}
	}

	public void getValue(StringBuffer text) {
		if (text.toString().endsWith(";"))
			text.delete(text.length() - 1, text.length());
		StringBuilder builder = new StringBuilder(text);
		tokens.add(builder.toString().trim());
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
		ErrorHandler.getInstance().setError("Error in CreateVariable");
	}
}
