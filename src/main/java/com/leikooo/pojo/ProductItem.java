package com.leikooo.pojo;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @Description
 */
@Entity
@Data
@Table(name = "product_item")
public class ProductItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int pid;
}
