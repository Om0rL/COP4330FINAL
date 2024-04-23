package oop.project.cli;


import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Command {
    private String name; // The name of the command
    private List<Argument> arguments; // The list of arguments

    private List<String> parameterNames;
    private Consumer<List<String>> action; // The action to execute with arguments

    public Command(String name, List<Argument> arguments, Consumer<List<String>> action) {
        this.name = name;
        this.arguments = arguments;
        this.parameterNames = arguments.stream()
                .map(Argument::getName)
                .collect(Collectors.toList());
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Argument> getArguments() {
        return arguments;
    }

    public void execute(List<String> params) {
        if (action != null) {
            try {
                action.accept(params);
            } catch (Exception e) {
                System.err.println("Error executing command '" + name + "': " + e.getMessage());
            }
        } else {
            System.out.println("No action defined for command: " + name);
        }
    }
}
