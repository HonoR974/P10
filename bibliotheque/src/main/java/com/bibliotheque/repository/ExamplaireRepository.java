package com.bibliotheque.repository;

import com.bibliotheque.model.Examplaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamplaireRepository extends JpaRepository<Examplaire,Long> {
    Examplaire findById(long id);

}
