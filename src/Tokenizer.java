import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

class Token {
    String type;
    String value;

    public Token(String type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return type + "(" + value + ")";
    }
}

public class Tokenizer {

    private static StringBuilder numberBuffer = new StringBuilder();

    private static ArrayList<Token> tokenize(String expr) {
        expr = expr.replaceAll("\\s+", "");
        String[] literals = expr.split("");
        System.out.println("Literals: " + Arrays.toString(literals));
        ArrayList<Token> tokens = new ArrayList<>();
        int i = 0;
        while (i < literals.length) {
            if (Pattern.matches("[0-9]", literals[i])) {
                numberBuffer.append(literals[i]);
            }
            else if (Pattern.matches("[-+*/]", literals[i])) {
                addNumberToken(tokens);
                tokens.add(new Token("Operator", literals[i]));
            }
            else if (Pattern.matches("[a-z]", literals[i])) {
                tokens.add(new Token("Variable", literals[i]));
            }
            if (i == literals.length - 1) {
                addNumberToken(tokens);
            }
            i++;
        }
        return tokens;
    }

    private static void addNumberToken(ArrayList<Token> tokens) {
        if (numberBuffer.length() > 0) {
            tokens.add(new Token("Number", numberBuffer.toString()));
            numberBuffer.delete(0, numberBuffer.length());
        }
    }

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        System.out.println(Tokenizer.tokenize("a500+200"));
        System.out.println(Tokenizer.tokenize("2 + 3 + 10 + 500 + 10"));
        System.out.println(Tokenizer.tokenize("2 * 5 / 6"));
    }
}