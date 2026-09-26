package com.sharecare.controller;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sharecare.entity.User;
import com.sharecare.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;
    }

    // =========================================================
    // REGISTER
    // =========================================================

    @GetMapping("/register")
    public String showRegisterPage(Model model) {

        model.addAttribute("user", new User());

        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @ModelAttribute("user") User user,
            Model model) {

        String result =
                userService.registerUser(user);

        if (result.equals("Phone number already exists")) {

            model.addAttribute("error", result);

            return "register";
        }

        model.addAttribute("success", result);

        model.addAttribute("user", new User());

        return "register";
    }

    // =========================================================
    // LOGIN
    // =========================================================

    @GetMapping("/login")
    public String showLoginPage(Model model) {

        model.addAttribute("user", new User());

        return "login";
    }

    @PostMapping("/login")
    public String loginUser(
            @ModelAttribute("user") User user,
            Model model,
            HttpSession session) {

        User existingUser =
                userService.loginUser(
                        user.getPhone(),
                        user.getPassword()
                );

        // Invalid login
        if (existingUser == null) {

            model.addAttribute(
                    "error",
                    "Invalid phone number or password"
            );

            return "login";
        }

        // Store logged-in user in session
        session.setAttribute(
                "loggedInPhone",
                existingUser.getPhone()
        );

        return "redirect:/dashboard";
    }

    // =========================================================
    // DASHBOARD
    // =========================================================

    @GetMapping("/dashboard")
    public String showDashboard(
            Model model,
            HttpSession session) {

        User user =
                userService.getLoggedInUser();

        // User is not logged in
        if (user == null) {

            return "redirect:/login";
        }

        model.addAttribute(
                "user",
                user
        );

        return "dashboard";
    }

   

    // =========================================================
    // UPDATE PROFILE INFORMATION
    // =========================================================

    @PostMapping("/profile/update")
    public String updateProfile(
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            Model model) {

        User user =
                userService.getLoggedInUser();

        // User is not logged in
        if (user == null) {

            return "redirect:/login";
        }

        // Check if another user already has this phone
        User existingUser =
                userService.getUserByPhone(phone);

        if (existingUser != null &&
                !existingUser.getId()
                        .equals(user.getId())) {

            model.addAttribute(
                    "error",
                    "Phone number already exists"
            );

            model.addAttribute(
                    "user",
                    user
            );

            return "profile";
        }

        user.setName(name);
        user.setPhone(phone);
        user.setAddress(address);

        userService.saveUser(user);

        // Update session with new phone
        // because phone is used to identify logged-in user
        //HttpSession session =
               // null;

        model.addAttribute(
                "success",
                "Profile updated successfully"
        );

        model.addAttribute(
                "user",
                user
        );

        return "profile";
    }

    // =========================================================
    // UPLOAD PROFILE PHOTO
    // =========================================================

    @PostMapping("/profile/photo")
    public String uploadProfilePhoto(
            @RequestParam("photo") MultipartFile photo,
            HttpSession session) {

        try {

            User user =
                    userService.getLoggedInUser();

            // User is not logged in
            if (user == null) {

                return "redirect:/login";
            }

            // No file selected
            if (photo.isEmpty()) {

                return "redirect:/profile";
            }

            // Save image bytes
            user.setProfilePhoto(
                    photo.getBytes()
            );

            // Save image type
            user.setProfilePhotoType(
                    photo.getContentType()
            );

            // Save user
            userService.saveUser(user);

            return "redirect:/profile";

        } catch (IOException e) {

            e.printStackTrace();

            return "redirect:/profile";
        }
    }

    // =========================================================
    // DISPLAY PROFILE PHOTO
    // =========================================================

    @GetMapping("/profile/photo")
    public ResponseEntity<byte[]> getProfilePhoto() {

        User user =
                userService.getLoggedInUser();

        // User not logged in
        if (user == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        // No profile photo
        if (user.getProfilePhoto() == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        String contentType =
                user.getProfilePhotoType();

        // Default image type
        if (contentType == null ||
                contentType.isBlank()) {

            contentType = "image/jpeg";
        }

        return ResponseEntity
                .ok()
                .contentType(
                        MediaType.parseMediaType(
                                contentType
                        )
                )
                .body(
                        user.getProfilePhoto()
                );
    }
 // =========================================================
 // PROFILE PAGE
 // =========================================================

 @GetMapping("/profile")
 public String showProfile(Model model) {

     User user = userService.getLoggedInUser();

     if (user == null) {
         return "redirect:/login";
     }

     model.addAttribute("user", user);

     return "profile";
 }

    // =========================================================
    // LOGOUT
    // =========================================================

    @GetMapping("/logout")
    public String logout(
            HttpSession session) {

        session.invalidate();

        return "redirect:/";
    }
}