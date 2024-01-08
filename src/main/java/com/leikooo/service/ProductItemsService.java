package com.leikooo.service;

import com.leikooo.items.composite.AbstractProductItem;
import com.leikooo.items.composite.ProductComposite;
import com.leikooo.pojo.ProductItem;
import com.leikooo.repo.ProductItemRepository;
import com.leikooo.util.ProductConstants;
import com.leikooo.util.RedisCommonProcessor;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/7
 * @description 描述
 */
@Service
public class ProductItemsService {
    @Resource
    private RedisCommonProcessor redisCommonProcessor;
    @Resource
    private ProductItemRepository productItemRepository;
    public ProductComposite fetchAllItems() {
        ProductComposite productComposite = (ProductComposite) redisCommonProcessor.get("items");
        if (productComposite != null) {
            return productComposite;
        }
        // Redis 没有数据，直接查询 DB
        List<ProductItem> items = productItemRepository.findAll();
        productComposite = generateTree(items);
        redisCommonProcessor.set(ProductConstants.PRODUCT_KEY, productComposite, 600);
        return productComposite;
    }

    private ProductComposite generateTree(List<ProductItem> items) {
        assert items != null : "数据为空无法生成对应的树型结构";
        List<ProductComposite> composites = new ArrayList<>(items.size());
        items.forEach(item -> composites.add(ProductComposite.builder().id(item.getId()).name(item.getName()).pid(item.getPid()).build()));
        Map<Integer, List<ProductComposite>> productItemsByParentIdMap = composites.stream().collect(Collectors.groupingBy(ProductComposite::getPid));
        composites.forEach(item -> {
            List<ProductComposite> list = productItemsByParentIdMap.get(item.getId());
            item.setChildren(list == null ? new ArrayList<>() : list.stream().map(x -> (AbstractProductItem) x).collect(Collectors.toList()));
        });
        return composites.size() == 0 ? null : composites.get(0);
    }
}
