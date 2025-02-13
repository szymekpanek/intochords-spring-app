package pl.wszib.edu.pl.intochordsspringapp.dto;

import lombok.Getter;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.TeacherFile;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.User;

import java.time.LocalDateTime;
@Getter
public class TeacherFileDTO {
    private int fileId;
    private String fileName;
    private String filePath;
    private LocalDateTime uploadDate;
    private int teacherId;

    public TeacherFileDTO(TeacherFile file) {
        this.fileId = file.getFileId();
        this.fileName = file.getFileName();
        this.filePath = file.getFilePath();
        this.uploadDate = file.getUploadDate();
        this.teacherId = file.getTeacher().getUserId(); // ✅ Pobieramy `teacherId` z obiektu `User`
    }

    // ✅ Konstruktor do konwersji plików w kontrolerze
    public TeacherFileDTO(int fileId, String fileName, String filePath, LocalDateTime uploadDate, int teacherId) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.filePath = filePath;
        this.uploadDate = uploadDate;
        this.teacherId = teacherId;
    }

}
