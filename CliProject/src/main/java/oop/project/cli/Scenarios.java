package oop.project.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Scenarios {

    CommandManager manager;

    public Scenarios() {

        Validator intValidator = new Main.IntegerValidator();
        ErrorHandler typeErrorHandler = new Main.TypeErrorHandler();

        List<Argument> arguments = new ArrayList<>();
        arguments.add(new Argument("first", Argument.Type.INTEGER, intValidator, typeErrorHandler));
        arguments.add(new Argument("second", Argument.Type.INTEGER, intValidator, typeErrorHandler));

        Consumer<List<String>> addFunction = params -> {
            int num1 = Integer.parseInt(params.get(0));
            int num2 = Integer.parseInt(params.get(1));
            System.out.println("Result: " + (num1 + num2));
        };

        Command addCommand = new Command("add", arguments, addFunction);

        this.manager = new CommandManager();
        manager.registerCommand("add", addCommand);

    }

    public CommandManager getManager() {
        return manager;
    }

    // Validator to check if a string is an integer
    public static class IntegerValidator implements Validator {
        @Override
        public boolean validate(String value) {
            try {
                Integer.parseInt(value);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }

    // ErrorHandler to report non-integer values
    public static class TypeErrorHandler implements ErrorHandler {
        @Override
        public void handleError(String error) {
            System.err.println("Type Error: Expected an integer but received '" + error + "'");
        }
    }
}
