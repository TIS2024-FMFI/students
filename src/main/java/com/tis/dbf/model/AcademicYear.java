package com.tis.dbf.model;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class AcademicYear {

    @XmlElement(name = "Registration.Date")
    private String registrationDate;

    @XmlElement(name = "Years")
    private String years;

    @XmlElement(name = "Predmety")
    private Subjects subjects;
}
