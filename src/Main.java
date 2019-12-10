import parser.lexer.Lexer;
import parser.parser.Parser;
import terms.LambdaTerm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder input = new StringBuilder();
        String buff;
        while ((buff = in.readLine()) != null){
            input.append(buff);
            input.append('\n');
        }
        Lexer lexer = new Lexer(input.toString());
        Parser parser = new Parser(lexer);
        LambdaTerm lambdaTerm = parser.parse();
        System.out.println(lambdaTerm);
    }
}
