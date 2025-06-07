package com.taskmanager.service;

import com.taskmanager.model.User;
import com.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired private UserRepository userRepository;

    // Simpan user (tanpa encode password, bisa tambahkan encode manual nanti)
    public void save(User user) {
        userRepository.save(user);
    }

    // Cek user berdasarkan username
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Autentikasi manual
    public boolean authenticate(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        return userOpt.isPresent() && userOpt.get().getPassword().equals(password);
    }
}
