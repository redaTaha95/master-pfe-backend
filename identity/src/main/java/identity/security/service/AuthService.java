package identity.security.service;

import identity.security.exception.AuthenticationFailedException;
import identity.security.config.CustomUserDetails;
import identity.security.dto.AuthRequest;
import identity.security.dto.AuthResponse;
import identity.security.dto.UserRequest;
import identity.security.dto.UserResponse;
import identity.security.entity.UserCredential;
import identity.security.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserCredentialRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserResponse saveUser(UserRequest userRequest) {
        UserCredential user = new UserCredential();
        BeanUtils.copyProperties(userRequest, user);

        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        UserCredential savedUser = repository.save(user);

        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(savedUser, userResponse);

        return userResponse;
    }

    public AuthResponse login(AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            AuthResponse authResponse = new AuthResponse();
            authResponse.setUserId(((CustomUserDetails) authenticate.getPrincipal()).getId());
            authResponse.setUsername(((CustomUserDetails) authenticate.getPrincipal()).getUsername());
            authResponse.setEmail(((CustomUserDetails) authenticate.getPrincipal()).getEmail());
            authResponse.setToken(generateToken(authRequest.getUsername()));
            authResponse.setRefreshToken(generateRefreshToken(authRequest.getUsername()));

            return authResponse;
        } else {
            throw new AuthenticationFailedException("invalid credentials");
        }
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    private String generateRefreshToken(String username) {
        return jwtService.generateRefreshToken(username);
    }

    public Boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }

    public Boolean validateRefreshToken(String refreshToken) {
        return jwtService.validateRefreshToken(refreshToken);
    }
}
