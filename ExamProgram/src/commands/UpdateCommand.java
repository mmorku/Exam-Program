package commands;

import entities.Exam;
import entities.Person;
import entities.Question;

import java.util.Scanner;

public abstract class UpdateCommand extends CommandHandler {

    public static void update(Scanner sc) {
        switch (selectEntity(sc)) {
            case "1" -> Person.update(sc);
            case "2" -> Question.update(sc);
            case "3" -> Exam.update(sc);
            default -> System.out.println("Unknown entity!");
        }
    }
}
