package commands;

import commands.enums.Command;

import java.util.Scanner;

public class CommandHandler {

    static String selectEntity(Scanner sc) {
        System.out.println("Select entity:");
        System.out.println("[1] - Person");
        System.out.println("[2] - Question");
        System.out.println("[3] - Exam");
        return sc.nextLine();
    }


    public static void printCommands() {
        for (Command c : Command.values()) {
            System.out.println(c);
        }
    }
}
