package com.leikooo.controller;

import com.leikooo.items.composite.ProductComposite;
import com.leikooo.pojo.ProductItem;
import com.leikooo.service.ProductItemsService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/7
 * @description
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Resource
    private ProductItemsService productItemsService;

    @PostMapping("/fetchAllItem")
    public ProductComposite fetchAllItem() {
        return productItemsService.fetchAllItems();
    }

    @PostMapping("/addItem")
    public ProductComposite addItem(@RequestBody ProductItem item) {
        return productItemsService.addItem(item);
    }

    @PostMapping("/delItem")
    public ProductComposite delItem(@RequestBody ProductItem item) {
        return productItemsService.delItem(item);
    }

}
