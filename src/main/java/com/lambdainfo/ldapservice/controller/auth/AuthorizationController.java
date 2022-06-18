package com.lambdainfo.ldapservice.controller.auth;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class AuthorizationController {

    @GetMapping("/greeting")
    public String root() {
        return "redirect:/index";
    }


}
