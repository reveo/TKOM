package model;

import java.util.HashMap;
import java.util.Map;

public class GlobalStack {
	Map<String, Variable> variables;

	private static volatile GlobalStack instance = null;

	public static GlobalStack getInstance() {
		if (instance == null) {
			synchronized (GlobalStack.class) {
				if (instance == null)
					instance = new GlobalStack();
			}
		}
		return instance;
	}

	private GlobalStack() {
		variables = new HashMap<String, Variable>();
	};

	public boolean checkIfVariableExists(String s) {
		for (String key : variables.keySet()) {
			if (key.equals(s))
				return true;
		}
		return false;
	}
	
	public void addVariable(String name, Variable.DataType dataType) {
		variables.put(name, new Variable(name));
	}
}
