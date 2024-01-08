package com.leikooo.items.composite;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/6
 * @description
 */
public abstract class AbstractProductItem {
    protected void addProductItem(AbstractProductItem item) {
        throw new UnsupportedOperationException("NOT SUPPORT CHILD ADD");
    }

    protected void delProductItem(AbstractProductItem item) {
        throw new UnsupportedOperationException("NOI SUPPORT CHILD DELETE");
    }
}
