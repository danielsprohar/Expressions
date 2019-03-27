package operator;

import java.util.HashMap;
import java.util.Map;

/**
 * Maps an operator to precedence value, represented by an integer value.
 *
 */
public final class OperatorTable {

	private final Map<Operator, Integer> _operatorTable = new HashMap<>();
	private static OperatorTable _instance = new OperatorTable();

	private OperatorTable() {
		_operatorTable.put(Operator.LEFT_PARENTHESIS, 0);
		_operatorTable.put(Operator.ADD, 1);
		_operatorTable.put(Operator.SUBTRACT, 1);
		_operatorTable.put(Operator.MULTIPLY, 2);
		_operatorTable.put(Operator.DIVIDE, 2);
		_operatorTable.put(Operator.EXPONENT, 3);
		_operatorTable.put(Operator.RIGHT_PARENTHESIS, 4);
	}

	public static OperatorTable getInstance() {
		return _instance;
	}

	public int getPrecedence(Operator op) {
		return _operatorTable.get(op);
	}
}
