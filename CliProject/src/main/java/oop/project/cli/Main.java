package oop.project.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;



public class Main {

    public static void main(String[] args) {

        Scenarios scenarios = new Scenarios();

        var scanner = new Scanner(System.in);
        while (true) {
            var input = scanner.nextLine();
            if (input.equals("exit")) {
                break;
            }
            try {
                var ast = RetrieveCommand.parse(input);
                scenarios.getManager().executeCommand(ast);
            } catch (ParseException e) {
                System.out.println("Error parsing input: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected exception: " + e.getMessage());
            }
        }


    }


}




