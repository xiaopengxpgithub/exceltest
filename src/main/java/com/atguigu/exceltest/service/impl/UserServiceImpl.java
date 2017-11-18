package com.atguigu.exceltest.service.impl;

import com.atguigu.exceltest.dao.UserDao;
import com.atguigu.exceltest.pojo.User;
import com.atguigu.exceltest.service.UserService;
import com.atguigu.exceltest.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 用户业务层
 * @Author: xiaopeng
 * @CreateDate: 2017/11/8 21:17
 * @Version: 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findUsers() {
        return userDao.findUsers();
    }

    @Override
    public void insertUsers() {
        //userDao.insertUsers();
        userDao.insertUsers2();
    }

    @Override
    public List<User> findUsersByPage(Page page) {

        return userDao.findUsersByPage(page);
    }
}
