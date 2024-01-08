package com.leikooo.items.composite;

import lombok.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/6
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductComposite extends AbstractProductItem {
    private int id;

    private int pid;

    private String name;

    private List<AbstractProductItem> children = new ArrayList<>();

    @Override
    public void addProductItem(AbstractProductItem item) {
        this.children.add(item);
    }

    @Override
    public void delProductItem(AbstractProductItem item) {
        ProductComposite composite = (ProductComposite) item;
        // 缺点不能访问前后两个元素
        // 优点: 支持在迭代过程中安全地修改集合的结构。
        // 提供了 remove 方法来删除当前迭代的元素。
        Iterator<AbstractProductItem> iterator = children.iterator();
        while (iterator.hasNext()) {
            ProductComposite next = (ProductComposite) iterator.next();
            // 移除相同 id 的商品
            if (next.getId() == composite.getId()) {
                iterator.remove();
                return;
            }
        }
    }
}

