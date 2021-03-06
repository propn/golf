package com.golf.dao.trans;

import java.util.List;

import org.junit.Test;

import com.golf.dao.Person;
import com.golf.dao.trans.Trans;

public class TransTest4 {

    @Test
    public void T0() throws Exception {

        final Person p = new Person();
        p.setAge(28);
        p.setPersonId("1");
        p.setPersonName("徐雷");

        System.out.println("delete begin ");
        Trans.transNew(new Trans() {
            @Override
            public Object call() throws Exception {
                p.getHelper().delete();
                return null;
            }
        });
        System.out.println("delete end ");
        System.out.println();

        try {
            System.out.println("insert 徐雷  begin");
            Trans.transNew(new Trans() {
                @Override
                public Object call() throws Exception {
                    p.getHelper().save();
                    System.out.println("------------------------------------nest");
                    System.out.println("update 东升 begin ");
                    try {
                        Trans.transNest(new Trans() {
                            @Override
                            public Object call() throws Exception {
                                p.setPersonName("东升");
                                p.getHelper().update();
                                // return null;
                                throw new Exception();
                            }
                        });
                    } catch (Exception e) {
                    }
                    System.out.println("update 东升 end");
                    System.out.println("------------------------------------nest");
                    return null;
                }
            });
            System.out.println("insert 徐雷  end");
        } catch (Exception e) {
        }

        System.out.println();
        System.out.println("查询 ");
        List<Person> list = (List<Person>) Trans.transNew(new Trans() {
            @Override
            public Object call() throws Exception {
                return p.getHelper().qryList();
            }
        });
        for (Person person : list) {
            System.out.println(person.getPersonName());
        }

    }

}
