package com.mitrais.RMS.StudyCase.Service;

import com.mitrais.RMS.StudyCase.Model.MyUserPrincipal;
import com.mitrais.RMS.StudyCase.Model.User;
import com.mitrais.RMS.StudyCase.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetails implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByName(s);
        if (user == null) {
            throw new UsernameNotFoundException(s);
        }
        return new MyUserPrincipal(user);
    }
}
