package line;

import model.ErrorHandler;
import view.CPlusPlusWindow;

public class ElseLine extends Token implements AbstractLine {

	public ElseLine(String text, int indent) {
		isOk = true;
		this.indent = indent;
	}

	public boolean isOk() {
		return isOk;
	}

	public void writeLine(CPlusPlusWindow cPlusPlusWindow) {
		StringBuilder builder = new StringBuilder();
		builder.append("else { ");
		cPlusPlusWindow.setText(builder.toString(), this.indent);
	}

	
	public void error() {
		ErrorHandler.getInstance().setError("Error in ElseLine");
		isOk = false;

	}

}
