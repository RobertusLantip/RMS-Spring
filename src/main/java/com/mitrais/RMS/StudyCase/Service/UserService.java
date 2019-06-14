package com.mitrais.RMS.StudyCase.Service;

import com.mitrais.RMS.StudyCase.Config.SpringSecurityConfiguration;
import com.mitrais.RMS.StudyCase.Model.User;
import com.mitrais.RMS.StudyCase.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    SpringSecurityConfiguration springSecurityConfiguration;

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public boolean createUser(User user){
        user.setPassword(springSecurityConfiguration.passwordEncoder().encode(user.getPassword()));
        boolean result;
        if(user.getName() != "" || user.getPassword() != ""){
            userRepository.saveAndFlush(user);
            result = true;
        }else {
            result = false;
        }
        return result;
    }

    public User findUserById(int id){
        return userRepository.findById(id).orElse(null);
    }

    public boolean deleteUser(int id){
        User user = userRepository.findById(id).orElse(null);
        userRepository.delete(user);
        return true;
    }

    public boolean updateUser(User user){
        boolean result;
        User updateuser = userRepository.findById(user.getId()).orElse(null);
        if(updateuser != null){
            updateuser.setName(user.getName());
            updateuser.setRole(user.getRole());
            this.userRepository.save(updateuser);
            result = true;
        }else {
            result = false;
        }
        return result;
    }

    public String getRole(int id){
        User user = userRepository.findById(id).orElse(null);
        return user.getRole();
    }

    public User getUser(String username, String password){
        User user = userRepository.findByNameAndPassword(username,password);
        return user;
    }

    public boolean changePassword(User user){
        boolean result;
        User updatepass = userRepository.findById(user.getId()).orElse(null);
        if(updatepass != null){
            updatepass.setPassword(springSecurityConfiguration.passwordEncoder().encode(user.getPassword()));
            this.userRepository.save(updatepass);
            result = true;
        }else {
            result = false;
        }
        return result;
    }


}