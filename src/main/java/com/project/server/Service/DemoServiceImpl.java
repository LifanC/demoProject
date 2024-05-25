package com.project.server.Service;

import com.project.server.Entity.CalculatePrice;
import com.project.server.Mapper.DemoMapper;
import com.project.server.Model.Demo;
import com.project.server.Model.PriceChange;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
public class DemoServiceImpl implements DemoService {
    @Resource
    private DemoMapper demoMapper;

    // 格式化日期
    private String getOutputDateStr(String queryDate) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate date = LocalDate.parse(queryDate, inputFormatter);
        LocalDateTime dateTime = date.atStartOfDay();
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateTime.format(outputFormatter);
    }

    // 查詢某日價格。
    @Override
    public BigDecimal demoDefault(String productID, String queryDate) {
        String outputDateStr = getOutputDateStr(queryDate);
        List<Demo> list = demoMapper.select(productID, outputDateStr);
        return (!list.isEmpty() ? list.get(list.size() - 1).getPrice() : null);
    }

    // 修改某日價格。
    @Override
    public int putDemo(String productID, String price, String convertedDate) {
        String outputDateStr = getOutputDateStr(convertedDate);
        return demoMapper.put(productID, price, outputDateStr);
    }

    // 新增價格至 DB。
    @Override
    public int postDemo(String productID, String price, String convertedDate) {
        return demoMapper.insert(productID, price, convertedDate);
    }

    // 可刪除某日價格。
    @Override
    public int deleteDemo(String productID, String convertedDate) {
        String outputDateStr = getOutputDateStr(convertedDate);
        return demoMapper.delete(productID, outputDateStr);
    }

    // 帶入開始結束時間，分別實作計算漲跌[後收-前收]和漲跌幅[(後收-前收)/前收]
    @Override
    public PriceChange calculate(CalculatePrice calculatePrice) {
        String productID = calculatePrice.getProductID();
        String from = getOutputDateStr(calculatePrice.getFrom());
        String to = getOutputDateStr(calculatePrice.getTo());
        if (from.compareTo(to) > 0) {
            return null;
        }
        List<Demo> fromList = demoMapper.select(productID, from);
        List<Demo> toList = demoMapper.select(productID, to);
        if (fromList.isEmpty() || toList.isEmpty()) {
            return null;
        }
        BigDecimal bfrom = fromList.get(fromList.size() - 1).getPrice();
        BigDecimal bto = toList.get(toList.size() - 1).getPrice();
        return calculatePriceChange(bfrom, bto);
    }

    // 計算漲跌[後收-前收]和漲跌幅[(後收-前收)/前收]
    private PriceChange calculatePriceChange(BigDecimal bfrom, BigDecimal bto) {
        BigDecimal upsAndDowns = bto.subtract(bfrom);
        BigDecimal quoteChange = upsAndDowns.divide(bfrom, 2, RoundingMode.HALF_EVEN);
        return new PriceChange(upsAndDowns, quoteChange);
    }


}
