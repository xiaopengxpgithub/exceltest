package com.atguigu.exceltest.utils;

import com.atguigu.exceltest.pojo.User;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @Description: excel数据导出工具类
 * @Author: xiaopeng
 * @CreateDate: 2017/11/9 18:37
 * @Version: 1.0
 */
public class ExcelUtils2 {

    public static void excelExport(List<User> users,String fileName, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {

        SXSSFWorkbook workbook=new SXSSFWorkbook(100);
        workbook.setCompressTempFiles(true);

        try {
            response.reset();

            response.setContentType("APPLICATION/vnd.ms-excel;charset=UTF-8");

            // 文件下载
            response.setHeader(
                    "Content-Disposition",
                    "attachment; filename="
                            + java.net.URLEncoder.encode(
                            "用户信息" + ".xlsx", "UTF-8"));//Excel 扩展名指定为xlsx  SXSSFWorkbook对象只支持xlsx格式

            OutputStream os = response.getOutputStream();

            CellStyle style = workbook.createCellStyle();

            // 设置样式
            style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);//设置单元格着色
            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  //设置单元格填充样式
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);//设置下边框
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//设置左边框
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);//设置右边框
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中

            try {
                int page_size = 100000;// 定义每页数据数量(excel2007最大支持10万条数据)
                int list_count = users.size();

                //总数量除以每页显示条数等于页数
                int export_times = list_count % page_size > 0 ? list_count / page_size
                        + 1 : list_count / page_size;
                //循环获取产生每页数据
                for (int m = 0; m < export_times; m++) {
                    //新建sheet
                    Sheet sheet = null;

                    sheet = workbook.createSheet(fileName+(m+1));

                    // 创建属于上面Sheet的Row，参数0可以是0～65535之间的任何一个，

                    Row header = sheet.createRow(0); // 第0行
                    // 产生标题列，每个sheet页产生一个标题
                    Cell cell;

                    // 对象属性,用来生成excel文件头
                    String[] headerArr = new String[]{"编号", "用户名称", "密码", "年龄", "邮箱"};

                    for (int j = 0; j < headerArr.length; j++) {
                        cell = header.createCell((short) j);
                        cell.setCellStyle(style);
                        cell.setCellValue(headerArr[j]);
                    }

                    // 迭代数据
                    if (users != null && users.size() > 0) {
                        int rowNum = 1;
                        for (int i = 0; i < users.size(); i++) {
                            User user = users.get(i);

                            sheet.setDefaultColumnWidth((short) 17);

                            // excel文件中每一行的列的值,注意要跟上面列头相对应
                            Row row = sheet.createRow(rowNum++);
                            row.createCell((short) 0).setCellValue(
                                    user.getId());

                            row.createCell((short) 1).setCellValue(
                                    user.getUserName());

                            row.createCell((short) 2)
                                    .setCellValue(user.getPassword());

                            row.createCell((short) 3).setCellValue(
                                    user.getAge());

                            if (user.getEmail() != null) {
                                row.createCell((short) 4).setCellValue(
                                        user.getEmail());
                            } else {
                                row.createCell((short) 4).setCellValue("");
                            }


//                            if (history.getTrainEndTime() != null) {
//                                row.createCell((short) 4).setCellValue(
//                                        DateUtil.toString(history.getTrainEndTime()));
//                            } else {
//                                row.createCell((short) 4).setCellValue("");
//                            }
                        }
                    }

                    users.clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                workbook.write(os);
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
