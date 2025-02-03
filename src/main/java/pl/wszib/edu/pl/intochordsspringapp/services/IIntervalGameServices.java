package pl.wszib.edu.pl.intochordsspringapp.services;

import pl.wszib.edu.pl.intochordsspringapp.model.Interval;
import pl.wszib.edu.pl.intochordsspringapp.model.Sound;

import java.util.List;
import java.util.Optional;

public interface IIntervalGameServices {
    Optional<Sound> getRandomSound();
    Optional<Interval> getRandomInterval ();
    List<Sound> findSoundsForRandomInterval(Optional<Interval> randomInterval);
}
