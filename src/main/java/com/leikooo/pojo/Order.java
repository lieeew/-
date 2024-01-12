package com.leikooo.pojo;

import com.leikooo.ordermanagement.state.OrderState;
import lombok.*;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Order {
    private String orderId;

    private String productId;

    private OrderState orderState;

    private Float price;
}
