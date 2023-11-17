package com.example.streamserver.controller;

import com.example.streamserver.entity.*;
import com.example.streamserver.repository.AppRoleRepository;
import com.example.streamserver.repository.UserRepository;
import com.example.streamserver.service.CustomUserDetailsService;
import com.example.streamserver.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AppRoleRepository roleRepository;

    @Autowired
    JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody LoginDto loginDto, HttpServletResponse response){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String email = authentication.getName();
        String accessToken = jwtService.generateToken(email);

        Cookie cookie = new Cookie("accessToken", accessToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(false);

        response.addCookie(cookie);
        System.out.println("Người dùng " + email + " đã đăng nhập thành công!");
        AuthResponse res = new AuthResponse("Đăng nhập thành công");
        return ResponseEntity.ok(res);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody SignUpDto signUpDto, HttpServletResponse response){
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return ResponseEntity.badRequest().body(new AuthResponse("Email đã có tài khoản"));
        }
        User user = new User();
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setEncrytedPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setPhone(signUpDto.getPhone());
        user.setEnabled(true);

        AppRole roles = roleRepository.findByRolename("USER").get();
        user.setRoles(Collections.singleton(roles));
        userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtService.generateToken(user.getEmail());

        Cookie cookie = new Cookie("accessToken", accessToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(false);

        response.addCookie(cookie);
        System.out.println("Người dùng " + user.getEmail() + " đã đăng kí thành công!");
        return ResponseEntity.ok(new AuthResponse("Đăng ký tài khoản thành công!"));

    }
}
