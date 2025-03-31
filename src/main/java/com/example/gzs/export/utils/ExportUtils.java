package com.example.gzs.export.utils;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.data.PictureType;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExportUtils {

    @Resource

    private static HttpServletResponse response;

    /**
     * templatePath--导出模板地址
     * fileName--要导出的文件名
     * map--业务数据
     * tableList--表格名称集合，List<String>类型，用于存放要导出的表格在map中的key名称，有几个表格写几个
     * imgList--图片集合，List<Map>类型，用于存放要导出的图片在map中的key名称，以及要导出图片的长和高参数，有几个图片写几个。名称为：imgName、图片长度(不写默认300)：imgLength、图片高度(不写默认300)：imgHeight。示例[{"imgName": "ebImg", "imgLength": 300, "imgHeight": 300},..]
     * */
    public static void exportWord(String templatePath, String fileName, Map<String, Object> map, List<String> tableNameList, List<Map<String, Object>> imgList) throws IOException {
        // 如果有表格  需要引用插件
        if (tableNameList.size() > 0) {
            LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
            ConfigureBuilder configureBuilder = Configure.builder();
            for (String table: tableNameList) {
                configureBuilder = configureBuilder.bind(table, policy);
            }
        }
        // 如果有照片 调用Pictures工厂构建图片模型  默认300*300
        if (imgList.size() > 0) {
            for (Map<String, Object> imgMap: imgList) {
                if (ObjectUtils.isEmpty(imgMap.get("imgName"))) {
                    throw new BadRequestException("没有图片名，导出失败。");
                }
                String imgName = imgMap.get("imgName").toString();
                Integer imgLength = 300;
                if (ObjectUtils.isNotEmpty(imgMap.get("imgLength"))) {
                    imgLength = (Integer) imgMap.get("imgLength");
                }
                Integer imgHeight = 300;
                if (ObjectUtils.isNotEmpty(imgMap.get("imgHeight"))) {
                    imgHeight = (Integer) imgMap.get("imgHeight");
                }
                FileInputStream imgInputStream = (FileInputStream) map.get(imgName);
                map.put(imgName, Pictures.ofStream(imgInputStream).size(imgLength, imgHeight).create());
            }
        }
        // 导出
        XWPFTemplate xwpfTemplate = null;
        OutputStream os = response.getOutputStream();
        try {
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");
            String date = DateFormatUtils.format(new Date(), "YYYY-dd-MM hh:ss");
            response.setHeader("Content-Disposition", "attachment;filename="+new String((date + "-" + fileName).getBytes("gb2312"), "iso8859-1")+".docx");
            xwpfTemplate = XWPFTemplate.compile(templatePath).render(map);
            xwpfTemplate.write(os);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            os.close();
        } finally {
            os.close();
        }
    }

    public static void exportWordToFile(String templatePath, String fileName, String filePath, Map<String, Object> map, List<String> tableNameList, List<Map<String, Object>> imgList) throws IOException {
        System.out.println("传来的参数，map：" + map);
        // 如果有表格  需要引用插件
        ConfigureBuilder configureBuilder = null;
        if (tableNameList.size() > 0) {
            LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
            configureBuilder = Configure.builder();
            for (String table: tableNameList) {
                configureBuilder = configureBuilder.bind(table, policy);
            }
        }
        // 如果有照片 调用Pictures工厂构建图片模型  默认300*300
        if (imgList.size() > 0) {
            for (Map<String, Object> imgMap: imgList) {
                if (ObjectUtils.isEmpty(imgMap.get("imgName"))) {
                    throw new BadRequestException("没有图片名，导出失败。");
                }
                String imgName = imgMap.get("imgName").toString();
                Integer imgLength = 300;
                if (ObjectUtils.isNotEmpty(imgMap.get("imgLength"))) {
                    imgLength = (Integer) imgMap.get("imgLength");
                }
                Integer imgHeight = 300;
                if (ObjectUtils.isNotEmpty(imgMap.get("imgHeight"))) {
                    imgHeight = (Integer) imgMap.get("imgHeight");
                }
                FileInputStream imgInputStream = (FileInputStream) map.get(imgName);
                map.put(imgName, Pictures.ofStream(imgInputStream, PictureType.PNG).size(imgLength, imgHeight).create());
            }
        }
        // 导出
        XWPFTemplate xwpfTemplate = null;
        try {
            String date = DateFormatUtils.format(new Date(), "YYYYddMM_hhss");
            assert configureBuilder != null;
            xwpfTemplate = XWPFTemplate.compile(templatePath, configureBuilder.build()).render(map);
            xwpfTemplate.write(new FileOutputStream(filePath + date + "-" + fileName + ".docx"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }


}
