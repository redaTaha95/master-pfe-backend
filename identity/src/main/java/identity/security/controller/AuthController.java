package identity.security.controller;

import identity.security.dto.AuthRequest;
import identity.security.dto.AuthResponse;
import identity.security.dto.UserRequest;
import identity.security.dto.UserResponse;
import identity.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse addNewUser(@RequestBody UserRequest userRequest) {
        return authService.saveUser(userRequest);
    }

    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @GetMapping("validate-token")
    public ResponseEntity validateToken(@RequestParam("token") String token) {
        if (authService.validateToken(token))
            return ResponseEntity.ok().body(Map.of("Token-validation", true));

        return ResponseEntity.badRequest().body(Map.of("Token-validation", false));
    }

    @GetMapping("generate-new-token")
    public ResponseEntity generateNewToken(@RequestParam("refreshToken") String refreshToken, @RequestParam("username") String username) {
        if (authService.validateRefreshToken(refreshToken))
            return ResponseEntity.ok().body(Map.of("token", authService.generateToken(username)));

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "invalidate refresh token"));
    }
}
