//package com.casestudy.pharma.controller;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.casestudy.pharma.utils.JwtUtils;
//
//import lombok.Getter;
//import lombok.Setter;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    private final JwtUtils jwtUtils;
//    private final PasswordEncoder passwordEncoder;
//
//    public AuthController(JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
//        this.jwtUtils = jwtUtils;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
//        // For simplicity, use hardcoded credentials. Replace with UserDetailsService logic.
//        if ("user".equals(request.getUsername()) && passwordEncoder.matches(request.getPassword(), passwordEncoder.encode("password"))) {
//            String token = jwtUtils.generateToken(request.getUsername());
//            return ResponseEntity.ok(token);
//        }
//        return ResponseEntity.status(401).body("Invalid credentials");
//    }
//}
//
//@Getter
//@Setter
//class LoginRequest {
//    private String username;
//    private String password;
//
//    // Getters and setters
//}
