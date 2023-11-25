package com.example.streamserver.service;

import com.example.streamserver.entity.AppRole;
import com.example.streamserver.entity.User;
//import com.example.streamserver.repository.AppRoleRepository;
import com.example.streamserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
//    private final AppRoleRepository appRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with or email: "+ email));

//        Set<GrantedAuthority> authorities = new HashSet<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

//        System.out.println(user.getEmail()+user.getRoles().getRoleName());
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
//        Set<GrantedAuthority> authorities = user
//                .getRoles()
//                .stream()
//                .map((role) -> new SimpleGrantedAuthority("ROLE_"+role.getRoleName())).collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getEncrytedPassword(),
                authorities);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
//    public List<AppRole> getAllRoles() {
//        return appRoleRepository.findAll();
//    }
}
