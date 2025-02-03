package pl.wszib.edu.pl.intochordsspringapp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wszib.edu.pl.intochordsspringapp.dao.GameDAO;
import pl.wszib.edu.pl.intochordsspringapp.dao.GameStatsDAO;
import pl.wszib.edu.pl.intochordsspringapp.dao.UserDAO;
import pl.wszib.edu.pl.intochordsspringapp.memory.IntervalDB;
import pl.wszib.edu.pl.intochordsspringapp.memory.SoundDB;
import pl.wszib.edu.pl.intochordsspringapp.model.Interval;
import pl.wszib.edu.pl.intochordsspringapp.model.Sound;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.Game;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.GameStats;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.User;
import pl.wszib.edu.pl.intochordsspringapp.services.IIntervalGameServices;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class IntervalGameServices {

    private final IntervalDB intervalDB;
    private final SoundDB soundDB;
    private final GameDAO gameDAO;
    private final GameStatsDAO gameStatsDAO;
    private final UserDAO userDAO;

    @Autowired
    public IntervalGameServices(IntervalDB intervalDB, SoundDB soundDB, GameDAO gameDAO, GameStatsDAO gameStatsDAO, UserDAO userDAO) {
        this.intervalDB = intervalDB;
        this.soundDB = soundDB;
        this.gameDAO = gameDAO;
        this.gameStatsDAO = gameStatsDAO;
        this.userDAO = userDAO;
    }

    public List<Interval> getAllIntervals() {
        return intervalDB.getIntervalList();
    }

    public Optional<Interval> getRandomInterval() {
        List<Interval> intervals = intervalDB.getIntervalList();
        if (intervals.isEmpty()) return Optional.empty();
        return Optional.of(intervals.get(new Random().nextInt(intervals.size())));
    }

    public Optional<Sound> getRandomSound() {
        List<Sound> sounds = soundDB.getSounds();
        if (sounds.isEmpty()) return Optional.empty();
        return Optional.of(sounds.get(new Random().nextInt(sounds.size())));
    }

    public Map<String, String> getGameData() {
        Optional<Interval> intervalOpt = getRandomInterval();
        Optional<Sound> firstSoundOpt = getRandomSound();

        if (intervalOpt.isPresent() && firstSoundOpt.isPresent()) {
            Interval interval = intervalOpt.get();
            Sound firstSound = firstSoundOpt.get();

            int semitones = interval.getSemitones();
            int firstSoundIndex = soundDB.getSounds().indexOf(firstSound);
            int secondSoundIndex = (firstSoundIndex + semitones) % soundDB.getSounds().size();
            if (secondSoundIndex < 0) {
                secondSoundIndex += soundDB.getSounds().size();
            }

            Sound secondSound = soundDB.getSounds().get(secondSoundIndex);

            Map<String, String> data = new HashMap<>();
            data.put("intervalName", interval.getName());
            data.put("firstSound", firstSound.getPath());
            data.put("secondSound", secondSound.getPath());

            return data;
        }
        return Collections.emptyMap();
    }

    public void saveGameResults(User user, int correct, int incorrect) {
        Game game = gameDAO.findByGameId(1);

        GameStats stats = new GameStats();
        stats.setUser(user);
        stats.setGame(game);
        stats.setGameDate(LocalDateTime.now());
        stats.setCorrectAnswer(correct);
        stats.setIncorrectAnswer(incorrect);

        gameStatsDAO.save(stats);
    }
}
