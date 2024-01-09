package com.leikooo.items.visitor;

import com.leikooo.items.composite.AbstractProductItem;
import com.leikooo.items.composite.ProductComposite;
import com.leikooo.util.ProductConstants;
import com.leikooo.util.RedisCommonProcessor;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/8
 * @description
 */
@Component
public class DelItemVisitor implements ItemVisitor<ProductComposite> {
    @Resource
    private RedisCommonProcessor redisCommonProcessor;
    @Override
    public ProductComposite visitor(AbstractProductItem productItem) {
        ProductComposite currentItem = (ProductComposite) redisCommonProcessor.get(ProductConstants.PRODUCT_KEY);
        ProductComposite delItem = (ProductComposite) productItem;
        if (currentItem.getId() == delItem.getId()) {
            currentItem.delProductItem(delItem);
            return currentItem;
        }
        delItem(currentItem, delItem);
        return currentItem;
    }

    public void delItem(ProductComposite currentItem, ProductComposite delItem) {
        List<AbstractProductItem> children = currentItem.getChildren();
        for (AbstractProductItem child : children) {
            ProductComposite productComposite = (ProductComposite) child;
            if (productComposite.getId() == delItem.getId()) {
                productComposite.delProductItem(productComposite);
                break;
            } else {
                delItem(productComposite, delItem);
            }
        }
    }
}
