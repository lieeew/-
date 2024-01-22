package com.leikooo.ticket.copy.copy;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/22
 * @description
 */
@Data
@Builder
public class Student implements Serializable {
    private String studentName;
}
