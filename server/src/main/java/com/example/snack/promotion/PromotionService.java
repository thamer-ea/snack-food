package com.example.snack.promotion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionService {

    @Autowired
    private PromotionGenericRepository promotionGenericRepository;

    public List<PromotionGeneric> getAllPromotion() {
        return promotionGenericRepository.findAll();
    }

}
