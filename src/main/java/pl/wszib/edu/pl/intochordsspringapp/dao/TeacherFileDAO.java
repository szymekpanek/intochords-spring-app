package pl.wszib.edu.pl.intochordsspringapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.TClass;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.TeacherFile;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.User;

import java.util.List;

public interface TeacherFileDAO extends JpaRepository<TeacherFile, Integer> {
    List<TeacherFile> findByTeacher(User teacher); // Pobiera pliki dodane przez danego nauczyciela
}


