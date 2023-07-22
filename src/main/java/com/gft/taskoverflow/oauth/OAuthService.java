package com.gft.taskoverflow.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.taskoverflow.customer.CustomerService;
import com.gft.taskoverflow.customer.CustomerUserDetails;
import com.gft.taskoverflow.jwt.JwtService;
import com.gft.taskoverflow.registration.RegistrationRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Data
public class OAuthService {
    private static final String REDIRECT_URI = "http://localhost:8080/oauth2/callback";
    private static final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v3/userinfo";
    private final CustomerService customerService;
    private final JwtService jwtService;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String CLIENT_ID;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String CLIENT_SECRET;

    public void oAuthCallback(String authorizationCode, HttpServletResponse response) throws JsonProcessingException {
        String accessToken = exchangeAuthorizationCodeForAccessToken(authorizationCode);
        String userInfo = fetchUserInfo(accessToken);
        String email = parseEmailFromUserInfo(userInfo);

        if (!customerService.existsByEmail(email)) {
            customerService.signUpCustomer(new RegistrationRequest(email, accessToken));
        }

        String jwt = jwtService.generateJwt(new CustomerUserDetails(email, accessToken));
        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setHttpOnly(false);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public String getAuthUrl() {
        String oauthEndpoint = "https://accounts.google.com/o/oauth2/auth";
        String scope = "email profile";

        return String.format("%s?client_id=%s&redirect_uri=%s&scope=%s&response_type=code",
                oauthEndpoint, CLIENT_ID, REDIRECT_URI, scope);
    }


    private String exchangeAuthorizationCodeForAccessToken(String authorizationCode) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        String requestBody = String.format("code=%s&client_id=%s&client_secret=%s&redirect_uri=%s&grant_type=authorization_code",
                authorizationCode, CLIENT_ID, CLIENT_SECRET, REDIRECT_URI);

        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange("https://oauth2.googleapis.com/token",
                HttpMethod.POST, httpEntity, String.class);
        String responseBody = responseEntity.getBody();

        return parseAccessTokenFromResponseBody(responseBody);
    }

    private String fetchUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(USER_INFO_URL,
                HttpMethod.GET, httpEntity, String.class);

        return responseEntity.getBody();
    }

    private String parseEmailFromUserInfo(String userInfo) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readTree(userInfo).get("email").asText();
    }

    private String parseAccessTokenFromResponseBody(String responseBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readTree(responseBody).get("access_token").asText();
    }
}
