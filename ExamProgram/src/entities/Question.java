package entities;

import configs.SessionFactoryMaker;
import jakarta.persistence.*;
import org.hibernate.Session;

import java.util.List;
import java.util.Scanner;

@Entity
@Table(name = "question")
public class Question {
    @Id
    private long id;
    @Column
    private String question;
    @Column
    private String answer;
    @Column(name = "option_a")
    private String optionA;
    @Column(name = "option_b")
    private String optionB;
    @Column(name = "option_c")
    private String optionC;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "exam_id")
    private Exam exam;

    public Question(Scanner sc) {
        System.out.println("Enter question text:");
        this.question = sc.nextLine();
        System.out.println("Enter answer option A:");
        this.optionA = sc.nextLine();
        System.out.println("Enter answer option B:");
        this.optionB = sc.nextLine();
        System.out.println("Enter answer option C:");
        this.optionC = sc.nextLine();
        System.out.println("Enter correct answer for this question:");
        this.answer = sc.nextLine();
    }

    public Question() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return String.format("|%3s|%15s|%15s|%15s|%15s|%15s|", this.getId(), this.getQuestion(), this.getOptionA(),
                this.getOptionB(), this.getOptionC(), this.getAnswer());
    }

    public static void add(Scanner sc) {
        System.out.println("Adding a new question");
        Question questionToAdd = new Question(sc);
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            session.getTransaction().begin();
            session.persist(questionToAdd);
            session.getTransaction().commit();
        }
    }

    public static void showAll() {
        System.out.println("All questions:");
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            List<Question> questions = session.createQuery("from entities.Question", Question.class).list();
            for (Question q : questions) {
                System.out.println(q);
            }
        }
    }

    public static void showById(Scanner sc) {
        System.out.println("Enter ID:");
        int id = Integer.parseInt(sc.nextLine());
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            System.out.println(session.get(Question.class, id));
        }
    }

    public static void deleteById(Scanner sc) {
        System.out.println("Enter ID:");
        int id = Integer.parseInt(sc.nextLine());
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            session.getTransaction().begin();
            Question questionToDelete = session.get(Question.class, id);
            session.remove(questionToDelete);
            session.getTransaction().commit();
        }
    }

    public static void update(Scanner sc) {
        showAll();
        System.out.println("Enter ID to update entity:");
        int id = Integer.parseInt(sc.nextLine());
        try (Session session = SessionFactoryMaker.getFactory().openSession()) {
            session.getTransaction().begin();
            Question questionToUpdate = session.get(Question.class, id);
            questionToUpdate.printFieldsAvailable();
            System.out.println("Enter field id to update:");
            questionToUpdate.updateFieldByFieldId(Integer.parseInt(sc.nextLine()), sc);
            session.merge(questionToUpdate);
            session.getTransaction().commit();
        }
    }

    private void printFieldsAvailable() {
        System.out.println("[0] entities.Question: " + this.question);
        System.out.println("[1] Possible answer option A: " + this.optionA);
        System.out.println("[2] Possible answer option B: " + this.optionB);
        System.out.println("[3] Possible answer option C: " + this.optionC);
        System.out.println("[4] Answer: " + this.answer);
    }

    private void updateFieldByFieldId(int fieldId, Scanner sc) {
        switch (fieldId) {
            case 0 -> this.setQuestion(sc.nextLine());
            case 1 -> this.setOptionA(sc.nextLine());
            case 2 -> this.setOptionB(sc.nextLine());
            case 3 -> this.setOptionC(sc.nextLine());
            case 4 -> this.setAnswer(sc.nextLine());
            default -> System.out.println("Unknown field!");
        }
    }
}
