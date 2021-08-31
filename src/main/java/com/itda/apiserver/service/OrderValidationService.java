package com.itda.apiserver.service;

import com.itda.apiserver.redis.OrderValidation;
import com.itda.apiserver.repository.OrderValidationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderValidationService {

    private final OrderValidationRepository orderValidationRepository;

    public boolean isDuplicatedOrder(Long userId) {
        return orderValidationRepository.findById(userId).isPresent();
    }

    public void save(Long userId) {
        orderValidationRepository.save(new OrderValidation(userId));
    }
}
