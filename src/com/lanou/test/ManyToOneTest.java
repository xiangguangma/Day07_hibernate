package com.lanou.test;

import com.lanou.domain.Customer;
import com.lanou.domain.Order;
import com.lanou.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.util.Date;

/**
 * Created by dllo on 17/10/18.
 */
public class ManyToOneTest {

    @Test
    public void single(){
        // 先要创建用户对象
        Customer customer = new Customer("王五", "wangwu", "123", "男");

        // 创建一个订单
        Order order = new Order("L002", 18880, new Date());

        // 绑定订单所属的用户
        order.setCustomer(customer);

        // 保存用户以及订单对象
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        // 单向n-1,在进行n 的插入时需要确定1已经保存到数据库, 即1是一个持久化对象
        // 此示例中指的是插入订单时需要确保所属用户已经存在

        session.save(customer);  //保存用户
        session.save(order);  // 保存订单


        transaction.commit();
        HibernateUtil.closeSession();

    }

    @Test
    public void singleFind(){
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        // 查找主键1 的订单对象
        Order order = session.get(Order.class, 1);

        System.out.println("订单的基础属性: "+ order);
//        System.out.println("订单所属用户的属性: " + order.getCustomer());

        transaction.commit();
        HibernateUtil.closeSession();
    }

    /**
     *  双向 1-n 中的插入
     */
    @Test
    public void doubleInsert(){
        // 创建用户对象和订单对象
        Customer customer = new Customer("杜拉拉", "dulala", "123","女");
        Order order = new Order("L003", 19980, new Date());

        // 绑定订单所属用户
        order.setCustomer(customer);

        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        // 保存用户与订单
        session.save(customer);
        session.save(order);

        transaction.commit();
        HibernateUtil.closeSession();

    }


    /**
     *  双向 1-n 查询用户下的所有订单集合
     */
    @Test
    public void doubleFind(){
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        // 查询主键id 是2 的用户对象
        Customer customer = session.get(Customer.class, 2);
        System.out.println("基础信息: "+customer);
        System.out.println("用户订单集合: " + customer.getOrders());

        transaction.commit();
        HibernateUtil.closeSession();
    }
}
