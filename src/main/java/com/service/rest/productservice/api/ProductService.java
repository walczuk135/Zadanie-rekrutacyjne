package com.service.rest.productservice.api;

import com.service.rest.productservice.api.discount.DiscountService;
import com.service.rest.productservice.data.ProductRow;
import com.service.rest.productservice.data.ProductRepository;
import com.service.rest.productservice.api.dto.ProductDto;
import com.service.rest.productservice.api.dto.ProductMapper;
import com.service.rest.productservice.web.exception.ProductIdNotFoundException;
import com.service.rest.productservice.web.exception.ProductNameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private DiscountService discountService;

    public ProductService(ProductRepository productRepository,DiscountService discountService) {
        this.productRepository = productRepository;
        this.discountService=discountService;
    }

    @Transactional
    public ProductDto findProductByName(String name) {
        ProductRow productRow = productRepository
                .findProductByName(name)
                .orElseThrow(() -> new ProductNameNotFoundException(name));

        productRow.setCount(productRow.getCount() + 1);

        ProductDto productDto=ProductMapper.productRowToProductDto(productRow);

        productDto.setPrice(discountService.calcDiscount(productDto.getPrice(),productDto.getType()));

        return productDto;
    }

    @Transactional
    public ProductDto findProductById(Long id) {
        ProductRow productRow = productRepository
                .findById(id)
                .orElseThrow(() -> new ProductIdNotFoundException(id));
        productRow.setCount(productRow.getCount() + 1);

        return ProductMapper.productRowToProductDto(productRow);
    }

    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(ProductMapper::productRowToProductDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDto save(ProductDto productDto) {
        productRepository.save(ProductMapper.productDtoToProductRow(productDto));
        return productDto;
    }

    public void deleteProduct(String name) {
        productRepository.deleteByName(name);
    }

    @PostConstruct
    public void init() {
        save(new ProductDto("lego", "Lego bricks", TypeProduct.KID, new BigDecimal("20.00")));
        save(new ProductDto("ball", "recreational ball", TypeProduct.MALE, new BigDecimal("49.99")));
        save(new ProductDto("lipstick", "Beauty and medicine", TypeProduct.FEMALE, new BigDecimal("34.99")));
        save(new ProductDto("testosterone", "diet supplement", TypeProduct.MALE, new BigDecimal("150.99")));
        save(new ProductDto("bag", "accessories", TypeProduct.FEMALE, new BigDecimal("25.99")));
        save(new ProductDto("AK-47", "Automatic gun", TypeProduct.MALE, new BigDecimal("3000.99")));
    }

}