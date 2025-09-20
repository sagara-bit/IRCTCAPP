package com.example.irctc.irctc_backend.config.Security;

import com.example.irctc.irctc_backend.Entity.User;
import com.example.irctc.irctc_backend.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        UserDetails userDetails =  User.builder()
//                .username("user")
//                .password("{noop}user123")
//                .roles("USER")
//                .build();
//         if(userDetails.getUsername().equals(username)){
//             return  userDetails;
//         }else{
//             throw  new UsernameNotFoundException("user not found");
//         }
     User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
     CustomUserDetail customUserDetail = new CustomUserDetail(user);
     return customUserDetail;


    }
}
