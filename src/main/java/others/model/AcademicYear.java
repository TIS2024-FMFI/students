package others.model;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class AcademicYear {

    @XmlElement(name = "Registration.Date")
    private String registrationDate;

    @XmlElement(name = "Years")
    private String years;

    @XmlElement(name = "Subjects")
    private StudySubjects studySubjects;
}
