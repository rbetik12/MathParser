import java.util.HashMap;
import java.util.Map;


public class Parser {

    public static int parse(String expression, Map<String, String> vars) {
        return RPNSolver.parse(expression, vars);
    }

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        Map<String, String> vars = new HashMap<>();
        vars.put("a", "30");
        System.out.println(parse("2 + 3", vars));
        System.out.println(parse("2 + 3 - 10", vars));
        System.out.println(parse("2 * 3 - 10", vars));
        System.out.println(parse("9 / 3 - 10", vars));
        System.out.println(parse("9 / 3 - a", vars));
        System.out.println(parse("9 / 3 - a * 10", vars));
    }
}
