package com.itda.apiserver.service;

import com.itda.apiserver.domain.MainCategory;
import com.itda.apiserver.domain.Product;
import com.itda.apiserver.domain.User;
import com.itda.apiserver.dto.AddproductRequestDto;
import com.itda.apiserver.repository.MainCategoryRepository;
import com.itda.apiserver.repository.ProductRepository;
import com.itda.apiserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final MainCategoryRepository mainCategoryRepository;

    public void addProduct(AddproductRequestDto addProductDto, Long userId) {

        User seller = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        MainCategory category = mainCategoryRepository.findById(addProductDto.getMainCategoryId()).orElseThrow(RuntimeException::new);

        Product product = Product
                .builder()
                .title(addProductDto.getName())
                .subTitle(addProductDto.getSubTitle())
                .imageUrl(addProductDto.getProductImage())
                .price(addProductDto.getPrice())
                .deliveryFee(addProductDto.getDeliveryFee())
                .deliveryDescription(addProductDto.getDeliveryFeeCondition())
                .salesUnit(addProductDto.getSalesUnit())
                .capacity(addProductDto.getCapacity())
                .origin(addProductDto.getOrigin())
                .packageType(addProductDto.getPackagingType())
                .notice(addProductDto.getNotice())
                .description(addProductDto.getDescription())
                .mainCategory(category)
                .seller(seller)
                .bank(addProductDto.getBank())
                .accountHolder(addProductDto.getAccountHolder())
                .account(addProductDto.getAccount())
                .build();

        productRepository.save(product);

    }
}
