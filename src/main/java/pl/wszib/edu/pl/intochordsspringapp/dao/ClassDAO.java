package pl.wszib.edu.pl.intochordsspringapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wszib.edu.pl.intochordsspringapp.model.dbo.TClass;

public interface ClassDAO extends JpaRepository<TClass, Integer> {
}

