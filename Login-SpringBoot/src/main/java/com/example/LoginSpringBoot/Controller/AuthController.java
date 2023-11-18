package com.example.LoginSpringBoot.Controller;

import com.example.LoginSpringBoot.Configuration.AuthenticationRequest;
import com.example.LoginSpringBoot.Configuration.AuthenticationResponse;
import com.example.LoginSpringBoot.Configuration.RegisterRequest;
import com.example.LoginSpringBoot.Model.User;
import com.example.LoginSpringBoot.UserService.AuthenticationService;
import com.example.LoginSpringBoot.dto.navBarLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse>register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse>register(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    public navBarLogin getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String firstname=authentication.getName();
        return new navBarLogin(firstname);
    }

    @GetMapping("/UserProfile")
    public User getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return authenticationService.getUserByEmail(email);
    }
}
