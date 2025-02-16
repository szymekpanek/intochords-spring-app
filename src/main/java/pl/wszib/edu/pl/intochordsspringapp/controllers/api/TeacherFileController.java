package pl.wszib.edu.pl.intochordsspringapp.controllers.api;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.wszib.edu.pl.intochordsspringapp.dto.TeacherFileDTO;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.User;
import pl.wszib.edu.pl.intochordsspringapp.services.impl.FileService;
import pl.wszib.edu.pl.intochordsspringapp.session.SessionConstants;

import java.util.List;

@RestController
@RequestMapping("/api/teacher/files")
@CrossOrigin
public class TeacherFileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, HttpSession session) {
        User teacher = (User) session.getAttribute(SessionConstants.USER_KEY);
        return fileService.uploadFile(file, teacher);
    }

    @GetMapping("/get-files")
    public ResponseEntity<List<TeacherFileDTO>> getFiles(HttpSession session) {
        User student = (User) session.getAttribute(SessionConstants.USER_KEY);
        return fileService.getFiles(student);
    }

    @GetMapping("/{classId}")
    public ResponseEntity<?> getFilesForClass(@PathVariable int classId, HttpSession session) {
        Integer sessionClassId = (Integer) session.getAttribute("classId");
        return fileService.getFilesForClass(classId, sessionClassId);
    }

    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<?> deleteFile(@PathVariable int fileId) {
        return fileService.deleteFile(fileId);
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable int fileId) {
        return fileService.downloadFile(fileId);
    }
}
