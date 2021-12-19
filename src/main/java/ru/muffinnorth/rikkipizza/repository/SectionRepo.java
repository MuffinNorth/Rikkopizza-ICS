package ru.muffinnorth.rikkipizza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.muffinnorth.rikkipizza.model.Section;

import java.util.List;

public interface SectionRepo extends JpaRepository<Section, Integer> {
    List<Section> findAllByTitle(String title);
}
