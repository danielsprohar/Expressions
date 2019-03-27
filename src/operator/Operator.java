package operator;

import java.util.Objects;

public enum Operator {
	LEFT_PARENTHESIS("("), RIGHT_PARENTHESIS(")"), EXPONENT("^"), MULTIPLY("*"), DIVIDE("/"), ADD("+"), SUBTRACT("-");

	private final String _value;

	private Operator(String value) {
		_value = value;
	}

	public boolean equals(String value) {
		return _value.equals(value);
	}

	public static Operator create(char c) {
		return create(String.valueOf(c));
	}

	/**
	 * Creates an instance of type <code>Operator</code> if the given string value
	 * matches a valid operator, otherwise null
	 * 
	 * @param value (non-null)
	 * @return
	 */
	public static Operator create(String value) {
		Objects.requireNonNull(value);

		switch (value) {
		case "^":
			return Operator.EXPONENT;
		case "*":
			return Operator.MULTIPLY;
		case "/":
			return Operator.DIVIDE;
		case "+":
			return Operator.ADD;
		case "-":
			return Operator.SUBTRACT;
		case "(":
			return Operator.LEFT_PARENTHESIS;
		case ")":
			return Operator.RIGHT_PARENTHESIS;
		default:
			return null;
		}
	}

	@Override
	public String toString() {
		return _value;
	}
}
