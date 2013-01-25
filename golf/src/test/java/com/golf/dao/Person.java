/**
 * 
 */
package com.golf.dao;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.golf.dao.anno.Column;
import com.golf.dao.anno.Id;
import com.golf.dao.anno.Table;
import com.golf.dao.entity.EntityHelper;
import com.golf.dao.entity.IEntity;

/**
 * @author Thunder.Hsu
 * 
 */
@Table(schema = "golf", name = "Person")
@XmlRootElement(name = "Person")
@XmlAccessorType(XmlAccessType.FIELD)
public class Person implements IEntity {
    @Id
    @Column(columnDefinition = "Long", nullable = false)
    @NotNull(message = "may not be null")
    private String personId;

    @Column(columnDefinition = "VARCHAR", length = 255, nullable = false)
    private String personName;

    @Column(columnDefinition = "TINYINT")
    @Min(value = 18, message = "You have to be 18 to drive a car")
    private int age;

    @Column(columnDefinition = "DATE")
    Date birthDay;

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.golf.dao.entity.IEntity#getHelper()
     */
    @Override
    public EntityHelper getHelper() {
        return new EntityHelper(this);
    }

}
