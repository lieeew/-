package com.leikooo.pojo;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/20
 * @description
 */
@Data
@Entity
@Table(name = "products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, name = "product_id")
    private String productId;

    @Column(nullable = false, name = "send_red_bag")
    private Integer sendRedBag;
}
