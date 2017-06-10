package com.spring3.demo.java8.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author ajay.kg created on 10/06/17.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Staff {

    private String name;
    private int age;
    private BigDecimal salary;
}
