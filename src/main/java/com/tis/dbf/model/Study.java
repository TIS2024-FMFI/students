package com.tis.dbf.model;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;


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

    @XmlElementWrapper(name = "Interruptions")
    @XmlElement(name = "Interruption")
    private List<Interruption> interruptions;

    @XmlElementWrapper(name = "Abroad.Programmes")
    @XmlElement(name = "Abroad.Programme")
    private List<AbroadProgramme> abroadProgrammes;

    private String sss ;
    private String studyRegistration;
    private int numOfYears;
    public String getStudyRegistration() {
        if (academicYears != null && !academicYears.isEmpty()) {
            return academicYears.get(0).getRegistrationDate();
        }
        return null;
    }


    public String getStudyStartDate() {
        if (academicYears != null && !academicYears.isEmpty()) {
            return academicYears.get(0).getRegistrationDate();
        }
        return null;
    }

    public String getStudyStartYear() {
        if (academicYears != null && !academicYears.isEmpty()) {
            return academicYears.get(0).getYears();
        }
        return null;
    }

    public String getStudyEndYear() {
        if (academicYears != null && !academicYears.isEmpty()) {
            return academicYears.get(academicYears.size() - 1).getYears();
        }
        return null;
    }

    /**
     * Získa počet akademických rokov.
     */
    public int getNumOfYears() {
        if (academicYears != null) {
            return academicYears.size();
        }
        return 0;
    }



}
