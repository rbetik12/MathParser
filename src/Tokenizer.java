import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {

    private enum Operator {
        ADD(1), SUBTRACT(1), MULTIPLY(2), DIVIDE(2);
        final int precedence;

        Operator(int p) {
            precedence = p;
        }
    }

    static Map<String, Operator> ops = new HashMap<>() {{
        put("+", Operator.ADD);
        put("-", Operator.SUBTRACT);
        put("*", Operator.MULTIPLY);
        put("/", Operator.DIVIDE);
    }};

    private static boolean isHigerPrec(String op, String sub) {
        return (ops.containsKey(sub) && ops.get(sub).precedence >= ops.get(op).precedence);
    }

    private static String[] buildPostfix(ArrayList<String> tokens) {
        StringBuilder output = new StringBuilder();
        Deque<String> stack = new LinkedList<>();

        for (String token : tokens) {
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

    private static ArrayList<String> buildTokensList(String expr) {
        expr = expr.replaceAll("\\s+", "");
        String[] literals = expr.split("");
//        System.out.println("Literals: " + Arrays.toString(literals));
        StringBuilder numberBuffer = new StringBuilder();
        ArrayList<String> tokens = new ArrayList<>();

        for (int i = 0; i < literals.length; i++) {
            if (i == 0 && literals[i].equals("-")) {
                numberBuffer.append("-");
            }
            else if (literals[i].equals("-") && Pattern.matches("[+*/()]", literals[i-1])) {
                numberBuffer.append("-");
            }
            else if (Pattern.matches("[0-9]", literals[i])) {
                numberBuffer.append(literals[i]);
            }
            else if (Pattern.matches("[-+*/()]", literals[i])) {
                if (numberBuffer.length() > 0) {
                    tokens.add(numberBuffer.toString());
                    numberBuffer.delete(0, numberBuffer.length());
                }
                tokens.add(literals[i]);
            }
            else if (Pattern.matches("[a-z]", literals[i])) {
                tokens.add(literals[i]);
            }
            if (i == literals.length - 1) {
                if (numberBuffer.length() > 0) {
                    tokens.add(numberBuffer.toString());
                    numberBuffer.delete(0, numberBuffer.length());
                }
            }
        }

        return tokens;
    }

    private static String replaceVars(Map<String, String> varsMap, String expr) {
        for (Map.Entry<String, String> var : varsMap.entrySet()) {
            expr = expr.replaceAll(var.getKey(), var.getValue());
        }
        return expr;
    }

    static String[] tokenize(String expr, Map<String, String> varsMap) {
        validate(expr);
        expr = replaceVars(varsMap, expr);
        return buildPostfix(buildTokensList(expr));
    }

    private static void validate(String expr) {
        Matcher matcher = Pattern.compile("[^-+/*a-z0-9\\s()]").matcher(expr);
        if (matcher.find()) {
            throw new Error("That expression contains inappropriate symbols: " + matcher.group());
        }
    }

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        Map<String, String> vars = new HashMap<>();
        vars.put("a", "30");
        System.out.println(Arrays.toString(Tokenizer.tokenize("2 * (30 + a) /    120", vars)));
        System.out.println(Arrays.toString(Tokenizer.tokenize("-5 * (3 - 2)", vars)));
        System.out.println(Arrays.toString(Tokenizer.tokenize("-5 * (3 - 2) + (-10*20) + a", vars)));
    }
}
