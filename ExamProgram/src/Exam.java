import jakarta.persistence.*;
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

    public Exam() {
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
}