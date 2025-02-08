package pl.wszib.edu.pl.intochordsspringapp.controllers.api;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.wszib.edu.pl.intochordsspringapp.TeacherFileDTO;
import pl.wszib.edu.pl.intochordsspringapp.dao.ClassDAO;
import pl.wszib.edu.pl.intochordsspringapp.dao.TeacherFileDAO;

import pl.wszib.edu.pl.intochordsspringapp.dao.UserClassDAO;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.TClass;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.TeacherFile;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.User;
import pl.wszib.edu.pl.intochordsspringapp.session.SessionConstants;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teacher/files")
@CrossOrigin
public class TeacherFileController {

    @Autowired
    private TeacherFileDAO teacherFileDAO;

    @Autowired
    private ClassDAO classDAO;

    @Autowired
    private UserClassDAO userClassDAO;

    private final String uploadDir = "uploads/";

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, HttpSession session) {
        User teacher = (User) session.getAttribute(SessionConstants.USER_KEY);

        if (teacher == null || teacher.getRole() != User.Role.TEACHER) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only teachers can upload files.");
        }

        try {
            // Pobranie ścieżki do folderu uploads
            String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/uploads/";

            // Tworzenie katalogu jeśli nie istnieje
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Pobranie nazwy pliku
            String fileName = file.getOriginalFilename();
            String filePath = uploadDir + fileName;

            // Zapis pliku na serwerze
            File dest = new File(filePath);
            file.transferTo(dest);

            // Zapis w bazie danych
            TeacherFile teacherFile = new TeacherFile(teacher, fileName, "/uploads/" + fileName, LocalDateTime.now());
            teacherFileDAO.save(teacherFile);

            return ResponseEntity.ok("File uploaded successfully.");
        } catch (IOException e) {
            e.printStackTrace(); // ✅ Dodaj, aby zobaczyć dokładny błąd w logach
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving file.");
        }
    }


    @GetMapping("/get-files")
    public ResponseEntity<List<TeacherFileDTO>> getFiles(HttpSession session) {
        User student = (User) session.getAttribute(SessionConstants.USER_KEY);

        if (student == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(List.of(new TeacherFileDTO(new TeacherFile(null, "error", "Please log in.", LocalDateTime.now()))));
        }

        Optional<TClass> studentClassOpt = classDAO.findById(student.getTClass().getClassId());

        if (studentClassOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(List.of(new TeacherFileDTO(new TeacherFile(null, "error", "Student is not assigned to a class.", LocalDateTime.now()))));
        }

        TClass studentClass = studentClassOpt.get();
        User teacher = studentClass.getCreator();

        List<TeacherFile> files = teacherFileDAO.findByTeacher(teacher);

        // ✅ Konwersja listy plików na DTO
        List<TeacherFileDTO> fileDTOs = files.stream().map(TeacherFileDTO::new).toList();

        return ResponseEntity.ok(fileDTOs);
    }



    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<?> deleteFile(@PathVariable int fileId) {
        Optional<TeacherFile> fileOpt = teacherFileDAO.findById(fileId);

        if (fileOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"File not found.\"}");
        }

        TeacherFile teacherFile = fileOpt.get();

        // Pobranie pełnej ścieżki pliku
        File fileToDelete = new File(System.getProperty("user.dir") + "/src/main/resources/static" + teacherFile.getFilePath());

        // ✅ Sprawdzamy, czy plik istnieje i poprawnie się usunął
        if (fileToDelete.exists()) {
            if (!fileToDelete.delete()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("{\"error\": \"Failed to delete file from server.\"}");
            }
        }

        // Usuwamy plik z bazy danych
        teacherFileDAO.delete(teacherFile);

        return ResponseEntity.ok("{\"message\": \"File deleted successfully.\"}");
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable int fileId) {
        Optional<TeacherFile> fileOpt = teacherFileDAO.findById(fileId);

        if (fileOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        TeacherFile teacherFile = fileOpt.get();

        // Pobranie ścieżki do pliku
        Path filePath = Paths.get(System.getProperty("user.dir"), "src/main/resources/static", teacherFile.getFilePath());
        File file = filePath.toFile();

        // Sprawdzamy, czy plik istnieje
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
