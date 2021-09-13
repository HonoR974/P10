package com.bibliotheque.repository;

import com.bibliotheque.model.Bibliotheque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface BibliothequeRepository extends JpaRepository<Bibliotheque,Long> {

    Bibliotheque findById(long id);

    Bibliotheque findByNom(String nom);

    List<Bibliotheque> findAll();
}
