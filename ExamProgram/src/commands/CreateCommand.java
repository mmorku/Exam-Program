package commands;

import entities.Exam;
import entities.Person;
import entities.Question;

import java.util.Scanner;

public abstract class CreateCommand extends CommandHandler {

    public static void addNew(Scanner sc) {
        switch (selectEntity(sc)) {
            case "1" -> Person.add(sc);
            case "2" -> Question.add(sc);
            case "3" -> Exam.add(sc);
            default -> System.out.println("Unknown entity!");
        }
    }

}
