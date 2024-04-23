package oop.project.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Scenarios {

    CommandManager manager;

    public Scenarios() {

        Validator intValidator = new IntegerValidator();
        Validator doubleValidator = new DoubleValidator();
        Validator dateValidator = new DateValidator();
        ErrorHandler typeErrorHandler = new TypeErrorHandler();

        List<Argument> argumentsInt = new ArrayList<>();
        argumentsInt.add(new Argument("firstInt", Argument.Type.INTEGER, intValidator, typeErrorHandler));
        argumentsInt.add(new Argument("secondInt", Argument.Type.INTEGER, intValidator, typeErrorHandler));

        List<Argument> argumentsDou = new ArrayList<>();
        argumentsDou.add(new Argument("firstDou", Argument.Type.DOUBLE, intValidator, typeErrorHandler));
        argumentsDou.add(new Argument("secondDou", Argument.Type.DOUBLE, intValidator, typeErrorHandler));

        List<Argument> dateArgs = new ArrayList<>();
        argumentsDou.add(new Argument("date", Argument.Type.STRING, intValidator, typeErrorHandler));



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

        Consumer<List<String>> dateFunction = params -> {
            DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            System.out.println("Result: " + LocalDate.parse(params.get(0), DATE_FORMATTER));
        };

        Command addCommand = new Command("add", argumentsInt, addFunction);
        Command subCommand = new Command("sub", argumentsInt, subFunction);
        Command sqrtCommand = new Command("sqrt", argumentsInt, sqrtFunction);
        Command divCommand = new Command("div", argumentsDou, divFunction);
        Command multCommand = new Command("mult", argumentsInt, multFunction);

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

    public class DateValidator implements Validator {
        private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        @Override
        public boolean validate(String value) {
            if (value == null) {
                return false;
            }
            if (!value.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return false;
            }
            try {
                LocalDate.parse(value, DATE_FORMATTER);
                return true;
            } catch (DateTimeParseException e) {
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
