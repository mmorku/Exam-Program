package commands;

import entities.Exam;
import entities.Person;
import entities.Question;

import java.util.Scanner;

public abstract class DeleteCommand extends CommandHandler {

    public static void delete(Scanner sc) {
        System.out.println("Select:");
        System.out.println("[1] - delete all");
        System.out.println("[2] - delete by id");
        switch (sc.nextLine()) {
            case "1" -> deleteAll(sc);
            case "2" -> deleteById(sc);
            default -> System.out.println("Unknown command!");
        }
    }

    public static void deleteAll(Scanner sc) {
        System.out.println("Not implemented yet!");
    }

    public static void deleteById(Scanner sc) {
        switch (selectEntity(sc)) {
            case "1" -> Person.deleteById(sc);
            case "2" -> Question.deleteById(sc);
            case "3" -> Exam.deleteById(sc);
            default -> System.out.println("Unknown entity!");
        }
    }
}
