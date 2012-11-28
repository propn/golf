package com.golf.mvc;

import javax.xml.bind.JAXBException;

import com.golf.dao.Person;
import com.golf.dao.Student;
import com.golf.dao.Teacher;
import com.golf.tools.JsonUtils;
import com.golf.tools.XmlUtils;

public class XmlViewTest {

    public static void main(String[] args) throws Exception {
        try {

            Person teacher = new Person();
            teacher.set("personId", "03023001");
            teacher.set("personName", "常继科");
            teacher.set("age", 28);

            Student st = new Student();
            st.set("grade", 2003);
            st.set("major", "computer");
            st.set("Counselor", teacher);
            st.set("personId", "03023152");
            st.set("personName", "徐雷");
            st.set("age", 18);
            // marshaller.marshal(st, System.out);
            System.out.println(JaxbUtils.toXml(st));

            Teacher t = new Teacher();
            t.set("personId", "03023001");
            t.set("personName", "333继科");
            t.set("age", 43);
            t.set("hight", 12);

            System.out.println(JaxbUtils.toXml(t));

            System.out.println(JsonUtils.toJson(st));
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
