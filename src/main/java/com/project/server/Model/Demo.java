package com.project.server.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Demo {
    private String product_id;
    private String product_name;
    private String timestamp;
    private BigDecimal price;
    private String converted_date;
}
