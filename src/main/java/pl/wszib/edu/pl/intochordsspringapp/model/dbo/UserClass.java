package pl.wszib.edu.pl.intochordsspringapp.model.dbo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@Entity
@Table(name = "t_user_class")
public class UserClass{
    @EmbeddedId
    private UserClassId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("classId")
    @JoinColumn(name = "class_id")
    private TClass TClass;

    public UserClass(UserClassId id, User user, TClass tClass) {
        this.id = id;
        this.user = user;
        this.TClass = tClass;
    }

    public UserClass() {}
}


