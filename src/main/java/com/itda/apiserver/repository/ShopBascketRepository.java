package com.itda.apiserver.repository;

import com.itda.apiserver.redis.ShopBasket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopBascketRepository extends CrudRepository<ShopBasket, Long> {
}
