package com.leikooo.repo;

import com.alipay.api.domain.Product;
import com.leikooo.pojo.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/20
 * @description
 */
@Repository
public interface ProductRepository extends JpaRepository<Products, Integer> {
    public Products findByProductId(String productId);
}
