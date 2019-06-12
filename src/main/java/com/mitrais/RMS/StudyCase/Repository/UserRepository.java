package com.mitrais.RMS.StudyCase.Repository;

import com.mitrais.RMS.StudyCase.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

}
