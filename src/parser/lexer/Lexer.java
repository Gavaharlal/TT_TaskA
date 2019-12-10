package parser.lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String input;
    private List<Token> tokens = new ArrayList<>();
    private int curPos = 0;


    public Lexer(String input) {
        this.input = input;
        tokenize();
        tokens.add(new Token(TokenType.END, "END"));
    }

    public boolean hasNext() {
        return curPos < tokens.size() - 1;
    }

    public Token current() {
        return tokens.get(curPos);
    }

    public void next() {
        ++curPos;
    }

    private void tokenize() {
        int i = -1;
        while (++i < input.length()) {
            if (Character.isWhitespace(input.charAt(i))) continue;
            switch (input.charAt(i)) {
                case '(':
                    tokens.add(new Token(TokenType.LEFT_BRACKET, "("));
                    break;
                case ')':
                    tokens.add(new Token(TokenType.RIGHT_BRACKET, ")"));
                    break;
                case '.':
                    tokens.add(new Token(TokenType.DOT, "."));
                    break;
                case '\\':
                    tokens.add(new Token(TokenType.BACKSLASH, "\\"));
                    break;
                default:
                    int left = i;
                    while (i < input.length() && isVariablePart(input.charAt(i))) i++;
                    String variableName = input.substring(left, i);
                    i--;
                    tokens.add(new Token(TokenType.VARIABLE, variableName));
            }
        }
    }

    private boolean isVariablePart(char c) {
        if ('a' <= c && c <= 'z') return true;
        if ('0' <= c && c <= '9') return true;
        if (c == '’') return true;
        return c == '\'';
    }
}
