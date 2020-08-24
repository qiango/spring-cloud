package com.ribbonconsumer.service.leyile;

import com.ribbonconsumer.mapper.leyile.LeMapper;
import com.ribbonconsumer.mapper.leyile.ProductMapper;
import com.ribbonconsumer.service.leyile.abstra.ProductAbstraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductService extends ProductAbstraService {

    @Autowired
    public ProductService(ProductMapper productMapper, LeMapper leMapper) {
        super(productMapper, leMapper);
    }
}
