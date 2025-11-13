package ca.kpu.info2413.library.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload, HttpServletRequest request) {
        String email = payload.get("notificationEmail");
        String password = payload.get("password");

        if (email == null || password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email and password required");
        }

        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(email, password);
            Authentication auth = authenticationManager.authenticate(token);

            // Set authentication into SecurityContext -> placed into the HTTP session
            SecurityContextHolder.getContext().setAuthentication(auth);
            // create session if not exist (this sets JSESSIONID cookie)
            request.getSession(true);

            Map<String, Object> result = new HashMap<>();
            result.put("message", "Login successful");
            // you can return user info from auth.getPrincipal() if needed
            return ResponseEntity.ok(result);
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    // Optional: logout endpoint
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        request.getSession(false); // optional check for session
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(Map.of("message", "Logged out"));
    }
}
