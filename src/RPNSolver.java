import java.util.*;
import java.util.regex.Pattern;

public class RPNSolver {
    public static int solve(String[] tokens) {
        Deque<String> stack = new LinkedList<>();
        for (String token : tokens) {
            if (Pattern.matches("-?[0-9]{1,10}", token)) {
                stack.push(token);
            } else if (Tokenizer.ops.containsKey(token)) {
                int number1 = Integer.parseInt(stack.pop());
                int number2 = Integer.parseInt(stack.pop());
                int result = 0;
                switch (token) {
                    case "+":
                        result = number2 + number1;
                        break;
                    case "-":
                        result = number2 - number1;
                        break;
                    case "*":
                        result = number2 * number1;
                        break;
                    case "/":
                        result = number2 / number1;
                        break;
                }
                stack.push(String.valueOf(result));
            }
        }
        return Integer.parseInt(stack.pop());
    }

    static int parse(String expr, Map<String, String> vars) {
        return solve(Tokenizer.tokenize(expr, vars));
    }
}

