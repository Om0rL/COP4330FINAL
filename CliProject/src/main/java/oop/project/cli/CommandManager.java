package oop.project.cli;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandManager {
    private Map<String, Command> commands;

    public CommandManager() {
        this.commands = new HashMap<>();
    }

    // Method to register a command with the manager
    public void registerCommand(String name, Command command) {
        commands.put(name, command);
    }

    // Method to execute a command by its name with a list of string parameters
    public void executeCommand(String commandName, List<String> parameters) {
        Command command = commands.get(commandName);
        if (command == null) {
            System.out.println("CommandHandle.Command not found: " + commandName);
            return;
        }

        List<Argument> arguments = command.getArguments();
        if (arguments.size() != parameters.size()) {
            System.out.println("Error: Incorrect number of parameters provided for " + commandName);
            return;
        }

        // Validate each argument with the provided parameters
        for (int i = 0; i < parameters.size(); i++) {
            Argument arg = arguments.get(i);
            String param = parameters.get(i);

            if (!arg.validate(param)) {
                arg.getErrorHandler().handleError(param);
                return;
            }
        }

        // If validation passes, execute the command with the updated parameters
        command.execute(parameters);
    }

    public static class CommandNotFoundHandler implements ErrorHandler {
        @Override
        public void handleError(String error) {
            System.err.println("Command not found: '" + error + "'");
        }
    }

    public static class IncorrectNumOfParamsHandler implements ErrorHandler {
        @Override
        public void handleError(String error) {
            System.err.println("Incorrect number of parameters provided for " + error );
        }
    }

    CommandNotFoundHandler cmdNotFound = new CommandNotFoundHandler();
    IncorrectNumOfParamsHandler incorrectNumOfParams = new IncorrectNumOfParamsHandler();

    public void executeCommand(ParsedCommand cmd) {
        var commandName  = cmd.getCommandName();
        var parameters = cmd.getTokens();
        var stringParams = cmd.getTokens().stream()
                .map(Token::value)
                .toList();
        Command command = commands.get(commandName);
        if (command == null) {
            cmdNotFound.handleError(commandName);
            return;
        }

        List<Argument> arguments = command.getArguments();
        if (arguments.size() != parameters.size()) {
            incorrectNumOfParams.handleError(commandName);
            return;
        }

        // Validate each argument with the provided parameters
        for (int i = 0; i < parameters.size(); i++) {
            Argument arg = arguments.get(i);
            String param = parameters.get(i).value();
            if (!arg.validate(param)) {
                arg.getErrorHandler().handleError(param);
                return;
            }
        }

        // If validation passes, execute the command with the updated parameters
        command.execute(stringParams);
    }
}
