package pl.wszib.edu.pl.intochordsspringapp;

import pl.wszib.edu.pl.intochordsspringapp.model.dbo.TeacherFile;

import java.time.LocalDateTime;

public class TeacherFileDTO {
    private int fileId;
    private String fileName;
    private String filePath;
    private LocalDateTime uploadDate;

    public TeacherFileDTO(TeacherFile file) {
        this.fileId = file.getFileId();
        this.fileName = file.getFileName();
        this.filePath = file.getFilePath();
        this.uploadDate = file.getUploadDate();
    }

    // Gettery
    public int getFileId() {
        return fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }
}
