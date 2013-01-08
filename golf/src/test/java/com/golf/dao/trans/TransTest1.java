package com.golf.dao.trans;

import java.util.List;

import org.junit.Test;

import com.golf.dao.Person;
import com.golf.dao.entity.Entity;
import com.golf.dao.trans.Trans;

public class TransTest1 {

    @Test
    public void T2() {

        final Person p = new Person();
        try {
            p.set("personId", "1");
            p.set("personName", "徐雷");
            p.set("age", 28);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        System.out.println("-----------------------1----------------------");
        try {
            Trans.transNew(new Trans() {
                @Override
                public Object call() throws Exception {
                    p.delete();
                    return null;
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("----------------------1-----------------------");
        try {
            Trans.transNew(new Trans() {
                @Override
                public Object call() throws Exception {
                    p.save();
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("---------------------1------------------------");
        try {
            Trans.transNew(new Trans() {
                @Override
                public Object call() throws Exception {
                    p.set("personName", "东升");
                    p.update();
                    return null;
                    // throw new Exception();
                }
            });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("----------------------1-----------------------");
        try {
            List<Entity> ps = (List<Entity>) Trans.transNew(new Trans() {
                @Override
                public Object call() throws Exception {
                    return p.qryList();
                }
            });
            for (Entity po : ps) {
                System.out.println(po.get("personName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
