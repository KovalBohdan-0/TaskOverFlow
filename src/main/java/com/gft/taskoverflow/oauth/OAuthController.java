package com.gft.taskoverflow.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Data
public class OAuthController {
    private final OAuthService oAuthService;

    @Operation(summary = "Handle Google OAuth callback")
    @GetMapping("/oauth2/callback")
    public void handleGoogleOAuthCallback(@RequestParam("code") String authorizationCode, HttpServletResponse response) throws JsonProcessingException {
        oAuthService.oAuthCallback(authorizationCode, response);
    }


    @Operation(summary = "Redirect to Google OAuth")
    @GetMapping("/oauth2/google")
    public void redirectToGoogleOAuth(HttpServletResponse response) throws IOException {
        response.sendRedirect(oAuthService.getAuthUrl());
    }
}
