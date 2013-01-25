package com.golf.mvc;

import javax.xml.bind.JAXBException;

import com.golf.dao.Person;
import com.golf.dao.Student;
import com.golf.dao.Teacher;
import com.golf.utils.JaxbUtils;
import com.golf.utils.json.Json;

public class XmlViewTest {

    public static void main(String[] args) throws Exception {
        try {

            Person teacher = new Person();
            teacher.getHelper().set("personId", "03023001");
            teacher.getHelper().set("personName", "常继科");
            teacher.getHelper().set("age", 28);

            Student st = new Student();
            st.getHelper().set("grade", 2003);
            st.getHelper().set("major", "computer");
            st.getHelper().set("Counselor", teacher);
            st.getHelper().set("personId", "03023152");
            st.getHelper().set("personName", "徐雷");
            st.getHelper().set("age", 18);
            // marshaller.marshal(st, System.out);
            System.out.println(JaxbUtils.toXml(st));

            Teacher t = new Teacher();
            t.getHelper().set("personId", "03023001");
            t.getHelper().set("personName", "333继科");
            t.getHelper().set("age", 43);
            t.getHelper().set("hight", 12);

            System.out.println(JaxbUtils.toXml(t));

            System.out.println(Json.toJson(t));
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
