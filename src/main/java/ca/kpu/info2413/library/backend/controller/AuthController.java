package ca.kpu.info2413.library.backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthController
{

    // Optional explicit logout endpoint (Spring Security already handles /logout if configured)
    @PostMapping("/api/auth/logout")
    public ResponseEntity<?> logout(HttpServletRequest request)
    {
        try
        {
            var session = request.getSession(false);
            if (session != null) session.invalidate();
        }
        catch (Exception ignored)
        {
        }

        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(Map.of("message", "Logged out"));
    }
}