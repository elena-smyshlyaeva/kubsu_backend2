package com.example.kubsu.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter(AccessLevel.PUBLIC)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "Email cannot be empty")
    @Pattern(
        regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9]+\\.[A-Za-z]{2,6}$",
        message = "Should have email format, please, use letters, special symbols and '@' symbol with domain")
    private String email;

    private String date;
    private String sex;
    private String count;
    private String superPower;
    private String biography;
    private boolean agreement;
    private String login;
    private String password;

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", date='" + date + '\'' +
            ", sex='" + sex + '\'' +
            ", count='" + count + '\'' +
            ", superPower='" + superPower + '\'' +
            ", biography='" + biography + '\'' +
            ", agreement=" + agreement +
            '}';
    }
}
