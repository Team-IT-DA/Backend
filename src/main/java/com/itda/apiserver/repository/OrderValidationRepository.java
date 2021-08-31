package com.itda.apiserver.repository;

import com.itda.apiserver.redis.OrderValidation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderValidationRepository extends CrudRepository<OrderValidation, Long> {
}
