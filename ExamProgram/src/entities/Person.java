package entities;

import configs.SessionFactoryMaker;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.Session;

import java.util.List;
import java.util.Scanner;

@Entity
@Table(name = "person")
public class Person {
    @Id
    private long id;
    @Column
    private String name;
    @Column
    private String surname;

    public Person(Scanner sc) {
        System.out.println("Enter name:");
        this.name = sc.nextLine();
        System.out.println("Enter surname:");
        this.surname = sc.nextLine();
    }

    public Person() {
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return String.format("|%3s|%15s|%15s|", this.getId(), this.getName(), this.getSurname());
    }

    public static void add(Scanner sc) {
        System.out.println("Adding a new person");
        Person personToAdd = new Person(sc);
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            session.getTransaction().begin();
            session.persist(personToAdd);
            session.getTransaction().commit();
        }
    }

    public static void showAll() {
        System.out.println("All persons list:");
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            List<Person> personsList = session.createQuery("from entities.Person", Person.class).list();
            for (Person p : personsList) {
                System.out.println(p);
            }
        }
    }

    public static void showById(Scanner sc) {
        System.out.println("Enter ID:");
        int id = Integer.parseInt(sc.nextLine());
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            System.out.println(session.get(Person.class, id));
        }
    }

    public static void deleteById(Scanner sc) {
        System.out.println("Enter ID:");
        int id = Integer.parseInt(sc.nextLine());
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            session.getTransaction().begin();
            Person personToDelete = session.get(Person.class, id);
            session.remove(personToDelete);
            session.getTransaction().commit();
        }
    }

    public static void update(Scanner sc) {
        showAll();
        System.out.println("Enter ID to update entity:");
        int id = Integer.parseInt(sc.nextLine());
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            session.getTransaction().begin();
            Person personToUpdate = session.get(Person.class, id);
            personToUpdate.printFieldsAvailable();
            System.out.println("Enter field id to update:");
            personToUpdate.updateFieldByFieldId(Integer.parseInt(sc.nextLine()), sc);
            session.merge(personToUpdate);
            session.getTransaction().commit();
        }
    }

    private void printFieldsAvailable() {
        System.out.println("[0] Name: " + this.name);
        System.out.println("[1] Surname: " + this.surname);
        System.out.println("[2] ID: " + this.id);
    }

    private void updateFieldByFieldId(int fieldId, Scanner sc) {
        switch (fieldId) {
            case 0 -> this.setName(sc.nextLine());
            case 1 -> this.setSurname(sc.nextLine());
            case 2 -> this.setId(Long.parseLong(sc.nextLine()));
            default -> System.out.println("Unknown field!");
        }
    }
}
