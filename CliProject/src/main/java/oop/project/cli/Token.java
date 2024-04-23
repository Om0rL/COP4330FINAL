package oop.project.cli;

public record Token(
        Type type,
        String value
) {

    public enum Type {
        NUMBER,
        IDENTIFIER,
        OPERATOR,
    }

}
