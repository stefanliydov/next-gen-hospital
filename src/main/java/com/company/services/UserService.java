package com.company.services;

import com.company.constants.Errors;
import com.company.data.dtos.RegisterUserModel;
import com.company.data.entities.Role;
import com.company.data.entities.User;
import com.company.enums.RoleType;
import com.company.errors.exceptions.RoleNotFoundException;
import com.company.errors.exceptions.UsernameAlreadyTakenException;
import com.company.repositories.RoleRepository;
import com.company.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * Register a new User.
     *
     * @param registerUserModel DNA test from where we can retrieve all Patient info.
     * @return Registered user.
     */
    public User registerUser(final RegisterUserModel registerUserModel) {
        validateUsernameIsFree(registerUserModel.getUsername());
        registerUserModel.setPassword(passwordEncoder.encode(registerUserModel.getPassword()));
        final User user = this.modelMapper.map(registerUserModel, User.class);
        final Role roleType = roleService.findByRoleType(registerUserModel.getRole());
        user.setRoleTypes(Arrays.asList(roleType));
        return this.userRepository.saveAndFlush(user);
    }

    public UserDetails loadUserByUsername(String username) {
        return this.getUserByUsername(username);
    }

    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(Errors.USERNAME_NOT_FOUND));
    }

    private void validateUsernameIsFree(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new UsernameAlreadyTakenException(Errors.USERNAME_ALREADY_TAKEN);
        }
    }

}
