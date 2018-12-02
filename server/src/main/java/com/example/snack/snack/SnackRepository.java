package com.example.snack.snack;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SnackRepository extends CrudRepository<Snack, String> {

    Page<Snack> findAll(Pageable pageable);

}
