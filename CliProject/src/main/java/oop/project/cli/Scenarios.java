package oop.project.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Scenarios {

    CommandManager manager;

    public Scenarios() {

        Validator intValidator = new IntegerValidator();
        Validator doubleValidator = new DoubleValidator();
        ErrorHandler typeErrorHandler = new TypeErrorHandler();

        List<Argument> arguments = new ArrayList<>();
        arguments.add(new Argument("first", Argument.Type.INTEGER, intValidator, typeErrorHandler));
        arguments.add(new Argument("second", Argument.Type.INTEGER, intValidator, typeErrorHandler));

        Consumer<List<String>> addFunction = params -> {
            int num1 = Integer.parseInt(params.get(0));
            int num2 = Integer.parseInt(params.get(1));
            System.out.println("Result: " + (num1 + num2));
        };
        Consumer<List<String>> subFunction = params -> {
            int num1 = Integer.parseInt(params.get(0));
            int num2 = Integer.parseInt(params.get(1));
            System.out.println("Result: " + (num1 - num2));
        };
        Consumer<List<String>> sqrtFunction = params -> {
            double num1 = Integer.parseInt(params.get(0));
            System.out.println("Result: " + (Math.sqrt(num1)));
        };
        Consumer<List<String>> divFunction = params -> {
            int num1 = Integer.parseInt(params.get(0));
            int num2 = Integer.parseInt(params.get(1));
            System.out.println("Result: " + (num1 / num2));
        };
        Consumer<List<String>> multFunction = params -> {
            int num1 = Integer.parseInt(params.get(0));
            int num2 = Integer.parseInt(params.get(1));
            System.out.println("Result: " + (num1 * num2));
        };

        Command addCommand = new Command("add", arguments, addFunction);
        Command subCommand = new Command("sub", arguments, subFunction);
        Command sqrtCommand = new Command("add", arguments, addFunction);
        Command divCommand = new Command("sub", arguments, subFunction);
        Command multCommand = new Command("mult", arguments, multFunction);

        this.manager = new CommandManager();
        manager.registerCommand("add", addCommand);
        manager.registerCommand("sub", subCommand);
        manager.registerCommand("sqrt", sqrtCommand);
        manager.registerCommand("div", divCommand);
        manager.registerCommand("mult", multCommand);

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

    public static class DoubleValidator implements Validator {
        @Override
        public boolean validate(String value) {
            try {
                Double.parseDouble(value);
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
