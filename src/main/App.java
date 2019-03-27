package main;

import java.util.ArrayList;
import java.util.List;

import expression.InfixExpression;
import expression.MalformedExpressionException;

public class App {

	public static void main(String[] args) throws MalformedExpressionException {

		List<InfixExpression> infixExpressions = new ArrayList<>();
		// page 339
		infixExpressions.add(InfixExpression.create("a - b + c * d")); // ab-cd*+
		infixExpressions.add(InfixExpression.create("a + c - h/b * r")); // ac+hb/r*-
		infixExpressions.add(InfixExpression.create("a + (c - h) / (b * r)")); // ach-br*/+
		infixExpressions.add(InfixExpression.create("(a + b) * c - (d + e * f/((g/h + i - j) * k))/ r"));
		
		String dashedLine = printDashedLine(110);
		System.out.println(dashedLine);
		System.out.format("%-75s%-50s%n", "Infix", "Postfix");
		System.out.println(dashedLine);
		
		for (InfixExpression expression : infixExpressions) {
			System.out.format("%-75s%-50s%n", expression, expression.toPostfix());
			System.out.println(dashedLine);
		}
	}
	
	private static String printDashedLine(int count) {
		StringBuilder sb = new StringBuilder(count);
		for (int i = 0; i < count; i++) {
			sb.append("=");
		}
		return sb.toString();
	}

}
