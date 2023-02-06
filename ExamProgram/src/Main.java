import commands.*;
import configs.SessionFactoryMaker;
import entities.Exam;
import entities.Person;
import entities.Question;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SessionFactoryMaker.getFactory();
//        testCriteriaBuilder();
        runProgram();
    }

    private static void runProgram() {
        Scanner sc = new Scanner(System.in);
        boolean runProgram = true;
        while (runProgram) {
            try {
                CommandHandler.printCommands();
                String input = sc.nextLine();
                switch (input) {
                    case "1" -> CreateCommand.addNew(sc);
                    case "2" -> ReadCommand.show(sc);
                    case "3" -> DeleteCommand.delete(sc);
                    case "4" -> UpdateCommand.update(sc);
                    case "0" -> runProgram = false;
                    default -> System.out.println("Incorrect input! Try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private static void testCriteriaBuilder() {
        SessionFactory sessionFactory = configs.SessionFactoryMaker.getFactory();
        try(Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Person.class);
            Root<Person> root = criteriaQuery.from(Person.class);
            Predicate nameStartsFromM = criteriaBuilder.equal(root.get("name"), "M%");
            criteriaQuery.select(root).where(nameStartsFromM);
            List<Person> personList = session.createQuery(criteriaQuery).getResultList();
            System.out.println("Pavyko");
        }

    }
}