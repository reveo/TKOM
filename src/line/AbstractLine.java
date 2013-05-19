package line;

import view.CPlusPlusWindow;

public interface AbstractLine {	
	String toString();		
	public boolean isOk();
	public void writeLine(CPlusPlusWindow cPlusPlusWindow);
	public void error();	
}
