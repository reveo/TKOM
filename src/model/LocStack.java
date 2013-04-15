package model;

import java.util.HashMap;
import java.util.Map;

public class LocStack {

	private Map<String, Variable> variables;
	private String iterateVariable;
	
	public LocStack() {
		variables = new HashMap<String, Variable>();
	}

	public boolean checkIfVariableExists(String name) {		
		for(String key : variables.keySet()) {
			if(key.equals(name)) return true;
		}
		return false;
	}

	public void setVariableValue(String value) {

	}

	public void addVariable(String name) {
		variables.put(new String(name), new Variable(name));
	}
	
	public void setIterateVariable(String name) {
		this.iterateVariable = name;
	}
	
	public String getIterateVariable() {
		return this.iterateVariable;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Map.Entry<String, Variable> entry : variables.entrySet()) {
			builder.append(entry.getKey() + "\n");
		}

		return builder.toString();
	}
}
