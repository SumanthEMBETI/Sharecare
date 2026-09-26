package com.sharecare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.sharecare.entity.FoodDonation;
import com.sharecare.service.FoodDonationService;

@Controller
public class FoodDonationController {

    private final FoodDonationService foodDonationService;

    public FoodDonationController(FoodDonationService foodDonationService) {
        this.foodDonationService = foodDonationService;
    }

    // ================= DONATE FOOD =================

    @GetMapping("/donate-food")
    public String showFoodDonationPage(Model model) {

        model.addAttribute("foodDonation", new FoodDonation());

        return "donate-food";
    }

    // Process food donation
    @PostMapping("/donate-food")
    public String submitFoodDonation(
            @ModelAttribute("foodDonation") FoodDonation foodDonation,
            Model model) {

        String result = foodDonationService.saveDonation(foodDonation);

        model.addAttribute("success", result);

        model.addAttribute("foodDonation", new FoodDonation());

        return "donate-food";
    }

    // ================= AVAILABLE FOOD =================

    @GetMapping("/available-food")
    public String showAvailableFood(Model model) {

        model.addAttribute(
                "foodDonations",
                foodDonationService.getAllFoodDonations()
        );

        return "available-food";
    }
}