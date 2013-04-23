package model;

public interface AbstractStack {
	public boolean checkIfVariableExists(String name);
	public void addVariable(String name);
	public boolean wasIf();
	public void setIf(boolean b);
	public void clear();
}
