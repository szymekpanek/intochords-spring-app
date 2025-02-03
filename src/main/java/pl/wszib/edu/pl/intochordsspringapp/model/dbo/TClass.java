package pl.wszib.edu.pl.intochordsspringapp.model.dbo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "t_class")
public class TClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private int classId;

    @Column(name = "class_name")
    private String className;

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "user_id")
    private User creator;

    @OneToMany(mappedBy = "aTClass")
    private List<UserClass> users = new ArrayList<>();

    public TClass(int classId, String className, User creator, List<UserClass> users) {
        this.classId = classId;
        this.className = className;
        this.creator = creator;
        this.users = users;
    }

    public TClass() {}
}

