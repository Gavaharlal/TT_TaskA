package parser.parser;

import parser.lexer.Lexer;
import parser.lexer.Token;
import parser.lexer.TokenType;
import terms.Abstraction;
import terms.Application;
import terms.LambdaTerm;
import terms.Variable;

public class Parser {
    private final Lexer lexer;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public LambdaTerm parse() {
        return expression();
    }

    private LambdaTerm expression() {
        Token token = lexer.current();
        LambdaTerm result;
        switch (token.getTokenType()) {
            case LEFT_BRACKET:
            case VARIABLE:
                //E -> A B
                LambdaTerm leftSon = application();
                LambdaTerm rightSon = b();
                if (rightSon == null) {
                    result = leftSon;
                } else {
                    result = new Application(leftSon, rightSon);
                }
                break;
            case BACKSLASH:
                // E -> \ V . E
                lexer.next();
                Variable v = variable();
                lexer.next();
                LambdaTerm e = expression();
                result = new Abstraction(v, e);
                break;
            default:
                throw new Error();
        }
        return result;
    }

    private Variable variable() {
        Token token = lexer.current();
        Variable result;
        if (token.getTokenType() == TokenType.VARIABLE) {
            lexer.next();
            result = new Variable(token.toString());
        } else {
            throw new Error();
        }
        return result;
    }

    private LambdaTerm b() {
        Token token = lexer.current();
        LambdaTerm result;
        switch (token.getTokenType()) {
            case BACKSLASH:
                // B -> \ V . E
                lexer.next();
                Variable v = variable();
                lexer.next();
                LambdaTerm e = expression();
                result = new Abstraction(v, e);
                break;
            case RIGHT_BRACKET:
            case END:
                // B -> eps
                result = null;
                break;
            default:
                throw new Error();

        }
        return result;
    }

    private LambdaTerm application() {
        Token token = lexer.current();
        LambdaTerm result;
        switch (token.getTokenType()) {
            case LEFT_BRACKET:
            case VARIABLE:
                // A -> P A'
                LambdaTerm p = primary();
                result = aTemporary(p);
                break;
            default:
                throw new Error();
        }
        return result;
    }

    private LambdaTerm aTemporary(LambdaTerm acc) {
        Token token = lexer.current();
        LambdaTerm result;
        switch (token.getTokenType()) {
            case LEFT_BRACKET:
            case VARIABLE:
                // A' -> P A'
                LambdaTerm p = primary();
                LambdaTerm res = aTemporary(new Application(acc, p));
                result = res;
                break;
            case RIGHT_BRACKET:
            case BACKSLASH:
            case END:
                // A' -> eps
                result = acc;
                break;
            default:
                throw new Error();

        }
        return result;
    }

    private LambdaTerm primary() {
        Token token = lexer.current();
        LambdaTerm result;
        switch (token.getTokenType()) {
            case LEFT_BRACKET:
                // P -> ( E )
                lexer.next();
                LambdaTerm e = expression();
                lexer.next();
                result = e;
                break;

            case VARIABLE:
                // P -> V
                result = variable();
                break;

            default:
                throw new Error();
        }
        return result;
    }
}
