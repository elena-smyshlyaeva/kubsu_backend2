package com.example.kubsu.repository;

import com.example.kubsu.model.SuperPower;
import com.example.kubsu.model.User;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);

    Integer countUsersBySuperPower(String superPower);
}
