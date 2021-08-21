package com.itda.apiserver.service;

import com.itda.apiserver.domain.Product;
import com.itda.apiserver.dto.CartRequestDto;
import com.itda.apiserver.redis.BascketProduct;
import com.itda.apiserver.redis.ShopBasket;
import com.itda.apiserver.repository.ProductRepository;
import com.itda.apiserver.repository.ShopBascketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ShopBascketRepository shopBascketRepository;
    private final ProductRepository productRepository;

    public void addProduct(CartRequestDto cartRequestDto, Long userId) {

        Product product = productRepository.findById(cartRequestDto.getId()).orElseThrow(RuntimeException::new);

        BascketProduct bascketProduct = new BascketProduct(product.getId(), product.getTitle(), product.getPrice(), product.getImageUrl(), cartRequestDto.getCount());
        ShopBasket shopBasket = new ShopBasket(userId);
        shopBasket.addProduct(bascketProduct);
        shopBascketRepository.save(shopBasket);
    }

}
