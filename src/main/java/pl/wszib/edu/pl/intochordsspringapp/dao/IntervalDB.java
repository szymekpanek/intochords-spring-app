package pl.wszib.edu.pl.intochordsspringapp.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;
import pl.wszib.edu.pl.intochordsspringapp.model.Interval;

import java.util.ArrayList;
import java.util.List;

@Repository
@Getter
@Setter
@AllArgsConstructor
public class IntervalDB {
    List<Interval> intervalList = new ArrayList<>();

    public IntervalDB() {
        intervalList.add(new Interval("Unison", 0));
        intervalList.add(new Interval("Minor 2nd", 1));
        intervalList.add(new Interval("Major 2nd", 2));
        intervalList.add(new Interval("Minor 3rd", 3));
        intervalList.add(new Interval("Major 3rd", 4));
        intervalList.add(new Interval("Perfect 4th", 5));
        intervalList.add(new Interval("Tritone", 6));
        intervalList.add(new Interval("Perfect 5th", 7));
        intervalList.add(new Interval("Minor 6th", 8));
        intervalList.add(new Interval("Major 6th", 9));
        intervalList.add(new Interval("Minor 7th", 10));
        intervalList.add(new Interval("Major 7th", 11));
        intervalList.add(new Interval("Octave", 12));
    }
}
