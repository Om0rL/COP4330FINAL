package oop.project.cli;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    private final TokenStream tokens;
    Parser(List<Token> tokens) {
        this.tokens = new TokenStream(tokens);
    }

    ParsedCommand parse() throws ParseException {
        if (!match("(")) {
            throw new ParseException("Expected '(' at the start");
        }
        if (!match(Token.Type.IDENTIFIER)) {
            throw new ParseException("Expected an identifier at token " + tokens.index + ", received " + tokens.get(0) + ".");
        }
        var name = tokens.get(-1).value();
        ParsedCommand parsedCommand = new ParsedCommand(name);
        if (!match("[")) {
            throw new ParseException("Expected '[' after command name");
        }
        while (!match("]")) {
            if (match(Token.Type.NUMBER) || match(Token.Type.IDENTIFIER)) {
                // Add the token that was just matched to the parsed command's token list
                parsedCommand.addToken(tokens.get(-1)); // -1 to get the last matched token
                //System.out.println(tokens.get(-1));
            } else {
                throw new ParseException("Expected an identifier or number as an argument");
            }
            if (peek(",")) {
                match(",");
            } else if (!peek("]")){
                throw new ParseException("Expected ',' or ']'");
            }
        }

        return parsedCommand;
    }

    //from blackjack practical
    private boolean peek(Object... objects) {
        for (var i = 0; i < objects.length; i++) {
            if (!tokens.has(i) || !test(objects[i], tokens.get(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean match(Object... objects) {
        var peek = peek(objects);
        if (peek) {
            tokens.advance(objects.length);
        }
        return peek;
    }

    private static boolean test(Object object, Token token) {
        return switch (object) {
            case Token.Type type -> token.type() == type;
            case String value -> token.value().equals(value);
            case List<?> options -> options.stream().anyMatch(o -> test(o, token));
            default -> throw new AssertionError(object);
        };
    }

    private static final class TokenStream {

        private final List<Token> tokens;
        private int index = 0;

        private TokenStream(List<Token> tokens) {
            this.tokens = tokens;
        }

        public boolean has(int offset) {
            return index + offset < tokens.size();
        }

        public Token get(int offset) {
            if (!has(offset)) {
                throw new IllegalArgumentException("Broken lexer invariant.");
            }
            return tokens.get(index + offset);
        }

        public void advance(int tokens) {
            index += tokens;
        }

    }

}
