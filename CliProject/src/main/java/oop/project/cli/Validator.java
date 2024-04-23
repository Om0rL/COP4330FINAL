package oop.project.cli;

/**
 * A generic interface for argument validation.
 * It allows implementing custom validation logic for different types of data.
 *
 */
public interface Validator {
    /**
     * Validates the given value.
     *
     * @param value the value to validate
     * @return true if the value is valid according to the implemented rules, false otherwise
     */
    boolean validate(String value);
}
