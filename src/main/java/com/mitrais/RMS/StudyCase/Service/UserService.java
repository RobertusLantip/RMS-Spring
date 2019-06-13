package com.mitrais.RMS.StudyCase.Service;

import com.mitrais.RMS.StudyCase.Model.User;
import com.mitrais.RMS.StudyCase.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public boolean createUser(User user){
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
            updateuser.setPassword(user.getPassword());
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

}