package com.test;

import java.util.*;

/**
 * This class, UserInput, handles user input for client actions.
 * It prompts the user for their choice and collects input based on the selected action.
 */
class UserInput {

    /**
     * Collects user input using the Scanner class.
     *
     * @return The user's input as a String.
     */
    private String userInputScanner () {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().toLowerCase();    // All user inputs will be handled as lower-case
    }

    /**
     * Used for printing out messages to the user.
     *
     * @return pre-defined messages as a String array.
     */
    private String[] messageString () {
        return new String[]{
                "Weapon's name:",
                "Strength:",
                "Speed:",
                "Description:"
        };
    }

    /**
     * Prompts the user for their choice and collects input based on the selected action.
     *
     * @return A map containing user input for the selected action.
     */
    List<String> userChoice () {
        String[] message = messageString();         // This is the pre-defined messages for the user to see
        String choice = userInputScanner();
        List<String> userInput = new ArrayList<>();
        userInput.add(choice);

        switch (choice) {                           // Process user choice
            case "read", "reset" -> {}              // Does nothing: these actions do not need any additional input
            case "add" -> {
                for (String msg : message) {        // User info is stored inside a list
                    System.out.println(msg);
                    userInput.add(userInputScanner());
                }
            }
            case "modify" -> {
                System.out.println("Old weapon's name:");
                userInput.add(userInputScanner());
                for (String s : message) {          // User info is stored inside a list
                    System.out.println(s);
                    userInput.add(userInputScanner());
                }
            }
            case "delete" -> {
                System.out.println("Weapon's name:");
                userInput.add(userInputScanner());
            }
            case "exit" -> System.exit(0);
            default -> System.out.println("Incorrect input\n");
        }
        return userInput;
    }
}
