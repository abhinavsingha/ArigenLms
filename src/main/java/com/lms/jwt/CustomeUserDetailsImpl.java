package com.lms.jwt;

import com.lms.entities.UsersInfo;
import com.lms.entities.repo.UsersInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class CustomeUserDetailsImpl implements UserDetailsService {

    @Autowired
    private UsersInfoRepo usersInfoRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UsersInfo user =usersInfoRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User Not found !!");
        }
        return user;
    }

}
