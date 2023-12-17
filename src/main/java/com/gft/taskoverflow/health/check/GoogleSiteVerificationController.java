package com.gft.taskoverflow.health.check;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GoogleSiteVerificationController {
    @Operation(summary = "Google site verification", description = "Only needed for google site verification of OAuth")
    @ApiResponse(responseCode = "200", description = "Google site verification")
    @GetMapping("/")
    @ResponseBody
    public String googleSiteVerification() {
        return "<meta name=\"google-site-verification\" content=\"8WY_FZVgrgodzm1CRoWSK_8r9afWIvNm2iViTb7_3hg\" />";
    }
}