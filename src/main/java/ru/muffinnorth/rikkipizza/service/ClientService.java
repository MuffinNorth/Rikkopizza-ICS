package ru.muffinnorth.rikkipizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.muffinnorth.rikkipizza.model.Client;
import ru.muffinnorth.rikkipizza.repository.ClientRepo;

@Service
public class ClientService {

    @Autowired
    private ClientRepo repository;

}
