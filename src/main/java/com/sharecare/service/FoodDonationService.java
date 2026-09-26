package com.sharecare.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sharecare.entity.FoodDonation;
import com.sharecare.repository.FoodDonationRepository;

@Service
public class FoodDonationService {

    private final FoodDonationRepository foodDonationRepository;

    public FoodDonationService(FoodDonationRepository foodDonationRepository) {
        this.foodDonationRepository = foodDonationRepository;
    }

    // Save donation
    public String saveDonation(FoodDonation foodDonation) {

        foodDonationRepository.save(foodDonation);

        return "Donation submitted successfully";

    }

    // Get all donations
    public List<FoodDonation> getAllFoodDonations() {

        return foodDonationRepository.findAll();

    }
}

