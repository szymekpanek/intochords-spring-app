package pl.wszib.edu.pl.intochordsspringapp.model.dbo;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class UserClassId implements java.io.Serializable {
    private int userId;
    private int classId;

    public UserClassId(Integer userId, Integer classId) {
        this.userId = userId;
        this.classId = classId;
    }

    public UserClassId() {

    }
}
