package com.gft.taskoverflow.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Data
public class OAuthController {
    private final OAuthService oAuthService;

    @GetMapping("/oauth2/callback")
    public void handleGoogleOAuthCallback(@RequestParam("code") String authorizationCode, HttpServletResponse response) throws JsonProcessingException {
        oAuthService.oAuthCallback(authorizationCode, response);
    }


    @GetMapping("/oauth2/google")
    public void redirectToGoogleOAuth(HttpServletResponse response) throws IOException {
        response.sendRedirect(oAuthService.getAuthUrl());
    }
}
