package com.exam.rbac_jwt_mfa_library.service;

import com.exam.rbac_jwt_mfa_library.domain.User;
import com.exam.rbac_jwt_mfa_library.repo.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User get(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public List<User> list() {
        return userRepository.findAll();
    }

    public User update(Long id, User data) {
        User user = get(id);
        user.setFullname(data.getFullname());
        user.setRoles(data.getRoles());
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }


}
