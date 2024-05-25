package com.project.server.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceChange {

    private BigDecimal upsAndDowns;
    private BigDecimal quoteChange;

}
