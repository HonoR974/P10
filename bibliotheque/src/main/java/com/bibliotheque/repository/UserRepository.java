package com.bibliotheque.repository;

import com.bibliotheque.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

@Repository
public interface UserRepository  extends JpaRepository <User, Long> {
    User findByUsername(String username);

    User findById(long id);
}
