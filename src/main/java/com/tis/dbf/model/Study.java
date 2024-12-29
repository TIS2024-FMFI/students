package com.tis.dbf.model;

import jakarta.xml.bind.annotation.*;
import lombok.Data;
import org.w3c.dom.Element;

import java.util.List;


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

    @XmlElement(name = "Registration.Date")
    private String studyRegistration; // XML Registration.Date

    @XmlElement(name = "Study.Status")
    private String studyStatus; // XML Registration.Date

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
