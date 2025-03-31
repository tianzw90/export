package com.example.gzs.export.demo;

import com.example.gzs.export.business.entity.ExportDemo;
import com.example.gzs.export.utils.ExportUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demo {
    @Value("exportDocx.imgsPath")
    private static String imgsPath;
    @Value("exportDocx.templatePath")
    private static String docxTemplatePath;

    public static void main(String [] args) {
        // 组装业务数据
        Map<String, Object> map = assembleBusinessDataNesting();
        // 组装表格名称数据集合
        List<String> tableNameList = new ArrayList<>();
        tableNameList.add("tableList");
        // 组装图片名称以及长高数据集合
        List<Map<String, Object>> imgList = new ArrayList<>();
        Map<String, Object> img = new HashMap<>();
        img.put("imgName", "img");
        img.put("imgLength", 300);
        img.put("imgHeight", 300);
        imgList.add(img);
        // 调用导出word
        try {
            ExportUtils.exportWordToFile( "src/main/resources/templates/wordTemplateNesting.docx", "wordDemo", "D:/", map, tableNameList, imgList);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 组装业务数据 支持换行符  \n
     * */
    public static Map<String, Object> assembleBusinessData() {
        Map<String, Object> map = new HashMap<>();
        // 文档总标题
        map.put("title", "测试总标题文档");
        // 段落
        List<Map<String, Object>> paragraphList = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            Map<String, Object> paragraphMap = new HashMap<>();
            String paragraphTitle = "标题" + i;
            String paragraphContent = "内容" + i;
            paragraphMap.put("paragraphTitle", paragraphTitle);
            paragraphMap.put("paragraphContent", paragraphContent);
            paragraphList.add(paragraphMap);
        }
        map.put("paragraphList", paragraphList);
        // 表格
        List<Map<String, Object>> tableList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Map<String, Object> tableMap = new HashMap<>();
            String tableContent1 = "tableContent1";
            String tableContent2 = "tableContent2";
            String tableContent3 = "tableContent3";
            String tableContent4 = "tableContent4";
            String tableContent5 = "tableContent5";
            tableMap.put("tableContent1", tableContent1);
            tableMap.put("tableContent2", tableContent2);
            tableMap.put("tableContent3", tableContent3);
            tableMap.put("tableContent4", tableContent4);
            tableMap.put("tableContent5", tableContent5);
            tableList.add(tableMap);
        }
        map.put("tableList", tableList);
        // 图片
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/imgs" + "/hsw.png");
            map.put("img", fileInputStream);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 组装业务数据 支持换行符  \n
     * 嵌套段落
     * */
    public static Map<String, Object> assembleBusinessDataNesting() {
        Map<String, Object> map = new HashMap<>();
        // 文档总标题
        map.put("title", "测试总标题文档");
        // 段落
        List<Map<String, Object>> paragraphList = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            Map<String, Object> paragraphMap = new HashMap<>();
            String paragraphTitle = "标题" + i;
            String paragraphContent = "内容" + i;
            paragraphMap.put("paragraphTitle", paragraphTitle);
            // 嵌套段落
            List<Map<String, Object>> paragraphChildList = new ArrayList<>();
            for (int j = 0; j < 2; j ++) {
                Map<String, Object> paragraphChildMap = new HashMap<>();
                paragraphChildMap.put("paragraphChildTitle", paragraphTitle);
                paragraphChildMap.put("paragraphChildContent", paragraphContent);
                paragraphChildList.add(paragraphChildMap);
            }
            paragraphMap.put("paragraphChildList", paragraphChildList);
            paragraphList.add(paragraphMap);
        }
        map.put("paragraphList", paragraphList);
        // 表格
        List<Map<String, Object>> tableList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Map<String, Object> tableMap = new HashMap<>();
            String tableContent1 = "tableContent1";
            String tableContent2 = "tableContent2";
            String tableContent3 = "tableContent3";
            String tableContent4 = "tableContent4";
            String tableContent5 = "tableContent5";
            tableMap.put("tableContent1", tableContent1);
            tableMap.put("tableContent2", tableContent2);
            tableMap.put("tableContent3", tableContent3);
            tableMap.put("tableContent4", tableContent4);
            tableMap.put("tableContent5", tableContent5);
            tableList.add(tableMap);
        }
        map.put("tableList", tableList);
        // 图片
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/imgs" + "/hsw.png");
            map.put("img", fileInputStream);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }


}
