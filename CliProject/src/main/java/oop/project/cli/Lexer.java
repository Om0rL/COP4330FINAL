package oop.project.cli;

import java.util.ArrayList;
import java.util.List;


public class Lexer {
    private final CharStream chars;

    Lexer(String input) {
        chars = new CharStream(input);
    }

    //we want the format to be (commandname[argument1,argument2,...])

    List<Token> lex() {
        List<Token> tokens = new ArrayList<>();
        while (chars.has(0)) { // while there are characters left to parse
            char current = chars.get(0);
            if (peek("[0-9]") || peek("[+\\-]", "[0-9]")) {
                tokens.add(lexNumber());
            } else if (Character.isLetter(current)) {
                tokens.add(lexIdentifier());
            } else if (peek("[\\[\\]\\(\\),]")) {
                tokens.add(lexOperator());
            } else if (Character.isWhitespace(current)) {
                chars.advance(1); // skip whitespace
            } else {
                throw new RuntimeException("Unexpected character: " + current);
            }
        }
        return tokens;
    }

    private Token lexNumber() {
        match("[+\\-]");
        while (match("[0-9]")) {}
        if (match('.', "[0-9]")) {
            while (match("[0-9]")) {}
        } else if (!peek("[,\\]]")){
            return lexIdentifier();
        }
        return chars.emit(Token.Type.NUMBER);
    }

    private Token lexOperator() {
        char operator = chars.get(0);
        chars.advance(1);
        return chars.emit(Token.Type.OPERATOR);
    }


    private Token lexIdentifier() {
        while (match("[A-Za-z0-9_+\\-*/<>=.:!?]")) {}
        return chars.emit(Token.Type.IDENTIFIER);
    }

    //these are from blackjack practical
    private boolean peek(Object... objects) {
        for (var i = 0; i < objects.length; i++) {
            if (!chars.has(i) || !test(objects[i], chars.get(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean match(Object... objects) {
        var peek = peek(objects);
        if (peek) {
            chars.advance(objects.length);
        }
        return peek;
    }

    private static boolean test(Object object, char character) {
        return switch (object) {
            case Character c -> character == c;
            case String regex -> Character.toString(character).matches(regex);
            case List<?> options -> options.stream().anyMatch(o -> test(o, character));
            default -> throw new AssertionError(object);
        };
    }

    private static final class CharStream {

        private final String input;
        private int index = 0;
        private int length = 0;

        private CharStream(String input) {
            this.input = input;
        }

        public boolean has(int offset) {
            return index + length + offset < input.length();
        }

        public char get(int offset) {
            if (!has(offset)) {
                throw new IllegalArgumentException("Broken lexer invariant.");
            }
            return input.charAt(index + length + offset);
        }

        public void advance(int chars) {
            length += chars;
        }

        public Token emit(Token.Type type) {
            var token = new Token(type, input.substring(index, index + length));
            index += length;
            length = 0;
            return token;
        }

    }

}
