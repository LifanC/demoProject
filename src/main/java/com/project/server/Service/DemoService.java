package com.project.server.Service;

import com.project.server.Entity.CalculatePrice;
import com.project.server.Model.PriceChange;

import java.math.BigDecimal;

public interface DemoService {
    BigDecimal demoDefault(String productID, String queryDate);

    int putDemo(String productID, String price, String convertedDate);

    int postDemo(String productID, String price, String convertedDate);

    int deleteDemo(String productID, String convertedDate);

    PriceChange calculate(CalculatePrice calculatePrice);
}
