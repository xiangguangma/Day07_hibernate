package com.lanou.test;

import com.lanou.domain.Customer;
import com.lanou.domain.Order;
import com.lanou.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * Created by dllo on 17/10/18.
 */
public class CascadeTest {

    // cascade级联单元测试

    /**
     * 测试级联中的更新操作
     * 即修改用户的相关信息时, 查询该用户名下的订单对象, 检查订单中的用户属性是否随之修改
     */
    @Test
    public void cascadeUpdate(){
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        //获得要修改的用户对象
        Customer customer = session.get(Customer.class, 1);
        // 修改用户信息
        customer.setName("王小二");
        System.out.println("修改后: "+customer);

        // 获得用户id为2 的订单, 该订单的id为1
        Order order = session.get(Order.class, 1);

        // 获得订单的所属用户
        System.out.println("通过订单找用户: " + order.getCustomer());

        transaction.commit();
        HibernateUtil.closeSession();

    }

    @Test
    public void cascadeSave(){
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        // 创建订单和用户
        Customer customer = new Customer("李白", "libai", "121", "男");
        Order order = new Order("L004", 19880, new Date());

        // 将订单对象添加到用户中的订单集合中
        customer.getOrders().add(order);

        order.setCustomer(customer);  // 给订单绑定用户

//        session.save(customer);  // 保存用户对象

        session.save(order);

        transaction.commit();
        HibernateUtil.closeSession();
    }

    /**
     *  删除用户时, 将该用户下的所有订单删除
     */
    @Test
    public void cascadeDelete(){
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        // 获得要删除的用户, 主键id 为10
        Customer customer = session.get(Customer.class, 10);

//        // 需要向删除该用户下的所有订单, 将这些都删除了, 该用户才能删除
//        Query query = session.createQuery("from Order where customer=?");
//        query.setEntity(0, customer);
//
//        List<Order> orderList = query.list();
//        System.out.println(orderList);
//
//        for (Order order : orderList) {
//            session.delete(order); // 删除订单
//        }

        session.delete(customer); // 删除用户

        transaction.commit();
        HibernateUtil.closeSession();
    }


    /**
     *  删除某个订单
     */
    @Test
    public void deleteOrder(){
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        // 获得要删除的订单
        Order order = session.get(Order.class, 1);
        session.delete(order);  // 删除订单

        transaction.commit();
        HibernateUtil.closeSession();
    }

}
