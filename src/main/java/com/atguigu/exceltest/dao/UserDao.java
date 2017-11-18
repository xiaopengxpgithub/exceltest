package com.atguigu.exceltest.dao;


import com.atguigu.exceltest.pojo.User;
import com.atguigu.exceltest.utils.Page;

import java.util.List;

public interface UserDao {

    public List<User> findUsers();

    public void insertUsers();

    public void  insertUsers2();

    List<User> findUsersByPage(Page page);
}
