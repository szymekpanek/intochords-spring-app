//package pl.wszib.edu.pl.intochordsspringapp.DataInitTest;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.util.DigestUtils;
//import pl.wszib.edu.pl.intochordsspringapp.dao.*;
//import pl.wszib.edu.pl.intochordsspringapp.dao.H2Initialize.DataInitialization;
//import pl.wszib.edu.pl.intochordsspringapp.model.dbo.*;
//
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//public class DataInitializationTest {
//
//    @Mock
//    private UserDAO userDAO;
//
//    @Mock
//    private GameDAO gameDAO;
//
//    @Mock
//    private GameStatsDAO gameStatsDAO;
//
//    @Mock
//    private ClassDAO classDAO;
//
//    @Mock
//    private UserClassDAO userClassDAO;
//
//    @InjectMocks
//    private DataInitialization dataInitialization;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testRun() throws Exception {
//        // Przygotuj dane testowe
//        User nauczyciel = new User(null, 1, "janusz", DigestUtils.md5DigestAsHex("janusz123".getBytes()), User.Role.USER);
//        when(userDAO.save(any(User.class))).thenReturn(nauczyciel);
//
//        Game intervalGame = new Game(null, Game.GameName.IntervalGame);
//        when(gameDAO.save(any(Game.class))).thenReturn(intervalGame);
//        when(gameDAO.findByGameName(Game.GameName.IntervalGame)).thenReturn(Optional.of(intervalGame));
//
//        TClass classA = new TClass(1, "Class A", nauczyciel);
//        when(classDAO.save(any(TClass.class))).thenReturn(classA);
//
//        // Uruchom metodę run
//        dataInitialization.run();
//
//        // Zweryfikuj, czy metody save zostały wywołane
//        verify(userDAO, times(4)).save(any(User.class));
//        verify(gameDAO, times(2)).save(any(Game.class));
//        verify(gameStatsDAO, times(2)).save(any(GameStats.class));
//        verify(classDAO, times(2)).save(any(TClass.class));
//        verify(userClassDAO, times(2)).save(any(UserClass.class));
//    }
//}
