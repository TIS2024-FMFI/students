package com.tis.dbf.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@XmlRootElement(name = "Studies")
@XmlAccessorType(XmlAccessType.FIELD)
public class Studies {
    @XmlElement(name = "Study")
    public List<Study> studies;
}
