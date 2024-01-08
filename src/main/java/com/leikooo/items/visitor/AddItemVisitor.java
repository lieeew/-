package com.leikooo.items.visitor;

import com.leikooo.items.composite.AbstractProductItem;
import com.leikooo.items.composite.ProductComposite;
import com.leikooo.service.ProductItemsService;
import com.leikooo.util.ProductConstants;
import com.leikooo.util.RedisCommonProcessor;
import jakarta.annotation.Resource;

import java.util.List;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/6
 * @description
 */
public class AddItemVisitor implements ItemVisitor<ProductComposite>{
    @Resource
    private RedisCommonProcessor redisCommonProcessor;
    @Override
    public ProductComposite visitor(AbstractProductItem productItem) {
        ProductComposite currentItem = (ProductComposite) redisCommonProcessor.get(ProductConstants.PRODUCT_KEY);
        ProductComposite addItem = (ProductComposite) productItem;
        if (currentItem.getId() == addItem.getId()) {
            currentItem.addProductItem(addItem);
            return currentItem;
        }
        addItem(currentItem, addItem);
        return currentItem;
    }
    private void addItem(ProductComposite currentItem, ProductComposite addItem) {
        List<AbstractProductItem> children = currentItem.getChildren();
        for (AbstractProductItem child : children) {
            ProductComposite productComposite = (ProductComposite) child;
            if (productComposite.getId() == addItem.getId()) {
                productComposite.addProductItem(productComposite);
                break;
            } else {
                addItem(productComposite, addItem);
            }
        }
    }
}

