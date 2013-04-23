package model;

import java.util.HashMap;
import java.util.Map;

public class GlobalStack implements AbstractStack {
	Map<String, Variable> variables;

	private static volatile GlobalStack instance = null;
	private boolean wasIf = false;

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

	public boolean checkIfVariableExists(String name) {
		for (String key : variables.keySet()) {
			if (key.equals(name))
				return true;
		}
		return false;
	}


	public void addVariable(String name) {
		variables.put(new String(name), new Variable(name));
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Map.Entry<String, Variable> entry : variables.entrySet()) {
			builder.append(entry.getKey() + "\n");
		}
		return builder.toString();
	}

	public boolean wasIf() {
		return wasIf;
	}

	public void setIf(boolean b) {
		wasIf = b;
	}

	public void clear() {
		variables = new HashMap<String, Variable>();
	}
}
