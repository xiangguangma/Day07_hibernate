package com.lanou.test;

import com.lanou.domain.IDCard;
import com.lanou.domain.Person;
import com.lanou.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by dllo on 17/10/18.
 */
public class OneToOneTest {
    private Session session;
    private Transaction transaction;

    @Before
    public void init(){
        session = HibernateUtil.getSession();
        transaction = session.beginTransaction();
    }

    @After
    public void destroy(){
        transaction.commit();
        HibernateUtil.closeSession();
    }

    @Test
    public void save(){
        Person person = new Person("洋洋", "123");
        IDCard idCard = new IDCard("520121199407082416");

        person.setIdCard(idCard);
        idCard.setPerson(person);

        /*
            1. 如果只保存idCard, 需要通过设置IDCard.hbm.xml中的cascade级联属性,
                才能级联保存Person对象

            2. 只保存person, 需要通过设置Person.hbm.xml中的cascade级联idCard对象,

            3. 维护外键的一方在不设置cascade级联时, 保存单个对象会抛出异常;
                而引用的一方不会抛出异常, 只不过不保存级联对象
         */
//        session.save(idCard);
        session.save(person);

    }

    @Test
    public void find(){
        // 查询某个人对应的身份证号码
        Person person = session.get(Person.class, 4);

        System.out.println("基础属性: " + person);
        System.out.println("身份证号码: "+ person.getIdCard());

        // 查询某个身份证号所属的用户信息
        IDCard idCard = session.get(IDCard.class, 4);
        System.out.println("基础属性: "+ idCard);
        System.out.println("用户信息: "+ idCard.getPerson());


    }

}
