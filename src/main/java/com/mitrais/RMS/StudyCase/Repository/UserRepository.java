package com.mitrais.RMS.StudyCase.Repository;

import com.mitrais.RMS.StudyCase.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByName(@NotNull String name);
    User findByNameAndPassword(String name, String password);
}