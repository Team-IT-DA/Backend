package com.itda.apiserver.service;

import com.itda.apiserver.domain.Product;
import com.itda.apiserver.dto.CartProduct;
import com.itda.apiserver.exception.NotFoundProductFromCartException;
import com.itda.apiserver.redis.BascketProduct;
import com.itda.apiserver.redis.ShopBasket;
import com.itda.apiserver.repository.ProductRepository;
import com.itda.apiserver.repository.ShopBascketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ShopBascketRepository shopBascketRepository;
    private final ProductRepository productRepository;

    public void addProduct(CartProduct cartRequestDto, Long userId) {

        Product product = productRepository.findById(cartRequestDto.getId()).orElseThrow(RuntimeException::new);
        Optional<ShopBasket> userCart = shopBascketRepository.findById(userId);

        BascketProduct bascketProduct
                = new BascketProduct(product.getId(),
                product.getTitle(),
                product.getPrice(),
                product.getDeliveryFee(),
                product.getImageUrl(),
                cartRequestDto.getCount(),
                product.getSeller().getName()
        );
        ShopBasket shopBasket = userCart.orElseGet(() -> new ShopBasket(userId));
        shopBasket.addProduct(bascketProduct);
        shopBascketRepository.save(shopBasket);
    }

    public void deleteProduct(Long productId, Long userId) {
        ShopBasket shopBasket = shopBascketRepository.findById(userId).orElseThrow(NotFoundProductFromCartException::new);
        shopBasket.dropProduct(productId);
        shopBascketRepository.save(shopBasket);
    }

    public ShopBasket getProducts(Long userId) {
        return shopBascketRepository.findById(userId).orElseThrow(RuntimeException::new);
    }
}
