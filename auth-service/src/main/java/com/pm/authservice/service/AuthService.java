package com.pm.authservice.service;

import com.pm.authservice.dto.LoginRequestDTO;
import com.pm.authservice.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    //Now here we need to call the UserService to find the user by email as we receive it in the
    //backend request DTO,So we use the DI approach,
    // i.e.,  we inject the UserService class into the AuthService class using DI same as how we injected Repository layer into UserService class
private final UserService userService;
public AuthService(UserService userService, PasswordEncoder passwordEncoder)
{
    this.userService=userService;
    this.passwordEncoder=passwordEncoder;
}


//Here in the service class we create an Authenticate method that holds all the logic
//to authenticate the user, the authenticate methods takes in the login request DTO
// that gets passed from the controller.


    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO)
    {
 //1st thing we do in here: Get the user from the db it it exists** based on the email Id we received from the login request DTO.
  Optional<String> token = userService.findByEmail(loginRequestDTO.getEmail())
          .filter(u-> passwordEncoder.matches(loginRequestDTO.getPassword(),
                  u.getPassword()))
          .map(u->jwtUtil.generateToken(u.getEmail(),u.getRole()));

  return token;
    }
}
