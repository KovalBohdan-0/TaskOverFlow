package com.gft.taskoverflow.oauth;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class OAuthSocketController {
    @SendTo("/topic/oauth2-google-callback")
    public void oAuthCallback() {
    }
}
