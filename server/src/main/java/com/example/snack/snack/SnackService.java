package com.example.snack.snack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SnackService {

    @Autowired
    private SnackRepository snackRepository;

    public Page<Snack> getAllSnacks(Pageable pageable) {
        return snackRepository.findAll(pageable);
    }

    public Optional<Snack> getSnack(String id) {
        return snackRepository.findById(id);
    }
}
