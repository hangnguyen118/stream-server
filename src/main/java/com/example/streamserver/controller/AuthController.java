package com.example.streamserver.controller;

import com.example.streamserver.dto.UserDto;
import com.example.streamserver.entity.*;
import com.example.streamserver.repository.UserRepository;
import com.example.streamserver.service.CustomUserDetailsService;
import com.example.streamserver.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
//    private final AppRoleRepository roleRepository;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<UserDto> authenticateUser(@RequestBody LoginDto loginDto, HttpServletResponse response){
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
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with or email: "+ email));
        UserDto userDto = new UserDto(user.getUsername(), user.getEmail(), user.getPhone());
        return ResponseEntity.ok(userDto);
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

        user.setRole(AppRole.USER);
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
