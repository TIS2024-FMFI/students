package com.tis.dbf.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

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
}
