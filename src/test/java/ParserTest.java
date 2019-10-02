import com.google.common.collect.ImmutableMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class ParserTest {

    private Random random = new Random();

    @Test
    public void test0() {
        System.out.println("========================================================TEST0========================================================");
        String expression = "(x + y) / z";
        testEquation(expression, ImmutableMap.of("x", 20, "y", 30, "z", 30), 1);
    }

    @Test
    public void test1() {
        System.out.println("========================================================TEST1========================================================");
        String expression = "(x + y) / (z * -x)";
        testEquation(expression, ImmutableMap.of("x", "20", "y", "30", "z", "30"), 0);
    }

    @Test
    public void test2() {
        System.out.println("========================================================TEST2========================================================");
        String expression = "x + y / z";
        testEquation(expression, ImmutableMap.of("x", "1", "y", "2", "z", "3"), 1);
    }

    @Test
    public void test3() throws Exception {
        System.out.println("========================================================TEST3========================================================");
        testEquation("x+y/z",
                ImmutableMap.of("x", "1", "y", "2", "z", "x"),
                3);
    }

    @Test
    public void test4() throws Exception {
        System.out.println("========================================================TEST4========================================================");
        testEquation("(f + k)*(h - g)/f",
                ImmutableMap.of("f", 61, "k", 32, "h", 354, "g", 19),
                510);
    }

    @Test
    public void test5() throws Exception {
        System.out.println("========================================================TEST5========================================================");
        testEquation("a/b/c/d",
                ImmutableMap.of("a", 89411, "b", 32, "c", 7, "d", 5),
                79);
    }

    @Test
    public void test6() throws Exception {
        System.out.println("========================================================TEST6========================================================");
        final int a = randomInt();
        final int b = randomInt();
        final int c = randomInt();
        testEquation("a+b/c",
                ImmutableMap.of("a", a, "b", b, "c", c),
                a + b / c);
    }

    @Test
    public void test7() throws Exception {
        System.out.println("========================================================TEST7========================================================");
        final int a = randomInt();
        final int b = randomInt();
        final int c = randomInt();
        testEquation("(c*(a-b)/b)*a",
                ImmutableMap.of("a", a, "b", b, "c", c),
                (c * (a - b) / b) * a);
    }

    @Test
    public void test8() throws Exception {
        System.out.println("========================================================TEST8========================================================");
        final int a = randomInt();
        final int b = randomInt();
        final Arg<Integer> c = randomChoose(arg("a", a), arg("b", b));
        testEquation("(c*(a-b)/b)*a",
                ImmutableMap.of("a", a, "b", b, "c", c.name),
                (c.val * (a - b) / b) * a);
    }

    private void testEquation(final String equation, final ImmutableMap<String, Object> params, final int expected) {
        int result = Parser.parse(equation, params);

        printResult(equation, result, expected);
        Assert.assertEquals(Parser.parse(equation, params), expected);
    }

    private void printResult(String expression, int actual, int expected) {
        System.out.println("Tested: " + expression);
        System.out.println("Got: " + actual);
        System.out.println("Expected: " + expected);
    }

    private int randomInt() {
        return random.nextInt(100) + 1;
    }

    @SafeVarargs
    private final <T> T randomChoose(T... values) {
        return values[random.nextInt(values.length)];
    }

    private static Arg<Integer> arg(final String name, final Integer val){
        return new Arg<Integer>(name, val);
    }

    private static class Arg<T> {
        private final String name;
        private final T val;

        private Arg(final String name, final T val) {
            this.name = name;
            this.val = val;
        }
    }
}