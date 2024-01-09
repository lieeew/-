package com.leikooo.service;

import com.leikooo.items.composite.AbstractProductItem;
import com.leikooo.items.composite.ProductComposite;
import com.leikooo.items.visitor.AddItemVisitor;
import com.leikooo.items.visitor.DelItemVisitor;
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

    @Resource
    private AddItemVisitor addItemVisitor;

    @Resource
    private DelItemVisitor delItemVisitor;

    public ProductComposite fetchAllItems() {
        ProductComposite productComposite = (ProductComposite) redisCommonProcessor.get(ProductConstants.PRODUCT_KEY);
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

    /**
     * 我们采取的措施是先更新 数据库 之后在更新 redis 的缓存
     *
     * @param addProduct 增加的 product
     */
    public ProductComposite addItem(ProductItem addProduct) {
        // 先更新数据库
        productItemRepository.addItem(addProduct.getName(), addProduct.getPid());
        ProductComposite updateProduct = addItemVisitor.visitor(ProductComposite.builder().name(addProduct.getName()).pid(addProduct.getPid()).id(addProduct.getId()).build());
        redisCommonProcessor.set(ProductConstants.PRODUCT_KEY, updateProduct);
        return updateProduct;
    }

    /**
     * 使用访问者模式删除
     *
     * @param delItem
     * @return
     */
    public ProductComposite delItem(ProductItem delItem) {
        // 先更新数据库
        productItemRepository.delItem(delItem.getId(), delItem.getPid());
        ProductComposite updateProduct = delItemVisitor.visitor(ProductComposite.builder().name(delItem.getName()).pid(delItem.getPid()).id(delItem.getId()).build());
        redisCommonProcessor.set(ProductConstants.PRODUCT_KEY, updateProduct);
        return updateProduct;
    }
}
