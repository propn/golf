package com.golf.dao;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.golf.dao.anno.Column;
import com.golf.dao.anno.Table;

@Table(name = "Student")
@XmlRootElement(name = "Student")
@XmlAccessorType(XmlAccessType.FIELD)
public class Student extends Person {
    @Column(name = "grade")
    int grade;
    @Column(name = "major")
    String major;

    @XmlElement(name = "Counselor")
    Person Counselor;

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Person getCounselor() {
        return Counselor;
    }

    public void setCounselor(Person counselor) {
        Counselor = counselor;
    }
    
    
}
