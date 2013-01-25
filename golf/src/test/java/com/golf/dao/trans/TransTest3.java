package com.golf.dao.trans;

import java.util.List;

import org.junit.Test;

import com.golf.dao.Person;
import com.golf.dao.entity.EntityHelper;
import com.golf.dao.trans.Trans;

public class TransTest3 {

    @Test
    public void T3() {

        final Person p = new Person();
        try {
            p.getHelper().set("personId", "1");
            p.getHelper().set("personName", "徐雷");
            p.getHelper().set("age", 28);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        
        System.out.println("------------------删除---------------------------");
        try {
            Trans.transNew(new Trans() {
                @Override
                public Object call() throws Exception {
                    p.getHelper().delete();
                    return null;
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("-------------------新增--------------------------");
        try {
            Trans.transNew(new Trans() {
                @Override
                public Object call() throws Exception {
                    p.getHelper().save();
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("------------------查询---------------------------");
        try {
            List<EntityHelper> ps = (List<EntityHelper>) Trans.transNew(new Trans() {
                @Override
                public Object call() throws Exception {
                    return p.getHelper().qryList();
                }
            });
            for (EntityHelper po : ps) {
                System.out.println(po.get("personName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
