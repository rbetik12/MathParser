import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void test0() {
        Map<String, String> vars = new HashMap<>();
        vars.put("x", "20");
        vars.put("y", "30");
        vars.put("z", "30");

        String expression = "(x + y) / z";
        int expected = 1;
        int actual = Parser.parse(expression, vars);

        printResult(expression, actual, expected);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test1() {
        Map<String, String> vars = new HashMap<>();
        vars.put("x", "20");
        vars.put("y", "30");
        vars.put("z", "30");

        String expression = "(x + y) / (z * -x)";
        int expected = 0;
        int actual = Parser.parse(expression, vars);

        printResult(expression, actual, expected);
        Assert.assertEquals(expected, actual);
    }

    private void printResult(String expression, int actual, int expected) {
        System.out.println("Tested: " + expression);
        System.out.println("Got: " + actual);
        System.out.println("Expected: " + expected);
    }
}