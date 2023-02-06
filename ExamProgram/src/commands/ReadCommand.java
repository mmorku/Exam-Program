package commands;

import entities.Exam;
import entities.Person;
import entities.Question;

import java.util.Scanner;

public abstract class ReadCommand extends CommandHandler {

    public static void show(Scanner sc) {
        System.out.println("Select:");
        System.out.println("[1] - show all");
        System.out.println("[2] - show by id");
        switch (sc.nextLine()) {
            case "1" -> showAll(sc);
            case "2" -> showById(sc);
            default -> System.out.println("Unknown command!");
        }
    }

    public static void showAll(Scanner sc) {
        switch (selectEntity(sc)) {
            case "1" -> Person.showAll();
            case "2" -> Question.showAll();
            case "3" -> Exam.showAll();
            default -> System.out.println("Unknown entity!");
        }
    }

    public static void showById(Scanner sc) {
        switch (selectEntity(sc)) {
            case "1" -> Person.showById(sc);
            case "2" -> Question.showById(sc);
            case "3" -> Exam.showById(sc);
            default -> System.out.println("Unknown entity!");
        }
    }
}
