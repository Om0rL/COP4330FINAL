package oop.project.cli;

public class Argument {
    // Enum to represent the type of the argument
    public enum Type {
        INTEGER, DOUBLE, STRING
    }

    // Value of the argument as a String
    private String name;
    // Type of the argument
    private Type type;
    // CommandHandle.Validator for this argument
    private Validator validator;

    private ErrorHandler errorHandler;

    // Constructor
    public Argument(String name, Type type, Validator validator, ErrorHandler errorHandler) {
        this.name = name;
        this.type = type;
        this.validator = validator;
        this.errorHandler = errorHandler;
    }

    // Getter for value
    public String getName() {
        return name;
    }

    // Setter for value
    public void setName(String value) {
        this.name = name;
    }

    // Getter for type
    public Type getType() {
        return type;
    }

    // Setter for type
    public void setType(Type type) {
        this.type = type;
    }

    // Getter for validator
    public Validator getValidator() {
        return validator;
    }

    // Setter for validator
    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public ErrorHandler getErrorHandler() {
        return errorHandler;
    }

    // Setter for validator
    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    // Method to validate the argument's value
    public boolean validate(String value) {

        return validator.validate(value);
    }
}
