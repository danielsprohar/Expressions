package expression;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import operator.Operator;
import operator.OperatorTable;

public class InfixExpression {

	private final String _infixExpression;

	private InfixExpression(String infixExpression) {
		_infixExpression = infixExpression;
	}

	public String getExpression() {
		return _infixExpression;
	}

	public static InfixExpression create(String expression) throws MalformedExpressionException {
		return new InfixExpression(validate(Objects.requireNonNull(expression)));
	}

	private static String validate(String expression) throws MalformedExpressionException {
		// only alphabet characters
		// operands.count > operators.count
		// leftParenthesisCount == rightParenthesisCount
		List<Operator> operators = new LinkedList<>();
		List<Character> operands = new LinkedList<>();
		int leftParenthesisCount = 0, rightParenthesisCount = 0;
		int length = expression.length();
		char currentCharacter;

		for (int i = 0; i < length; i++) {
			currentCharacter = expression.charAt(i);
			if (Character.isWhitespace(currentCharacter)) {
				continue;
			}
			if (Character.isDigit(currentCharacter)) {
				throw new MalformedExpressionException("expression contains a number");
			}

			Operator operator = Operator.create(currentCharacter);
			if (operator == null) {
				// current character is not an operator, must be an operand
				operands.add(currentCharacter);
				continue;
			}

			// current character is an operator, but what kind?
			if (operator.equals(Operator.LEFT_PARENTHESIS)) {
				leftParenthesisCount++;
			} else if (operator.equals(Operator.RIGHT_PARENTHESIS)) {
				rightParenthesisCount++;
			} else {
				// operator is not a parenthesis
				operators.add(operator);
			}
		}

		if (operators.size() >= operands.size()) {
			throw new MalformedExpressionException(
					"expression contains an equal or greater amount of operators than operands");
		}
		if (leftParenthesisCount != rightParenthesisCount) {
			if (leftParenthesisCount > rightParenthesisCount) {
				throw new MalformedExpressionException("there are more left parenthesis than right parenthesis");
			}
			throw new MalformedExpressionException("there are more right parenthesis than left parenthesis");
		}

		return expression;
	}

	public String toPostfix() {
		// setup
		Deque<Operator> stack = new ArrayDeque<>();
		OperatorTable operatorTable = OperatorTable.getInstance();
		String infix = _infixExpression;
		infix = infix.replaceAll(" ", "");
		final int length = infix.length();
		StringBuilder postfixBuilder = new StringBuilder(length);
		String operand;
		int beginIndex = 0, endIndex, i;
		char c;

		for (i = 0; i < length; i++) {
			c = infix.charAt(i);
			Operator operator = Operator.create(c);
			if (operator == null) {
				continue;
			}

			// found operator index, now extract the operand
			endIndex = i;
			operand = infix.substring(beginIndex, endIndex);
			beginIndex = endIndex + 1;
			postfixBuilder.append(operand);

			if (operator.equals(Operator.LEFT_PARENTHESIS)) {
				stack.push(operator);
			} else if (operator.equals(Operator.RIGHT_PARENTHESIS)) {
				while (!stack.peek().equals(Operator.LEFT_PARENTHESIS)) {
					postfixBuilder.append(stack.pop().toString());
				}
				stack.pop(); // dispose of left parenthesis
			} else {
				// operator is not a parenthesis, evaluate precedence
				// evaluate current operator
				int currentPrecedence = operatorTable.getPrecedence(operator);
				int topPrecedence = stack.isEmpty() ? -1 : operatorTable.getPrecedence(stack.peek());

				if (stack.isEmpty() || currentPrecedence > topPrecedence) {
					stack.push(operator);
				} else {
					postfixBuilder.append(stack.pop().toString());
					stack.push(operator);
				}
			}
		}

		// end of infix string, extract the last operand
		operand = infix.substring(beginIndex, i);
		postfixBuilder.append(operand);

		while (!stack.isEmpty()) {
			postfixBuilder.append(stack.pop().toString());
		}

		return postfixBuilder.toString();
	}

	@Override
	public String toString() {
		return _infixExpression;
	}

}
