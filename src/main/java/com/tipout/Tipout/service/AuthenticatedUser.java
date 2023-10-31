package com.tipout.Tipout.service;

import com.tipout.Tipout.models.Employer;
import com.tipout.Tipout.models.UserEntity;
import com.tipout.Tipout.models.data.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AuthenticatedUser {

EmployerRepository employerRepository;
    @Autowired
    public AuthenticatedUser(EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }

    public UserEntity getUser() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Employer> optionalEmployer = employerRepository.findByUsername(name);
        if (optionalEmployer.isEmpty()) {
           return null;
        }
        return optionalEmployer.get();
    };
}

