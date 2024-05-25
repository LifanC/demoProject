package com.project.server.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemoBean {
    private String productID;
    private String price;
    private String converted_date;
}
