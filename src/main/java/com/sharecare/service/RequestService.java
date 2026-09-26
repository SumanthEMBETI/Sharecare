package com.sharecare.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sharecare.entity.Request;
import com.sharecare.repository.RequestRepository;

@Service
public class RequestService {

    private final RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    // Save a support request
    public String saveRequest(Request request) {

        request.setStatus("PENDING");

        requestRepository.save(request);

        return "Request submitted successfully";
    }

    // Accept a support request
    public String acceptRequest(Long id) {

        Request request = requestRepository.findById(id).orElse(null);

        if (request == null) {
            return "Request not found";
        }

        request.setStatus("ACCEPTED");

        requestRepository.save(request);

        return "Request accepted successfully";
    }

    // Get all support requests
    public List<Request> getAllRequests() {
        return requestRepository.findAllByOrderByIdDesc();
    }
}