package com.project.server.Mapper;

import com.project.server.Model.Demo;
import org.apache.ibatis.annotations.Mapper;

import java.util.*;

@Mapper
public interface DemoMapper {

    List<Demo> select(String productID, String outputDateStr);

    int put(String productID, String price, String outputDateStr);

    int insert(String productID, String price, String convertedDate);

    int delete(String productID, String outputDateStr);
}
