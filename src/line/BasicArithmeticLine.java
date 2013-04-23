package line;

import model.ErrorHandler;
import view.CPlusPlusWindow;

public class BasicArithmeticLine extends Token implements AbstractLine {
	String outputString;

	public BasicArithmeticLine(String name, int indent) {
		outputString = name;
		isOk = true;
		this.indent = indent;

		setOutput();
	}

	public boolean isOk() {
		return isOk;
	}

	private void setOutput() {
		if (outputString.endsWith(";")) {
			StringBuffer buffer = new StringBuffer(outputString);
			buffer.delete(buffer.length() - 1, buffer.length());
			outputString = buffer.toString();
		}
	}

	public void writeLine(CPlusPlusWindow cPlusPlusWindow) {
		StringBuilder builder = new StringBuilder();
		builder.append("cout<<(" + outputString + ")<<endl;");
		cPlusPlusWindow.setText(builder.toString(), indent);
	}

	public void error() {
		ErrorHandler.getInstance().setError("Error in BasicArithmeticLine");
		isOk = false;
	}

}
