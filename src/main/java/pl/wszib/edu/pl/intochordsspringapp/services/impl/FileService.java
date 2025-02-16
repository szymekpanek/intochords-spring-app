package pl.wszib.edu.pl.intochordsspringapp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.wszib.edu.pl.intochordsspringapp.dao.ClassDAO;
import pl.wszib.edu.pl.intochordsspringapp.dao.TeacherFileDAO;
import pl.wszib.edu.pl.intochordsspringapp.dto.TeacherFileDTO;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.TClass;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.TeacherFile;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.User;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    @Autowired
    private TeacherFileDAO teacherFileDAO;

    @Autowired
    private ClassDAO classDAO;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/uploads/";

    public ResponseEntity<String> uploadFile(MultipartFile file, User teacher) {
        if (teacher == null || teacher.getRole() != User.Role.TEACHER) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only teachers can upload files.");
        }

        try {
            // Tworzenie katalogu jeśli nie istnieje
            File directory = new File(UPLOAD_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Pobranie nazwy pliku i zapis na serwerze
            String fileName = file.getOriginalFilename();
            String filePath = UPLOAD_DIR + fileName;
            file.transferTo(new File(filePath));

            // Zapis informacji o pliku w bazie danych
            TeacherFile teacherFile = new TeacherFile(teacher, fileName, "/uploads/" + fileName, LocalDateTime.now());
            teacherFileDAO.save(teacherFile);

            return ResponseEntity.ok("File uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving file.");
        }
    }

    public ResponseEntity<List<TeacherFileDTO>> getFiles(User student) {
        if (student == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(List.of(new TeacherFileDTO(new TeacherFile(null, "error", "Please log in.", LocalDateTime.now()))));
        }

        Optional<TClass> studentClassOpt = classDAO.findById(student.getTClass().getClassId());
        if (studentClassOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(List.of(new TeacherFileDTO(new TeacherFile(null, "error", "Student is not assigned to a class.", LocalDateTime.now()))));
        }

        User teacher = studentClassOpt.get().getCreator();
        List<TeacherFile> files = teacherFileDAO.findByTeacher(teacher);

        return ResponseEntity.ok(files.stream().map(TeacherFileDTO::new).toList());
    }

    public ResponseEntity<?> getFilesForClass(int classId, Integer sessionClassId) {
        if (sessionClassId == null || !sessionClassId.equals(classId)) {
            return ResponseEntity.status(403).body("Access denied.");
        }

        Optional<TClass> classOpt = classDAO.findById(classId);
        if (classOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Class not found.");
        }

        User teacher = classOpt.get().getCreator();
        List<TeacherFile> files = teacherFileDAO.findByTeacher(teacher);

        if (files.isEmpty()) {
            return ResponseEntity.status(404).body("No files found.");
        }

        List<TeacherFileDTO> fileDTOs = files.stream()
                .map(file -> new TeacherFileDTO(file.getFileId(), file.getFileName(), file.getFilePath(), file.getUploadDate(), teacher.getUserId()))
                .toList();

        return ResponseEntity.ok(fileDTOs);
    }

    public ResponseEntity<String> deleteFile(int fileId) {
        Optional<TeacherFile> fileOpt = teacherFileDAO.findById(fileId);
        if (fileOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"File not found.\"}");
        }

        TeacherFile teacherFile = fileOpt.get();
        File fileToDelete = new File(System.getProperty("user.dir") + "/src/main/resources/static" + teacherFile.getFilePath());

        if (fileToDelete.exists() && !fileToDelete.delete()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Failed to delete file from server.\"}");
        }

        teacherFileDAO.delete(teacherFile);
        return ResponseEntity.ok("{\"message\": \"File deleted successfully.\"}");
    }

    public ResponseEntity<Resource> downloadFile(int fileId) {
        Optional<TeacherFile> fileOpt = teacherFileDAO.findById(fileId);
        if (fileOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        TeacherFile teacherFile = fileOpt.get();
        Path filePath = Paths.get(System.getProperty("user.dir"), "src/main/resources/static", teacherFile.getFilePath());
        File file = filePath.toFile();

        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        try {
            Resource resource = new UrlResource(filePath.toUri());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + teacherFile.getFileName() + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
