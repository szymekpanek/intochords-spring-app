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
        intervalList.add(new Interval("Minor Second", 1));
        intervalList.add(new Interval("Major Second", 2));
        intervalList.add(new Interval("Minor Third", 3));
        intervalList.add(new Interval("Major Third", 4));
        intervalList.add(new Interval("Perfect Fourth", 5));
        intervalList.add(new Interval("Augmented Fourth / Diminished Fifth", 6));
        intervalList.add(new Interval("Perfect Fifth", 7));
        intervalList.add(new Interval("Minor Sixth", 8));
        intervalList.add(new Interval("Major Sixth", 9));
        intervalList.add(new Interval("Minor Seventh", 10));
        intervalList.add(new Interval("Major Seventh", 11));
        intervalList.add(new Interval("Octave", 12));
    }
}
