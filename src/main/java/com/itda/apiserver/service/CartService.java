package com.itda.apiserver.service;

import com.itda.apiserver.domain.Product;
import com.itda.apiserver.dto.CartProduct;
import com.itda.apiserver.exception.NotFoundProductFromCartException;
import com.itda.apiserver.redis.BasketProduct;
import com.itda.apiserver.redis.ShopBasket;
import com.itda.apiserver.repository.ProductRepository;
import com.itda.apiserver.repository.ShopBasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ShopBasketRepository shopBasketRepository;
    private final ProductRepository productRepository;

    public void addProduct(CartProduct cartRequestDto, Long userId) {

        Product product = productRepository.findById(cartRequestDto.getId()).orElseThrow(RuntimeException::new);
        Optional<ShopBasket> userCart = shopBasketRepository.findById(userId);

        BasketProduct basketProduct
                = new BasketProduct(product.getId(),
                product.getTitle(),
                product.getPrice(),
                product.getDeliveryFee(),
                product.getImageUrl(),
                cartRequestDto.getCount(),
                product.getSeller().getName()
        );
        ShopBasket shopBasket = userCart.orElseGet(() -> new ShopBasket(userId));
        shopBasket.addProduct(basketProduct);
        shopBasketRepository.save(shopBasket);
    }

    public void deleteProduct(Long productId, Long userId) {
        ShopBasket shopBasket = shopBasketRepository.findById(userId).orElseThrow(NotFoundProductFromCartException::new);
        shopBasket.dropProduct(productId);
        shopBasketRepository.save(shopBasket);
    }

    public ShopBasket getProducts(Long userId) {
        return shopBasketRepository.findById(userId).orElseThrow(RuntimeException::new);
    }
}
