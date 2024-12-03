package com.tis.dbf.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@XmlRootElement(name = "Students")
@XmlAccessorType(XmlAccessType.FIELD)
public class Students {

    @XmlElement(name = "Student")
    public List<Student> students;
}
