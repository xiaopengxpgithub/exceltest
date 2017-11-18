package com.atguigu.exceltest.pojo;

import javax.persistence.*;

/**
 * @Description: 用户实体类对象
 * @Author: xiaopeng
 * @CreateDate: 2017/11/8 20:55
 * @Version: 1.0
 */
@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name",length = 20,nullable = false)
    private String userName;

    @Column(name = "password",length = 20,nullable = false)
    private String password;

    @Column(name = "age",length = 3,nullable = false)
    private int age;

    @Column(name = "email",length = 20)
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User() {
    }
}
