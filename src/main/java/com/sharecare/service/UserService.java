package com.sharecare.service;

import org.springframework.stereotype.Service;

import com.sharecare.entity.User;
import com.sharecare.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final HttpSession session;

    public UserService(
            UserRepository userRepository,
            HttpSession session) {

        this.userRepository = userRepository;
        this.session = session;
    }

    // ================= REGISTER =================

    public String registerUser(User user) {

        if (userRepository.existsByPhone(user.getPhone())) {

            return "Phone number already exists";
        }

        userRepository.save(user);

        return "Registration successful";
    }

    // ================= LOGIN =================

    public User loginUser(String phone, String password) {

        User user = userRepository.findByPhone(phone);

        if (user != null &&
                user.getPassword().equals(password)) {

            // Store logged-in user's phone in session
            session.setAttribute(
                    "loggedInPhone",
                    user.getPhone()
            );

            return user;
        }

        return null;
    }

    // ================= GET USER BY PHONE =================

    public User getUserByPhone(String phone) {

        return userRepository.findByPhone(phone);
    }

    // ================= GET LOGGED-IN USER =================

    public User getLoggedInUser() {

        // Get logged-in user's phone from session
        String phone =
                (String) session.getAttribute("loggedInPhone");

        // No user logged in
        if (phone == null) {

            return null;
        }

        // Find exact user from database
        return userRepository.findByPhone(phone);
    }

    // ================= SAVE USER =================

    public User saveUser(User user) {

        return userRepository.save(user);
    }

    // ================= LOGOUT =================

    public void logout() {

        session.invalidate();
    }
}