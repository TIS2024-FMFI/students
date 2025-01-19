package com.tis.dbf.model;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Interruption {

    @XmlElement(name = "Reason")
    private String reason;

    @XmlElement(name = "Start.Date")
    private String startDate;

    @XmlElement(name = "End.Date")
    private String endDate;
}