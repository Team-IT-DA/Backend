package com.itda.apiserver.service;

import com.itda.apiserver.domain.Product;
import com.itda.apiserver.dto.CartRequestDto;
import com.itda.apiserver.redis.BascketProduct;
import com.itda.apiserver.redis.ShopBasket;
import com.itda.apiserver.repository.ProductRepository;
import com.itda.apiserver.repository.ShopBascketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ShopBascketRepository shopBascketRepository;
    private final ProductRepository productRepository;

    public void addProduct(CartRequestDto cartRequestDto, Long userId) {

        List<BascketProduct> bascketProducts = cartRequestDto.getProducts().stream().map((cart) -> {
            Product product = productRepository.findById(cart.getId()).orElseThrow(RuntimeException::new);
            return new BascketProduct(product.getId(), product.getTitle(), product.getPrice(), product.getImageUrl(), cart.getCount());
        }).collect(Collectors.toList());

        ShopBasket shopBasket = new ShopBasket(userId, bascketProducts);
        shopBascketRepository.save(shopBasket);
    }

    public ShopBasket getProducts(Long userId) {
        return shopBascketRepository.findById(userId).orElseThrow(RuntimeException::new);
    }
}
