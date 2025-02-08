package pl.wszib.edu.pl.intochordsspringapp.model.dbo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


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

    @OneToMany(mappedBy = "TClass")
    private List<UserClass> users = new ArrayList<>();

    public TClass(int classId, String className, User creator, List<UserClass> users) {
        this.classId = classId;
        this.className = className;
        this.creator = creator;
        this.users = users;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<UserClass> getUsers() {
        return users;
    }

    public void setUsers(List<UserClass> users) {
        this.users = users;
    }

    public TClass() {}
}

