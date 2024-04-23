package oop.project.cli;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class ParsedCommand {
    // Field for the command name
    private String commandName;
    // List of tokens associated with the command
    private List<Token> tokens;

    // Constructor
    public ParsedCommand(String commandName) {
        this.commandName = commandName;
        this.tokens = new ArrayList<>();
    }

    public ParsedCommand(String commandName, List<Token> tokens) {
        this.commandName = commandName;
        this.tokens = tokens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ParsedCommand that = (ParsedCommand) o;
        return Objects.equals(commandName, that.commandName) &&
                Objects.equals(tokens, that.tokens);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commandName, tokens);
    }

    // Getter for the command name
    public String getCommandName() {
        return commandName;
    }

    // Setter for the command name
    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    // Getter for the tokens list
    public List<Token> getTokens() {
        return tokens;
    }

    // Setter for the tokens list
    private void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    // Method to add a token to the list of tokens
    void addToken(Token token) {
        this.tokens.add(token);
    }
}
