package com.golf.dao;

import com.golf.dao.entity.IEntity;
import com.golf.dao.trans.Trans;

public class PersonTest {

    public static void main(String[] args) throws Exception {

        Person p = new Person();
        p.getHelper().set("personName", "Thunder");
        p.getHelper().set("age", 28);

        System.out.println(p.getHelper().get("personName"));
        System.out.println(p.getHelper().get("age"));

        //
        IEntity s = new Student();
        s.getHelper().set("personName", "Thunder.hsu");
        s.getHelper().set("age", 28);
        //
        s.getHelper().set("grade", 2003);
        s.getHelper().set("major", "计算机");
        s.getHelper().set("Counselor", p);

        System.out.println(s.getHelper().get("personName"));
        System.out.println(s.getHelper().get("age"));

        System.out.println(s.getHelper().get("grade"));
        System.out.println(s.getHelper().get("major"));

        final Person t = (Person) s.getHelper().get("Counselor");

        if (p.equals(t)) {
            System.out.println("1212");
            System.out.println(p.getHelper().get("personName"));
        }

        System.out.println("currentThread :" + Thread.currentThread().getId());

        Trans.transNew(new Trans() {
            @Override
            public Object call() throws Exception {
                t.getHelper().save();
                return null;
            }
        });
    }
}
