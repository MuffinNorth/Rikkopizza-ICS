package ru.muffinnorth.rikkipizza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.muffinnorth.rikkipizza.model.Client;

public interface ClientRepo extends JpaRepository<Client, Integer> {
}
