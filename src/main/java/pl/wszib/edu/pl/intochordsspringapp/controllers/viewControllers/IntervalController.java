//package pl.wszib.edu.pl.intochordsspringapp.controllers;
//
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import pl.wszib.edu.pl.intochordsspringapp.dao.GameDAO;
//import pl.wszib.edu.pl.intochordsspringapp.dao.GameStatsDAO;
//import pl.wszib.edu.pl.intochordsspringapp.model.Interval;
//import pl.wszib.edu.pl.intochordsspringapp.model.dbo.Game;
//import pl.wszib.edu.pl.intochordsspringapp.model.dbo.GameStats;
//import pl.wszib.edu.pl.intochordsspringapp.model.dbo.User;
//import pl.wszib.edu.pl.intochordsspringapp.services.impl.IntervalGameServices;
//import pl.wszib.edu.pl.intochordsspringapp.session.SessionConstants;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//@Controller
//public class IntervalController {
//    private final IntervalGameServices intervalGameServices;
//    private final GameStatsDAO gameStatsDAO;
//    private final GameDAO gameDAO;
//
//    @Autowired
//    public IntervalController(IntervalGameServices intervalGameServices, GameStatsDAO gameStatsDAO, GameDAO gameDAO) {
//        this.intervalGameServices = intervalGameServices;
//        this.gameStatsDAO = gameStatsDAO;
//        this.gameDAO = gameDAO;
//    }
//
//    @GetMapping("/interval-game")
//    public String showIntervalGame(Model model, HttpSession httpSession) {
//        Optional<Interval> randomIntervalOptional = intervalGameServices.getRandomInterval();
//        if (randomIntervalOptional.isPresent()) {
//            Interval randomInterval = randomIntervalOptional.get();
//            model.addAttribute("intervals", intervalGameServices.getAllIntervals());
//            model.addAttribute("randomInterval", randomInterval);
//            model.addAttribute("sounds", intervalGameServices.findSoundsForRandomInterval(randomIntervalOptional));
//
//            httpSession.setAttribute("randomInterval", randomInterval.getName());
//        }
//        return "/interval-game";
//    }
//
//    @PostMapping("/check")
//    public String checkAnswer(HttpSession httpSession,
//                              @RequestParam String userAnswer,
//                              Model model,
//                              RedirectAttributes redirectAttributes) {
//
//        User loggedInUser = (User) httpSession.getAttribute(SessionConstants.USER_KEY);
//        boolean isAuthenticated = loggedInUser != null;
//
//        String randomInterval = (String) httpSession.getAttribute("randomInterval");
//        boolean isCorrect = intervalGameServices.checkAnswer(userAnswer, randomInterval);
//
//        if (isAuthenticated) {
//            Game intervalGame = gameDAO.findByGameName(Game.GameName.IntervalGame)
//                    .orElseThrow(() -> new RuntimeException("Game not found"));
//
////            GameStats gameStats = new GameStats(null, loggedInUser, intervalGame, LocalDateTime.now(),
//                    isCorrect ? 1 : 0, isCorrect ? 0 : 1);
//
////            gameStatsDAO.save(gameStats);
//        }
//
//        redirectAttributes.addFlashAttribute("result", isCorrect ? "Correct" : "Not correct");
//        httpSession.removeAttribute("randomInterval");
//
//        return "redirect:/interval-game";
//    }
//}
