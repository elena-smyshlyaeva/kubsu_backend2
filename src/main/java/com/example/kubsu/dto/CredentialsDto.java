package com.example.kubsu.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PUBLIC)
@NoArgsConstructor
public class CredentialsDto {
    private String login;
    private char[] password;

    public CredentialsDto(String login, char[] password) {
        this.login = login;
        this.password = password;
    }

}
