package com.tis.dbf.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name = "Student")
@XmlAccessorType(XmlAccessType.FIELD)
public class Student {
    @XmlElement(name = "id.UPN")
    private String UPN;
    @XmlElement(name = "id.CRS")
    private String CRS;
    @XmlElement(name = "Name.Given")
    private String firstName;
    @XmlElement(name = "Name.Family")
    private String lastName;
    @XmlElement(name = "Birth.Number")
    private String birthNumber;
    @XmlElement(name = "id.Sex")
    private String sex;
    @XmlElement(name = "Birth.Date")
    private String birthDate;
    @XmlElement(name = "Birth.Place")
    private String birthdayPlace;
    @XmlElement(name = "Birth.Country")
    private String birthCountry;
    @XmlElement(name = "Birth.Name")
    private String birthName;


    private String studyProgram;
    private String degree;





    @Override
    public String toString() {
        return "Student{" +
                "first name=" + firstName +
                ", second name='" + lastName + '\'' +
                ", birthday day='" + birthDate + '\'' +
                '}';
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getStudyProgramDeg() {
        return degree + "." + studyProgram;
    }

    public void setStudyProgram(String studyProgram) {
        this.studyProgram = studyProgram;
    }

}