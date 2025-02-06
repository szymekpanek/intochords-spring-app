package pl.wszib.edu.pl.intochordsspringapp.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wszib.edu.pl.intochordsspringapp.dao.ClassDAO;
import pl.wszib.edu.pl.intochordsspringapp.dao.GameStatsDAO;
import pl.wszib.edu.pl.intochordsspringapp.dao.UserClassDAO;
import pl.wszib.edu.pl.intochordsspringapp.dao.UserDAO;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.*;
import pl.wszib.edu.pl.intochordsspringapp.services.impl.ClassGameStatsService;
import pl.wszib.edu.pl.intochordsspringapp.session.SessionConstants;

import jakarta.servlet.http.HttpSession;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/class")
public class RestClassController {

    @Autowired
    private ClassDAO classDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserClassDAO userClassDAO;

    @Autowired
    private GameStatsDAO gameStatsDAO;

    @Autowired
    private ClassGameStatsService classGameStatsService;

    @PostMapping("/create")
    public ResponseEntity<String> createClass(@RequestParam String className, HttpSession session) {
        User teacher = (User) session.getAttribute(SessionConstants.USER_KEY);

        if (teacher == null || teacher.getRole() != User.Role.TEACHER) {
            return ResponseEntity.status(403).body("Only teachers can create classes.");
        }

        // Sprawdzenie, czy klasa o tej nazwie już istnieje
        Optional<TClass> existingClass = classDAO.findByClassName(className);
        if (existingClass.isPresent()) {
            return ResponseEntity.badRequest().body("Class name already taken.");
        }

        // Tworzenie nowej klasy
        TClass newClass = new TClass();
        newClass.setClassName(className);
        newClass.setCreator(teacher);
        classDAO.save(newClass);

        // 🔹 Po zapisaniu klasy, dodajemy nauczyciela do t_user_class
        UserClass userClass = new UserClass();
        userClass.setId(new UserClassId(teacher.getUserId(), newClass.getClassId()));
        userClass.setUser(teacher);
        userClass.setTClass(newClass);
        userClassDAO.save(userClass); // ✅ Zapis do `t_user_class`

        // 🔹 Aktualizacja pola `class_id` w tabeli `t_user`
        teacher.setTClass(newClass);
        userDAO.save(teacher);

        return ResponseEntity.ok("/class-panel/add-students?classId=" + newClass.getClassId());
    }


    /**
     * Pobiera listę dostępnych uczniów (role = USER, class_id = NULL)
     */
    @GetMapping("/get-available-students")
    public ResponseEntity<List<Map<String, Object>>> getAvailableStudents() {
        List<User> students = userDAO.findByRole(User.Role.USER);

        List<Map<String, Object>> studentList = students.stream().map(student -> {
            Map<String, Object> studentData = new HashMap<>();
            studentData.put("userId", student.getUserId());
            studentData.put("name", student.getName());
            studentData.put("surname", student.getSurname());
            studentData.put("login", student.getLogin());
            return studentData;
        }).toList();

        return ResponseEntity.ok(studentList);
    }

    /**
     * Obsługuje dodawanie uczniów do klasy
     */
    @PostMapping("/add-students")
    public ResponseEntity<?> addStudentsToClass(@RequestBody Map<String, Object> payload) {
        try {
            System.out.println("Received payload: " + payload);

            // Pobieranie i konwersja studentów
            Object studentsObj = payload.get("students");
            if (!(studentsObj instanceof List<?>)) {
                return ResponseEntity.badRequest().body("Invalid student list format.");
            }

            List<?> rawStudentIds = (List<?>) studentsObj;
            List<Integer> studentIds = rawStudentIds.stream()
                    .filter(item -> item instanceof Number)
                    .map(item -> ((Number) item).intValue())
                    .toList();

            // Pobieranie i konwersja `classId`
            Object classIdObj = payload.get("classId");
            Integer classId;

            if (classIdObj instanceof String) {
                try {
                    classId = Integer.parseInt((String) classIdObj);
                } catch (NumberFormatException e) {
                    return ResponseEntity.badRequest().body("Invalid class ID format.");
                }
            } else if (classIdObj instanceof Integer) {
                classId = (Integer) classIdObj;
            } else {
                return ResponseEntity.badRequest().body("Class ID is missing or incorrectly formatted.");
            }

            System.out.println("✅ Received classId: " + classId);
            System.out.println("✅ Received studentIds: " + studentIds);

            if (classId == null) {
                return ResponseEntity.badRequest().body("Class ID is missing.");
            }

            Optional<TClass> optionalTClass = classDAO.findById(classId);
            if (optionalTClass.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Class not found.");
            }

            TClass tClass = optionalTClass.get();
            List<User> students = userDAO.findByUserIdIn(studentIds);

            if (students.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No students found.");
            }

            // 🔹 Dodaj studentów do tabeli `t_user_class`
            List<UserClass> userClassList = new ArrayList<>();
            for (User student : students) {
                student.setTClass(tClass); // Aktualizacja t_class w t_user

                UserClassId userClassId = new UserClassId(student.getUserId(), classId);
                UserClass userClass = new UserClass(userClassId, student, tClass);
                userClassList.add(userClass);
            }

            // Zapisanie studentów w `t_user`
            userDAO.saveAll(students);

            // Zapisanie rekordów w `t_user_class`
            userClassDAO.saveAll(userClassList);

            return ResponseEntity.ok("Students successfully added to the class.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding students: " + e.getMessage());
        }
    }






//    @GetMapping("/get-teacher-class")
//    public ResponseEntity<Map<String, Object>> getTeacherClass(HttpSession session) {
//        User teacher = (User) session.getAttribute(SessionConstants.USER_KEY);
//
//        if (teacher == null || teacher.getRole() != User.Role.TEACHER) {
//            System.out.println("🔴 Błąd: Użytkownik niezalogowany lub nie jest nauczycielem.");
//            return ResponseEntity.status(403).build();
//        }
//
//        System.out.println("🟢 Użytkownik nauczyciel: " + teacher.getUserId());
//
//        Optional<TClass> existingClass = classDAO.findByCreator(teacher);
//
//        if (existingClass.isEmpty()) {
//            System.out.println("🔴 Brak klasy dla nauczyciela ID: " + teacher.getUserId());
//            return ResponseEntity.ok(Collections.singletonMap("className", null));
//        }
//
//        TClass tClass = existingClass.get();
//        System.out.println("🟢 Znaleziono klasę: " + tClass.getClassName());
//
//        List<Map<String, String>> students = tClass.getUsers().stream()
//                .map(userClass -> Map.of(
//                        "name", userClass.getUser().getName(),
//                        "surname", userClass.getUser().getSurname()
//                ))
//                .toList();
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("classId", tClass.getClassId());
//        response.put("className", tClass.getClassName());
//        response.put("students", students);
//
//        return ResponseEntity.ok(response);
//    }

    @GetMapping("/get-teacher-class")
    public ResponseEntity<Map<String, Object>> getTeacherClass(HttpSession session) {
        User teacher = (User) session.getAttribute(SessionConstants.USER_KEY);

        if (teacher == null || teacher.getRole() != User.Role.TEACHER) {
            return ResponseEntity.status(403).build();
        }

        Optional<TClass> existingClass = classDAO.findByCreator(teacher);

        if (existingClass.isEmpty()) {
            return ResponseEntity.ok(Collections.singletonMap("classId", null));
        }

        TClass tClass = existingClass.get();
        Map<String, Object> response = new HashMap<>();
        response.put("classId", tClass.getClassId()); // ✅ Sprawdź, czy zwracane!
        response.put("className", tClass.getClassName());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{classId}")
    public ResponseEntity<Map<String, Object>> getClassGameStats(@PathVariable Integer classId) {
        // Sprawdzamy, czy klasa istnieje
        Optional<TClass> optionalTClass = classDAO.findById(classId);
        if (optionalTClass.isEmpty()) {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", "Class not found"));
        }

        TClass tClass = optionalTClass.get();
        List<User> students = tClass.getUsers().stream()
                .map(userClass -> userClass.getUser()) // Pobieramy użytkowników z `t_user_class`
                .toList();

        if (students.isEmpty()) {
            return ResponseEntity.ok(Collections.singletonMap("message", "No students found in this class"));
        }

        // Pobieramy statystyki dla wszystkich uczniów w klasie
        List<Map<String, Object>> studentStatsList = new ArrayList<>();

        for (User student : students) {
            List<GameStats> studentStats = classGameStatsService.getGameStatsByUser(student.getUserId());

            List<Map<String, Object>> gameStatsList = studentStats.stream().map(gs -> {
                Map<String, Object> gameStatsMap = new HashMap<>();
                gameStatsMap.put("gameStatsId", gs.getGameStatsId());
                gameStatsMap.put("gameId", gs.getGame().getGameId());
                gameStatsMap.put("gameName", gs.getGame().getGameName().name());
                gameStatsMap.put("gameDate", gs.getGameDate().toString());
                gameStatsMap.put("correctAnswer", gs.getCorrectAnswer());
                gameStatsMap.put("incorrectAnswer", gs.getIncorrectAnswer());
                return gameStatsMap;
            }).collect(Collectors.toList());

            Map<String, Object> studentData = new HashMap<>();
            studentData.put("studentName", student.getName() + " " + student.getSurname());
            studentData.put("login", student.getLogin());
            studentData.put("gameStats", gameStatsList);

            studentStatsList.add(studentData);
        }

        // Tworzymy mapę odpowiedzi
        Map<String, Object> response = new HashMap<>();
        response.put("className", tClass.getClassName());
        response.put("students", studentStatsList);

        return ResponseEntity.ok(response);
    }
}



