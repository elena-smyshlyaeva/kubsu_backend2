package com.example.kubsu.service;

import com.example.kubsu.model.User;
import java.util.List;

public interface UserService {

    User saveUser(User user);
    User getUserByLogin(String login);

    User updateUser(User user, String login);

    List<User> getAllOrders();

    User getUserById(Long id);

    void deleteById(Long id);

    User updateById(User user, Long id);
}
