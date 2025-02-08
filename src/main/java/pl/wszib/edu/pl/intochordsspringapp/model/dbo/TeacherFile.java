package pl.wszib.edu.pl.intochordsspringapp.model.dbo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "t_teacher_files")
public class TeacherFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private int fileId;  // Unikalny identyfikator pliku

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "user_id", nullable = false)
    private User teacher; // ID nauczyciela, który dodał plik

    @Column(name = "file_name", nullable = false)
    private String fileName; // Oryginalna nazwa pliku

    @Column(name = "file_path", nullable = false)
    private String filePath; // Ścieżka pliku

    @Column(name = "upload_date", nullable = false)
    private LocalDateTime uploadDate; // Data dodania pliku

    public TeacherFile() {}

    public TeacherFile(User teacher, String fileName, String filePath, LocalDateTime uploadDate) {
        this.teacher = teacher;
        this.fileName = fileName;
        this.filePath = filePath;
        this.uploadDate = uploadDate;
    }
}
