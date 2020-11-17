package com.company.controllers;


import com.company.data.dtos.LoginUserModel;
import com.company.data.response.AuthenticationResponse;
import com.company.services.UserService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/login")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtTokenUtil;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AuthenticationResponse> login(@RequestBody final LoginUserModel loginUserModel) {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserModel.getUsername(), loginUserModel.getPassword()));
        final UserDetails userDetails = this.userService.loadUserByUsername(loginUserModel.getUsername());
        final String jwt = jwtTokenUtil.generateToken((userDetails));
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
