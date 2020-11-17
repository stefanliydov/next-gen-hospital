package com.company.controllers;

import com.company.constants.Success;
import com.company.data.dtos.RegisterUserModel;
import com.company.data.entities.User;
import com.company.data.response.SuccessResponse;
import com.company.services.UserService;
import com.sun.net.httpserver.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/register")
public class RegisterController {

    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public RegisterController(UserService userService, ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.eventPublisher = eventPublisher;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<SuccessResponse> register(@RequestBody RegisterUserModel registerUserModel) {
        User registered = this.userService.registerUser(registerUserModel);
        return new ResponseEntity<>(new SuccessResponse(Success.USER_CREATED), HttpStatus.CREATED);
    }

}