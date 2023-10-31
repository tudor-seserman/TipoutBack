package com.tipout.Tipout.controllers;

import com.tipout.Tipout.models.DTOs.AuthResponseDTO;
import com.tipout.Tipout.models.DTOs.LoginFormDTO;
import com.tipout.Tipout.models.DTOs.LoginRequest;
import com.tipout.Tipout.models.DTOs.RegistrationFormDTO;
import com.tipout.Tipout.models.Employer;
import com.tipout.Tipout.models.UserEntity;
import com.tipout.Tipout.models.data.EmployerRepository;
import com.tipout.Tipout.models.data.UserRepository;
import com.tipout.Tipout.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/*
Controls authentication for Employers currently
 */
@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private UserRepository userRepository;
    private EmployerRepository employerRepository;
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationController(UserRepository userRepository, EmployerRepository employerRepository,
            AuthenticationManager authenticationManager, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.employerRepository = employerRepository;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "register", method = POST, produces = "application/json")
    public HttpStatus processRegistrationForm(@RequestBody @Valid RegistrationFormDTO employerRegistrationFormDTO,
            Errors errors) {

        if (errors.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create account");
        }

        Optional<UserEntity> existingUser = userRepository.findByUsername(employerRegistrationFormDTO.getUsername());

        if (existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "A user with that username already exists, please select another.");
        }

        String password = employerRegistrationFormDTO.getPassword();
        String verifyPassword = employerRegistrationFormDTO.getVerifyPassword();

        // Makes sure that the passwords match up.
        if (!password.equals(verifyPassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }


        Employer newUser = new Employer(employerRegistrationFormDTO.getBusinessName(),
                employerRegistrationFormDTO.getUsername(), passwordEncoder.encode(employerRegistrationFormDTO.getPassword()));

        userRepository.save(newUser);

        return HttpStatus.OK;
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequest loginRequest) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.username(),
                            loginRequest.password()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = tokenService.generateToken(authentication);
            return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
        }catch (AuthenticationException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
