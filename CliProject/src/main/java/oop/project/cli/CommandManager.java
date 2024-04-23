package oop.project.cli;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandManager {
    private Map<String, Command> commands;

    /**
     * Constructs a new {@code CommandManager} instance.
     */
    public CommandManager() {
        this.commands = new HashMap<>();
    }

    /**
     * Registers a command with a specific name in the command manager.
     * This allows the command to be later executed by its name.
     *
     * @param name    the name to associate with the command
     * @param command the command object to be registered
     */
    public void registerCommand(String name, Command command) {
        commands.put(name, command);
    }

    /**
     * Executes a command based on its name with a list of string parameters.
     * Validates the parameters against the command's requirements before execution.
     *
     * @param commandName the name of the command to execute
     * @param parameters  a list of parameters in string form that the command will use
     */
    public void executeCommand(String commandName, List<String> parameters) {
        Command command = commands.get(commandName);
        if (command == null) {
            System.out.println("Command not found: " + commandName);
            return;
        }

        List<Argument> arguments = command.getArguments();
        if (arguments.size() != parameters.size()) {
            System.out.println("Error: Incorrect number of parameters provided for " + commandName);
            return;
        }

        for (int i = 0; i < parameters.size(); i++) {
            Argument arg = arguments.get(i);
            String param = parameters.get(i);

            if (!arg.validate(param)) {
                arg.getErrorHandler().handleError(param);
                return;
            }
        }

        command.execute(parameters);
    }

    /**
     * Handler for scenarios where a command is not found within the command manager.
     */
    public static class CommandNotFoundHandler implements ErrorHandler {
        @Override
        public void handleError(String error) {
            throw new RuntimeException("Command not found: '" + error + "'");
        }
    }

    /**
     * Handler for scenarios where the number of parameters provided does not match
     * the number required by the command.
     */
    public static class IncorrectNumOfParamsHandler implements ErrorHandler {
        @Override
        public void handleError(String error) {
            throw new RuntimeException("Incorrect number of parameters provided for " + error);
        }
    }

    CommandNotFoundHandler cmdNotFound = new CommandNotFoundHandler();
    IncorrectNumOfParamsHandler incorrectNumOfParams = new IncorrectNumOfParamsHandler();

    /**
     * Executes a parsed command object by validating its parameters and invoking the corresponding command.
     * This method handles both validation and execution, and it also applies detailed error handling through
     * specialized {@code ErrorHandler} implementations.
     *
     * @param cmd the parsed command object, containing command name and parameters
     */
    public void executeCommand(ParsedCommand cmd) {
        var commandName = cmd.getCommandName();
        var parameters = cmd.getTokens();
        var stringParams = parameters.stream()
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

        for (int i = 0; i < parameters.size(); i++) {
            Argument arg = arguments.get(i);
            String param = parameters.get(i).value();
            if (!arg.validate(param)) {
                arg.getErrorHandler().handleError(param);
                return;
            }
        }

        command.execute(stringParams);
    }
}
