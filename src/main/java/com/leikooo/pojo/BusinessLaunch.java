package com.leikooo.pojo;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/16
 * @description
 */
@Data
@Entity
@Table(name = "business_launch")
public class BusinessLaunch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, name = "business_detail")
    private String businessDetail;

    @Column(nullable = false, name = "target_city")
    private String targetCity;

    @Column(nullable = false, name = "target_sex")
    private String targetSex;

    @Column(nullable = false, name = "target_product")
    private String targetProduct;
}
