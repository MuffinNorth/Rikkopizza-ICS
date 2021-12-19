package ru.muffinnorth.rikkipizza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.muffinnorth.rikkipizza.model.Item;

public interface ItemRepo extends JpaRepository<Item, Integer> {
}
