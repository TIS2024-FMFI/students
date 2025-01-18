package com.tis.dbf.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
    private String secondName;
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


    private String studyProgramme;
    private String degree;
    private String studyRegistration;
    private String studyStatus;
    private int numOfYears;
    private String studyStartYear;
    private String studyEndYear;
    List<String> academicYears = new ArrayList<>();
    private List<Interruption> interruptions = new ArrayList<>();
    private List<AbroadProgramme> abroadProgrammes = new ArrayList<>();




    @Override
    public String toString() {
        return "Student{" +
                "first name=" + firstName +
                ", second name='" + secondName + '\'' +
                ", birthday day='" + birthDate + '\'' +
                '}';
    }

    public String getFullName() {
        return firstName + " " + secondName;
    }

    public String getStudyProgramDeg() {
        return degree + "." + studyProgramme;
    }

}