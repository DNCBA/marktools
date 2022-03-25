package com.mhc;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ItemVO {

    @ExcelProperty("id")
    private Integer id;

    @ExcelProperty("title")
    private String title;

    @ExcelProperty("desc")
    private String desc;

    @ExcelProperty("mainType")
    private String mainType;

    @ExcelProperty("subType")
    private String subType;

    @ExcelProperty("minType")
    private String minType;

    @ExcelProperty("info")
    private String info;

}
