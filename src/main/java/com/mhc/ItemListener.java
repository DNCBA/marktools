package com.mhc;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ItemListener extends AnalysisEventListener<ItemVO> {

    private List<ItemVO> context = new ArrayList<ItemVO>();


    private List<TypeNode> subTypeList;
    private List<TypeNode> minTypeList;

    public ItemListener(List<TypeNode> subTypeList, List<TypeNode> minTypeList) {
        this.subTypeList = subTypeList;
        this.minTypeList = minTypeList;
    }

    public void invoke(ItemVO itemVO, AnalysisContext analysisContext) {

        //首先精准匹配
        if (StringUtils.isNotEmpty(itemVO.getTitle())) {
            String title = itemVO.getTitle();
            match(itemVO, title);
        }


        if (StringUtils.isNotEmpty(itemVO.getDesc())) {
            String desc = itemVO.getDesc();
            match(itemVO, desc);
        }


        context.add(itemVO);
    }

    private void match(ItemVO itemVO, String title) {
        for (TypeNode subType : subTypeList) {
            if (title.contains(subType.getTypeName())) {
                itemVO.setSubType(subType.getTypeName());
                break;
            }
        }

        for (TypeNode minType : minTypeList) {
            if (title.contains(minType.getTypeName())) {
                itemVO.setMinType(minType.getTypeName());

                if (StringUtils.isEmpty(itemVO.getSubType())) {
                    itemVO.setSubType(minType.getParent().getTypeName());
                } else {
                    if (itemVO.getSubType() != minType.getParent().getTypeName()) {
                        itemVO.setInfo("minType 与 subType 不符");
                    }
                }

                break;
            }
        }
    }

    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }


    public List<ItemVO> getContext() {
        return context;
    }
}
