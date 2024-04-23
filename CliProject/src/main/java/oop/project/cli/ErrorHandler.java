package oop.project.cli;

public interface ErrorHandler {
    /**
     * Handles an error associated with a validation failure. This method is
     * called when a validation process fails and needs to report an error.
     *
     * @param validationError the error message describing what went wrong during validation.
     */
    void handleError(String validationError);
}
