package com.golf.ioc;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.golf.Golf;
import com.golf.dao.Person;
import com.golf.dao.Student;
import com.golf.ioc.BeanUtils;

public class BeanUtilsTest {
    
    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        Golf.init("com.glof");
    }

    @Test
    public void testRegistBeanStringArray() throws IOException, ClassNotFoundException, Exception {
        Di di = BeanUtils.getInstance(Di.class);
        di.ipaa.sayHello();
        di.ipab.sayHello();
    }

    @Test
    public void testRegistBeanSetOfClassOfQ() {
    }

    @Test
    public void testGetBean() {
        Class<? extends Person> a = Student.class.asSubclass(Person.class);
        Ipab.class.asSubclass(Ipa.class);
        Ipab.class.asSubclass(Ip.class);
        Ipa.class.asSubclass(Ip.class);

        System.out.println(Ipa.class.isAssignableFrom(Ipab.class));
        System.out.println(Ipa.class.isAssignableFrom(Ipab.class));
        System.out.println(Ip.class.isAssignableFrom(Ipab.class));

    }

}
