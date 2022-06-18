package com.lambdainfo.ldapservice.domain;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class LoginDetails {
    @NotNull
    private String user;
    @NotNull
    private String password;
}
