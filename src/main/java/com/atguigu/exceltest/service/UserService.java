package com.atguigu.exceltest.service;

import com.atguigu.exceltest.pojo.User;
import com.atguigu.exceltest.utils.Page;

import java.util.List;

public interface UserService {

    public List<User> findUsers();

    public void insertUsers();

    List<User> findUsersByPage(Page page);
}
