package pl.wszib.edu.pl.intochordsspringapp.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wszib.edu.pl.intochordsspringapp.dao.SoundDB;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

@Service
public class IntervalGameServices {
//    @Autowired
//    SoundDB soundDB;
//    @Getter
//    Map<String, String> intervalsMap = new LinkedHashMap<>();
//    public void Intervals_Map(){
//        intervalsMap.put("U", "Unison");
//        intervalsMap.put("m2", "Minor second");
//        intervalsMap.put("M2", "Major second");
//        intervalsMap.put("m3", "Minor third");
//        intervalsMap.put("M3", "Major third");
//        intervalsMap.put("P4", "Perfect fourth");
//        intervalsMap.put("T", "Triton");
//        intervalsMap.put("P5", "Perfect fifth");
//        intervalsMap.put("m6", "Minor sixth");
//        intervalsMap.put("M6", "Major sixth");
//        intervalsMap.put("m7", "Minor seventh");
//        intervalsMap.put("M7", "Major seventh");
//        intervalsMap.put("8", "Octave");
//    }
//
//    public void printIntervals() {
//        for (String interval : intervalsMap.values()) {
//            System.out.println(interval);
//        }
//    }
//
//    public void showIntervalsMap() {
//        intervalsMap.entrySet().stream()
//                .map(entry -> entry.getKey() + " = " + entry.getValue())
//                .forEach(System.out::println);
//    }
//
//    public String getRandomKey() {
//        return intervalsMap.keySet()
//                .stream()
//                .skip(new Random().nextInt(intervalsMap.size()))
//                .findFirst()
//                .orElse(null);
//    }

}
