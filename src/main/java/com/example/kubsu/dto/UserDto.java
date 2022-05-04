package com.example.kubsu.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String login;

    public UserDto(Long id, String name, String login) {
        this.id = id;
        this.name = name;
        this.login = login;
    }
}
