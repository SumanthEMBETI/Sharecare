package com.sharecare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sharecare.entity.Request;
import com.sharecare.service.RequestService;

@Controller
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    // ================= REQUEST SUPPORT PAGE =================

    @GetMapping("/request")
    public String showRequestPage(Model model) {

        model.addAttribute("request", new Request());

        return "request";
    }

    // ================= SUBMIT SUPPORT REQUEST =================

    @PostMapping("/request")
    public String submitRequest(
            @ModelAttribute("request") Request request,
            Model model) {

        String result = requestService.saveRequest(request);

        model.addAttribute("success", result);
        model.addAttribute("request", new Request());

        return "request";
    }

    // ================= VIEW ALL SUPPORT REQUESTS =================

    @GetMapping("/food-requests")
    public String showSupportRequests(Model model) {

        model.addAttribute("requests", requestService.getAllRequests());

        return "food-requests";
    }

    // ================= ACCEPT SUPPORT REQUEST =================

    @PostMapping("/requests/accept/{id}")
    public String acceptRequest(
            @PathVariable Long id) {

        requestService.acceptRequest(id);

        return "redirect:/food-requests";
    }
}

