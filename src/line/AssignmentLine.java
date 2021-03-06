package line;

import java.util.Vector;

import model.ErrorHandler;

import view.CPlusPlusWindow;

public class AssignmentLine extends Token implements AbstractLine {

	public AssignmentLine(String text, int indent) {
		tokens = new Vector<String>();
		StringBuffer stringBuffer = new StringBuffer(text);
		isOk = true;
		this.indent = indent;
		getVariableName(stringBuffer);
		getValueName(stringBuffer);
	}

	public void getVariableName(StringBuffer text) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < text.length(); ++i) {
			char c = text.charAt(i);
			if (c == '=') {
				text = text.delete(0, i + 1);
				tokens.add(builder.toString().trim());
				return;
			}
			builder.append(c);
		}
	}

	public void getValueName(StringBuffer text) {
		if (text.toString().endsWith(";"))
			text.delete(text.length() - 1, text.length());
		StringBuilder builder = new StringBuilder(text);
		String value = builder.toString().trim().replaceAll("\'", "\"");
		tokens.add(value);
	}


	public boolean isOk() {
		return true;
	}

	public void writeLine(CPlusPlusWindow cPlusPlusWindow) {
		StringBuilder builder = new StringBuilder();
		builder.append(tokens.firstElement() + ".setValue("
				+ tokens.lastElement() + ");");
		cPlusPlusWindow.setText(builder.toString(), indent);
	}
	
	public void error() {
		ErrorHandler.getInstance().setError("Error in AssignmentLine");
	}
}
