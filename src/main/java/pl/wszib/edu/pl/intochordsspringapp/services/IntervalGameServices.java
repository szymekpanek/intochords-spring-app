package pl.wszib.edu.pl.intochordsspringapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wszib.edu.pl.intochordsspringapp.dao.IntervalDB;
import pl.wszib.edu.pl.intochordsspringapp.dao.SoundDB;
import pl.wszib.edu.pl.intochordsspringapp.model.Interval;
import pl.wszib.edu.pl.intochordsspringapp.model.Sound;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class IntervalGameServices {

    @Autowired
    private IntervalDB intervalDB;

    @Autowired
    private SoundDB soundDB;

    public List<Interval> getAllIntervals() {
        return intervalDB.getIntervalList();
    }

    private Optional<Interval> getRandomInterval (){
        Random random = new Random();
        return intervalDB.getIntervalList().stream()
                .skip(random.nextInt(intervalDB.getIntervalList().size()))
                .findFirst();
    }

    private Optional<Sound> getRandomSound(){
        Random random = new Random();
        return soundDB.getSounds().stream()
                .skip(random.nextInt(soundDB.getSounds().size()))
                .findFirst();
    }

    public List<Sound> findSoundsForRandomInterval() {
        Optional<Interval> randomInterval = getRandomInterval();
        Optional<Sound> firstSound = getRandomSound();

        if (randomInterval.isPresent() && firstSound.isPresent()) {
            Interval interval = randomInterval.get();
            Sound sound = firstSound.get();

            int semitones = interval.getSemitones();
            int firstSoundIndex = soundDB.getSounds().indexOf(sound);
            int secondSoundIndex = (firstSoundIndex + semitones) % soundDB.getSounds().size();

            Sound secondSound = soundDB.getSounds().get(secondSoundIndex);

            List<Sound> result = new ArrayList<>();
            result.add(sound);
            result.add(secondSound);

            return result;
        }
        return new ArrayList<>();
    }


}
