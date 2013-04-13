package model;

import java.util.HashMap;
import java.util.Map;

public class LocalStack {
	Map<String, Variable> variables;

	public LocalStack() {
		variables = new HashMap<String,Variable>();
	}
}
