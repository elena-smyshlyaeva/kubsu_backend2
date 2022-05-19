package com.example.kubsu.service.impls;

import com.example.kubsu.model.Role;
import com.example.kubsu.model.User;
import com.example.kubsu.repository.UserRepository;
import com.example.kubsu.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @Override
    public User saveUser(User user) {
        log.info("saveUser() method invoked");

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    @Override
    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public User updateUser(User user, String login) {
        User userToUpdate = userRepository.findByLogin(login).orElseThrow(EntityNotFoundException::new);
        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setSex(user.getSex());
        userToUpdate.setCount(user.getCount());
        userToUpdate.setSuperPower(user.getSuperPower());
        userToUpdate.setBiography(user.getBiography());
        return userRepository.save(userToUpdate);
    }

    @Override
    public List<User> getAllOrders() {
        List<User> all = userRepository.findAll();
        return all.stream().filter(user -> user.getRole() != Role.ADMIN).collect(Collectors.toList());
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateById(User user, Long id) {
        User userToUpdate = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setSex(user.getSex());
        userToUpdate.setCount(user.getCount());
        userToUpdate.setSuperPower(user.getSuperPower());
        userToUpdate.setBiography(user.getBiography());
        return userRepository.save(userToUpdate);
    }
}
