package com.example.snack.promotion;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionGenericRepository extends CrudRepository<PromotionGeneric, String> {

    List<PromotionGeneric> findAll();

}
