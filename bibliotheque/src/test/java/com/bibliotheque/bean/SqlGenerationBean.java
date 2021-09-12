package com.bibliotheque.bean;

import com.bibliotheque.model.Bibliotheque;
import com.bibliotheque.repository.BibliothequeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SqlGenerationBean {

    @Autowired
    private BibliothequeRepository repo;

    @PostConstruct
    public void init() {

        Bibliotheque b1 = new Bibliotheque("B1", "rue1");
        repo.save(b1);
    }
}