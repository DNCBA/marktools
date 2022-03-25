package com.mhc;

import com.alibaba.excel.EasyExcel;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) throws IOException {

        String sourcePath = "/Users/jerryhcao/Downloads/test.xls";
        String resultPath = "/Users/jerryhcao/Downloads/result.xls";
        String type = "/Users/jerryhcao/Downloads/type.txt";





        List<TypeNode> subTypeNodeList = new ArrayList<TypeNode>();
        List<TypeNode> minTypeNodeList = new ArrayList<TypeNode>();


        BufferedReader reader = new BufferedReader(new FileReader(new File(type)));
        String typeLine = reader.readLine();
        while (StringUtils.isNotEmpty(typeLine)) {
            String[] split = typeLine.split("=");

            TypeNode subTypeNode = new TypeNode();
            subTypeNode.setTypeName(split[0]);
            subTypeNodeList.add(subTypeNode);

            for (String minType : split[1].split(",")) {
                TypeNode minTypeNode = new TypeNode();
                minTypeNode.setTypeName(minType);
                minTypeNode.setParent(subTypeNode);
                minTypeNodeList.add(minTypeNode);
            }

            typeLine = reader.readLine();
        }


        ItemListener itemListener = new ItemListener(subTypeNodeList, minTypeNodeList);
        EasyExcel.read(new File(sourcePath), ItemVO.class, itemListener).sheet().doRead();


        List<ItemVO> result = itemListener.getContext();
        EasyExcel.write(new File(resultPath), ItemVO.class).sheet("result").doWrite(result);


    }
}
