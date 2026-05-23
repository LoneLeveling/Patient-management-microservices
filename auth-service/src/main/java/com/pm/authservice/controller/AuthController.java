package com.pm.authservice.controller;

import com.pm.authservice.dto.LoginRequestDTO;
import com.pm.authservice.dto.LoginResponseDTO;
import com.pm.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService)
    {
        this.authService=authService;
    }


    @Operation(summary = "Generate token on user login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO)
    {
        Optional<String> tokenOptional = authService.authenticate(loginRequestDTO);
     //An Optional object in java means that this is going to be either empty or its going to contain a String

        if(tokenOptional.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = tokenOptional.get();
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @Operation(summary = "Validate Token")
    @GetMapping("/validate")
    public ResponseEntity<Void> validateToken(@RequestHeader("Authorization") String authHeader)
    {
        //Authorization: Bearer<Token>
        if(authHeader==null ||!authHeader.startsWith("Bearer"))
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }
        return authService.validateToken(authHeader.substring(7)) //7 because no. of characters =7 in String bearer+ a space
                ? ResponseEntity.ok().build()
                :ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
