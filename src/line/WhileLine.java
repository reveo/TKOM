package line;

import java.util.Vector;

import model.ErrorHandler;

import view.CPlusPlusWindow;

public class WhileLine extends Token implements ComplexLine {

	public WhileLine(String text, int indent) {
		tokens = new Vector<String>();
		StringBuffer stringBuffer = new StringBuffer(text.toLowerCase());
		isOk = true;
		this.indent = indent;

		getCondition(stringBuffer);
	}

	public void getCondition(StringBuffer stringBuffer) {
		if (!stringBuffer.toString().startsWith("while")) {
			error();
			return;
		}
		stringBuffer.delete(0, 5);
		stringBuffer = new StringBuffer(stringBuffer.toString().trim());
		if(!stringBuffer.toString().startsWith("(")) {
			error();
			return;
		}
		if(!stringBuffer.toString().endsWith("):")) {
			error();
			return;
		}
		
		stringBuffer.delete(0,1);
		stringBuffer.delete(stringBuffer.length()-2, stringBuffer.length());
		System.out.println(stringBuffer.toString());
	}


	public boolean isOk() {
		return isOk;
	}

	
	public void writeLine(CPlusPlusWindow cPlusPlusWindow) {
		

	}

	public void error() {
		ErrorHandler.getInstance().setError("error in  \"while\" loop");
		isOk = false;
	}

	public String getIterateVariable() {
		return null;
	}

}
