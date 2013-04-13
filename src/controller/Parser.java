package controller;

public class Parser {
	public Parser() {

	}

	public void parseText(String text) {
		if (text.startsWith("#")) {
			System.out.println("KOMENTARZ");
			//Create new CommentLine object and return;
		}
		

	}

	public String getNextToken(String text) {
		StringBuilder token = new StringBuilder();
		for (int i = 0; i < text.length(); i++){
		    char c = text.charAt(i);        
		    if(c == ' ')
		    	return token.toString();
		    token.append(c);
		}
		return token.toString();
	}
}
