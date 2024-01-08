package com.leikooo.items.visitor;

import com.leikooo.items.composite.AbstractProductItem;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/8
 * @description
 */
public interface ItemVisitor<T> {

    T visitor(AbstractProductItem productItem);
}
