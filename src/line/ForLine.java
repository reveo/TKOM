package line;

import java.util.Vector;

public class ForLine implements AbstractLine, ComplexLine {

	Vector<String> tokens;

	public ForLine(String text) {
		tokens = new Vector<String>();
		StringBuffer stringBuffer = new StringBuffer(text);
		tokens.add(getVariableToken(stringBuffer));
		System.out.println("TUTAJ TOKEN TO");
		System.out.println(stringBuffer);
		tokens.add(getRangeToken(stringBuffer));
	}

	public Vector<String> getTokens() {
		return tokens;
	}

	public String toString() {
		return null;
	}

	private String getVariableToken(StringBuffer text) {
		StringBuilder variableToken = new StringBuilder();
		for (int i = 0; i < text.length(); ++i) {
			char c = text.charAt(i);
			if (c == ' ') {
				text = text.delete(0, i+1);
				break;
			}
		}

		for (int i = 0; i < text.length(); ++i) {
			char c = text.charAt(i);
			if (c == ' ') {
				text = text.delete(0, i+1);
				return variableToken.toString();
			}
			variableToken.append(c);
		}
		return null;
	}

	private String getRangeToken(StringBuffer text)
	{
		System.out.println(text);
		if(text.toString().startsWith("in range(") ){
			text.delete(0, 9);
			text.delete(text.length()-1, text.length());
			int i = Integer.parseInt(text.toString());
		}
		else System.out.println("JEST NIE OK");
		return null;
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

}
