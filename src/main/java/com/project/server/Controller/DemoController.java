package com.project.server.Controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.project.server.Entity.CalculatePrice;
import com.project.server.Entity.DemoBean;
import com.project.server.Model.PriceChange;
import com.project.server.Service.DemoService;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class DemoController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Gson gson = new Gson();
    @Resource
    private DemoService demoService;

    private boolean chkParamIsNotBlank(String param) {
        return StringUtils.isNotBlank(param);
    }

    @GetMapping("/DemoDefault")
    public String DemoDefault(@Param("productID") String productID, @Param("queryDate") String queryDate) {
        BigDecimal bigDecimal = null;
        JsonObject jsonObject = new JsonObject();
        if (chkParamIsNotBlank(productID) && chkParamIsNotBlank(queryDate)) {
            try {
                bigDecimal = demoService.demoDefault(productID, queryDate);
                jsonObject.addProperty("err", false);
            } catch (Exception ex) {
                jsonObject.addProperty("err", true);
                jsonObject.addProperty("errMessage", ex.getMessage());
            }
        } else {
            jsonObject.addProperty("paramErr", true);
            return gson.toJson(jsonObject);
        }
        jsonObject.addProperty("entity", (bigDecimal == null));
        jsonObject.addProperty("price", bigDecimal);
        logger.info("DemoDefault ProductID: {}", productID);
        logger.info("DemoDefault queryDate: {}", queryDate);
        logger.info("查詢某日價格: {}", jsonObject);
        return gson.toJson(jsonObject);
    }

    @PutMapping("/putDemo")
    public String putDemo(@RequestBody DemoBean demoBean) {
        JsonObject jsonObject = new JsonObject();
        int i = 0;
        if (chkParamIsNotBlank(demoBean.getProductID())
                && chkParamIsNotBlank(demoBean.getPrice())
                && chkParamIsNotBlank(demoBean.getConverted_date())) {
            try {
                i = demoService.putDemo(demoBean.getProductID(), demoBean.getPrice(), demoBean.getConverted_date());
                jsonObject.addProperty("err", false);
            } catch (Exception ex) {
                jsonObject.addProperty("err", true);
                jsonObject.addProperty("errMessage", ex.getMessage());
            }
        } else {
            jsonObject.addProperty("paramErr", true);
            return gson.toJson(jsonObject);
        }
        jsonObject.addProperty("entity", i <= 0);
        logger.info("putDemo ProductID: {}", demoBean.getProductID());
        logger.info("putDemo Price: {}", demoBean.getPrice());
        logger.info("putDemo Converted_date: {}", demoBean.getConverted_date());
        logger.info("修改某日價格: {}", jsonObject);
        return gson.toJson(jsonObject);
    }

    @PostMapping("/postDemo")
    public String postDemo(@RequestBody DemoBean demoBean) {
        JsonObject jsonObject = new JsonObject();
        int i = 0;
        if (chkParamIsNotBlank(demoBean.getProductID())
                && chkParamIsNotBlank(demoBean.getPrice())
                && chkParamIsNotBlank(demoBean.getConverted_date())) {
            try {
                i = demoService.postDemo(demoBean.getProductID(), demoBean.getPrice(), demoBean.getConverted_date());
                jsonObject.addProperty("err", false);
            } catch (Exception ex) {
                jsonObject.addProperty("err", true);
                jsonObject.addProperty("errMessage", ex.getMessage());
            }
        } else {
            jsonObject.addProperty("paramErr", true);
            return gson.toJson(jsonObject);
        }
        logger.info("postDemo ProductID: {}", demoBean.getProductID());
        logger.info("postDemo Price: {}", demoBean.getPrice());
        logger.info("postDemo Converted_date: {}", demoBean.getConverted_date());
        jsonObject.addProperty("entity", i <= 0);
        logger.info("新增價格至 DB: {}", jsonObject);
        return gson.toJson(jsonObject);
    }

    @DeleteMapping("/deleteDemo")
    public String deleteDemo(@RequestBody DemoBean demoBean) {
        JsonObject jsonObject = new JsonObject();
        int i = 0;
        if (chkParamIsNotBlank(demoBean.getProductID())
                && chkParamIsNotBlank(demoBean.getConverted_date())) {
            try {
                i = demoService.deleteDemo(demoBean.getProductID(), demoBean.getConverted_date());
                jsonObject.addProperty("err", false);
            } catch (Exception ex) {
                jsonObject.addProperty("err", true);
                jsonObject.addProperty("errMessage", ex.getMessage());
            }
        } else {
            jsonObject.addProperty("paramErr", true);
            return gson.toJson(jsonObject);
        }
        jsonObject.addProperty("entity", i <= 0);
        logger.info("deleteDemo ProductID: {}", demoBean.getProductID());
        logger.info("deleteDemo Converted_date: {}", demoBean.getConverted_date());
        logger.info("可刪除某日價格: {}", jsonObject);
        return gson.toJson(jsonObject);
    }

    @PostMapping("/calculate")
    public String calculate(@RequestBody CalculatePrice calculatePrice) {
        JsonObject jsonObject = new JsonObject();
        PriceChange priceChange = demoService.calculate(calculatePrice);
        jsonObject.addProperty("entity", priceChange == null);
        if (priceChange != null) {
            jsonObject.addProperty("upsAndDowns", priceChange.getUpsAndDowns());
            jsonObject.addProperty("quoteChange", priceChange.getQuoteChange());
        }
        logger.info("前收 From: {}", calculatePrice.getFrom());
        logger.info("後收 To: {}", calculatePrice.getTo());
        logger.info("entity: {}", jsonObject.get("entity"));
        logger.info("漲跌upsAndDowns[後收-前收]: {}", jsonObject.get("upsAndDowns"));
        logger.info("漲跌幅quoteChange[(後收-前收)/前收]: {}", jsonObject.get("quoteChange"));
        return gson.toJson(jsonObject);
    }

}
