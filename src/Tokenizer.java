import java.util.*;

public class Tokenizer {

    private enum Operator {
        ADD(1), SUBTRACT(1), MULTIPLY(2), DIVIDE(2);
        final int precedence;

        Operator(int p) {
            precedence = p;
        }
    }

    public static Map<String, Operator> ops = new HashMap<>() {{
        put("+", Operator.ADD);
        put("-", Operator.SUBTRACT);
        put("*", Operator.MULTIPLY);
        put("/", Operator.DIVIDE);
    }};

    private static boolean isHigerPrec(String op, String sub) {
        return (ops.containsKey(sub) && ops.get(sub).precedence >= ops.get(op).precedence);
    }

    public static String[] postfix(String infix) {
        StringBuilder output = new StringBuilder();
        Deque<String> stack = new LinkedList<>();

        for (String token : infix.split("\\s")) {
            if (ops.containsKey(token)) {
                while (!stack.isEmpty() && isHigerPrec(token, stack.peek()))
                    output.append(stack.pop()).append(' ');
                stack.push(token);
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.peek().equals("("))
                    output.append(stack.pop()).append(' ');
                stack.pop();
            } else {
                output.append(token).append(' ');
            }
        }

        while (!stack.isEmpty())
            output.append(stack.pop()).append(' ');

        return output.toString().split(" ");
    }

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        System.out.println(Arrays.toString(postfix("2 * ( 3 - 2 )")));
        System.out.println(Arrays.toString(postfix("- 5 * ( 3 - 2 )")));
    }
}
