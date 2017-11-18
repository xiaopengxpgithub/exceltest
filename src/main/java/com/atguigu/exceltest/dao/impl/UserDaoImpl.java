package com.atguigu.exceltest.dao.impl;

import com.atguigu.exceltest.dao.UserDao;
import com.atguigu.exceltest.pojo.User;
import com.atguigu.exceltest.utils.Page;
import net.sf.ehcache.hibernate.HibernateUtil;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @Description: 用户访问层
 * @Author: xiaopeng
 * @CreateDate: 2017/11/8 21:07
 * @Version: 1.0
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    // 获取与当前线程绑定的session
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<User> findUsers() {
        String hql = "select u from User u";
        return getSession().createQuery(hql).list();
    }

    @Override
    public void insertUsers2() {
        Session sess = null;
        Transaction tx = null;
        try {
            sess = sessionFactory.openSession();
            tx = sess.beginTransaction();

            //执行Work对象指定的操作，即调用Work对象的execute()方法
            //Session会把当前使用的数据库连接传给execute()方法
            sess.doWork(new Work() {
                @Override
                public void execute(Connection connection) throws SQLException {//需要注意的是，不需要调用close()方法关闭这个连接
                    //通过JDBC API执行用于批量插入的sql语句
                    String sql = "insert into tb_user(user_name,password,age,email) values(?,?,?,?)";
                    PreparedStatement ps = connection.prepareStatement(sql);

                    // 插入5万条数据
                    for (int i = 0; i < 50000; i++) {

                        ps.setString(1, "aa" + i);
                        ps.setString(2, "123456");
                        ps.setInt(3, 20);
                        ps.setString(4, "aa" + i + "@163.com");

                        ps.addBatch();

                        if (i % 100 == 0) {
                            ps.executeBatch();
                            connection.commit();
                        }
                    }
                    ps.executeBatch();
                }
            });
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            sess=null;
            sessionFactory=null;
        }
    }


    @Override
    public List<User> findUsersByPage(Page page) {
        String hql="SELECT u FROM User u";

        Query query=getSession().createQuery(hql);
        query.setFirstResult((page.getCurrentPage()-1)*page.getPageSize());
        query.setMaxResults(page.getPageSize());

        return query.list();
    }


    @Override
    public void insertUsers() {

        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();

            tx = session.beginTransaction();

            for (int i = 0; i < 100000; i++) {
                User user = new User();
                user.setUserName("aa" + i);
                user.setPassword("123456");
                user.setEmail("aa" + i + "@163.com");
                user.setAge(22);

                session.save(user);
                if (i % 50 == 0) {

                    session.flush();
                    session.clear();
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
            sessionFactory.close();
        }

    }
}
