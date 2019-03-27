package expression;

public class MalformedExpressionException extends Exception {

	// to quiet the compiler
	private static final long serialVersionUID = 1L;

	public MalformedExpressionException(String errorMessage) {
		super(errorMessage);
	}

}
