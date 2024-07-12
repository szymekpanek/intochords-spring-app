package pl.wszib.edu.pl.intochordsspringapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wszib.edu.pl.intochordsspringapp.memory.IntervalDB;
import pl.wszib.edu.pl.intochordsspringapp.memory.SoundDB;
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

    public Optional<Interval> getRandomInterval (){
        Random random = new Random();
        return intervalDB.getIntervalList().stream()
                .skip(random.nextInt(intervalDB.getIntervalList().size()))
                .findFirst();
    }

    private Optional<Sound> getRandomSound() {
        Random random = new Random();
        List<Sound> sounds = soundDB.getSounds();

        int limit = Math.min(12, sounds.size());
        if (limit == 0) {
            return Optional.empty();
        }

        int randomIndex = random.nextInt(limit);
        return Optional.of(sounds.get(randomIndex));
    }

    public List<Sound> findSoundsForRandomInterval(Optional<Interval> randomInterval) {
        Optional<Sound> firstSound = getRandomSound();

        if (randomInterval.isPresent() && firstSound.isPresent()) {
            Sound sound = firstSound.get();

            System.out.println("Interwał wylosowany w serwisach: " + randomInterval.get().getName());

            int semitones = randomInterval.get().getSemitones();


            System.out.println("Semitones: " + semitones);

            int firstSoundIndex = soundDB.getSounds().indexOf(sound);
            int secondSoundIndex = (firstSoundIndex + semitones) % soundDB.getSounds().size();

            if (secondSoundIndex < 0) {
                secondSoundIndex += soundDB.getSounds().size();
            }

            Sound secondSound = soundDB.getSounds().get(secondSoundIndex);

            List<Sound> result = new ArrayList<>();
            result.add(sound);
            result.add(secondSound);

            System.out.println("Wylosowane dzwieki: " + sound.getName());
            System.out.println("Wylosowane dzwieki: " + secondSound.getName());

            return result;
        }
        return new ArrayList<>();
    }

    public boolean checkAnswer(String answer, String randomInterval){
        return randomInterval != null && randomInterval.equals(answer);
    }


}