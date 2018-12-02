package com.example.snack.ingredient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface IngredientRepository extends CrudRepository<Ingredient, String> {

    Page<Ingredient> findAll(Pageable pageable);

}
