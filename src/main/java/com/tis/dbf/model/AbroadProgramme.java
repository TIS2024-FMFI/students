package com.tis.dbf.model;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class AbroadProgramme {

    @XmlElement(name = "Start.Date")
    private String startDate;

    @XmlElement(name = "End.Date")
    private String endDate;

    @XmlElement(name = "Semester")
    private String semester;

    @XmlElement(name = "University")
    private String university;

    @XmlElement(name = "Country")
    private String country;
}
