import java.util.*;
import java.util.regex.Pattern;

public class RPNSolver {
    public static int solve(String[] tokens) throws Exception {
        Deque<String> stack = new LinkedList<>();
        System.out.println(Arrays.toString(tokens));
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

    public static void main(String[] args) throws Exception {
        Map<String, String> vars = new HashMap<>();
        vars.put("a", "30");
        System.out.println(RPNSolver.solve(Tokenizer.tokenize("2 * (30 + a) / 100 * a", vars)));
        System.out.println(RPNSolver.solve(Tokenizer.tokenize("3 + 4 * 2 / (1 - 5)", vars)));
    }
}

