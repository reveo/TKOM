package line;

import java.util.Vector;

public class CommentLine implements AbstractLine {

	public Vector<String> tokens;
	
	public CommentLine(String text) {
		tokens = new Vector<String>();
		text = text.substring(1);
		tokens.add(text);
	}
	public String getNextToken() {
		return tokens.elementAt(0);
	}
	
	public Vector<String> getTokens() {
		return tokens;
	}
}