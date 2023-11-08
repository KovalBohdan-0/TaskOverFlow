package com.gft.taskoverflow.health.check;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GoogleSiteVerificationController {
    @GetMapping("/google-site-verification")
    @ResponseBody
    public String googleSiteVerification() {
        return "<meta name=\"google-site-verification\" content=\"8WY_FZVgrgodzm1CRoWSK_8r9afWIvNm2iViTb7_3hg\" />";
    }
}