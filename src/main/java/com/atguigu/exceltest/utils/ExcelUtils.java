package com.atguigu.exceltest.utils;

import com.atguigu.exceltest.pojo.User;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: Excel工具类
 * @Author: xiaopeng
 * @CreateDate: 2017/11/8 21:26
 * @Version: 1.0
 */
public class ExcelUtils {

    // 获取excel文件表格数据的日期类型
    public static Map<String,CellStyle> getExcelCellDataType(SXSSFWorkbook wb){
        // 文件压缩
        wb.setCompressTempFiles(true);

        Map<String,CellStyle> map=new HashMap<>();

        //样式
        //创建样式1（列标识样式）
        CellStyle styleContent = wb.createCellStyle();
        styleContent.setFillBackgroundColor((short)13);//背景色
        styleContent.setFillForegroundColor((short)15);//前景色
        styleContent.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleContent.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        styleContent.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        styleContent.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        styleContent.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        styleContent.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
        styleContent.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        Font fontContent = wb.createFont();
        fontContent.setFontName("宋体");
        fontContent.setBold(true);
        fontContent.setFontHeightInPoints((short)11);//字体大小
        styleContent.setFont(fontContent);
        styleContent.setWrapText(true);//自动换行

        map.put("cell_content",styleContent);

        //创建样式2(数据项样式)
        CellStyle style= wb.createCellStyle();
        style.setFillBackgroundColor(HSSFColor.WHITE.index);//背景色
        style.setFillForegroundColor(HSSFColor.WHITE.index);//前景色
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)11);//字体大小
        style.setFont(font);
        style.setWrapText(true);//自动换行

        map.put("cell_style",style);

        //创建样式3(数据项样式-日期)
        CellStyle style_date = wb.createCellStyle();
        DataFormat format = wb.createDataFormat();
        style_date.setDataFormat(format.getFormat("yyyy-MM-dd"));
        style_date.setFillBackgroundColor(HSSFColor.WHITE.index);//背景色
        style_date.setFillForegroundColor(HSSFColor.WHITE.index);//前景色
        style_date.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style_date.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        style_date.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        style_date.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style_date.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        style_date.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
        style_date.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        Font font_date = wb.createFont();
        font_date.setFontName("宋体");
        font_date.setFontHeightInPoints((short)11);//字体大小
        style_date.setFont(font_date);
        style_date.setWrapText(true);//自动换行

        map.put("cell_data",style_date);

        return  map;
    }

    public static Map<String, Object> buildExcelDocument(List<User> list,String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String,Object> map =new HashMap<>();

        if(list!=null && list.size() > 0){
            //获取分页指标
            int total = list.size();//数据总数
            int mus = 50000;//每个sheet中数据数量
            int avg = (total/mus)+1;//sheet数量

            //第一步：创建Workbook，对应一个excel文件
            SXSSFWorkbook wb = new SXSSFWorkbook(100);//每次缓存100条到内存，其余的写到磁盘中，避免内存溢出

            //第二步：在workbook添加sheet
            for(int m = 0; m < avg; m++){
                SXSSFSheet sheet = (SXSSFSheet) wb.createSheet(fileName+(m+1));

                int sheet_row = 0;
                //设置列宽-（从0开始）
                sheet.setColumnWidth(0, 500);
                sheet.setColumnWidth(1, 500);
                sheet.setColumnWidth(2, 500);
                sheet.setColumnWidth(3, 500);

                //第三步：表头
                SXSSFRow sheet_row1 = (SXSSFRow) sheet.createRow(sheet_row);
                sheet_row1.setHeight((short)400);//设置行高

                SXSSFCell sheetcell11 = (SXSSFCell) sheet_row1.createCell(0);
                sheetcell11.setCellValue("用户编号");
                sheetcell11.setCellStyle(getExcelCellDataType(wb).get("cell_content"));

                SXSSFCell sheetcell12 = (SXSSFCell) sheet_row1.createCell(1);
                sheetcell12.setCellValue("用户名称");
                sheetcell12.setCellStyle(getExcelCellDataType(wb).get("cell_content"));
                SXSSFCell sheetcell13 = (SXSSFCell) sheet_row1.createCell(2);
                sheetcell13.setCellValue("用户年龄");
                sheetcell13.setCellStyle(getExcelCellDataType(wb).get("cell_content"));
                SXSSFCell sheetcell14 = (SXSSFCell) sheet_row1.createCell(3);
                sheetcell14.setCellValue("用户邮箱");
                sheetcell14.setCellStyle(getExcelCellDataType(wb).get("cell_content"));

                sheet_row = sheet_row+1;

                //数据渲染
                User user =null;

                SXSSFRow sheet_rows = null;
                SXSSFCell sheetcell = null;
                for(int n = mus*m; n < mus*(m+1); n++){
                    if(n < total){

                        user = list.get(n);

                        // 将对象的属性读取到excel的表格中,注意要跟上面的表头对应
                        sheet_rows = (SXSSFRow) sheet.createRow(sheet_row);
                        sheet_rows.setHeight((short)400);//设置行高
                        sheetcell = (SXSSFCell) sheet_rows.createCell(0);
                        sheetcell.setCellValue(user.getUserName());
                        sheetcell.setCellStyle(getExcelCellDataType(wb).get("cell_style"));
                        sheetcell = (SXSSFCell) sheet_rows.createCell(1);
                        sheetcell.setCellValue(user.getPassword());
                        sheetcell.setCellStyle(getExcelCellDataType(wb).get("cell_style"));
                        sheetcell = (SXSSFCell) sheet_rows.createCell(2);
                        sheetcell.setCellValue(user.getAge());
                        sheetcell.setCellStyle(getExcelCellDataType(wb).get("cell_style"));
                        sheetcell = (SXSSFCell) sheet_rows.createCell(3);
                        sheetcell.setCellValue(user.getEmail());
                        sheetcell.setCellStyle(getExcelCellDataType(wb).get("cell_style"));

                        // 日期类型
//                        sheetcell = (SXSSFCell) sheet_rows.createCell(4);
//                        if(pct.getContract_date()!=null){
//                            sheetcell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(pct.getContract_date()));
//                        }
//                        sheetcell.setCellStyle(style_date);

                        sheet_row = sheet_row +1;

                        user = null;
                        sheet_rows = null;
                        sheetcell = null;
                    }
                }
            }

            //第四步：保存
            try {
                // 文件下载
                fileName = fileName+".xlsx";
                response.setContentType("applicatin/ms-excel");
                response.setHeader("Content-Disposition",
                        "attachment;filename="+new String(fileName.getBytes("gb2312"),"iso-8859-1"));

                wb.write(response.getOutputStream());

                wb.dispose();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }else{
            map.put("errorMsg", "无数据！");
            return map;
        }
    }
}