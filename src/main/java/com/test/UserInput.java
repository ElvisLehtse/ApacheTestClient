package com.test;

import java.util.*;
import org.json.JSONObject;

/**
 * This class, UserInput, handles user input for client actions.
 * It prompts the user for their choice and collects input based on the selected action.
 */
class UserInput {

    private String userChoice;
    private String oldWeapon;

    public void setUserChoice(String userChoice) {
        this.userChoice = userChoice;
    }
    public void setOldWeapon(String oldWeapon) {
        this.oldWeapon = oldWeapon;
    }
    public String getUserChoice() {
        return this.userChoice;
    }
    public String getOldWeapon() {
        return this.oldWeapon;
    }

    /**
     * Collects user input using the Scanner class.
     *
     * @return The user's input as a String.
     */
    private String userInputScanner () {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().toLowerCase();    // All user inputs will be handled as lower-case
    }

    private JSONObject weaponData () {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonDetails = new JSONObject();
        System.out.println("Weapon's name:");
        String weaponsName = userInputScanner();
        System.out.println("Strength:");
        jsonDetails.put("str", userInputScanner());
        System.out.println("Speed:");
        jsonDetails.put("speed", userInputScanner());
        System.out.println("Description:");
        jsonDetails.put("desc", userInputScanner());
        jsonObject.put(weaponsName, jsonDetails);
        return jsonObject;
    }

    /**
     * Prompts the user for their choice and collects input based on the selected action.
     *
     * @return A map containing user input for the selected action.
     */
    JSONObject userJson () {
        setUserChoice(userInputScanner());
        JSONObject jsonObject = new JSONObject();

        switch (getUserChoice()) {                           // Process user choice
            case "read", "reset" -> {}              // Does nothing: these actions do not need any additional input
            case "add" -> jsonObject = weaponData();
            case "modify" -> {
                System.out.println("Old weapon's name:");
                setOldWeapon(userInputScanner());
                jsonObject = weaponData();
            }
            case "delete" -> {
                System.out.println("Weapon's name:");
                setOldWeapon(userInputScanner());
                return null;
            }
            case "exit" -> System.exit(0);
            default -> System.out.println("Incorrect input\n");
        }
        return jsonObject;
    }
}
