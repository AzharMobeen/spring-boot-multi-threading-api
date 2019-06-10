package com.az.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.az.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
