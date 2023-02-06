package entities;

import configs.SessionFactoryMaker;
import jakarta.persistence.*;
import org.hibernate.Session;

import java.util.List;
import java.util.Scanner;

@Entity
@Table(name = "exam")
public class Exam {
    @Id
    private long id;
    @Column
    private int result;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    private Person person;

    public Exam(int result) {
        this.result = result;
    }

    public Exam() {
    }

    public Exam(Scanner sc) {
        System.out.println("Enter result:");
        this.result = Integer.parseInt(sc.nextLine());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return String.format("|%15s|%15s|", this.getId(), this.getResult());
    }

    public static void add(Scanner sc) {
        System.out.println("Adding a new exam");
        Exam examToAdd = new Exam(sc);
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            session.getTransaction().begin();
            session.persist(examToAdd);
            session.getTransaction().commit();
        }
    }
    public static void showAll() {
        System.out.println("All exams:");
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            List<Exam> exams = session.createQuery("from entities.Exam", Exam.class).list();
            for (Exam e : exams) {
                System.out.println(e);
            }
        }
    }

    public static void showById(Scanner sc) {
        System.out.println("Enter ID:");
        int id = Integer.parseInt(sc.nextLine());
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            System.out.println(session.get(Exam.class, id));
        }
    }

    public static void deleteById(Scanner sc) {
        System.out.println("Enter ID:");
        int id = Integer.parseInt(sc.nextLine());
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            session.getTransaction().begin();
            Exam examToDelete = session.get(Exam.class, id);
            session.remove(examToDelete);
            session.getTransaction().commit();
        }
    }

    public static void update(Scanner sc) {
        showAll();
        System.out.println("Enter ID to update entity:");
        int id = Integer.parseInt(sc.nextLine());
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            session.getTransaction().begin();
            Exam examToUpdate = session.get(Exam.class, id);
            examToUpdate.printFieldsAvailable();
            System.out.println("Enter field id to update:");
            examToUpdate.updateFieldByFieldId(Integer.parseInt(sc.nextLine()), sc);
            session.merge(examToUpdate);
            session.getTransaction().commit();
        }
    }

    private void printFieldsAvailable() {
        System.out.println("[0] ID: " + this.id);
        System.out.println("[1] Result: " + this.result);
    }

    private void updateFieldByFieldId(int fieldId, Scanner sc) {
        switch (fieldId) {
            case 0 -> this.setId(Long.parseLong(sc.nextLine()));
            case 1 -> this.setResult(Integer.parseInt(sc.nextLine()));
            default -> System.out.println("Unknown field!");
        }
    }
}