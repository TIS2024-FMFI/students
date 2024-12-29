package com.tis.dbf.model;

import jakarta.xml.bind.annotation.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Element;

import java.util.List;
import java.util.Map;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlTransient;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

@Data
@XmlRootElement(name = "Study")
@XmlAccessorType(XmlAccessType.FIELD)
public class Study {
    @XmlElement(name = "id.UPN")
    private String UPN;

    @XmlElement(name = "Study.Programme")
    private String studyProgramme;

    @XmlElement(name = "Degree")
    private String degree;

//    @XmlElement(name = "Registration.Date")
//    private String registrationDate;


    @XmlElement(name = "Study.Status")
    private String studyStatus; // XML Registration.Date

    @XmlElementWrapper(name = "Academic.Years")
    @XmlElement(name = "AcademicYear")
    private List<AcademicYear> academicYears;

    private String sss ;
    private String studyRegistration;
    public String getStudyRegistration() {
        if (academicYears != null && !academicYears.isEmpty()) {
            return academicYears.get(0).getRegistrationDate();
        }
        return null;
    }
    public void setStudyRegistration() {
        if (academicYears != null && !academicYears.isEmpty()) {
            this.studyRegistration = academicYears.get(0).getRegistrationDate();
        }
    }
    //    // Pridáme nový atribút pre dátum začiatku štúdia
//    private String startDate;
//
//    public void setStartDate(String startDate) {
//        this.startDate = startDate;
//    }
//
//    public String getStartDate() {
//        return startDate;
//    }

}
