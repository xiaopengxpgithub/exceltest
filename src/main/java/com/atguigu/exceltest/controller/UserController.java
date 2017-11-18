package com.atguigu.exceltest.controller;

import com.atguigu.exceltest.pojo.User;
import com.atguigu.exceltest.service.UserService;
import com.atguigu.exceltest.utils.ExcelUtils;
import com.atguigu.exceltest.utils.ExcelUtils2;
import com.atguigu.exceltest.utils.Page;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @Description: 用户控制层
 * @Author: xiaopeng
 * @CreateDate: 2017/11/8 21:22
 * @Version: 1.0
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 数据导出
     * http://www.jianshu.com/p/4e873e66ee73
     */
    @RequestMapping(value = "/excelExport")
    public void excelExport(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 分页查询
        Page page=new Page();
        page.setCurrentPage(1);//设置第几页
        page.setPageSize(100000);////每页显示多少条数据
        List<User> users=userService.findUsersByPage(page);

        ExcelUtils2.excelExport(users,"用户信息",request,response);
    }


    /**
     * 数据导出
     *
     * @param response
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "/buildExcel")
    public void buildExcelFile(HttpServletResponse response, HttpServletRequest request) throws Exception {
        List<User> list = userService.findUsers();
        String excelName = "用户信息";
        ExcelUtils.buildExcelDocument(list, excelName,request, response);
    }

    /**
     * 插入数据
     */
    @RequestMapping(value = "/insertUsers")
    public void insertUsers() {
        userService.insertUsers();
    }
}
