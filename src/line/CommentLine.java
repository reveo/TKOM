package line;

import java.util.Vector;

import model.ErrorHandler;

import view.CPlusPlusWindow;

public class CommentLine extends Token implements AbstractLine {

	
	public CommentLine(String text, int indent) {
		tokens = new Vector<String>();
		text = text.substring(1);
		tokens.add(text);
		this.indent = indent;
		isOk = true;
	}
	public String getNextToken() {
		return tokens.elementAt(0);
	}
	
	public boolean isOk() {
		return isOk;
	}
	
	public void writeLine(CPlusPlusWindow cPlusPlusWindow) {
		StringBuilder builder = new StringBuilder();
		builder.append("//" + tokens.firstElement());
		cPlusPlusWindow.setText(builder.toString(), indent);
	}
	
	public void error() {
		ErrorHandler.getInstance().setError("Error in comment");
		this.isOk = false;
	}
}
